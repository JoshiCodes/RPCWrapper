package de.joshizockt.rpcwrapper.preset;

public interface Preset {

    String getName();

    String getState();

    String getDetails();

    String getLargeImage();
    String getLargeImageText();

    String getSmallImage();
    String getSmallImageText();

    long getStartTimestamp();
    long getEndTimestamp();

}
