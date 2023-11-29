package com.timwang.command;

import com.timwang.workspace.WorkSpaceManager;

public class ExitCommand implements Command{
    public ExitCommand(String args){};
    @Override
    public void execute() throws Exception {
        WorkSpaceManager.closeAllWorkSpace();
        System.exit(0);
    }
}
