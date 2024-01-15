package com.timwang.command;

import com.timwang.filedisplay.Display;
import com.timwang.filedisplay.TreeDisplay;
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
