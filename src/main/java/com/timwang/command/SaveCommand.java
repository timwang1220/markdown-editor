package com.timwang.command;

import com.timwang.workspace.WorkSpaceManager;

public class SaveCommand extends FileCommand {
    public SaveCommand(String args){
        this.operatingFile = WorkSpaceManager.getActiveWorkSpace().getMarkdownFile();
        return;
    }
    @Override
    public void execute() throws Exception {
        if (operatingFile == null) {
            throw new Exception("No file is opening");
        }
        operatingFile.save();
    }
    
}
