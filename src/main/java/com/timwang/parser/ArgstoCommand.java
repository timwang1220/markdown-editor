package com.timwang.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.timwang.command.AppendHeadCommand;
import com.timwang.command.AppendTailCommand;
import com.timwang.command.Command;
import com.timwang.command.DeleteCommand;
import com.timwang.command.DirTreeCommand;
import com.timwang.command.ExitCommand;
import com.timwang.command.HistoryCommand;
import com.timwang.command.InsertCommand;
import com.timwang.command.ListCommand;
import com.timwang.command.ListTreeCommand;
import com.timwang.command.LoadCommand;
import com.timwang.command.RedoCommand;
import com.timwang.command.SaveCommand;
import com.timwang.command.StatCommand;
import com.timwang.command.UndoCommand;

public class ArgstoCommand {
    private static Map<String, Function<String, Command>> commandMap;
    static {
        commandMap = new HashMap<>();
        commandMap.put("load", LoadCommand::new);
        commandMap.put("save", SaveCommand::new);
        commandMap.put("append-head", AppendHeadCommand::new);
        commandMap.put("append-tail", AppendTailCommand::new);
        commandMap.put("insert", InsertCommand::new);
        commandMap.put("delete", DeleteCommand::new);
        commandMap.put("list-tree", ListTreeCommand::new);
        commandMap.put("list", ListCommand::new);
        commandMap.put("dir-tree", DirTreeCommand::new);
        commandMap.put("undo",UndoCommand::new);
        commandMap.put("redo",RedoCommand::new);
        commandMap.put("history", HistoryCommand::new);
        commandMap.put("stats", StatCommand::new);
        commandMap.put("exit", ExitCommand::new);
        
    }

    public static Command getCommand(String args) throws Exception {
        String[] argsArray = args.split(" ", 2);
        if (commandMap.containsKey(argsArray[0])) {
            if (argsArray.length == 1)
                return commandMap.get(argsArray[0]).apply("");
            else
                return commandMap.get(argsArray[0]).apply(argsArray[1]);
        } else
            throw new Exception("Invalid command");

    }
}
