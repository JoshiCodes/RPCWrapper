package de.joshizockt.rpcwrapper;

import de.joshizockt.rpcwrapper.commands.*;
import de.joshizockt.rpcwrapper.util.command.CommandManager;
import net.arikia.dev.drpc.DiscordRPC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] startupArgs) {

        new RPCWrapper();
        // Run Callbacks
        int interval = RPCWrapper.getInstance().getConfig().getInt("timings.updateInterval", 1000);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                DiscordRPC.discordRunCallbacks();
            }
        }, interval, interval);


        CommandManager commandManager = CommandManager.getInstance();

        commandManager.registerCommand(new StopCommand());
        commandManager.registerCommand(new HelpCommand());
        commandManager.registerCommand(new RefreshCommand());
        commandManager.registerCommand(new RestartCommand());
        commandManager.registerCommand(new PresetCommand());

        // Read Console
        new Thread(() -> {
            while(true) {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    String line = reader.readLine();
                    String[] args = line.split(" ");

                    commandManager.handle(args);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

    }

}
