package de.joshizockt.rpcwrapper;

import de.joshizockt.rpcwrapper.config.YamlConfig;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args) {
        YamlConfig config = new YamlConfig(new File("config.yml"));
        config.copyDefaults();


        // Starting RPC
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {
            System.out.println("Ready for User " + user.username + "#" + user.discriminator + " (" + user.userId + ")!");

            // Initial Presence
            DiscordRichPresence presence = new DiscordRichPresence.Builder(config.get("defaults.rpc.state") + "").setBigImage("logo", "RPCWrapper").setDetails(config.get("defaults.rpc.details") + "").setStartTimestamps(System.currentTimeMillis() / 1000).build();
            updatePresence(presence);

        }).build();
        DiscordRPC.discordInitialize(config.get("defaults.rpc.clientId") + "", handlers, true);

        // Run Callbacks
        int interval = config.getInt("timings.updateInterval", 1000);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                DiscordRPC.discordRunCallbacks();
            }
        }, interval, interval);

    }

    public static void updatePresence(DiscordRichPresence presence) {
        DiscordRPC.discordUpdatePresence(presence);
    }

}
