package com.timwang;

import org.junit.Test;

import com.timwang.command.Command;
import com.timwang.parser.ArgstoCommand;

public class CommandTest {
    @Test
    public void commandTest() throws Exception{
        Command command1 = ArgstoCommand.getCommand("load ./src/test/resources/commandtest.md");
        command1.execute();
        Command command2 = ArgstoCommand.getCommand("insert 1 * abcdefg");
        command2.execute();
        Command command3 = ArgstoCommand.getCommand("append-head + afg");
        command3.execute();
        Command command4 = ArgstoCommand.getCommand("append-tail - bbi");
        command4.execute();
        Command command5 = ArgstoCommand.getCommand("insert 2 # title1");
        command5.execute();
        Command command6 = ArgstoCommand.getCommand("insert 3 ## title2");
        command6.execute();
        Command command7 = ArgstoCommand.getCommand("delete title2");
        command7.execute();
        Command command8 = ArgstoCommand.getCommand("save");
        command8.execute();
    }
}
