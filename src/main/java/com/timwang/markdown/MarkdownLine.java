package com.timwang.markdown;

public abstract class MarkdownLine {
    protected String line;
    protected int lineCount;
    protected MarkdownComposite parent;
    protected int priority; // 0-6, 0 for the root, 6 for the content
    protected static int rootPriority = 0;
    protected static int contentPriority = 6;
    protected abstract void delete() throws Exception;
    public abstract String toMDString();
    public int getLineCount(){
        return lineCount;
    };
    public String getLine(){
        return line;
    };
    public String getDisplayLine(){
        return line;
    }
    public int getProirity(){
        return priority;
    };
    public MarkdownComposite getParent(){
        return parent;
    };
    public void setParent(MarkdownComposite parent){
        this.parent = parent;
    }
    
}
