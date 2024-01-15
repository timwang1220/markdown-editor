package com.timwang;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;


import com.timwang.markdown.MarkdownRoot;
import com.timwang.markdown.MarkdownTitle;
import com.timwang.processor.FileProcessor;

public class FileProcessorTest {
    private static final String IN_FILE_PATH = "./src/test/resources/testin.md";
    private static final String OUTPUT_FILE_PATH = "./src/test/resources/testout.md";
    private MarkdownRoot root;

    @Before
    public void setUp() {
        root = new MarkdownRoot();
    }

    @Test
    public void testInitFile() throws Exception {
        FileProcessor.initFile(IN_FILE_PATH, root);
        assertEquals(9, root.getLineCount());
        MarkdownTitle title1 = ((MarkdownTitle) root.getSubLines().get(0));
        assertEquals("title1", title1.getLine());
        assertEquals(8, title1.getLineCount());
        MarkdownTitle title2 = ((MarkdownTitle) title1.getSubLines().get(0));
        assertEquals("title2", title2.getLine());
        assertEquals(4, title2.getLineCount());
        MarkdownTitle title3 = ((MarkdownTitle) title1.getSubLines().get(1));
        assertEquals("title3", title3.getLine());
        assertEquals(3, title3.getLineCount());
        assertEquals("title5", root.getSubLines().get(1).getLine());
    }

    @Test
    public void testWriteFile() throws Exception {
        FileProcessor.initFile(IN_FILE_PATH, root);
        BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE_PATH));
        FileProcessor.writeFile(writer, root);
        writer.close();
        // compare per line in file1 and file2
        try (BufferedReader reader1 = new BufferedReader(new FileReader(IN_FILE_PATH));
                BufferedReader reader2 = new BufferedReader(new FileReader(OUTPUT_FILE_PATH))) {
            String line1 = reader1.readLine();
            String line2 = reader2.readLine();
            assertEquals(line1, line2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // assertEquals(TEST_FILE_CONTENTS, outputBuilder.toString());
    }
}