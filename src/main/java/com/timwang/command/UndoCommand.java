package com.timwang.command;

public class UndoCommand extends RecoverCommand{
    public UndoCommand(String args){
        super();
    }
    public void maintainStack() throws Exception{
        if (CommandExecutor.undoStack.isEmpty()) {
                    throw new Exception("Ilegal Undo: No command to undo");
                }
                OperatingCommand operatingCommand = CommandExecutor.undoStack.pop();
                operatingCommand.undo();
                CommandExecutor.redoStack.push(operatingCommand);
    }
}
