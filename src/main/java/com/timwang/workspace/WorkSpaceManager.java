package com.timwang.workspace;

import java.util.ArrayList;
import java.util.Scanner;

import com.timwang.memento.WorkSpaceMemento;



public class WorkSpaceManager {
    private static ArrayList<WorkSpace> workSpaces;
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

    public static void newWorkSpace(String fileName) throws Exception {
        for (WorkSpace workSpace : workSpaces) {
            if (workSpace.getMarkdownFile().getFilename().equals(fileName)) {
                throw new Exception("WorkSpace already exists");
            }
        }
        WorkSpace workSpace = new WorkSpace(fileName);
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
        WorkSpaceMemento memento = backUp();
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
        }
        if (saveOption == SaveOption.SAVE || saveOption == SaveOption.UNSET) {
            memento.saveToDisk();
        }
        workSpaces.clear();
        activeWorkSpace = null;
    }

    public static void clear() {
        workSpaces = new ArrayList<WorkSpace>();
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

    public static WorkSpaceMemento backUp() throws Exception{
        return new WorkSpaceMemento(workSpaces, activeWorkSpace);
    }

   

    public static void init() {
        WorkSpaceMemento memento = WorkSpaceMemento.recoverFromJsonFile();
        if (memento == null) {
            workSpaces = new ArrayList<WorkSpace>();
            activeWorkSpace = null;
            return;
        }
        workSpaces = memento.getWorkSpaces();
        activeWorkSpace = memento.getActiveWorkSpace();

    }


}
