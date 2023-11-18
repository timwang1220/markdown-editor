package com.timwang.command;

import com.timwang.markdown.MarkdownFile;

public class LoadCommand extends FileCommand{
    private String filename;
    public LoadCommand(String args){
        this.filename = args;
    }


    @Override
    public void execute() throws Exception {
        if (FileCommand.operatingFile != null) {
            FileCommand.operatingFile.close();
        }
        FileCommand.operatingFile = new MarkdownFile(filename);
        
    }
    @Override
    public void maintainStack() throws Exception {
        CommandExecutor.undoStack.clear();
        CommandExecutor.redoStack.clear(); 
    }
}
