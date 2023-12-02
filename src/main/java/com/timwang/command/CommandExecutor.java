package com.timwang.command;

import java.util.ArrayList;
import java.util.Stack;

import com.timwang.markdown.MarkdownFile;
import com.timwang.observer.Observer;
import com.timwang.workspace.WorkSpace;
import com.timwang.workspace.WorkSpaceManager;

public class CommandExecutor {
    private Stack<OperatingCommand> undoStack;
    private Stack<OperatingCommand> redoStack;
    private ArrayList<Observer> observers;
    private static CommandExecutor commandExecutor;
    private CommandExecutor(){
        undoStack = new Stack<OperatingCommand>();
        redoStack = new Stack<OperatingCommand>();
        observers = new ArrayList<Observer>();
    }
    private void notifyObservers(Command command){
        for (Observer observer : observers) {
            observer.update(command);
        }
    }
    public static CommandExecutor getInstance(){
        if (commandExecutor == null) {
            commandExecutor = new CommandExecutor();
        }
        return commandExecutor;
    }
    public void execute(Command command) throws Exception{
        WorkSpace workSpace = WorkSpaceManager.getActiveWorkSpace();
        MarkdownFile operatingFile = (workSpace == null) ? null : workSpace.getMarkdownFile();
        command.execute();
        if (command instanceof OperatingCommand) {
            operatingFile.setDirty();
            undoStack.push((OperatingCommand)command);
            redoStack.clear();
        }
        if (command instanceof UndoCommand){
            if (undoStack.isEmpty()) {
                throw new Exception("Ilegal Undo: No command to undo");
            }
            OperatingCommand operatingCommand = undoStack.pop();
            operatingCommand.undo();
            redoStack.push(operatingCommand);
            if (undoStack.isEmpty()) {
                operatingFile.setClean();
            }
        }
        if (command instanceof RedoCommand){
            if (redoStack.isEmpty()) {
                throw new Exception("Ilegal Redo: No command to redo");
            }
            OperatingCommand operatingCommand = redoStack.pop();
            operatingCommand.redo();
            undoStack.push(operatingCommand);
            operatingFile.setDirty();
        }
        if (command instanceof LoadCommand){
            // load command will change the workspace
            undoStack = WorkSpaceManager.getActiveWorkSpace().getUndoStack();
            redoStack = WorkSpaceManager.getActiveWorkSpace().getRedoStack();
        }
        if (command instanceof SaveCommand){
            operatingFile.setClean();
        }
        notifyObservers(command);
    }
}
