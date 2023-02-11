package de.joshizockt.rpcwrapper;

import de.joshizockt.rpcwrapper.util.config.YamlConfig;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

import java.io.File;

public class RPCWrapper {

    private static RPCWrapper instance;

    private YamlConfig config;

    private DiscordRichPresence currentPresence;

    public RPCWrapper() {

        instance = this;

        config = new YamlConfig(new File("config.yml"));
        config.copyDefaults();

        start();

    }

    public void start() {
        // Starting RPC
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {
            System.out.println("Ready for User " + user.username + "#" + user.discriminator + " (" + user.userId + ")!");

            // Initial Presence
            DiscordRichPresence presence = new DiscordRichPresence.Builder(config.get("defaults.rpc.state") + "").setBigImage("logo", "RPCWrapper").setDetails(config.get("defaults.rpc.details") + "").setStartTimestamps(System.currentTimeMillis() / 1000).build();
            updatePresence(presence);

        }).build();
        DiscordRPC.discordInitialize(config.get("defaults.rpc.clientId") + "", handlers, true);
    }

    public static RPCWrapper getInstance() {
        return instance;
    }

    public void refresh() {
        updatePresence(currentPresence);
    }

    public YamlConfig getConfig() {
        return config;
    }

    public static void updatePresence(DiscordRichPresence presence) {
        instance.currentPresence = presence;
        DiscordRPC.discordUpdatePresence(presence);
    }

    public DiscordRichPresence current() {
        return currentPresence;
    }

}
