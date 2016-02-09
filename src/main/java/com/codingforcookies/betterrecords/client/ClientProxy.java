package com.codingforcookies.betterrecords.src.client;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import org.lwjgl.input.Keyboard;

import com.codingforcookies.betterrecords.src.BetterRecords;
import com.codingforcookies.betterrecords.src.CommonProxy;
import com.codingforcookies.betterrecords.src.LibrarySong;
import com.codingforcookies.betterrecords.src.client.models.BlockFrequencyTunerRenderer;
import com.codingforcookies.betterrecords.src.client.models.BlockLazerClusterRenderer;
import com.codingforcookies.betterrecords.src.client.models.BlockLazerRenderer;
import com.codingforcookies.betterrecords.src.client.models.BlockRadioRenderer;
import com.codingforcookies.betterrecords.src.client.models.BlockRecordEtcherRenderer;
import com.codingforcookies.betterrecords.src.client.models.BlockRecordPlayerRenderer;
import com.codingforcookies.betterrecords.src.client.models.BlockRecordSpeakerRenderer;
import com.codingforcookies.betterrecords.src.client.models.BlockStrobeLightRenderer;
import com.codingforcookies.betterrecords.src.client.sound.SoundHandler;
import com.codingforcookies.betterrecords.src.items.TileEntityFrequencyTuner;
import com.codingforcookies.betterrecords.src.items.TileEntityLazer;
import com.codingforcookies.betterrecords.src.items.TileEntityLazerCluster;
import com.codingforcookies.betterrecords.src.items.TileEntityRadio;
import com.codingforcookies.betterrecords.src.items.TileEntityRecordEtcher;
import com.codingforcookies.betterrecords.src.items.TileEntityRecordPlayer;
import com.codingforcookies.betterrecords.src.items.TileEntityRecordSpeaker;
import com.codingforcookies.betterrecords.src.items.TileEntityStrobeLight;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy {
	public static ClientProxy instance;
	public static Configuration config;
	
	public static KeyBinding keyConfig;
	
	/**
	 * Last checked:
	 *   0 = Unchecked
	 *   1 = Singleplayer
	 *   2 = Multiplayer
	 */
	public static int lastCheckType = 0;
	public static File localLibrary;
	
	public static String defaultLibraryURL = "";
	public static ArrayList<LibrarySong> defaultLibrary;
	public static ArrayList<String> encodings;
	
	public static HashMap<String, Boolean> tutorials;
	
	public static boolean checkForUpdates = true;
	public static boolean hasCheckedForUpdates = false;
	
	public static boolean devMode = false;

	public static boolean playWhileDownload = false;
	public static int downloadMax = 10;
	public static int flashyMode = -1;
	
	public void preInit() {
		defaultLibrary = new ArrayList<LibrarySong>();
		encodings = new ArrayList<String>();
		encodings.add("audio/ogg");
		encodings.add("application/ogg");
		encodings.add("audio/mpeg");
		encodings.add("audio/mpeg; charset=UTF-8");
		encodings.add("application/octet-stream");
		encodings.add("audio/wav");
		encodings.add("audio/x-wav");
		
		tutorials = new HashMap<String, Boolean>();
		tutorials.put("recordplayer", false);
		tutorials.put("speaker", false);
		tutorials.put("radio", false);
		tutorials.put("strobelight", false);
		tutorials.put("lazer", false);
		tutorials.put("lazercluster", false);
		
		SoundHandler.initalize();
		loadConfig();
	}
	
	public void init() {
		instance = this;
		
		keyConfig = new KeyBinding("key.betterconfig.desc", Keyboard.KEY_N, "key.betterconfig.category");
		ClientRegistry.registerKeyBinding(keyConfig);
		
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
		FMLCommonHandler.instance().bus().register(new BetterEventHandler());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRecordEtcher.class, new BlockRecordEtcherRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BetterRecords.blockRecordEtcher), new ClientItemRenderer());	
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRecordPlayer.class, new BlockRecordPlayerRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BetterRecords.blockRecordPlayer), new ClientItemRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFrequencyTuner.class, new BlockFrequencyTunerRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BetterRecords.blockFrequencyTuner), new ClientItemRenderer());	
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRadio.class, new BlockRadioRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BetterRecords.blockRadio), new ClientItemRenderer());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRecordSpeaker.class, new BlockRecordSpeakerRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BetterRecords.blockSMSpeaker), new ClientItemRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BetterRecords.blockMDSpeaker), new ClientItemRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BetterRecords.blockLGSpeaker), new ClientItemRenderer());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStrobeLight.class, new BlockStrobeLightRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BetterRecords.blockStrobeLight), new ClientItemRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLazer.class, new BlockLazerRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BetterRecords.blockLazer), new ClientItemRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLazerCluster.class, new BlockLazerClusterRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BetterRecords.blockLazerCluster), new ClientItemRenderer());
	}
	
	public static void loadConfig() {
		config = new Configuration(new File(Minecraft.getMinecraft().mcDataDir, "betterrecords/config.cfg"));
		config.load();
		
		SoundHandler.downloadSongs = config.get(Configuration.CATEGORY_GENERAL, "downloadSongs", true).getBoolean(true);
		downloadMax = config.get(Configuration.CATEGORY_GENERAL, "downloadMax", 10).getInt(10);
		playWhileDownload = config.get(Configuration.CATEGORY_GENERAL, "playWhileDownload", false).getBoolean(false);
		SoundHandler.streamBuffer = config.get(Configuration.CATEGORY_GENERAL, "streamBuffer", 1024).getInt(1024);
		
		SoundHandler.streamRadio = config.get(Configuration.CATEGORY_GENERAL, "streamRadio", true).getBoolean(true);
		
		flashyMode = config.get(Configuration.CATEGORY_GENERAL, "flashyMode", -1).getInt(-1);
		
		for(Entry<String, Boolean> entry : tutorials.entrySet())
			entry.setValue(config.get("tutorials", entry.getKey(), false).getBoolean(false));
		
		devMode = config.get("other", "devMode", false).getBoolean(false);
		
		defaultLibraryURL = config.get("other", "defaultLibrary", "https://raw.githubusercontent.com/stumblinbear/Versions/master/betterrecords/defaultlibrary.json").getString();
		
		if(defaultLibraryURL.equals("http://files.enjin.com/788858/SBear'sMods/defaultlibrary.json"))
			defaultLibraryURL = "https://raw.githubusercontent.com/stumblinbear/Versions/master/betterrecords/defaultlibrary.json";
			config.get("other", "defaultLibrary", "https://raw.githubusercontent.com/stumblinbear/Versions/master/betterrecords/defaultlibrary.json").set("https://raw.githubusercontent.com/stumblinbear/Versions/master/betterrecords/defaultlibrary.json");
		
		config.save();
	}
	
	public static void saveConfig() {
		config.get(Configuration.CATEGORY_GENERAL, "downloadSongs", true).set(SoundHandler.downloadSongs);
		config.get(Configuration.CATEGORY_GENERAL, "downloadMax", 10).set(downloadMax);
		config.get(Configuration.CATEGORY_GENERAL, "playWhileDownload", false).set(playWhileDownload);
		config.get(Configuration.CATEGORY_GENERAL, "streamBuffer", false).set(SoundHandler.streamBuffer);
		
		config.get(Configuration.CATEGORY_GENERAL, "streamRadio", true).set(SoundHandler.streamRadio);
		
		config.get(Configuration.CATEGORY_GENERAL, "flashyMode", -1).set(flashyMode);
		
		for(Entry<String, Boolean> entry : tutorials.entrySet())
			config.get("tutorials", entry.getKey(), false).set(entry.getValue());
		
		config.get("other", "devMode", false).set(devMode);
		
		config.get("other", "defaultLibrary", "https://raw.githubusercontent.com/stumblinbear/Versions/master/betterrecords/defaultlibrary.json").set(defaultLibraryURL);
		
		config.save();
	}
}