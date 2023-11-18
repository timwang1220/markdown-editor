package com.timwang.display;


import com.timwang.markdown.MarkdownContent;
import com.timwang.markdown.MarkdownLine;
import com.timwang.markdown.MarkdownRoot;
import com.timwang.markdown.MarkdownComposite;

public class TreeDisplay implements Display {
    static final char NORAML_BEGIN = '├';
    static final char LAST_BEGIN = '└';

    private static void levelDisplay(MarkdownLine line, int level, Boolean[] isLast) {
        if (!(line instanceof MarkdownRoot)) {
            String ouString = "";
            for (int i = 1; i < level; i++) {
                if (!isLast[i])
                    ouString += "│    ";
                else
                    ouString += "    ";
            }
            ouString += (isLast[level] ? LAST_BEGIN : NORAML_BEGIN);
            ouString += "── ";
            ouString += line.getDisplayLine();
            System.out.println(ouString);
        }
        if (line instanceof MarkdownContent) {
            return;
        } 
        else {
            for (int i = 0; i < ((MarkdownComposite) line).getSubLines().size(); i++) {
                if (i == ((MarkdownComposite) line).getSubLines().size() - 1)
                    isLast[level + 1] = true;
                levelDisplay(((MarkdownComposite) line).getSubLines().get(i), level + 1, isLast);
                isLast[level + 1] = false;
            }
        }
    }

    @Override
    public void display(MarkdownLine line) {
        Boolean[] islast = new Boolean[10];
        for (int i = 0; i < 10; i++)
            islast[i] = false;
        if (line instanceof MarkdownRoot)
            levelDisplay(line, 0, islast);
        else {
            islast[1] = true;
            levelDisplay(line, 1, islast);
        }

    }

}
