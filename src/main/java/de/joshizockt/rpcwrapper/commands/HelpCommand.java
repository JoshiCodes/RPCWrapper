package de.joshizockt.rpcwrapper.commands;

import de.joshizockt.rpcwrapper.util.command.Command;
import de.joshizockt.rpcwrapper.util.command.CommandManager;

public class HelpCommand extends Command {


    public HelpCommand() {
        super("help", "View all commands", "?", "h");
    }

    @Override
    public void execute(String label, String[] args) {
        if(args.length >= 1) {
            String cmd = args[0];
            Command c = null;
            for(Command command : CommandManager.getInstance().getCommands()) {
                if(command.getLabel().equalsIgnoreCase(cmd)) {
                    c = command;
                    break;
                }
                for(String alias : command.getAliases()) {
                    if(alias.equalsIgnoreCase(cmd)) {
                        c = command;
                        break;
                    }
                }
            }
            if(c != null) {
                System.out.println("Command: " + c.getLabel());
                System.out.println("Description: " + c.getDescription());
                System.out.println("Aliases: " + String.join(", ", c.getAliases()));
                return;
            }
        }
        System.out.println("RPCWrapper created by @JoshiCodes on GitHub");
        System.out.println();
        System.out.println("Commands:");
        for(Command command : CommandManager.getInstance().getCommands()) {
            System.out.println("- " + command.getLabel() + " | " + command.getDescription());
        }
        System.out.println();
        System.out.println("Use \"help <command>\" to view more information about a command.");
    }

}
