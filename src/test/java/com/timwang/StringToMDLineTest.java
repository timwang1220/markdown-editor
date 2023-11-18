package com.timwang;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.timwang.markdown.MarkdownLine;
import com.timwang.markdown.MarkdownOrderedList;
import com.timwang.markdown.MarkdownTitle;
import com.timwang.markdown.MarkdownUnorderedList;
import com.timwang.parser.StringToMDLine;

public class StringToMDLineTest {
    @Test
    public void testTransTitle() throws Exception {
        String str = "# Title1";
        MarkdownLine line = StringToMDLine.trans(str);
        assertTrue(line instanceof MarkdownTitle);
        assertEquals("Title1", line.getLine());
        assertEquals(1, line.getProirity());
    }

    @Test
    public void testTransOrderedList() throws Exception {
        String str = "1. Item 1";
        MarkdownLine line = StringToMDLine.trans(str);
        assertTrue(line instanceof MarkdownOrderedList);
        assertEquals("Item 1", line.getLine());
        assertEquals(1, ((MarkdownOrderedList) line).getListOrder());
        assertEquals(6, line.getProirity());
    }

    @Test
    public void testTransUnorderedList() throws Exception {
        String str = "* Item 1";
        MarkdownLine line = StringToMDLine.trans(str);
        assertTrue(line instanceof MarkdownUnorderedList);
        assertEquals("Item 1", line.getLine());
        assertEquals('*', ((MarkdownUnorderedList) line).getListTag());
    }

    @Test(expected = Exception.class)
    public void testTransInvalid() throws Exception {
        String str = "100This is not a valid markdown line";
        MarkdownLine line = StringToMDLine.trans(str);
        assertNull(line);
    }
}