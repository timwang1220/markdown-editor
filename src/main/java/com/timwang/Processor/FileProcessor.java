package com.timwang.Processor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.timwang.markdown.MarkdownComposite;
import com.timwang.markdown.MarkdownLine;
import com.timwang.markdown.MarkdownRoot;
import com.timwang.parser.StringToMDLine;
import com.timwang.tools.Tools;

public class FileProcessor {
    public static void initFile(String filePath, MarkdownRoot root) throws Exception {
        // open the file and read each line, invoke StringToMDLine
        // and add the line to the root
        // use utf-8
        Tools.createFileIFNotExists(filePath);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                root.insertLine(lineNumber, StringToMDLine.trans(line));
                lineNumber++;
            }
        } catch (Exception e) {
            throw new Exception("Invalid Markdown File.");
        }
    }
    
    public static void writeFile(BufferedWriter writer, MarkdownLine line) throws Exception{
            if (line.toMDString().length() > 0){
                writer.write(line.toMDString());
                writer.newLine();
            }
            if (line instanceof MarkdownComposite){
                for (MarkdownLine subLine : ((MarkdownComposite) line).getSubLines()){
                    writeFile(writer, subLine);
                }
            }
       
    }
}
