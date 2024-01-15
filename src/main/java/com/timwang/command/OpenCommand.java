package com.timwang.command;

import com.timwang.workspace.WorkSpaceManager;

public class OpenCommand implements Command{
    private String workSpaceName;
    public OpenCommand(String args) {
        this.workSpaceName = args;
    }
    @Override
    public void execute() {
        WorkSpaceManager.switchWorkSpace(workSpaceName);
    }
}
