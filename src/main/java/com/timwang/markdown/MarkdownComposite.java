package com.timwang.markdown;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.TreeMap;

public abstract class MarkdownComposite extends MarkdownLine {
    protected ArrayList<MarkdownLine> subLines = new ArrayList<MarkdownLine>();

    public abstract void acceptInsertLine(int count);

    public abstract void acceptDeleteLine(int count);

    private void insertAfterTitle(MarkdownTitle title, MarkdownTitle taggedTitle) {
        int listIndex = 0;
        for (MarkdownLine subLine : subLines) {
            if (subLine == taggedTitle) {
                listIndex++;
                subLines.add(listIndex, title);
                break;
            }
            listIndex++;
        }
    }

    public void clear() {
        this.subLines.clear();
    }

    public void insertLine(int lineNum, MarkdownLine line) throws Exception {
        // 对边界进行检查，title与root的linecount计算方式不同
        if (lineNum > lineCount || lineNum < 1)
            throw new Exception("Cannot Insert Line here: Line Number Out of Range.");
        if (lineNum == lineCount) {
            this.appendLine(line);
            return;
        }
        int listIndex = 0;
        int lineIndex = 1;
        for (MarkdownLine subLine : subLines) {
            if (lineIndex == lineNum && line instanceof MarkdownContent) {
                subLines.add(listIndex, line);
                line.setParent(this);
                acceptInsertLine(line.getLineCount());
                break;
            }
            if (lineIndex == lineNum && line instanceof MarkdownTitle) {
                if (this.priority + 1 == line.priority) {
                    int transNum = 0;
                    for (int i = listIndex; i < subLines.size(); i++) {
                        MarkdownLine transline = subLines.get(i);
                        if (transline.getProirity() <= line.getProirity())
                            break;
                        transNum++;
                        transline.setParent((MarkdownTitle) line);
                        ((MarkdownTitle) line).subLines.add(transline);
                    }
                    subLines.subList(listIndex, listIndex + transNum).clear();
                    subLines.add(listIndex, line);
                    line.setParent(this);
                    acceptInsertLine(line.getLineCount());
                    line.lineCount += transNum;
                    break;
                }
                if (this.priority == line.priority) {
                    // 插入权值与当前权值相同时，需要完成嫁接操作，将原节点的后面所有sublines嫁接到新节点上
                    for (int i = listIndex; i < subLines.size(); i++) {
                        MarkdownLine transline = subLines.get(i);
                        transline.setParent(((MarkdownTitle) line));
                        ((MarkdownTitle) line).subLines.add(transline);
                    }
                    subLines.subList(listIndex, subLines.size()).clear();
                    line.setParent(this.parent);
                    this.parent.acceptInsertLine(line.getLineCount());
                    this.parent.insertAfterTitle((MarkdownTitle) line, (MarkdownTitle) this);
                    line.lineCount = lineCount - lineIndex + 1;
                    this.lineCount = lineIndex;
                    break;
                } else
                    throw new Exception("Cannot Insert Title here: Illgeal Grammar.");
            }
            if (subLine instanceof MarkdownTitle && lineIndex < lineNum
                    && lineNum < lineIndex + subLine.getLineCount()) {
                ((MarkdownTitle) subLine).insertLine(lineNum - lineIndex, line);
                break;
            }
            if (subLine instanceof MarkdownTitle && lineNum == lineIndex + subLine.getLineCount() &&
                    line.getProirity() > this.getProirity() + 1) {
                ((MarkdownTitle) subLine).appendLine(line);
                break;
            }
            lineIndex += subLine.getLineCount();
            listIndex++;
        }
        return;
    }

    public void appendLine(MarkdownLine line) throws Exception {
        if (line.getProirity() == this.priority + 1) {
            subLines.add(line);
            line.setParent(this);
            acceptInsertLine(line.getLineCount());
        } else if ((subLines.isEmpty() || subLines.get(subLines.size() - 1) instanceof MarkdownContent)) {
            if (line instanceof MarkdownTitle)
                throw new Exception("Cannot Insert Title here: Illgeal Grammar.");
            else {
                subLines.add(line);
                line.setParent(this);
                acceptInsertLine(line.getLineCount());
            }
        } else
            ((MarkdownTitle) (subLines.get(subLines.size() - 1))).appendLine(line);
    }

