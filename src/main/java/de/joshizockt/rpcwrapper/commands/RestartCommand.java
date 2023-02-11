package de.joshizockt.rpcwrapper.commands;

import de.joshizockt.rpcwrapper.RPCWrapper;
import de.joshizockt.rpcwrapper.util.command.Command;
import net.arikia.dev.drpc.DiscordRPC;

import java.util.Timer;
import java.util.TimerTask;

public class RestartCommand extends Command {


    public RestartCommand() {
        super("restart", "Restarts the RPC", "reboot");
    }

    @Override
    public void execute(String label, String[] args) {
        System.out.println("Stopping the RPC...");
        DiscordRPC.discordShutdown();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Starting the RPC...");
                RPCWrapper.getInstance().start();
                System.out.println("RPC restarted! May take a few seconds to update.");
            }
        }, 1000);
    }

}
