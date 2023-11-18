package com.timwang.markdown;

import java.util.ArrayList;

public class MarkdownTitle extends MarkdownComposite {
    public MarkdownTitle(String line, int priority){
        this.line = line;
        this.priority = priority;
        this.subLines = new ArrayList<MarkdownLine>();
        this.lineCount = 1;
    }
    public String toMDString(){
        StringBuilder priorityBuilder = new StringBuilder();
        for (int i = 0; i < priority; i++) {
            priorityBuilder.append("#");
        }
        return String.format("%s %s", priorityBuilder.toString(), line);
    } 
    public void acceptInsertLine(int count){
        lineCount += count;
        parent.acceptInsertLine(count);
    }
    public void acceptDeleteLine(int count){
        lineCount -= count;
        parent.acceptDeleteLine(count);
    }

}
