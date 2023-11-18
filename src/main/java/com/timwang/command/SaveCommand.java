package com.timwang.command;

public class SaveCommand extends FileCommand {
    public SaveCommand(String args){
        return;
    }
    @Override
    public void execute() throws Exception {
        if (FileCommand.operatingFile == null) {
            throw new Exception("No file is opening");
        }
        FileCommand.operatingFile.save();
    }
    
    @Override
    public void maintainStack() throws Exception {
        CommandExecutor.undoStack.clear();
        CommandExecutor.redoStack.clear(); 
    }
}
