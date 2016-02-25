package com.codingforcookies.betterrecords.common.block;

import com.codingforcookies.betterrecords.common.block.tile.*;
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

        blockSMSpeaker = new BlockRecordSpeaker("speaker.sm", 0);
        blockMDSpeaker = new BlockRecordSpeaker("speaker.md", 1);
        blockLGSpeaker = new BlockRecordSpeaker("speaker.lg", 2);

        blockStrobeLight = new BlockStrobeLight("strobelight");
        blockLazer = new BlockLazer("lazer");
        blockLazerCluster = new BlockLazerCluster("lazercluster");

        initTileEntities();
    }

    private static void initTileEntities() {
        registerTile(TileEntityRecordEtcher.class, "recordetcher");
        registerTile(TileEntityRecordPlayer.class, "recordplayer");
        registerTile(TileEntityFrequencyTuner.class, "frequencytuner");
        registerTile(TileEntityRadio.class, "radio");
        registerTile(TileEntityRecordSpeaker.class, "speaker");
        registerTile(TileEntityStrobeLight.class, "strobelight");
        registerTile(TileEntityLazer.class, "lazer");
        registerTile(TileEntityLazerCluster.class, "lazercluster");
    }

    private static void registerTile(Class<? extends TileEntity> class_, String key) {
        GameRegistry.registerTileEntity(class_, "betterrecords:" + key);
    }
}
