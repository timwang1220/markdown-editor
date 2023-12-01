package com.timwang.command;

import com.timwang.tools.WorkDirTree;

public class LSCommand implements Command {
    public LSCommand(String args){};
    @Override
    public void execute(){
        WorkDirTree.listMarkdownFiles("./");
    }
}
