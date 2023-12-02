package com.timwang;

import com.timwang.command.CommandExecutor;
import com.timwang.command.LoadCommand;
import com.timwang.command.OpenCommand;
import com.timwang.command.StatCommand;
import com.timwang.workspace.WorkSpaceManager;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;


public class StatObserverTest {

    private static final String TEST_DIR = "/test_dir/";
    @Before
    public void setUp() throws Exception {
        WorkSpaceManager.init();
        CommandExecutor.clear();
        
    }

    @Test
    public void testOpenFile() throws Exception {
        CommandExecutor.getInstance().execute(new LoadCommand(TEST_DIR + "test.md"));
        Thread.sleep(3000);
        
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        CommandExecutor.getInstance().execute(new StatCommand("current"));
        assertEquals("/test_dir/test.md 3秒\r\n", outContent.toString());
    }

    @Test
    public void testSwitchWorkSpace() throws Exception {

        CommandExecutor.getInstance().execute(new LoadCommand(TEST_DIR + "test.md"));
        Thread.sleep(2000);
        CommandExecutor.getInstance().execute(new LoadCommand(TEST_DIR + "test2.md"));
        Thread.sleep(2000);
        CommandExecutor.getInstance().execute(new OpenCommand(TEST_DIR + "test"));
        Thread.sleep(2000);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        CommandExecutor.getInstance().execute(new StatCommand("all"));
        String ouString[] = outContent.toString().split("\r\n");
        assertEquals("/test_dir/test.md 4秒", ouString[1]);
        assertEquals("/test_dir/test2.md 2秒", ouString[2]);
    }

}