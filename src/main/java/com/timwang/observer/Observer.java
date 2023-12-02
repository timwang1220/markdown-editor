package com.timwang.observer;

import com.timwang.command.Command;

public interface Observer {
    public void update(Command command);
    
} 

