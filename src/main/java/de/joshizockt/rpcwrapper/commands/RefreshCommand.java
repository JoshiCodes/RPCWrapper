package de.joshizockt.rpcwrapper.commands;

import de.joshizockt.rpcwrapper.RPCWrapper;
import de.joshizockt.rpcwrapper.util.command.Command;
import net.arikia.dev.drpc.DiscordRPC;

public class RefreshCommand extends Command {

    public RefreshCommand() {
        super("refresh", "Refreshes the Rich Presence", "r", "update");
    }

    @Override
    public void execute(String label, String[] args) {
        boolean force = false;
        if(args.length >= 1 && args[0].equalsIgnoreCase("force")) {
            force = true;
            DiscordRPC.discordClearPresence();
        }
        System.out.println("Refreshing Rich Presence..." + (force ? " (Forced)" : ""));
        RPCWrapper.getInstance().refresh();
        System.out.println("Done! If you don't see anything, try " + (force ? "to restart the application." : "running \"refresh force\" to force an update."));
    }

}
