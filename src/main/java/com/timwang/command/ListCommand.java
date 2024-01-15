package com.timwang.command;

import com.timwang.filedisplay.Display;
import com.timwang.filedisplay.ListDisplay;
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
