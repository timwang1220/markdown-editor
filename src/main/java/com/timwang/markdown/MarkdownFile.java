package com.timwang.markdown;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.TreeMap;

import com.timwang.log.Stat;
import com.timwang.processor.FileProcessor;

public class MarkdownFile {
    private String filename;
    private MarkdownRoot root;
    private LocalDateTime openFileTime;
    private boolean dirty;

    public MarkdownFile(String filename) throws Exception {
        openFileTime = LocalDateTime.now();
        this.filename = filename;
        this.root = new MarkdownRoot();
        this.dirty = false;
        FileProcessor.initFile(filename, root);
    }
    public void insert(int lineNum, MarkdownLine line) throws Exception {
        root.insertLine(lineNum, line);
    }
    public void save() throws Exception {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF-8"));
        FileProcessor.writeFile(writer, root);
        writer.close();
    }
    public int append(MarkdownLine line) throws Exception{
        root.appendLine(line);
        return root.getLineCount();
    }
    public MarkdownLine deleteByLineNum(int lineNum) throws Exception{
        return root.deleteByLineNum(lineNum);
    }
    public TreeMap<Integer,MarkdownLine> deleteByString(String line) throws Exception{
        TreeMap<Integer, MarkdownLine> lineMap = new TreeMap<Integer, MarkdownLine>();
        LinkedHashMap<Integer, MarkdownLine> lineMap2 = new LinkedHashMap<Integer, MarkdownLine>();
        root.findByString(line, 0, lineMap2);
        for (Integer i : lineMap2.keySet()) {
            lineMap.put(i, lineMap2.get(i));
        }
        root.deleteByString(line, getFileLine());
        return lineMap;
    }
    public void close(){
        Stat.closeFile(this);
    }
    public int getFileLine(){
        return root.getLineCount();
    }
    public MarkdownRoot getRoot(){
        return root;
    }
    public LocalDateTime getOpenFileTime(){
        return openFileTime;
    }
    public String getFilename(){
        return filename;
    }
    public boolean isDirty(){
        return dirty;
    }
    public void setClean(){
        this.dirty = false;
    }
    public void setDirty(){
        this.dirty = true;
    }

}
