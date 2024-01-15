package com.timwang.command;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public abstract class OperatingCommand extends FileCommand{
    protected ArrayList<OperatingTuple> operatingTuples;
    public abstract void undo() throws Exception;
    public abstract void redo() throws Exception;
    public OperatingCommand(ArrayList<OperatingTuple> operatingTuples){
        this.operatingTuples = operatingTuples;
    }
    public OperatingCommand(){
        this.operatingTuples = new ArrayList<OperatingTuple>();
    }
    public String toJsonString(){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (OperatingTuple operatingTuple : operatingTuples) {
            jsonArray.add(operatingTuple.toJsonString());
        }
        jsonObject.put("type", this.getClass());
        jsonObject.put("operatingTuples", jsonArray);
        return jsonObject.toJSONString();
    }
    public static OperatingCommand fromJsonString(String string) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(string);
        JSONArray jsonArray = jsonObject.getJSONArray("operatingTuples");
        ArrayList<OperatingTuple> operatingTuples = new ArrayList<OperatingTuple>();
        for (int i = 0; i < jsonArray.size(); i++) {
            operatingTuples.add(OperatingTuple.fromJsonString(jsonArray.getString(i)));
        }
        Class<?> clazz = Class.forName(jsonObject.getString("type"));
        Constructor<?> constructor = clazz.getConstructor(ArrayList.class);
        return (OperatingCommand) constructor.newInstance(operatingTuples);

    }
}
