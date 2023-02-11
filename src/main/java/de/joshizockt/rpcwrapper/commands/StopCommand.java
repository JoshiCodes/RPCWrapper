package de.joshizockt.rpcwrapper.commands;

import de.joshizockt.rpcwrapper.util.command.Command;

public class StopCommand extends Command {

    public StopCommand() {
        super("stop", "Stops the RPC Wrapper", "exit", "quit");
    }

    @Override
    public void execute(String label, String[] args) {
        System.out.println("Stopping RPC Wrapper...");
        System.exit(0);
    }

}
