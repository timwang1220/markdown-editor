package com.timwang.command;

import com.timwang.workspace.WorkSpaceManager;

public class ListWorkSpaceCommand implements Command{
    public ListWorkSpaceCommand(String args){};
    @Override
    public void execute() {
        WorkSpaceManager.listAllWorkSpace();
    }
}
