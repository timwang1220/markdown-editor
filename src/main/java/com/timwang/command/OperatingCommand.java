package com.timwang.command;

import java.util.ArrayList;

public abstract class OperatingCommand extends FileCommand{
    protected ArrayList<OperatingTuple> operatingTuples;
    public abstract void undo() throws Exception;
    public abstract void redo() throws Exception;
}
