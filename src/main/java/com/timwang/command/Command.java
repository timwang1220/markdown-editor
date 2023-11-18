package com.timwang.command;

public interface Command {
    
    public void execute() throws Exception;

    public void maintainStack() throws Exception;
}
