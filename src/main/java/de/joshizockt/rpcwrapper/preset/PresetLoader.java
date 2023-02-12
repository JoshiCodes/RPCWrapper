package de.joshizockt.rpcwrapper.preset;

import de.joshizockt.rpcwrapper.util.config.YamlConfig;
import net.arikia.dev.drpc.DiscordRichPresence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PresetLoader {

    private final YamlConfig config;

    public PresetLoader(YamlConfig config) {
        this.config = config;
    }

    public DiscordRichPresence toPresence(Preset preset) {

        DiscordRichPresence.Builder builder = new DiscordRichPresence.Builder(preset.getState());
        if(preset.getDetails() != null) builder.setDetails(preset.getDetails());
        if(preset.getLargeImage() != null) builder.setBigImage(preset.getLargeImage(), preset.getLargeImageText());
        if(preset.getSmallImage() != null) builder.setSmallImage(preset.getSmallImage(), preset.getSmallImageText());
        if(preset.getStartTimestamp() > 0) builder.setStartTimestamps(preset.getStartTimestamp());
        else if(preset.getStartTimestamp() == 0) builder.setStartTimestamps(System.currentTimeMillis() / 1000);
        if(preset.getEndTimestamp() > 0) builder.setEndTimestamp(preset.getEndTimestamp());

        return builder.build();

    }

    public Preset loadPreset(String name) {
        if(config.get("presets." + name) == null) return null;

        String state = config.getString("presets." + name + ".state");
        String details = config.getString("presets." + name + ".details", null);
        String largeImage = config.getString("presets." + name + ".largeImage", null);
        String largeImageText = config.getString("presets." + name + ".largeImageText", null);
        String smallImage = config.getString("presets." + name + ".smallImage", null);
        String smallImageText = config.getString("presets." + name + ".smallImageText", null);
        long startTimestamp = config.getLong("presets." + name + ".startTimestamp", 0);
        long endTimestamp = config.getLong("presets." + name + ".endTimestamp", -1);

        Preset preset = new Preset() {


            @Override
            public String getName() {
                return name;
            }

            @Override
            public String getState() {
                return state;
            }

            @Override
            public String getDetails() {
                return details;
            }

            @Override
            public String getLargeImage() {
                return largeImage;
            }

            @Override
            public String getLargeImageText() {
                return largeImageText;
            }

            @Override
            public String getSmallImage() {
                return smallImage;
            }

            @Override
            public String getSmallImageText() {
                return smallImageText;
            }

            @Override
            public long getStartTimestamp() {
                return startTimestamp;
            }

            @Override
            public long getEndTimestamp() {
                return endTimestamp;
            }
        };
        return preset;
    }

    public List<Preset> getPresets() {

        List<Preset> list = new ArrayList<>();
        HashMap<String, HashMap<String, Object>> presets = (HashMap<String, HashMap<String, Object>>) config.get("presets");

        presets.forEach((name, preset) -> {
            list.add(loadPreset(name));
        });

        return list;

    }

}
