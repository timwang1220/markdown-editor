package com.timwang.command;

import com.timwang.log.Stat;
import com.timwang.workspace.WorkSpaceManager;

public class StatCommand extends FileCommand{
    boolean showCurrent;
    public StatCommand(String args){
        showCurrent = true;
        if (args.equals("all")) {
            showCurrent = false;
        }
        this.operatingFile = WorkSpaceManager.getActiveWorkSpace().getMarkdownFile();
    }


    @Override
    public void execute() throws Exception {
        if (operatingFile == null) {
            throw new Exception("No file has been opened.");
        }
        if (showCurrent) {
            Stat.showCurrentState(operatingFile);
        } else {
            Stat.showAllState(operatingFile);
        }
    }
    
}
