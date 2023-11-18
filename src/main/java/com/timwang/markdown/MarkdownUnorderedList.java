package com.timwang.markdown;

public class MarkdownUnorderedList extends MarkdownContent {
    private char listTag;
    public MarkdownUnorderedList(String line, char listTag) {
        this.line = line;
        this.listTag = listTag;
        this.priority = contentPriority;
        this.lineCount = 1;
    }
    @Override
    public String toMDString() {
        return listTag + " " + line;
    }
    @Override
    public String getDisplayLine(){
        return "·" + line;
    }
    public char getListTag(){
        return listTag;
    }
}
