package de.joshizockt.rpcwrapper.util.command;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    private static CommandManager instance;

    private List<Command> commands;

    public CommandManager() {
        instance = this;
        commands = new ArrayList<>();
    }

    public void registerCommand(Command command) {
        commands.add(command);
    }

    public void handle(String[] args) {
        if(args.length == 0) return;
        String label = args[0];
        if(label.equals("")) return;
        Command c = null;
        for(Command command : commands) {
            if(command.getLabel().equalsIgnoreCase(label)) {
                c = command;
                break;
            }
            for(String alias : command.getAliases()) {
                if(alias.equalsIgnoreCase(label)) {
                    c = command;
                    break;
                }
            }
        }
        if(c != null) {
            String argLabel = args[0];
            String[] newArgs = new String[args.length - 1];
            System.arraycopy(args, 1, newArgs, 0, args.length - 1);
            c.execute(argLabel, newArgs);
        } else
            System.out.println("Unknown command. Type \"help\" for help.");
    }

    public static CommandManager getInstance() {
        if(instance == null) new CommandManager();
        return instance;
    }

    public List<Command> getCommands() {
        return commands;
    }

}
