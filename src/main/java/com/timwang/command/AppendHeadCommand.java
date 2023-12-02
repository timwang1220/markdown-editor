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
        
    }
    public AppendHeadCommand(ArrayList<OperatingTuple> operatingTuples){
        super(operatingTuples);
    }
 
    @Override
    public void execute() throws Exception {
        this.operatingFile = WorkSpaceManager.getActiveWorkSpace().getMarkdownFile();
        if (operatingFile == null) {
            throw new Exception("No file is opening");
        }
        MarkdownLine line = StringToMDLine.trans(content);
        operatingFile.insert(1, line);;
        this.operatingTuples.add(new OperatingTuple(1, line));
    }
    @Override
    public void undo() throws Exception {
        this.operatingFile = WorkSpaceManager.getActiveWorkSpace().getMarkdownFile();
        if (operatingFile == null) {
            throw new Exception("No file is opening");
        }
        OperatingTuple operatingTuple = this.operatingTuples.get(0);
        operatingFile.deleteByLineNum(operatingTuple.getLineNum());
    }
    @Override
    public void redo() throws Exception {
        this.operatingFile = WorkSpaceManager.getActiveWorkSpace().getMarkdownFile();
        if (operatingFile == null) {
            throw new Exception("No file is opening");
        }
        OperatingTuple operatingTuple = this.operatingTuples.get(0);
        operatingFile.insert(1, operatingTuple.getContent());
    }
    
    
}
