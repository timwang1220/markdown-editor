package com.timwang.command;

import java.util.ArrayList;

import com.timwang.markdown.MarkdownLine;
import com.timwang.parser.StringToMDLine;
import com.timwang.workspace.WorkSpaceManager;

public class AppendTailCommand extends OperatingCommand{
    private String content;
    private int lineNums;
    public AppendTailCommand(String args){
        this.operatingTuples = new ArrayList<OperatingTuple>();
        this.content = args;
        
    }

    @Override
    public void execute() throws Exception {
        this.operatingFile = WorkSpaceManager.getActiveWorkSpace().getMarkdownFile();
        if (operatingFile == null) {
            throw new Exception("No file is opening");
        }
        MarkdownLine line = StringToMDLine.trans(content);
        this.lineNums = operatingFile.append(line);
        this.operatingTuples.add(new OperatingTuple(lineNums, line));
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
        operatingFile.append(operatingTuple.getContent());
    }
    
    
}
