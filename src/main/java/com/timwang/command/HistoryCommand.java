package com.timwang.command;

import com.timwang.log.Log;

public class HistoryCommand implements Command {
    private int historyNum;

    public HistoryCommand(String args) {
        if (args.matches("\\d+")) {
            historyNum = Integer.parseInt(args);
        } 
        else historyNum = -1;

    }

    @Override
    public void execute() throws Exception {
        if (historyNum == -1) {
            System.out.println(String.join("\n", Log.getAllLogs()));
        } else {
            System.out.println(String.join("\n", Log.getRecentLogs(historyNum)));
        }

    }
    public void maintainStack() {
        return;
    }

}
