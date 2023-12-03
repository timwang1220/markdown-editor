package com.timwang.memento;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.timwang.tools.Tools;
import com.timwang.workspace.WorkSpace;

public class WorkSpaceMemento implements Memento {
    @JSONField(name = "workspaces")
    private ArrayList<WorkSpace> workSpaces;
    @JSONField(name = "activeworkspace")
    private WorkSpace activeWorkSpace;
    @JSONField(serialize = false)
    private LocalDateTime saveTime;
    private final static String WORKSPACE_LOG = "log/workspace.log";

    public WorkSpaceMemento(ArrayList<WorkSpace> workSpaces, WorkSpace activeWorkSpace) {
        this.workSpaces = workSpaces;
        this.activeWorkSpace = activeWorkSpace;
        this.saveTime = LocalDateTime.now();
    }
    public WorkSpaceMemento() {
        this.saveTime = LocalDateTime.now();
    }


    public ArrayList<WorkSpace> getWorkSpaces() {
        return workSpaces;
    }

    public WorkSpace getActiveWorkSpace() {
        return activeWorkSpace;
    }

    // public void saveToDisk() {
    //     // saveTo
    //      ObjectMapper mapper = new ObjectMapper();
    //     return mapper.writeValueAsString(this);
        
    // }
    public String toJsonString(){
        JSONArray jsonArray = new JSONArray();
        for (WorkSpace workSpace : workSpaces) {
            jsonArray.add(workSpace.toJsonString());
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("workspaces", jsonArray);
        if (activeWorkSpace == null) {
           jsonObject.put("activeworkspace", null);
        }
        else jsonObject.put("activeworkspace", activeWorkSpace.toJsonString());
        return jsonObject.toJSONString();
    }

    private static WorkSpaceMemento fromJsonString(String jsonString) throws Exception{
        if (jsonString == null) {
            return null;
        }
        WorkSpaceMemento memento = new WorkSpaceMemento();
        JSONObject jsonObject = JSON.parseObject(jsonString);
        String activeWorkSpaceString = jsonObject.getString("activeworkspace");
        String activeWorkSpaceName = (activeWorkSpaceString == null) ? null: JSONObject.parseObject(activeWorkSpaceString).getString("name");
        JSONArray jsonArray = jsonObject.getJSONArray("workspaces");
        memento.workSpaces = new ArrayList<WorkSpace>();
        memento.activeWorkSpace = null;
        for (int i = 0; i < jsonArray.size(); i++) {
            String workSpaceString = jsonArray.getString(i);
            WorkSpace workSpace = WorkSpace.fromJsonString(workSpaceString);
            memento.workSpaces.add(workSpace);
            if (activeWorkSpaceName !=null && activeWorkSpaceName.equals(workSpace.getName())) {
                memento.activeWorkSpace = workSpace;
            }
        }
        
        return memento;
    }

    public static WorkSpaceMemento recoverFromJsonFile() throws Exception{
        Tools.createFileIfNotExists(WORKSPACE_LOG);
        try (BufferedReader br = new BufferedReader(new FileReader(WORKSPACE_LOG))) {
            String jsonString = br.readLine();
            return fromJsonString(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void saveToDisk() throws Exception {
        Tools.createFileIfNotExists(WORKSPACE_LOG);
        if (workSpaces == null || workSpaces.size() == 0) {
            return;
        }
        try (FileWriter fileWriter = new FileWriter(WORKSPACE_LOG)) {
            fileWriter.write(toJsonString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
