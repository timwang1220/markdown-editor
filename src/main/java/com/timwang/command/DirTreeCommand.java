package com.timwang.command;

import java.util.LinkedHashMap;

import com.timwang.display.Display;
import com.timwang.display.TreeDisplay;
import com.timwang.markdown.MarkdownLine;

public class DirTreeCommand extends FileCommand {
    private String dirTitle;
    private Display dirTreeDisplay;
    public DirTreeCommand(String args){
        dirTitle = args;
        dirTreeDisplay = new TreeDisplay();
    };
    @Override
    public void execute() throws Exception {
        if (FileCommand.operatingFile == null)
            throw new Exception("No file is loaded");
        if (dirTitle == "") {
            dirTreeDisplay.display(FileCommand.operatingFile.getRoot());
        }
        else{
            LinkedHashMap<Integer, MarkdownLine> dirMap = new LinkedHashMap<Integer, MarkdownLine>();
            FileCommand.operatingFile.getRoot().findByString(dirTitle, 0, dirMap);
            for (MarkdownLine line : dirMap.values()) {
                dirTreeDisplay.display(line);
                System.out.println("\n");
            }
        }
    }
    
}
