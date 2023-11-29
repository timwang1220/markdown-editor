package com.timwang.workspace;

import java.util.Stack;

import com.timwang.command.OperatingCommand;
import com.timwang.markdown.MarkdownFile;

public class WorkSpace {
    private MarkdownFile markdownFile;
    private String name;
    private boolean isActive;
    private Stack<OperatingCommand> undoStack;
    private Stack<OperatingCommand> redoStack;
    
    public WorkSpace(String name) throws Exception{
        this.name = name.split(".md")[0];
        this.markdownFile = new MarkdownFile(name);
        isActive = false;
        undoStack = new Stack<OperatingCommand>();
        redoStack = new Stack<OperatingCommand>();
    }
    public MarkdownFile getMarkdownFile() {
        return markdownFile;
    }

    public String getName() {
        return name;
    }
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
    public boolean isActive() {
        return isActive;
    }

    public Stack<OperatingCommand> getUndoStack() {
        return undoStack;
    }
    public Stack<OperatingCommand> getRedoStack() {
        return redoStack;
    }
}
