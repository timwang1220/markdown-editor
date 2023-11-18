package com.timwang.command;

import com.timwang.log.Stat;

public class StatCommand extends FileCommand{
    boolean showCurrent;
    public StatCommand(String args){
        showCurrent = true;
        if (args.equals("all")) {
            showCurrent = false;
        }
    }


    @Override
    public void execute() throws Exception {
        if (FileCommand.operatingFile == null) {
            throw new Exception("No file has been opened.");
        }
        if (showCurrent) {
            Stat.showCurrentState(FileCommand.operatingFile);
        } else {
            Stat.showAllState(FileCommand.operatingFile);
        }
    }

    public void maintainStack() throws Exception {
        return;  
    }
    
}
