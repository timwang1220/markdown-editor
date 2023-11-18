package com.timwang.command;

public class RedoCommand extends RecoverCommand {
    public RedoCommand(String args) {
        super();
    }

    @Override
    public void maintainStack() throws Exception {
        if (CommandExecutor.redoStack.isEmpty()) {
            throw new Exception("Ilegal Redo: No command to redo");
        }
        OperatingCommand operatingCommand = CommandExecutor.redoStack.pop();
        operatingCommand.redo();
        CommandExecutor.undoStack.push(operatingCommand);
    }

}
