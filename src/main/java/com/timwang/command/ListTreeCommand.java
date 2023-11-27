package com.timwang.command;

import com.timwang.display.Display;
import com.timwang.display.TreeDisplay;
import com.timwang.workspace.WorkSpaceManager;

public class ListTreeCommand extends FileCommand{
    private Display treeDisplay;
    public ListTreeCommand(String args){
        treeDisplay = new TreeDisplay();
        this.operatingFile = WorkSpaceManager.getActiveWorkSpace().getMarkdownFile();
    }
    @Override
    public void execute() throws Exception {
        if (operatingFile == null)
            throw new Exception("No file is loaded");
       treeDisplay.display(operatingFile.getRoot());
    }
    
}
