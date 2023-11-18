package com.timwang.command;

import com.timwang.markdown.MarkdownLine;

public class OperatingTuple {
    private int lineNum;
    private MarkdownLine content;
    public OperatingTuple(int lineNum, MarkdownLine content){
        this.lineNum = lineNum;
        this.content = content;
    }
    public int getLineNum() {
        return lineNum;
    }
    public MarkdownLine getContent() {
        return content;
    }

}
