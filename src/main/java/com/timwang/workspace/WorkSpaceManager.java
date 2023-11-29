package com.timwang.workspace;

import java.util.ArrayList;
import java.util.Scanner;

public class WorkSpaceManager {
    private static ArrayList<WorkSpace> workSpaces = new ArrayList<WorkSpace>();
    private static WorkSpace activeWorkSpace;

    public static void switchWorkSpace(String workSpaceName) {
        for (WorkSpace workSpace : workSpaces) {
            if (workSpace.getName().equals(workSpaceName)) {
                activeWorkSpace.setActive(false);
                activeWorkSpace = workSpace;
                workSpace.setActive(true);
                return;
            }
        }
        throw new RuntimeException("No such workspace");
    }

    public static void newWorkSpace(String workSpaceName) throws Exception {
        for (WorkSpace workSpace : workSpaces) {
            if (workSpace.getName().equals(workSpaceName)) {
                throw new Exception("WorkSpace already exists");
            }
        }
        WorkSpace workSpace = new WorkSpace(workSpaceName);
        workSpaces.add(workSpace);
        if (activeWorkSpace != null) activeWorkSpace.setActive(false);
        activeWorkSpace = workSpace;
        workSpace.setActive(true);
    }

    public static WorkSpace closeCurrentWorkSpace() throws Exception {
        if (activeWorkSpace == null) {
            throw new Exception("No active workspace");
        }
        if (activeWorkSpace.getMarkdownFile().isDirty()) {

            System.out.println("Do you want to save the current workspace [Y\\N] ?");
            Scanner scanner = new Scanner(System.in);
            char userInput = scanner.next().charAt(0);
            if (userInput == 'Y' || userInput == 'y') {
                activeWorkSpace.getMarkdownFile().save();
            }
            scanner.close();

            activeWorkSpace.getMarkdownFile().setClean();
        }
        activeWorkSpace.setActive(false);
        activeWorkSpace.getMarkdownFile().close();
        workSpaces.remove(activeWorkSpace);
        WorkSpace workSpace = activeWorkSpace;
        activeWorkSpace = null;
        return workSpace;
    }

    public static ArrayList<WorkSpace> getAllWorkSpaces() {
        return workSpaces;
    }

    public static WorkSpace getActiveWorkSpace() {
        return activeWorkSpace;
    }

    private enum SaveOption {
        SAVE, DISCARD, UNSET
    }
    public static void closeAllWorkSpace() throws Exception {
        SaveOption saveOption = SaveOption.UNSET;
        for (WorkSpace workSpace : workSpaces) {
            if (workSpace.getMarkdownFile().isDirty()) {
                if (saveOption == SaveOption.UNSET) {
                    System.out.println("Do you want to save the unsaved workspace [Y\\N] ?");
                    Scanner scanner = new Scanner(System.in);
                    char userInput = scanner.next().charAt(0);
                    if (userInput == 'Y' || userInput == 'y') {
                        saveOption = SaveOption.SAVE;
                    } else {
                        saveOption = SaveOption.DISCARD;
                    }
                    scanner.close();
                }
                if (saveOption == SaveOption.SAVE) {
                    workSpace.getMarkdownFile().save();
                }
            }
            workSpace.getMarkdownFile().close();
        }
        workSpaces.clear();
        activeWorkSpace = null;
    }

    public static void clear() {
        workSpaces.clear();
        activeWorkSpace = null;
    }

    public static void listAllWorkSpace(){
        for (WorkSpace workSpace : workSpaces) {
            String ouString = "";
            if (!workSpace.isActive()) {
                ouString += "  ";
            }
            else ouString += "->";
            ouString += workSpace.getName();
            if (workSpace.getMarkdownFile().isDirty()) {
                ouString += " *";
            }
            System.out.print(ouString + "\n");
        }
    }

}
