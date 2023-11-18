package com.timwang;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

import com.timwang.markdown.MarkdownComposite;
import com.timwang.markdown.MarkdownLine;
import com.timwang.markdown.MarkdownRoot;
import com.timwang.markdown.MarkdownTitle;
import com.timwang.markdown.MarkdownUnorderedList;

public class MarkdownDeleteTest {
    private MarkdownComposite root;
    @Before
    public void setUp() {
        root = new MarkdownRoot();
    }

    @Test
    public void deleteByStringTest() throws Exception {
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
        root.insertLine(3, title[2]);
        root.insertLine(4, title[3]);
        root.insertLine(7, title[4]);
        root.insertLine(9, title[0]);
        assertEquals(10, root.getLineCount());
        LinkedHashMap<Integer, MarkdownLine> returnNums1 = new LinkedHashMap<Integer, MarkdownLine>();
        TreeMap<Integer, MarkdownLine> returnNums = new TreeMap<Integer, MarkdownLine>();
        root.findByString("Title5", 0, returnNums1);
        for (Integer i : returnNums1.keySet()) {
            returnNums.put(i, returnNums1.get(i));
        }
        root.deleteByString("Title5", 0);
        assertEquals(1, returnNums.size());
        assertEquals(8, returnNums.firstKey().intValue());
        assertEquals(9, root.getLineCount());
        assertEquals(7, title[1].getLineCount());
        returnNums.clear();
        returnNums1.clear();
        //duplicated delete
        root.findByString("Title4", 0, returnNums1);
        System.out.println(returnNums1.size());
        for (Integer i : returnNums1.keySet()) {
            returnNums.put(i, returnNums1.get(i));
        }
        root.deleteByString("Title4", 0);
        assertEquals(2, returnNums.size());
        assertEquals(7, returnNums.firstKey().intValue());
        assertEquals(3, returnNums.get(7).getProirity());
        assertEquals(8, returnNums.lastKey().intValue());
        assertEquals(1, returnNums.get(8).getProirity());
        returnNums.clear();
        returnNums1.clear();
        //delete and graft
        root.findByString("Title3", 0, returnNums1);
        for (Integer i : returnNums1.keySet()) {
            returnNums.put(i, returnNums1.get(i));
        }
        root.deleteByString("Title3", 0);
        assertEquals(1, returnNums.size());
        assertEquals(4, returnNums.firstKey().intValue());
        assertEquals(3, title[2].getLineCount());
        assertEquals(5, title[1].getLineCount());
        assertEquals(6, root.getLineCount());

    }
}
