package com.timwang.command;

import com.timwang.display.Display;
import com.timwang.display.ListDisplay;

public class ListCommand extends FileCommand {
    private Display listDisplay;
    public ListCommand(String args){
        listDisplay = new ListDisplay();
    };
    @Override
    public void execute() throws Exception {
        if (FileCommand.operatingFile == null)
            throw new Exception("No file is loaded");
        listDisplay.display(FileCommand.operatingFile.getRoot());
    }
    
}
