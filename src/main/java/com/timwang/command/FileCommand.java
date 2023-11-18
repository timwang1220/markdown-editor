package com.timwang.command;

import com.timwang.markdown.MarkdownFile;

public abstract class FileCommand implements Command{
    protected static MarkdownFile operatingFile;
    public static MarkdownFile getOperatingFile(){
        return operatingFile;
    }
    public void maintainStack() throws Exception {
        return;  
    }

}
