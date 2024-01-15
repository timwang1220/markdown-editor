package com.timwang.command;

import java.util.ArrayList;
import java.util.Stack;

import com.timwang.markdown.MarkdownFile;
import com.timwang.observer.Observer;
import com.timwang.observer.StatObserver;
import com.timwang.workspace.WorkSpace;
import com.timwang.workspace.WorkSpaceManager;

public class CommandExecutor {
    private Stack<OperatingCommand> undoStack;
    private Stack<OperatingCommand> redoStack;
    private ArrayList<Observer> observers;
    private static CommandExecutor commandExecutor;
    private CommandExecutor(Stack<OperatingCommand> undoStack, Stack<OperatingCommand> redoStack){
        this.undoStack = undoStack;
        this.redoStack = redoStack;
        observers = new ArrayList<Observer>();
        observers.add(new StatObserver());
    }
    private void notifyObservers(Command command){
        for (Observer observer : observers) {
            observer.update(command);
        }
    }
    public static void clear(){
        commandExecutor = null;
    }
    public static CommandExecutor getInstance(){
        if (commandExecutor == null) {
            if (WorkSpaceManager.getActiveWorkSpace() == null) {
                commandExecutor = new CommandExecutor(null, null);
            }
            else commandExecutor = new CommandExecutor(WorkSpaceManager.getActiveWorkSpace().getUndoStack(), WorkSpaceManager.getActiveWorkSpace().getRedoStack());
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
        if (command instanceof LoadCommand || command instanceof OpenCommand){
            // load command will change the workspace
            undoStack = WorkSpaceManager.getActiveWorkSpace().getUndoStack();
            redoStack = WorkSpaceManager.getActiveWorkSpace().getRedoStack();
        }
        if (command instanceof CloseCommand){
            undoStack = new Stack<OperatingCommand>();
            redoStack = new Stack<OperatingCommand>();
        }
        if (command instanceof SaveCommand){
            operatingFile.setClean();
        }

        notifyObservers(command);
        if (command instanceof ExitCommand){
            System.exit(0);
        }
    }
}
