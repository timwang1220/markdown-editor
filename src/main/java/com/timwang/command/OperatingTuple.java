package com.timwang.command;

import com.alibaba.fastjson.JSONObject;
import com.timwang.markdown.MarkdownLine;
import com.timwang.parser.StringToMDLine;

public class OperatingTuple {
    private int lineNum;
    private MarkdownLine content;
    public OperatingTuple(int lineNum, MarkdownLine content){
        this.lineNum = lineNum;
        this.content = content;
    }
    public int getLineNum() {
        return lineNum;
    }
    public MarkdownLine getContent() {
        return content;
    }
    public String toJsonString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("lineNum", lineNum);
        jsonObject.put("content", content.toMDString());
        return jsonObject.toJSONString();
    }
    public static OperatingTuple fromJsonString(String string) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(string);
        return new OperatingTuple(jsonObject.getIntValue("lineNum"), StringToMDLine.trans(jsonObject.getString("content")));
    }

}
