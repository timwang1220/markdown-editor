package com.timwang;

import org.junit.Before;
import org.junit.Test;

import com.timwang.command.AppendHeadCommand;
import com.timwang.command.CommandExecutor;
import com.timwang.command.DeleteCommand;
import com.timwang.command.InsertCommand;
import com.timwang.command.LoadCommand;
import com.timwang.command.OperatingCommand;
import com.timwang.command.SaveCommand;
import com.timwang.memento.WorkSpaceMemento;
import com.timwang.workspace.WorkSpace;
import com.timwang.workspace.WorkSpaceManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Stack;

public class WorkSpaceMementoTest {
    private static final String TEST_DIR = "./test_dir/";

    @Before
    public void setUp() throws Exception {
        WorkSpaceManager.clear();
        WorkSpaceManager.newWorkSpace(TEST_DIR + "test1.md");
        WorkSpaceManager.newWorkSpace(TEST_DIR + "test2.md");
        WorkSpaceManager.newWorkSpace(TEST_DIR + "test3.md");
        WorkSpaceManager.switchWorkSpace(TEST_DIR + "test2");
    }

    @Test
    public void testGetWorkSpace() throws Exception {
        WorkSpaceMemento memento = WorkSpaceManager.backUp();
        ArrayList<WorkSpace> workSpaces = memento.getWorkSpaces();
        assertNotNull(workSpaces);
        assertEquals(3, workSpaces.size());
        assertEquals(TEST_DIR + "test1", workSpaces.get(0).getName());
        assertEquals(TEST_DIR + "test1.md", workSpaces.get(0).getMarkdownFile().getFilename());
        assertEquals(TEST_DIR + "test2", memento.getActiveWorkSpace().getName());
    }


    @Test
    public void testSaveAndRecover() throws Exception {
        WorkSpaceMemento memento = WorkSpaceManager.backUp();
        memento.saveToDisk();
        WorkSpaceManager.clear();
        WorkSpaceManager.init();
        ArrayList<WorkSpace> workSpaces = WorkSpaceManager.getAllWorkSpaces();
        assertNotNull(workSpaces);
        assertEquals(3, workSpaces.size());
        assertEquals(TEST_DIR + "test1", workSpaces.get(0).getName());
        assertEquals(TEST_DIR + "test1.md", workSpaces.get(0).getMarkdownFile().getFilename());
        assertEquals(TEST_DIR + "test2", WorkSpaceManager.getActiveWorkSpace().getName());
        
    }

    @Test
    public void testRecoverUndoRedoStack() throws Exception{
        WorkSpaceManager.clear();
        CommandExecutor.getInstance().execute(new LoadCommand(TEST_DIR + "test2.md"));
        OperatingCommand command1 = new InsertCommand("1 # test1");
        OperatingCommand command2 = new DeleteCommand("1"); 
        CommandExecutor.getInstance().execute(command1);
        CommandExecutor.getInstance().execute(command2);
         OperatingCommand command3 = new AppendHeadCommand("# test2");
        CommandExecutor.getInstance().execute(command3);
        OperatingCommand command4 = new DeleteCommand("test2");
        CommandExecutor.getInstance().execute(command4);
        CommandExecutor.getInstance().execute(new SaveCommand(""));
        WorkSpaceMemento memento = WorkSpaceManager.backUp();
        memento.saveToDisk();
        WorkSpaceManager.clear();
        WorkSpaceManager.init();
        Stack<OperatingCommand> undoStack = WorkSpaceManager.getActiveWorkSpace().getUndoStack();
        assertEquals(4, undoStack.size());
        OperatingCommand command = undoStack.pop();
        assertEquals(command4.getClass(), command.getClass());
        assertEquals(command4.toJsonString(), command.toJsonString());
        command = undoStack.pop();
        assertEquals(command3.getClass(), command.getClass());
        assertEquals(command3.toJsonString(), command.toJsonString());
        command = undoStack.pop();
        assertEquals(command2.getClass(), command.getClass());
        assertEquals(command2.toJsonString(), command.toJsonString());
        command = undoStack.pop();
        assertEquals(command1.getClass(), command.getClass());
        assertEquals(command1.toJsonString(), command.toJsonString());


        
    }


}