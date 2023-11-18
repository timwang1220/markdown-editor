package com.timwang.log;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Stack;

import com.timwang.tools.Tools;

public class Log {
    private static Stack<String> logsInfo = new Stack<String>();
    private static DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
    private final static String logPath = "./log/history.log"; 
    static{
        String startlog = "session start at "+ LocalDateTime.now().format(formatter);
        logsInfo.push(startlog);
        try {
            writeLog(startlog);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void writeLog(String log) throws IOException {
        // if logPath not exists, create it
        try {
            Tools.createFileIFNotExists(logPath);
        } catch (Exception e) {
            throw new IOException("Creating LogFile Error.");
        }
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logPath, true), "UTF-8"))) {
            writer.write(log);
            writer.newLine();
        } catch (IOException e) {
            throw new IOException("Writing LogFile Error.");
        }
    }
    public static void initialize() {
        logsInfo.clear();
        String startlog = "session start at "+ LocalDateTime.now().format(formatter);
        logsInfo.push(startlog);
    }
    public static void addLog(String log) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        log = now.format(formatter) + " " + log;
        logsInfo.push(log);
        writeLog(log);
    } 
    public static String[] getAllLogs() {
        return Tools.StackToArray(logsInfo);
    }
    public static String[] getRecentLogs(int n){
        if (n >= logsInfo.size()) {
            return getAllLogs();
        }
        return Tools.StackToArray(logsInfo, n);
    }
}
