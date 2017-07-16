package com.codingforcookies.betterrecords.block;

import com.codingforcookies.betterrecords.block.tile.*;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class ModBlocks {

    public static Block blockRecordEtcher;
    public static Block blockRecordPlayer;
    public static Block blockFrequencyTuner;
    public static Block blockRadio;

    public static Block blockSMSpeaker;
    public static Block blockMDSpeaker;
    public static Block blockLGSpeaker;

    public static Block blockStrobeLight;
    public static Block blockLazer;
    public static Block blockLazerCluster;

    public static void init() {
        blockRecordEtcher = new BlockRecordEtcher("recordetcher");
        blockRecordPlayer = new BlockRecordPlayer("recordplayer");
        blockFrequencyTuner = new BlockFrequencyTuner("frequencytuner");
        blockRadio = new BlockRadio("radio");

        blockSMSpeaker = new BlockSpeaker("speaker.sm", 0);
        blockMDSpeaker = new BlockSpeaker("speaker.md", 1);
        blockLGSpeaker = new BlockSpeaker("speaker.lg", 2);

        blockStrobeLight = new BlockStrobeLight("strobelight");
        blockLazer = new BlockLazer("lazer");
        blockLazerCluster = new BlockLazerCluster("lazercluster");

        initTileEntities();
    }

    private static void initTileEntities() {
        registerTile(TileRecordEtcher.class, "recordetcher");
        registerTile(TileRecordPlayer.class, "recordplayer");
        registerTile(TileFrequencyTuner.class, "frequencytuner");
        registerTile(TileRadio.class, "radio");
        registerTile(TileSpeaker.class, "speaker");
        registerTile(TileStrobeLight.class, "strobelight");
        registerTile(TileLazer.class, "lazer");
        registerTile(TileLazerCluster.class, "lazercluster");
    }

    private static void registerTile(Class<? extends TileEntity> class_, String key) {
        GameRegistry.registerTileEntity(class_, "betterrecords:" + key);
    }
}
