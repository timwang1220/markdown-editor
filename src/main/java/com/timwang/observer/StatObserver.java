package com.timwang.observer;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.timwang.command.CloseCommand;
import com.timwang.command.Command;
import com.timwang.command.ExitCommand;
import com.timwang.command.LoadCommand;
import com.timwang.command.OpenCommand;
import com.timwang.command.StatCommand;
import com.timwang.markdown.MarkdownFile;
import com.timwang.tools.Tools;
import com.timwang.workspace.WorkSpaceManager;

public class StatObserver implements Observer{
    private Map<String, Duration> fileDuration = new HashMap<String, Duration>();
    private LocalDateTime startTime = LocalDateTime.now();
    private DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
    private String statPath = "./log/stat.log";
    private MarkdownFile currentOpenFile = null;

    private void openFile(MarkdownFile markdownFile) {
        if (currentOpenFile != null) closeCurrentFile();
        currentOpenFile = markdownFile;
        markdownFile.updateOpenTime();
    }
    private void closeCurrentFile() {
        Duration duration = Duration.between(currentOpenFile.getOpenFileTime(), java.time.LocalDateTime.now());
        if (fileDuration.containsKey(currentOpenFile.getFilename())) {
            Duration oldDuration = fileDuration.get(currentOpenFile.getFilename());
            duration = duration.plus(oldDuration);
        }
        fileDuration.put(currentOpenFile.getFilename(), duration);
        currentOpenFile = null;
    }


    private void writeStat() throws IOException{
        if (currentOpenFile != null) closeCurrentFile();
        try {
            Tools.createFileIfNotExists(statPath);
        } catch (Exception e) {
            throw new IOException("Creating StatFile Error.");
        }
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(statPath, true), "UTF-8"))) {
            String startLog = "session start at "+ startTime.format(formatter);
            writer.write(startLog);
            writer.newLine();
            for (String filename : fileDuration.keySet()) {
                String stat = filename + " " + Tools.durationToString(fileDuration.get(filename));
                writer.write(stat);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new IOException("Writing StatFile Error.\n");
        }
    }

    private void showCurrentState(MarkdownFile markdownFile){
        Duration duration = Duration.between(markdownFile.getOpenFileTime(), java.time.LocalDateTime.now());
        if (fileDuration.containsKey(markdownFile.getFilename())) {
            Duration oldDuration = fileDuration.get(markdownFile.getFilename());
            duration = duration.plus(oldDuration);            
        }
        String stat = markdownFile.getFilename() + " " + Tools.durationToString(duration);
        System.out.println(stat);
    }


    private void showAllState(MarkdownFile nowFile){
        String startLog = "session start at "+ startTime.format(formatter);
        System.out.println(startLog);
        for (String filename : fileDuration.keySet()) {
            Duration duration = fileDuration.get(filename);
            if (filename.equals(nowFile.getFilename())) {
                duration = duration.plus(Duration.between(nowFile.getOpenFileTime(), java.time.LocalDateTime.now()));
            }
            String stat = filename + " " + Tools.durationToString(duration);
            System.out.println(stat);
        }
        if (!fileDuration.containsKey(nowFile.getFilename())) {
            Duration duration = Duration.between(nowFile.getOpenFileTime(), java.time.LocalDateTime.now());
            String stat = nowFile.getFilename() + " " + Tools.durationToString(duration);
            System.out.println(stat);
        }
    }


    @Override
    public void update(Command command) {
        if (command instanceof StatCommand){
            StatCommand statCommand = (StatCommand)command;
            if (statCommand.isShowCurrent()) {
                showCurrentState(WorkSpaceManager.getActiveWorkSpace().getMarkdownFile());
            } else {
                showAllState(WorkSpaceManager.getActiveWorkSpace().getMarkdownFile());
            }
        }
        if (command instanceof OpenCommand || command instanceof LoadCommand){
            MarkdownFile markdownFile = WorkSpaceManager.getActiveWorkSpace().getMarkdownFile();
            if (markdownFile != null) {
                openFile(markdownFile);
            }           
        }
        if (command instanceof CloseCommand){
            closeCurrentFile();
        }
        if (command instanceof ExitCommand){
            try {
                writeStat();
            } catch (IOException e) {
                System.out.println("Writing StatFile Error.\n");
            }
        }
    }
}
