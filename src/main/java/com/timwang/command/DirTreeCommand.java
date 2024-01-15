package com.timwang.command;

import java.util.LinkedHashMap;

import com.timwang.filedisplay.Display;
import com.timwang.filedisplay.TreeDisplay;
import com.timwang.markdown.MarkdownLine;
import com.timwang.workspace.WorkSpaceManager;

public class DirTreeCommand extends FileCommand {
    private String dirTitle;
    private Display dirTreeDisplay;
    public DirTreeCommand(String args){
        dirTitle = args;
        dirTreeDisplay = new TreeDisplay();
        this.operatingFile = WorkSpaceManager.getActiveWorkSpace().getMarkdownFile();
    };
    @Override
    public void execute() throws Exception {
        if (operatingFile == null)
            throw new Exception("No file is loaded");
        if (dirTitle == "") {
            dirTreeDisplay.display(operatingFile.getRoot());
        }
        else{
            LinkedHashMap<Integer, MarkdownLine> dirMap = new LinkedHashMap<Integer, MarkdownLine>();
            operatingFile.getRoot().findByString(dirTitle, 0, dirMap);
            for (MarkdownLine line : dirMap.values()) {
                dirTreeDisplay.display(line);
                System.out.println("\n");
            }
        }
    }
    
}
