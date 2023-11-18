package com.timwang.command;

import java.util.ArrayList;

import com.timwang.markdown.MarkdownLine;
import com.timwang.parser.StringToMDLine;

public class AppendTailCommand extends OperatingCommand{
    private String content;
    private int lineNums;
    public AppendTailCommand(String args){
        this.operatingTuples = new ArrayList<OperatingTuple>();
        this.content = args;
    }

    @Override
    public void execute() throws Exception {
        if (FileCommand.operatingFile == null) {
            throw new Exception("No file is opening");
        }
        MarkdownLine line = StringToMDLine.trans(content);
        this.lineNums = FileCommand.operatingFile.append(line);
        this.operatingTuples.add(new OperatingTuple(lineNums, line));
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
        FileCommand.operatingFile.append(operatingTuple.getContent());
    }
    
    
}
