package com.timwang.display;

import com.timwang.markdown.MarkdownComposite;
import com.timwang.markdown.MarkdownLine;

public class ListDisplay implements Display {

    @Override
    public void display(MarkdownLine line) {
        if (line.toMDString().length() > 0) System.out.println(line.toMDString());
        if (line instanceof MarkdownComposite){
            for (MarkdownLine subLine : ((MarkdownComposite) line).getSubLines()) {
                display(subLine);
            }
        }
    }

}
