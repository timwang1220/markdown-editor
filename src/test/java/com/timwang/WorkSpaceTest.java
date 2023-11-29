
package com.timwang;

import org.junit.Before;
import org.junit.Test;

import com.timwang.command.OperatingCommand;
import com.timwang.workspace.WorkSpace;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Stack;

public class WorkSpaceTest {
    private WorkSpace workSpace;

    @Before
    public void setUp() throws Exception {
        workSpace = new WorkSpace("./test/resources/TestWorkspace");
    }

    @Test
    public void testGetMarkdownFile() {
        assertNotNull(workSpace.getMarkdownFile());
    }

    @Test
    public void testGetName() {
        assertEquals("./test/resources/TestWorkspace", workSpace.getName());
    }

    @Test
    public void testSetActive() {
        workSpace.setActive(true);
        assertTrue(workSpace.isActive());
    }

    @Test
    public void testGetUndoStack() {
        Stack<OperatingCommand> undoStack = workSpace.getUndoStack();
        assertNotNull(undoStack);
        assertTrue(undoStack.isEmpty());
    }

    @Test
    public void testGetRedoStack() {
        Stack<OperatingCommand> redoStack = workSpace.getRedoStack();
        assertNotNull(redoStack);
        assertTrue(redoStack.isEmpty());
    }
}