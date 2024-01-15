package com.timwang.command;

import com.timwang.markdown.MarkdownFile;

public abstract class FileCommand implements Command{
    protected MarkdownFile operatingFile;
    public MarkdownFile getOperatingFile(){
        return operatingFile;
    }
}
