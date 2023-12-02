package com.timwang.command;

import java.util.ArrayList;

import com.timwang.markdown.MarkdownLine;
import com.timwang.parser.StringToMDLine;
import com.timwang.workspace.WorkSpaceManager;

public class AppendHeadCommand extends OperatingCommand{
    private String content;
    public AppendHeadCommand(String args){
        this.operatingTuples = new ArrayList<OperatingTuple>();
        this.content = args;
        this.operatingFile = WorkSpaceManager.getActiveWorkSpace().getMarkdownFile();
    }
    public AppendHeadCommand(ArrayList<OperatingTuple> operatingTuples){
        super(operatingTuples);
    }
 
    @Override
    public void execute() throws Exception {
        if (operatingFile == null) {
            throw new Exception("No file is opening");
        }
        MarkdownLine line = StringToMDLine.trans(content);
        operatingFile.insert(1, line);;
        this.operatingTuples.add(new OperatingTuple(1, line));
    }
    @Override
    public void undo() throws Exception {
        if (operatingFile == null) {
            throw new Exception("No file is opening");
        }
        OperatingTuple operatingTuple = this.operatingTuples.get(0);
        operatingFile.deleteByLineNum(operatingTuple.getLineNum());
    }
    @Override
    public void redo() throws Exception {
        if (operatingFile == null) {
            throw new Exception("No file is opening");
        }
        OperatingTuple operatingTuple = this.operatingTuples.get(0);
        operatingFile.insert(1, operatingTuple.getContent());
    }
    
    
}
