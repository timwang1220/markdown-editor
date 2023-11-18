package com.timwang.command;

import com.timwang.display.Display;
import com.timwang.display.TreeDisplay;

public class ListTreeCommand extends FileCommand{
    private Display treeDisplay;
    public ListTreeCommand(String args){
        treeDisplay = new TreeDisplay();
    }
    @Override
    public void execute() throws Exception {
        if (FileCommand.operatingFile == null)
            throw new Exception("No file is loaded");
       treeDisplay.display(operatingFile.getRoot());
    }
    
}