    protected void delete() throws Exception {
        int index = parent.subLines.indexOf(this);
        if ((index == 0 || parent.subLines.get(index - 1) instanceof MarkdownContent)
                && !(parent instanceof MarkdownRoot)) {
            // 如果是第一个元素或者前一个元素是content且父亲不是root，那只有全content情况可以删除该标题
            for (MarkdownLine subLine : this.subLines) {
                if (subLine instanceof MarkdownTitle) {
                    throw new Exception("Cannot Delete Title here: Illgeal Grammar.");
                }
            }
            parent.subLines.remove(this);
            for (int i = 0; i < this.subLines.size(); i++) {
                subLines.get(i).setParent(parent);
                parent.subLines.add(i + index, subLines.get(i));
            }
            this.parent.acceptDeleteLine(1);
            this.subLines.clear();
            return;
        } else if (index == 0) {
            // 此时parent = root且index为0
            int count = 0;
            this.parent.subLines.remove(this);
            for (MarkdownLine subLine : this.subLines) {
                subLine.setParent(this.parent);
                parent.subLines.add(count, subLine);
                count++;
            }
            this.acceptDeleteLine(1);
            this.parent = null;
            this.subLines.clear();
            this.lineCount = 1;
        } else {
            // 如果前一个元素是标题，那将所有元素嫁接append到前一个标题上，由前一个标题判断是否合法
            MarkdownComposite preLine = (MarkdownComposite) parent.subLines.get(index - 1);
            try {
                for (MarkdownLine subLine : this.subLines) {
                    preLine.appendLine(subLine);
                }
                parent.subLines.remove(this);
                this.subLines.clear();
                acceptDeleteLine(this.lineCount);
                this.lineCount = 1;
                this.parent = null;
            } catch (Exception e) {
                throw new Exception("Cannot Delete Title here: Illgeal Grammar.");
            }

            return;
        }
    }

    public void deleteByString(String line, int lineIndex) throws Exception {
        LinkedHashMap<Integer, MarkdownLine> findMap = new LinkedHashMap<>();
        findByString(line, lineIndex, findMap);
        TreeMap<Integer,MarkdownLine> deletedMap = new TreeMap<Integer, MarkdownLine>();
        for (Integer key : findMap.keySet()) {
            deletedMap.put(key, findMap.get(key));
        }
        for (Integer key : deletedMap.keySet()) {
            MarkdownLine line2 = deletedMap.get(key);
            line2.delete();
        }
    }

    public MarkdownLine deleteByLineNum(int lineNum) throws Exception {
        MarkdownLine line = findByLineNum(lineNum);
        if (line == null)
            throw new Exception("Cannot Delete Line here: Line Number Out of Range.");
        line.delete();
        return line;
    }

    public MarkdownLine findByLineNum(int lineNum) {
        int lineIndex = 1;
        for (MarkdownLine subLine : subLines) {
            if (lineNum == lineIndex)
                return subLine;
            if (subLine instanceof MarkdownComposite && lineNum > lineIndex
                    && lineNum < lineIndex + subLine.getLineCount()) {
                return ((MarkdownTitle) subLine).findByLineNum(lineNum - lineIndex);
            }
            lineIndex += subLine.getLineCount();
        }
        return null;
    }

    public void findByString(String line, int lineIndex, LinkedHashMap<Integer, MarkdownLine> lineMap) {
        if (this.subLines.isEmpty())
            return;
        int temp2 = lineIndex + 1;
        for (MarkdownLine subLine : subLines) {
            if (subLine instanceof MarkdownComposite)
                ((MarkdownComposite) subLine).findByString(line, temp2, lineMap);
            temp2 += subLine.getLineCount();
        }
        int temp1 = lineIndex + 1;
        for (MarkdownLine subLine : subLines) {
            if (subLine.getLine().equals(line)) {
                lineMap.put(temp1, subLine);
            }
            temp1 += subLine.getLineCount();
        }

        return;
    }

    public ArrayList<MarkdownLine> getSubLines() {
        return subLines;
    }

}
