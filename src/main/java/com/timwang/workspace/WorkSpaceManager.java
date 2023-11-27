package com.timwang.workspace;

import java.util.ArrayList;
import java.util.Scanner;

public class WorkSpaceManager {
    private static ArrayList<WorkSpace> workSpaces = new ArrayList<WorkSpace>();
    private static WorkSpace activeWorkSpace;
    public static void switchWorkSpace(String workSpaceName){
        for (WorkSpace workSpace : workSpaces) {
            if (workSpace.getName().equals(workSpaceName)) {
                activeWorkSpace.setActive(false);
                activeWorkSpace = workSpace;
                workSpace.setActive(true);
            }
        }
        throw new RuntimeException("No such workspace");
    }

    public static void newWorkSpace(String workSpaceName) throws Exception{
        for (WorkSpace workSpace : workSpaces) {
            if (workSpace.getName().equals(workSpaceName)) {
                throw new Exception("WorkSpace already exists");
            }
        }
        WorkSpace workSpace = new WorkSpace(workSpaceName);
        workSpaces.add(workSpace);
        activeWorkSpace = workSpace;
        workSpace.setActive(true);
    }
    
    public static void closeCurrentWorkSpace() throws Exception{
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
        }
        activeWorkSpace.getMarkdownFile().close();
        workSpaces.remove(activeWorkSpace);
        activeWorkSpace = null;
    }


    public static WorkSpace getActiveWorkSpace() {
        return activeWorkSpace;
    }

    public static void closeAllWorkSpace() {
        for (WorkSpace workSpace : workSpaces) {
            try {
                workSpace.getMarkdownFile().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
