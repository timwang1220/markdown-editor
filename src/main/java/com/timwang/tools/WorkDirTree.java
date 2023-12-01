package com.timwang.tools;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

public class WorkDirTree {
    private static boolean Last[] = new boolean[100];
    public static void listMarkdownFiles(String directory) {
        listMarkdownFiles(new File(directory), -1, true);
        Arrays.fill(Last, false);
    }

    private static void listMarkdownFiles(File dir, int depth, boolean isLast) {
        String header = isLast ? "└── " : "├── ";
        if (depth >= 0) Last[depth] = isLast;
        if (dir.isDirectory()) {
            if (dir.getName().startsWith(".") && !dir.getName().equals(".")) return; // Skip hidden directories
            if (depth >= 0) System.out.println(getIndentation(depth) + header + dir.getName());
            File[] files = dir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return file.isDirectory() || (file.isFile() && file.getName().toLowerCase().endsWith(".md"));
                }
            });
            if (files.length == 0) return;
            for (int i = 0; i < files.length - 1; i++) {
                listMarkdownFiles(files[i], depth + 1, false);
            }
            listMarkdownFiles(files[files.length - 1], depth + 1, true);
        } else if (dir.isFile() && dir.getName().toLowerCase().endsWith(".md")) {
            // Print the file path with indentation based on depth
            String filename = dir.toString().split(Pattern.quote("\\"))[dir.toString().split(Pattern.quote("\\")).length - 1];
            System.out.println(getIndentation(depth) + header + filename);
        }
    }

    private static String getIndentation(int depth) {
        StringBuilder indentation = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            if (Last[i]) indentation.append("    ");
            else indentation.append("│    ");
        }
        return indentation.toString();
    }
}
