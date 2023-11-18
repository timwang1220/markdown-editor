package com.timwang.command;

import java.util.ArrayList;

import com.timwang.markdown.MarkdownLine;
import com.timwang.parser.StringToMDLine;

public class AppendHeadCommand extends OperatingCommand{
    private String content;
    public AppendHeadCommand(String args){
        this.operatingTuples = new ArrayList<OperatingTuple>();
        this.content = args;
    }
 
    @Override
    public void execute() throws Exception {
        if (FileCommand.operatingFile == null) {
            throw new Exception("No file is opening");
        }
        MarkdownLine line = StringToMDLine.trans(content);
        FileCommand.operatingFile.insert(1, line);;
        this.operatingTuples.add(new OperatingTuple(1, line));
    }
    @Override
    public void undo() throws Exception {
        if (FileCommand.operatingFile == null) {
            throw new Exception("No file is opening");
        }
        OperatingTuple operatingTuple = this.operatingTuples.get(0);
        FileCommand.operatingFile.deleteByLineNum(operatingTuple.getLineNum());
    }
    @Override
    public void redo() throws Exception {
        if (FileCommand.operatingFile == null) {
            throw new Exception("No file is opening");
        }
        OperatingTuple operatingTuple = this.operatingTuples.get(0);
        FileCommand.operatingFile.insert(1, operatingTuple.getContent());
    }
    
    
}
