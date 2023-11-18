package com.timwang.command;

import java.util.ArrayList;

public abstract class OperatingCommand extends FileCommand{
    protected ArrayList<OperatingTuple> operatingTuples;
    public abstract void undo() throws Exception;
    public abstract void redo() throws Exception;
    
    @Override
    public void maintainStack() throws Exception{
        CommandExecutor.undoStack.push(this);
        CommandExecutor.redoStack.clear();
    }
}
