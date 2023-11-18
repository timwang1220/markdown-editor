package com.timwang.markdown;

public class MarkdownRoot extends MarkdownComposite {
    public MarkdownRoot(){
        this.priority = rootPriority;
        this.line = "";
        this.lineCount = 0;
    }
    public void acceptInsertLine(int count){
        lineCount += count;
    }
    public void acceptDeleteLine(int count){
        lineCount -= count;
    }
    public String toMDString(){
        return "";
    }
    @Override
    public void insertLine(int lineNum, MarkdownLine line) throws Exception {
        // 对边界进行检查，title与root的linecount计算方式不同
        if (lineNum > lineCount + 1 || lineNum < 1) 
            throw new Exception("Cannot Insert Line here: Line Number Out of Range.");
        if (lineNum == lineCount + 1){
            this.appendLine(line);
            return;
        }
        int listIndex = 0; int lineIndex = 1;
        for (MarkdownLine subLine : subLines) {
            if (lineIndex == lineNum && line instanceof MarkdownContent) {
                subLines.add(listIndex, line);
                line.setParent(this);
                acceptInsertLine(line.getLineCount());
                break;
            }
            if (lineIndex == lineNum && line instanceof MarkdownTitle) { 
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
            listIndex ++;
        }
        return;
    }

    @Override
    public void appendLine(MarkdownLine line) throws Exception {
        if (subLines.isEmpty()){
            subLines.add(line);
            line.setParent(this);
            acceptInsertLine(line.getLineCount());
            return;
        }
        MarkdownLine lastLine = subLines.get(subLines.size() - 1);
        if (lastLine instanceof MarkdownTitle && line.getProirity() > this.priority + 1 && lastLine.getProirity() < line.getProirity()) {
            ((MarkdownTitle) lastLine).appendLine(line);
            return;
        }
        else{
            subLines.add(line);
            line.setParent(this);
            acceptInsertLine(line.getLineCount());
            return;
        }
    }
}
