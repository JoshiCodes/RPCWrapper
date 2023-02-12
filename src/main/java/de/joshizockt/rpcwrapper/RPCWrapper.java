package de.joshizockt.rpcwrapper;

import de.joshizockt.rpcwrapper.preset.Preset;
import de.joshizockt.rpcwrapper.preset.PresetLoader;
import de.joshizockt.rpcwrapper.util.config.YamlConfig;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

import java.io.File;

public class RPCWrapper {

    public static DiscordRichPresence DEFAULT_PRESENCE;

    private static RPCWrapper instance;

    private YamlConfig config;

    private PresetLoader presetLoader;

    private DiscordRichPresence currentPresence;

    public RPCWrapper() {

        instance = this;

        config = new YamlConfig(new File("config.yml"));
        config.copyDefaults();

        DEFAULT_PRESENCE = new DiscordRichPresence.Builder(config.get("defaults.rpc.state") + "").setBigImage("logo", "RPCWrapper").setDetails(config.get("defaults.rpc.details") + "").setStartTimestamps(System.currentTimeMillis() / 1000).build();

        presetLoader = new PresetLoader(config);

        start();

    }

    public void start() {
        // Starting RPC
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {
            System.out.println("Ready for User " + user.username + "#" + user.discriminator + " (" + user.userId + ")!");

            if(config.get("startup.preset") != null) {
                String presetName = config.getString("startup.preset");
                if(!presetName.equals("default")) {
                    Preset preset = presetLoader.loadPreset(presetName);
                    if(preset != null) {
                        updatePresence(presetLoader.toPresence(preset));
                        return;
                    } else {
                        System.out.println("Preset " + presetName + " not found! Continuing with default values...");
                    }
                }
            }

            // Initial Presence
            updatePresence(DEFAULT_PRESENCE);

        }).build();
        DiscordRPC.discordInitialize(config.get("defaults.rpc.clientId") + "", handlers, true);
    }

    public static RPCWrapper getInstance() {
        return instance;
    }

    public PresetLoader getPresetLoader() {
        return presetLoader;
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
