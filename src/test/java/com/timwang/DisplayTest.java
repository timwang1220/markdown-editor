package com.timwang;

import org.junit.Test;

import com.timwang.command.Command;
import com.timwang.parser.ArgstoCommand;

public class DisplayTest {
    @Test
    public void TreeDisplayTest() throws Exception{
        Command command1 = ArgstoCommand.getCommand("load ./src/test/resources/testin.md");
        command1.execute();
        Command command2 = ArgstoCommand.getCommand("list-tree");
        command2.execute();
        Command command3 = ArgstoCommand.getCommand("dir-tree title2");
        command3.execute();
    }

    @Test
    public void ListDisplayTest() throws Exception{
        Command command1 = ArgstoCommand.getCommand("load ./src/test/resources/testin.md");
        command1.execute();
        Command command2 = ArgstoCommand.getCommand("list");
        command2.execute();
    }
}
