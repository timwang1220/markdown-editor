package com.timwang.workspace;

import java.io.Serializable;
import java.util.Stack;

import com.alibaba.fastjson.JSONArray;
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
        JSONArray undoStackJsonArray = jsonObject.getJSONArray("undoStack");
        JSONArray redoStackJsonArray = jsonObject.getJSONArray("redoStack");
        for (Object object : undoStackJsonArray) {
            OperatingCommand operatingCommand = OperatingCommand.fromJsonString(object.toString());
            workSpace.undoStack.push(operatingCommand);
        }
        for (Object object : redoStackJsonArray) {
            OperatingCommand operatingCommand = OperatingCommand.fromJsonString(object.toString());
            workSpace.redoStack.push(operatingCommand);
        }
        return workSpace;      
    }

    public String toJsonString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("active", isActive);
        jsonObject.put("undostacksize", undoStack.size());
        JSONArray undoStackJsonArray = new JSONArray();
        for (OperatingCommand operatingCommand : undoStack) {
            undoStackJsonArray.add(operatingCommand.toJsonString());
        }
        JSONArray redoStackJsonArray = new JSONArray();
        for (OperatingCommand operatingCommand : redoStack) {
            redoStackJsonArray.add(operatingCommand.toJsonString());
        }
        jsonObject.put("undoStack", undoStackJsonArray);
        jsonObject.put("redoStack", redoStackJsonArray);
        return jsonObject.toJSONString();
    }
}
