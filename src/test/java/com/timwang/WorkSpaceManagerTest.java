package com.timwang;

import org.junit.Before;
import org.junit.Test;

import com.timwang.workspace.WorkSpace;
import com.timwang.workspace.WorkSpaceManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class WorkSpaceManagerTest {

    static final String RelativePath = "./test-dir/";

    @Before
    public void setUp() throws Exception {
        WorkSpaceManager.clear();
    }

    @Test
    public void testSwitchWorkSpace() throws Exception {
        String workSpace1 = RelativePath + "Workspace1";
        String workSpace2 = RelativePath + "Workspace2";
        String workSpace3 = RelativePath + "Workspace3";

        WorkSpaceManager.newWorkSpace(workSpace1 + ".md");
        WorkSpaceManager.newWorkSpace(workSpace2 + ".md");
        WorkSpaceManager.newWorkSpace(workSpace3 + ".md");

        WorkSpaceManager.switchWorkSpace(workSpace2);

        assertEquals(workSpace2, WorkSpaceManager.getActiveWorkSpace().getName());
    }

    @Test(expected = RuntimeException.class)
    public void testSwitchWorkSpace_NonexistentWorkspace() {
        WorkSpaceManager.switchWorkSpace("NonexistentWorkspace");
    }

    @Test
    public void testNewWorkSpace() throws Exception {
        String workSpaceName = RelativePath + "NewWorkspace";
        WorkSpaceManager.newWorkSpace(workSpaceName + ".md");

        ArrayList<WorkSpace> workSpaces = WorkSpaceManager.getAllWorkSpaces();
        assertEquals(1, workSpaces.size());

        WorkSpace workSpace = workSpaces.get(0);
        assertNotNull(workSpace);
        assertEquals(workSpaceName, workSpace.getName());
        assertTrue(workSpace.isActive());
    }

    @Test(expected = Exception.class)
    public void testNewWorkSpace_WorkspaceAlreadyExists() throws Exception {
        String workSpaceName = RelativePath + "ExistingWorkspace";
        WorkSpaceManager.newWorkSpace(workSpaceName + ".md");
        WorkSpaceManager.newWorkSpace(workSpaceName + ".md");
    }

    @Test(expected = Exception.class)
    public void testCloseCurrentWorkSpace_NoActiveWorkspace() throws Exception {
        WorkSpaceManager.closeCurrentWorkSpace();
    }

    @Test
    public void testCloseAllWorkSpace() throws Exception {
        String workSpace1 = RelativePath + "Workspace1";
        String workSpace2 = RelativePath + "Workspace2";
        String workSpace3 = RelativePath + "Workspace3";

        WorkSpaceManager.newWorkSpace(workSpace1 + ".md");
        WorkSpaceManager.newWorkSpace(workSpace2 + ".md");
        WorkSpaceManager.newWorkSpace(workSpace3 + ".md");

        WorkSpaceManager.closeAllWorkSpace();

        ArrayList<WorkSpace> workSpaces = WorkSpaceManager.getAllWorkSpaces();
        assertEquals(0, workSpaces.size());
    }

    @Test
    public void testListAllWorkSpace() throws Exception {
        String workSpace1 = RelativePath + "Workspace1";
        String workSpace2 = RelativePath + "Workspace2";
        String workSpace3 = RelativePath + "Workspace3";

        WorkSpaceManager.newWorkSpace(workSpace1 + ".md");
        WorkSpaceManager.newWorkSpace(workSpace2 + ".md");
        WorkSpaceManager.newWorkSpace(workSpace3 + ".md");

        WorkSpaceManager.switchWorkSpace(workSpace2);
        WorkSpaceManager.getActiveWorkSpace().getMarkdownFile().setDirty();
        WorkSpaceManager.switchWorkSpace(workSpace3);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        WorkSpaceManager.listAllWorkSpace();

        String expectedOutput = "  "+ workSpace1 + "\n  "+ workSpace2 +" *\n->" + workSpace3 + "\n";
        assertEquals(expectedOutput, outContent.toString());
    }
}