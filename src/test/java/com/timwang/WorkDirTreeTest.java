package com.timwang;


import org.junit.Before;
import org.junit.Test;

import com.timwang.tools.WorkDirTree;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class WorkDirTreeTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    public void testListMarkdownFiles() {
        WorkDirTree.listMarkdownFiles("./sample_dir");
        String expectedOutput = 
                "├── file1.md\r\n" +
                "├── file2.md\r\n" +
                "└── subdirectory\r\n" +
                "    ├── subfile1.md\r\n" +
                "    └── subfile2.md\r\n";
        assertEquals(expectedOutput, outContent.toString());
    }
}