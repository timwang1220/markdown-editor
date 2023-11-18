package com.timwang.log;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.timwang.command.OperatingCommand;
import com.timwang.markdown.MarkdownFile;
import com.timwang.tools.Tools;

public class Stat {
    private static Map<String, Duration> fileDuration = new HashMap<String, Duration>();
    private static LocalDateTime startTime = LocalDateTime.now();
    private static DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
    private static String statPath = "./log/stat.log";
    public static void closeFile(MarkdownFile markdownFile) {
        Duration duration = Duration.between(markdownFile.getOpenFileTime(), java.time.LocalDateTime.now());
        if (fileDuration.containsKey(markdownFile.getFilename())) {
            Duration oldDuration = fileDuration.get(markdownFile.getFilename());
            duration = duration.plus(oldDuration);
        }
        fileDuration.put(markdownFile.getFilename(), duration);
    }


    public static void writeStat() throws IOException{
        if (OperatingCommand.getOperatingFile() != null)
            closeFile(OperatingCommand.getOperatingFile());
        try {
            Tools.createFileIFNotExists(statPath);
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

    public static void showCurrentState(MarkdownFile markdownFile){
        Duration duration = Duration.between(markdownFile.getOpenFileTime(), java.time.LocalDateTime.now());
        if (fileDuration.containsKey(markdownFile.getFilename())) {
            Duration oldDuration = fileDuration.get(markdownFile.getFilename());
            duration = duration.plus(oldDuration);            
        }
        String stat = markdownFile.getFilename() + " " + Tools.durationToString(duration);
        System.out.println(stat);
    }


    public static void showAllState(MarkdownFile nowFile){
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
}
