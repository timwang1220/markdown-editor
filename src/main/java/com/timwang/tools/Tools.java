package com.timwang.tools;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Stack;

public final class Tools {
    public static String[] StackToArray(Stack<String> stack) {
        int n = stack.size();
        String[] array = new String[n];
        int mid = n / 2;
        for (int i = 0; i <= mid; i++) {
            array[i] = stack.get(n - i - 1);
            array[n - i - 1] = stack.get(i);
        }
        return array;
    }

    public static String[] StackToArray(Stack<String> stack, int index) {
        int n = stack.size();
        String[] array = new String[index];
        int mid = n / 2;
        for (int i = 0; i <= mid && i < index; i++) {
            array[i] = stack.get(n - i - 1);
            if (n - i - 1 < index)
                array[n - i - 1] = stack.get(i);
        }
        return array;
    }

    public static void createFileIFNotExists(String filePath) throws Exception {

        // 检查文件路径是否存在
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    throw new Exception("Error: Cannot create new File");
                }
            } catch (IOException e) {
                throw new Exception("Error: Cannot create new File");
            }
        }
    }

    public static String durationToString(Duration duration) {
        long hours = duration.toHours();
        long minutes = (duration.toMinutes() % 60);
        long seconds = (duration.getSeconds() % 60);
        return  (hours > 0 ? hours + "小时" : "" )  + (minutes > 0 ? minutes + "分钟":"") 
                +  (seconds > 0 ? seconds +"秒" : "");
    }
}