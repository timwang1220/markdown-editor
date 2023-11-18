package com.timwang;

import com.timwang.command.Command;
import com.timwang.command.CommandExecutor;
import com.timwang.log.Log;
import com.timwang.log.Stat;
import com.timwang.parser.ArgstoCommand;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args){
        while (true) {
            try {
                System.out.print(">");
                String input = System.console().readLine();
                if (input.equals("exit") || input.equals("quit")) {
                    System.out.println("Bye!");
                    Stat.writeStat();
                    return;
                }
                Command command = ArgstoCommand.getCommand(input);
                CommandExecutor.getInstance().execute(command);
                Log.addLog(input);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }
}
