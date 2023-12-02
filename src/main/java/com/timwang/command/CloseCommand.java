package com.timwang.command;

import com.timwang.workspace.WorkSpaceManager;

public class CloseCommand implements Command{
    public CloseCommand(String args) {}
    @Override
    public void execute() throws Exception {
        WorkSpaceManager.closeCurrentWorkSpace();
    }
}
