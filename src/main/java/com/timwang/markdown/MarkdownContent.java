package com.timwang.markdown;

public abstract class MarkdownContent extends MarkdownLine{
    protected void delete(){
        parent.subLines.remove(this);
        parent.acceptDeleteLine(lineCount);
        this.lineCount = 1;
        this.parent = null;
    }
}
