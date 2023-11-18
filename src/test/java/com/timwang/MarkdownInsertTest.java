package com.timwang;

import org.junit.Before;
import org.junit.Test;

import com.timwang.markdown.MarkdownComposite;
import com.timwang.markdown.MarkdownContent;
import com.timwang.markdown.MarkdownOrderedList;
import com.timwang.markdown.MarkdownRoot;
import com.timwang.markdown.MarkdownTitle;
import com.timwang.markdown.MarkdownUnorderedList;


import static org.junit.Assert.*;

public class MarkdownInsertTest {
    private MarkdownComposite root;

    @Before
    public void setUp() {
        root = new MarkdownRoot();
    }

    @Test
    public void testInsertTitle() throws Exception {
        MarkdownTitle title1 = new MarkdownTitle("Title1", 1);
        MarkdownTitle title2 = new MarkdownTitle("Title2", 1);
        MarkdownTitle title3 = new MarkdownTitle("Title3", 2);
        MarkdownTitle title4 = new MarkdownTitle("Title4", 3);
        MarkdownTitle title5 = new MarkdownTitle("Title5", 1);
        assertEquals("Title1", title1.getLine());
        assertEquals(0, root.getLineCount());
        root.insertLine(1, title1);
        assertEquals(1, root.getLineCount());
        root.insertLine(1, title2);
        assertEquals(2, root.getLineCount());
        assertEquals(root, title1.getParent());
        root.insertLine(2, title3);
        assertEquals(3, root.getLineCount());
        assertEquals(2, title2.getLineCount());
        assertEquals(title2, title3.getParent());
        root.insertLine(3, title4);
        assertEquals(4, root.getLineCount());
        assertEquals(title3, title4.getParent());
        assertEquals(3, title2.getLineCount());
        root.insertLine(2, title5);
        assertEquals(5, root.getLineCount());
        assertEquals(1, title2.getLineCount());
        assertEquals(title5, title3.getParent());
        assertEquals("## Title3", title3.toMDString());

    }

    @Test
    public void testInsertLine() throws Exception {
        MarkdownTitle title1 = new MarkdownTitle("Title1", 1);
        MarkdownTitle title2 = new MarkdownTitle("Title2", 2);
        MarkdownTitle title3 = new MarkdownTitle("Title3", 2);
        MarkdownContent list1 = new MarkdownUnorderedList("list1", '*');
        MarkdownContent list2 = new MarkdownUnorderedList("list2", '+');
        root.insertLine(1, title1);
        root.insertLine(2, title2);
        root.insertLine(2, list1);
        assertEquals(3, title1.getLineCount());
        root.insertLine(4, list2);
        assertEquals(2, title2.getLineCount()); 
        root.insertLine(2, title3);
        assertEquals(5, root.getLineCount());
        assertEquals(2, title3.getLineCount());
        assertEquals(title3, list1.getParent());
    }

    @Test
    public void testInsertBefore() throws Exception{
        MarkdownTitle title1 = new MarkdownTitle("title1", 2);
        MarkdownTitle title2 = new MarkdownTitle("title2", 1);
        root.insertLine(1, title1);
        root.insertLine(1, title2);
        assertEquals(2, root.getLineCount());
        assertEquals(2, title2.getLineCount());
        assertEquals(title2, title1.getParent());
    }


    @Test
    public void testInsertAll() throws Exception{
        MarkdownComposite[] title = new MarkdownComposite[7];
        int[] titleindex = {0,1,2,2,3,2,1};
        for (int i = 1; i < 7; i++) {
            title[i] = new MarkdownTitle("Title" + i, titleindex[i]);
        }
        title[0] = new MarkdownTitle("Title4", 1);
        MarkdownUnorderedList list1 = new MarkdownUnorderedList("abc", '*');
        MarkdownUnorderedList list2 = new MarkdownUnorderedList("def", '+');
        MarkdownUnorderedList list3 = new MarkdownUnorderedList("ghi", '-');
        root.insertLine(1, title[1]);
        root.insertLine(2, title[6]);
        root.insertLine(2, title[5]);
        root.insertLine(2, list3);
        root.insertLine(3, list1);
        root.insertLine(4, list2);
        assertEquals(5, title[1].getLineCount());
        assertEquals(6, root.getLineCount());
        root.insertLine(3, title[2]);
        assertEquals(6, title[1].getLineCount());
        root.insertLine(4, title[3]);
        assertEquals(3, title[3].getLineCount());
        assertEquals(7, title[1].getLineCount());
        assertEquals(title[3], list1.getParent());
        root.insertLine(7, title[4]);
        root.insertLine(9, title[0]);
        assertEquals(10, root.getLineCount());
        assertEquals(8, title[1].getLineCount());
        assertEquals(1, title[2].getLineCount());
        assertEquals(4, title[3].getLineCount());
        assertEquals(1, title[4].getLineCount());
        assertEquals(1, title[6].getLineCount());
        assertEquals(title[3], list1.getParent());
        assertEquals(title[3], list2.getParent());
        assertEquals(title[1], list3.getParent());
    }

    @Test(expected = Exception.class)
    public void testInsertLineWithIllegalGrammar() throws Exception {
        MarkdownTitle title1 = new MarkdownTitle("Title1", 1);  
        MarkdownTitle title2 = new MarkdownTitle("Title2", 2);
        MarkdownTitle title3 = new MarkdownTitle("Title3", 3);
        MarkdownContent content = new MarkdownOrderedList("Hello, world!", 10);
        root.insertLine(1, title1);
        root.insertLine(2, title2);
        root.insertLine(3, content);
        root.insertLine(2, title3);
    }
}