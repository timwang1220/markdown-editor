package com.timwang.command;

import java.util.ArrayList;
import java.util.TreeMap;

import com.timwang.markdown.MarkdownComposite;
import com.timwang.markdown.MarkdownLine;
import com.timwang.workspace.WorkSpaceManager;

public class DeleteCommand extends OperatingCommand{
    private int deleteLineNum;
    private String deleteContent; //对于命令中存在多条deleteContent，实现全部删除
    
    public DeleteCommand(String args){
        if (args.matches("\\d+")){
            deleteLineNum = Integer.parseInt(args);
            deleteContent = null;
        }
        else{
            deleteContent = args;
            deleteLineNum = -1;
        }
        this.operatingTuples = new ArrayList<OperatingTuple>();
        this.operatingFile = WorkSpaceManager.getActiveWorkSpace().getMarkdownFile();
    }

    public DeleteCommand(ArrayList<OperatingTuple> operatingTuples){
        super(operatingTuples);
    }


    @Override
    public void execute() throws Exception {
        if (operatingFile == null) {
            throw new Exception("No file is opening");
        }
        if (deleteLineNum != -1){
            MarkdownLine line = operatingFile.deleteByLineNum(deleteLineNum);
            this.operatingTuples.add(new OperatingTuple(deleteLineNum, line));
        }
        else{
            TreeMap<Integer,MarkdownLine> deletedMap = operatingFile.deleteByString(deleteContent);
            for (Integer lineNum : deletedMap.keySet()){
                this.operatingTuples.add(new OperatingTuple(lineNum, deletedMap.get(lineNum)));
            }
        }
    }



    @Override
    public void undo() throws Exception {
        if (operatingFile == null) {
            throw new Exception("No file is opening");
        }
        for (OperatingTuple operatingTuple : this.operatingTuples){
            MarkdownLine line = operatingTuple.getContent();
            if (line instanceof MarkdownComposite) ((MarkdownComposite)line).clear();
            operatingFile.insert(operatingTuple.getLineNum(), operatingTuple.getContent());
        }
    }



    @Override
    public void redo() throws Exception {
        if (operatingFile == null) {
            throw new Exception("No file is opening");
        }
        int count = 0;
        for (OperatingTuple operatingTuple : this.operatingTuples){
            operatingFile.deleteByLineNum(operatingTuple.getLineNum() - count);
            count++;
        }
    }
    
}
