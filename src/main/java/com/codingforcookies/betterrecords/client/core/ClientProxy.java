package com.codingforcookies.betterrecords.client.core;

import com.codingforcookies.betterrecords.ConstantsKt;
import com.codingforcookies.betterrecords.api.BetterRecordsAPI;
import com.codingforcookies.betterrecords.api.song.LibrarySong;
import com.codingforcookies.betterrecords.client.core.handler.BetterEventHandler;
import com.codingforcookies.betterrecords.client.core.handler.TESRRenderHandler;
import com.codingforcookies.betterrecords.client.render.*;
import com.codingforcookies.betterrecords.client.sound.SoundHandler;
import com.codingforcookies.betterrecords.block.ModBlocks;
import com.codingforcookies.betterrecords.block.tile.*;
import com.codingforcookies.betterrecords.common.core.CommonProxy;
import com.codingforcookies.betterrecords.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ClientProxy extends CommonProxy {
    public static ClientProxy instance;

    /**
     * Last checked:
     *   0 = Unchecked
     *   1 = Singleplayer
     *   2 = Multiplayer
     */
    public static int lastCheckType = 0;
    public static File localLibrary;

    public static ArrayList<LibrarySong> defaultLibrary;
    public static ArrayList<String> encodings;


    public static boolean checkForUpdates = true;
    public static boolean hasCheckedForUpdates = false;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        defaultLibrary = new ArrayList<LibrarySong>();
        encodings = new ArrayList<String>();
        encodings.add("audio/ogg");
        encodings.add("application/ogg");
        encodings.add("audio/mpeg");
        encodings.add("audio/mpeg; charset=UTF-8");
        encodings.add("application/octet-stream");
        encodings.add("audio/wav");
        encodings.add("audio/x-wav");

        // Temporary way to render TESR's in the inventory. Should be changed once the JSON format supports animations
        registerTESRRender(ModBlocks.blockRecordEtcher, new BlockRecordEtcherRenderer(), TileRecordEtcher.class, "recordetcher");
        registerTESRRender(ModBlocks.blockRecordPlayer, new BlockRecordPlayerRenderer(), TileRecordPlayer.class, "recordplayer");
        registerTESRRender(ModBlocks.blockFrequencyTuner, new BlockFrequencyTunerRenderer(), TileFrequencyTuner.class, "frequencytuner");
        registerTESRRender(ModBlocks.blockRadio, new BlockRadioRenderer(), TileRadio.class, "radio");
        registerTESRRender(ModBlocks.blockSMSpeaker, new BlockRecordSpeakerRenderer(), TileSpeaker.class, "speaker.sm");
        registerTESRRender(ModBlocks.blockMDSpeaker, new BlockRecordSpeakerRenderer(), TileSpeaker.class, "speaker.md");
        registerTESRRender(ModBlocks.blockLGSpeaker, new BlockRecordSpeakerRenderer(), TileSpeaker.class, "speaker.lg");
        registerTESRRender(ModBlocks.blockStrobeLight, new BlockStrobeLightRenderer(), TileStrobeLight.class, "strobelight");
        registerTESRRender(ModBlocks.blockLazer, new BlockLazerRenderer(), TileLazer.class, "lazer");
        registerTESRRender(ModBlocks.blockLazerCluster, new BlockLazerClusterRenderer(), TileLazerCluster.class, "lazercluster");
        MinecraftForge.EVENT_BUS.register(new TESRRenderHandler());


        SoundHandler.initalize();
    }

    private void registerTESRRender(Block block, TileEntitySpecialRenderer renderer,  Class<? extends TileEntity> te, String name) {
        ClientRegistry.bindTileEntitySpecialRenderer(te, renderer);
        Item item = Item.getItemFromBlock(block);
        ForgeHooksClient.registerTESRItemStack(item, 0, te);
        ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(BetterRecordsAPI.CARDINAL_DIRECTIONS).build());
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(ConstantsKt.ID + ":itemblock/" + name, "inventory"));
    }


    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        instance = this;

        localLibrary = new File(Minecraft.getMinecraft().mcDataDir, "betterrecords/localLibrary.json");

        if(!localLibrary.exists())
            try {
                localLibrary.createNewFile();

                BufferedWriter writer = null;
                try {
                    writer = new BufferedWriter(new FileWriter(ClientProxy.localLibrary));
                    writer.write("{}");
                } finally {
                        writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        MinecraftForge.EVENT_BUS.register(new BetterEventHandler());

        RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
        renderItem.getItemModelMesher().register(ModItems.itemFreqCrystal, 0, new ModelResourceLocation(ConstantsKt.ID + ":" + "freqcrystal", "inventory"));
        renderItem.getItemModelMesher().register(ModItems.itemRecordWire, 0, new ModelResourceLocation(ConstantsKt.ID + ":" + "recordwire", "inventory"));
        renderItem.getItemModelMesher().register(ModItems.itemRecordCutters, 0, new ModelResourceLocation(ConstantsKt.ID + ":" + "recordwirecutters", "inventory"));
        renderItem.getItemModelMesher().register(ModItems.itemURLMultiRecord, 0, new ModelResourceLocation(ConstantsKt.ID + ":" + "urlmultirecord", "inventory"));
        renderItem.getItemModelMesher().register(ModItems.itemURLRecord, 0, new ModelResourceLocation(ConstantsKt.ID + ":" + "urlrecord", "inventory"));
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);

        IItemColor color = new IItemColor() {
            @Override
            public int getColorFromItemstack(ItemStack itemStack, int tintIndex) {
                return (itemStack.getTagCompound() != null && itemStack.getTagCompound().hasKey("color") ? itemStack.getTagCompound().getInteger("color") : 0xFFFFFF);
            }
        };

        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(color, ModItems.itemURLRecord);
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(color, ModItems.itemURLMultiRecord);
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(color, ModItems.itemFreqCrystal);
    }
}
