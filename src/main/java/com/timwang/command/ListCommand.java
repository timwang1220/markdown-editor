package com.timwang.command;

import com.timwang.display.Display;
import com.timwang.display.ListDisplay;
import com.timwang.workspace.WorkSpaceManager;

public class ListCommand extends FileCommand {
    private Display listDisplay;
    public ListCommand(String args){
        listDisplay = new ListDisplay();
        this.operatingFile = WorkSpaceManager.getActiveWorkSpace().getMarkdownFile();
    };
    @Override
    public void execute() throws Exception {
        if (operatingFile == null)
            throw new Exception("No file is loaded");
        listDisplay.display(operatingFile.getRoot());
    }
    
}
