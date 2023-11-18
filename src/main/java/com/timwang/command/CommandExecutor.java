package com.timwang.command;

import java.util.Stack;

public class CommandExecutor {
    static Stack<OperatingCommand> undoStack;
    static Stack<OperatingCommand> redoStack;
    private static CommandExecutor commandExecutor;
    private CommandExecutor(){
        undoStack = new Stack<OperatingCommand>();
        redoStack = new Stack<OperatingCommand>();
    }
    public static CommandExecutor getInstance(){
        if (commandExecutor == null) {
            commandExecutor = new CommandExecutor();
        }
        return commandExecutor;
    }
    public void execute(Command command) throws Exception{
        command.execute();
        command.maintainStack();
        if (command instanceof OperatingCommand) {
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
        }
        if (command instanceof RedoCommand){
            if (redoStack.isEmpty()) {
                throw new Exception("Ilegal Redo: No command to redo");
            }
            OperatingCommand operatingCommand = redoStack.pop();
            operatingCommand.redo();
            undoStack.push(operatingCommand);
        }
        if (command instanceof LoadCommand || command instanceof SaveCommand){
            undoStack.clear();
            redoStack.clear();
        }
    }
}
