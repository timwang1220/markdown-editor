package com.timwang.command;

import com.timwang.workspace.WorkSpaceManager;

public class CloseCommand implements Command{
    @Override
    public void execute() throws Exception {
        WorkSpaceManager.closeCurrentWorkSpace();
    }
}
