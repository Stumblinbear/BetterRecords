package com.codingforcookies.betterrecords.common.core.handler;

import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ConfigHandler {

    public static Configuration config;

    // Server
    public static int maxSpeakerRadius;

    // Client
    public static boolean downloadSongs;
    public static boolean devMode;
    public static int flashyMode = -1;

    public static boolean playWhileDownload;
    public static int downloadMax;
    public static int streamBuffer = 256;
    public static boolean streamRadio = true;

    public static String defaultLibraryURL = "";

    public static HashMap<String, Boolean> tutorials = new HashMap<String, Boolean>() {{
        put("recordplayer", false);
        put("speaker", false);
        put("radio", false);
        put("strobelight", false);
        put("laser", false);
        put("lasercluster", false);
    }};

    public static void loadConfig(File configurationFile) {
        config = new Configuration(configurationFile);
        config.load();

        // Server Config
        maxSpeakerRadius = config.getInt("maxSpeakerRadius", "server", -1, -1, 1000, "Maxium speaker radius");

        // Client Config
        downloadSongs = config.getBoolean("downloadSongs", "client", true, "Should download songs from the internet?");
        devMode = config.getBoolean("devMode", "client", false, "Enable developer mode");
        downloadMax = config.getInt("downloadMax", "client", 10, 1, 1000, "Max file size to download");
        playWhileDownload = config.getBoolean("playWhileDownload", "client", false, "Play songs while downloading?");
        streamBuffer = config.getInt("streamBuffer", "client", 1024, 256, 2048, "");
        streamRadio = config.getBoolean("streamRadio", "client", true, "Should radio be streamed?");
        flashyMode = config.getInt("flashyMode", "client", -1, -1, 3, "Intensity of lights");
        defaultLibraryURL = config.getString("defaultLibrary", "client",
                "https://raw.githubusercontent.com/stumblinbear/Versions/master/betterrecords/defaultlibrary.json", "");

        for (Map.Entry<String, Boolean> entry : tutorials.entrySet()) {
            entry.setValue(config.getBoolean(entry.getKey(), "client.tutorials", false, ""));
        }

        if(config.hasChanged()) config.save();
    }

    public static void updateConfig() {
        config.get("server", "maxSpeakerRadius", true).set(maxSpeakerRadius);

        config.get("client", "downloadSongs", true).set(downloadSongs);
        config.get("client", "devMode", false).set(devMode);
        config.get("client", "downloadMax", 10).set(downloadMax);
        config.get("client", "playWhileDownload", false).set(playWhileDownload);
        config.get("client", "streamBuffer", 1024).set(streamBuffer);
        config.get("client", "streamRadio", true).set(streamRadio);
        config.get("client", "flashyMode", -1).set(flashyMode);
        config.get("client", "defaultLibrary",
                "https://raw.githubusercontent.com/stumblinbear/Versions/master/betterrecords/defaultlibrary.json").set(defaultLibraryURL);

        for (Map.Entry<String, Boolean> entry : tutorials.entrySet()) {
            config.get("client.tutorials", entry.getKey(), false).set(entry.getValue());
        }

        if(config.hasChanged()) config.save();
    }


}
