package com.timwang.command;

import java.util.ArrayList;

import com.timwang.markdown.MarkdownLine;
import com.timwang.parser.StringToMDLine;
import com.timwang.workspace.WorkSpaceManager;

public class InsertCommand extends OperatingCommand {
    private int lineNums;
    private String content;

    public InsertCommand(String args) {
        String[] lineNumAndContent = args.split(" ", 2);
        if (lineNumAndContent[0].matches("\\d+")) {
            this.lineNums = Integer.parseInt(lineNumAndContent[0]);
            this.content = lineNumAndContent[1];
        } else{
            this.content = args;
            this.lineNums = -1;       
        }
        this.operatingTuples = new ArrayList<OperatingTuple>();
        
    }

    public InsertCommand(ArrayList<OperatingTuple> operatingTuples) {
        super(operatingTuples);
    }


    @Override
    public void execute() throws Exception {
        this.operatingFile = WorkSpaceManager.getActiveWorkSpace().getMarkdownFile();
        if (operatingFile == null) {
            throw new Exception("No file is opening");
        } 
        MarkdownLine line = StringToMDLine.trans(content);
        if (lineNums == -1){
            this.lineNums = operatingFile.append(line);
        }
        else{
            operatingFile.insert(lineNums, line);
        }
        this.operatingTuples.add(new OperatingTuple(lineNums, line));
    }

    @Override
    public void undo() throws Exception {
        this.operatingFile = WorkSpaceManager.getActiveWorkSpace().getMarkdownFile();
        if (operatingFile == null) {
            throw new Exception("No file is opening");
        }
        for (OperatingTuple operatingTuple : this.operatingTuples) {
            operatingFile.deleteByLineNum(operatingTuple.getLineNum());
        }
    }

    @Override
    public void redo() throws Exception {
        this.operatingFile = WorkSpaceManager.getActiveWorkSpace().getMarkdownFile();
        if (operatingFile == null) {
            throw new Exception("No file is opening");
        }
        for (OperatingTuple operatingTuple : this.operatingTuples) {
            operatingFile.insert(operatingTuple.getLineNum(), operatingTuple.getContent());
        }
    }

}
