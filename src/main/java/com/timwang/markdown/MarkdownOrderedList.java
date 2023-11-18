package com.timwang.markdown;

public class MarkdownOrderedList extends MarkdownContent{
    private int listOrder;
    public MarkdownOrderedList(String line, int listOrder) {
        this.line = line;
        this.listOrder = listOrder;
        this.priority = contentPriority;
        this.lineCount = 1;
    }
    @Override
    public String toMDString() {
        return listOrder + ". " + line;
    }
    
    @Override
    public String getDisplayLine(){
        return listOrder + ". " + line;
    }

    public int getListOrder(){
        return listOrder;
    }
}
