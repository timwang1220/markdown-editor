package com.timwang.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.timwang.markdown.MarkdownLine;
import com.timwang.markdown.MarkdownOrderedList;
import com.timwang.markdown.MarkdownTitle;
import com.timwang.markdown.MarkdownUnorderedList;

public class StringToMDLine {
    public static MarkdownLine trans(String str) throws Exception{
        String[] strings = str.split(" ",2);
        Pattern titleRegex = Pattern.compile("#{1,5}");
        Pattern orderedListRegex = Pattern.compile("(\\d+)\\.");
        Matcher orderedMatcher = orderedListRegex.matcher(strings[0]);
        Pattern unorderedListPattern = Pattern.compile("[*+-]");
        if (titleRegex.matcher(strings[0]).matches()){
            return new MarkdownTitle(strings[1], strings[0].length());
        }
        else if (orderedMatcher.find()){
            int order = Integer.parseInt(orderedMatcher.group(1));
            return new MarkdownOrderedList(strings[1], order);
        }
        else if (unorderedListPattern.matcher(strings[0]).matches()){
            return new MarkdownUnorderedList(strings[1], strings[0].charAt(0));
        }
        else{
            throw new Exception("Invalid Markdown Line.");
        }
    }
}
