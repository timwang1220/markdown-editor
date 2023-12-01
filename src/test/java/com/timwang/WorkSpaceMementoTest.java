package com.timwang;

import org.junit.Before;
import org.junit.Test;

import com.timwang.memento.WorkSpaceMemento;
import com.timwang.workspace.WorkSpace;
import com.timwang.workspace.WorkSpaceManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

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


}