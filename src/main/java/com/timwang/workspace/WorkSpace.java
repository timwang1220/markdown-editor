package com.timwang.workspace;

import java.io.Serializable;
import java.util.Stack;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.timwang.command.OperatingCommand;
import com.timwang.markdown.MarkdownFile;

public class WorkSpace implements Serializable{
    private String name;
    private boolean isActive;

    @JSONField(serialize = false)
    private MarkdownFile markdownFile;
    
    @JSONField(serialize = false)
    private Stack<OperatingCommand> undoStack;
    @JSONField(serialize = false)
    private Stack<OperatingCommand> redoStack;

    public WorkSpace(String name) throws Exception {
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

    public static WorkSpace fromJsonString(String jsonString) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        WorkSpace workSpace = new WorkSpace(jsonObject.getString("name") + ".md");
        workSpace.isActive = jsonObject.getBoolean("active");
        System.out.println(workSpace.getName());
        System.out.println(workSpace.getMarkdownFile().getFilename());
        return workSpace;      
    }
}
