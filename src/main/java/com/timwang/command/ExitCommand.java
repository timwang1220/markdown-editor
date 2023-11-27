package com.timwang.command;

import com.timwang.workspace.WorkSpaceManager;

public class ExitCommand implements Command{
    @Override
    public void execute() throws Exception {
        WorkSpaceManager.closeAllWorkSpace();
        System.exit(0);
    }
}
