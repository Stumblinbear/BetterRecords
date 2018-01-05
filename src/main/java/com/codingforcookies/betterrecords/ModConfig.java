package com.codingforcookies.betterrecords;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Config file for BetterRecords
 *
 * For now, this is written in Java, since the annotation
 * based config system does not play nice with kotlin.
 */
@Config(modid = ConstantsKt.ID)
@Config.LangKey("betterrecords.config.title")
public class ModConfig {

    @Config.Comment("Maximum speaker radius")
    @Config.RangeInt(min = -1, max = 1000)
    public static int maxSpeakerRadius = -1;

    @Config.Comment("Client-Specific config settings")
    @Config.LangKey("betterrecords.config.client.title")
    public static Client client = new Client();
    public static class Client {

        @Config.Comment("Should the mod download songs from the internet?")
        public boolean downloadSongs = true;

        @Config.Comment("Enable developer mode")
        public boolean devMode = false;

        @Config.Comment("Max file size to download (in megabytes)")
        @Config.RangeInt(min = 1, max = 1000)
        public int downloadMax = 10;

        @Config.Comment("Play Songs while downloading\nFor those with fast internet!")
        public boolean playWhileDownloading = false;

        @Config.RangeInt(min = 256, max = 2048)
        public int streamBuffer = 1024;

        @Config.Comment("Should radio be streamed")
        public boolean streamRadio = true;

        @Config.Comment("Intensity of lights")
        @Config.RangeInt(min = -1, max = 3)
        public int flashMode;
    }

    public static void update() {
        ConfigManager.sync(ConstantsKt.ID, Config.Type.INSTANCE);
    }

    @Mod.EventBusSubscriber
    private static class EventHandler {

        /**
         * Inject the new values and save to the config file when the config has been changed from the GUI.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(ConstantsKt.ID)) {
                ModConfig.update();
            }
        }
    }
}
