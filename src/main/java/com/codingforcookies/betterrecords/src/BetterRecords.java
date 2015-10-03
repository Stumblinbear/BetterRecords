package com.codingforcookies.betterrecords.src;

import org.apache.commons.lang3.text.WordUtils;

import com.codingforcookies.betterrecords.src.client.BetterCreativeTab;
import com.codingforcookies.betterrecords.src.gui.GuiHandler;
import com.codingforcookies.betterrecords.src.items.BlockFrequencyTuner;
import com.codingforcookies.betterrecords.src.items.BlockLazer;
import com.codingforcookies.betterrecords.src.items.BlockLazerCluster;
import com.codingforcookies.betterrecords.src.items.BlockRadio;
import com.codingforcookies.betterrecords.src.items.BlockRecordEtcher;
import com.codingforcookies.betterrecords.src.items.BlockRecordPlayer;
import com.codingforcookies.betterrecords.src.items.BlockRecordSpeaker;
import com.codingforcookies.betterrecords.src.items.BlockStrobeLight;
import com.codingforcookies.betterrecords.src.items.ItemFreqCrystal;
import com.codingforcookies.betterrecords.src.items.ItemRecordWire;
import com.codingforcookies.betterrecords.src.items.ItemRecordWireCutter;
import com.codingforcookies.betterrecords.src.items.ItemURLMultiRecord;
import com.codingforcookies.betterrecords.src.items.ItemURLRecord;
import com.codingforcookies.betterrecords.src.items.TileEntityFrequencyTuner;
import com.codingforcookies.betterrecords.src.items.TileEntityLazer;
import com.codingforcookies.betterrecords.src.items.TileEntityLazerCluster;
import com.codingforcookies.betterrecords.src.items.TileEntityRadio;
import com.codingforcookies.betterrecords.src.items.TileEntityRecordEtcher;
import com.codingforcookies.betterrecords.src.items.TileEntityRecordPlayer;
import com.codingforcookies.betterrecords.src.items.TileEntityRecordSpeaker;
import com.codingforcookies.betterrecords.src.items.TileEntityStrobeLight;
import com.codingforcookies.betterrecords.src.packets.ChannelHandler;
import com.codingforcookies.betterrecords.src.packets.PacketHandler;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;


@Mod(modid = BetterRecords.ID, version = "@VERSION@", useMetadata = true, name = "Better Records",
acceptableRemoteVersions = "@CHANGE_VERSION@", acceptedMinecraftVersions = "@MC_VERSION@", acceptableSaveVersions = "@CHANGE_VERSION@")
public class BetterRecords {
	public static final String ID = "betterrecords";
	public static final String VERSION = "@VERSION@";
	
	@Instance(value = ID)
	public static BetterRecords instance;
	
	@SidedProxy(clientSide = "com.codingforcookies.betterrecords.src.client.ClientProxy", serverSide = "com.codingforcookies.betterrecords.src.CommonProxy")
	public static CommonProxy proxy;
	
	public static final BetterCreativeTab recordsTab = new BetterCreativeTab();
	
	public static final ItemURLRecord itemURLRecord = (ItemURLRecord)new ItemURLRecord().setUnlocalizedName("urlrecord").setTextureName(ID + ":urlrecord").setCreativeTab(recordsTab);
	public static final ItemURLMultiRecord itemURLMultiRecord = (ItemURLMultiRecord)new ItemURLMultiRecord().setUnlocalizedName("urlmultirecord").setTextureName(ID + ":urlmultirecord").setCreativeTab(recordsTab);
	public static final ItemFreqCrystal itemFreqCrystal = (ItemFreqCrystal)new ItemFreqCrystal().setUnlocalizedName("freqcrystal").setTextureName(ID + ":freqcrystal").setCreativeTab(recordsTab);
	
	
	public static final ItemRecordWire itemRecordWire = (ItemRecordWire)new ItemRecordWire().setUnlocalizedName("recordwire").setTextureName(ID + ":recordwire").setCreativeTab(recordsTab);
	public static final ItemRecordWireCutter itemRecordCutters = (ItemRecordWireCutter)new ItemRecordWireCutter().setUnlocalizedName("recordwirecutters").setTextureName(ID + ":recordwirecutters").setCreativeTab(recordsTab);
	
	
	public static final BlockRecordEtcher blockRecordEtcher = (BlockRecordEtcher)new BlockRecordEtcher().setBlockName("recordetcher").setBlockTextureName(ID + ":breaktexture").setHardness(1.5F).setResistance(5.5F).setCreativeTab(recordsTab);
	public static final BlockRecordPlayer blockRecordPlayer = (BlockRecordPlayer)new BlockRecordPlayer().setBlockName("recordplayer").setBlockTextureName(ID + ":breaktexture").setHardness(1F).setResistance(5F).setCreativeTab(recordsTab);
	public static final BlockFrequencyTuner blockFrequencyTuner = (BlockFrequencyTuner)new BlockFrequencyTuner().setBlockName("frequencytuner").setBlockTextureName(ID + ":breaktexture").setHardness(1.5F).setResistance(5.5F).setCreativeTab(recordsTab);
	public static final BlockRadio blockRadio = (BlockRadio)new BlockRadio().setBlockName("shoutcastradio").setBlockTextureName(ID + ":breaktexture").setHardness(2F).setResistance(6.3F).setCreativeTab(recordsTab);
	
	public static final BlockRecordSpeaker blockSMSpeaker = (BlockRecordSpeaker)new BlockRecordSpeaker(0).setBlockName("recordspeaker.sm").setBlockTextureName(ID + ":breaktexture").setHardness(2F).setResistance(7.5F).setCreativeTab(recordsTab);
	public static final BlockRecordSpeaker blockMDSpeaker = (BlockRecordSpeaker)new BlockRecordSpeaker(1).setBlockName("recordspeaker.md").setBlockTextureName(ID + ":breaktexture").setHardness(3F).setResistance(8F).setCreativeTab(recordsTab);
	public static final BlockRecordSpeaker blockLGSpeaker = (BlockRecordSpeaker)new BlockRecordSpeaker(2).setBlockName("recordspeaker.lg").setBlockTextureName(ID + ":breaktexture").setHardness(4F).setResistance(9.5F).setCreativeTab(recordsTab);
	
	public static final BlockStrobeLight blockStrobeLight = (BlockStrobeLight)new BlockStrobeLight().setBlockName("strobelight").setBlockTextureName(ID + ":breaktexture").setHardness(2.75F).setResistance(4F).setCreativeTab(recordsTab);
	public static final BlockLazer blockLazer = (BlockLazer)new BlockLazer().setBlockName("lazer").setBlockTextureName(ID + ":breaktexture").setHardness(3.2F).setResistance(4.3F).setCreativeTab(recordsTab);
	public static final BlockLazerCluster blockLazerCluster = (BlockLazerCluster)new BlockLazerCluster().setBlockName("lazercluster").setBlockTextureName(ID + ":breaktexture").setHardness(4.8F).setResistance(4.8F).setCreativeTab(recordsTab);
	
	@EventHandler
	public void preInit(final FMLPreInitializationEvent event) {

		proxy.preInit();
	}
	
	@EventHandler
	public void init(final FMLInitializationEvent event) {
		PacketHandler.channels = NetworkRegistry.INSTANCE.newChannel("BetterRecords", new ChannelHandler());

		GameRegistry.registerItem(itemURLRecord, "urlrecord");
		GameRegistry.registerItem(itemURLMultiRecord, "urlmultirecord");
		GameRegistry.registerItem(itemFreqCrystal, "freqcrystal");
		GameRegistry.registerItem(itemRecordWire, "recordwire");
		GameRegistry.registerItem(itemRecordCutters, "recordwirecutters");
		
		GameRegistry.registerBlock(blockRecordEtcher, "recordetcher");
		GameRegistry.registerBlock(blockRecordPlayer, "recordplayer");
		GameRegistry.registerBlock(blockFrequencyTuner, "frequencytuner");
		GameRegistry.registerBlock(blockRadio, "shoutcastradio");
		GameRegistry.registerBlock(blockSMSpeaker, "recordspeaker.sm");
		GameRegistry.registerBlock(blockMDSpeaker, "recordspeaker.md");
		GameRegistry.registerBlock(blockLGSpeaker, "recordspeaker.lg");
		GameRegistry.registerBlock(blockStrobeLight, "strobelight");
		GameRegistry.registerBlock(blockLazer, "lazer");
		GameRegistry.registerBlock(blockLazerCluster, "lazercluster");
		
		
		GameRegistry.registerTileEntity(TileEntityRecordEtcher.class, "recordetcher");
		GameRegistry.registerTileEntity(TileEntityRecordPlayer.class, "recordplayer");
		GameRegistry.registerTileEntity(TileEntityFrequencyTuner.class, "frequencytuner");
		GameRegistry.registerTileEntity(TileEntityRadio.class, "shoutcastradio");
		GameRegistry.registerTileEntity(TileEntityRecordSpeaker.class, "recordspeaker");
		GameRegistry.registerTileEntity(TileEntityStrobeLight.class, "strobelight");
		GameRegistry.registerTileEntity(TileEntityLazer.class, "lazer");
		GameRegistry.registerTileEntity(TileEntityLazerCluster.class, "lazercluster");
		
		GameRegistry.addRecipe(new RecipeRecord());
		GameRegistry.addRecipe(new RecipeMultiRecord());
		GameRegistry.addRecipe(new RecipeRecordRepeatable());
		GameRegistry.addRecipe(new RecipeRecordShuffle());
		GameRegistry.addRecipe(new RecipeColoredRecord());
		
		GameRegistry.addShapedRecipe(new ItemStack(itemFreqCrystal), "RQR", "QDQ", "RQR", 'R', Items.redstone, 'Q', Items.quartz, 'D', Items.diamond);
		GameRegistry.addRecipe(new RecipeColoredFreqCrystal());
		
		GameRegistry.addShapedRecipe(new ItemStack(itemRecordWire, 4), "WWW", "III", "WWW", 'I', Items.iron_ingot, 'W', new ItemStack(Blocks.wool, 1, 15));
		GameRegistry.addShapedRecipe(new ItemStack(itemRecordWire, 4), "WIW", "WIW", "WIW", 'I', Items.iron_ingot, 'W', new ItemStack(Blocks.wool, 1, 15));
		GameRegistry.addShapedRecipe(new ItemStack(itemRecordCutters), "I I", " I ", "WIW", 'I', Items.iron_ingot, 'W', new ItemStack(Blocks.wool, 1, 15));
		
		GameRegistry.addShapedRecipe(new ItemStack(blockRecordEtcher), "HIH", "PQP", "PPP", 'H', Blocks.wooden_slab, 'I', Items.iron_ingot, 'P', Blocks.planks, 'Q', Items.quartz);
		GameRegistry.addShapedRecipe(new ItemStack(blockRecordPlayer), "GGG", "PDP", "PPP", 'G', Blocks.glass_pane, 'P', Blocks.planks, 'D', Blocks.diamond_block);
		GameRegistry.addShapedRecipe(new ItemStack(blockFrequencyTuner), "SHH", "PQP", "PIP", 'H', Blocks.wooden_slab, 'I', Items.iron_ingot, 'S', Items.stick, 'P', Blocks.planks, 'Q', itemFreqCrystal);
		GameRegistry.addShapedRecipe(new ItemStack(blockRadio), "HIH", "PQP", "PHP", 'H', Blocks.wooden_slab, 'I', Items.iron_ingot, 'P', Blocks.planks, 'Q', itemFreqCrystal);
		
		GameRegistry.addShapedRecipe(new ItemStack(blockSMSpeaker), "LLW", "QDW", "LLW", 'L', Blocks.log, 'W', new ItemStack(Blocks.wool, 1, 15), 'D', Items.diamond, 'Q', Items.quartz);
		GameRegistry.addShapedRecipe(new ItemStack(blockMDSpeaker), "LLW", "ESW", "LLW", 'L', Blocks.log, 'W', new ItemStack(Blocks.wool, 1, 15), 'S', blockSMSpeaker, 'E', Items.ender_eye);
		GameRegistry.addShapedRecipe(new ItemStack(blockLGSpeaker), "LLW", "CMW", "LLW", 'L', Blocks.log, 'W', new ItemStack(Blocks.wool, 1, 15), 'M', blockMDSpeaker, 'C', Items.comparator);
		
		GameRegistry.addShapedRecipe(new ItemStack(blockStrobeLight), "GGG", "GRG", "CTC", 'G', Blocks.glass, 'C', Items.comparator, 'R', Blocks.redstone_lamp, 'T', Blocks.redstone_torch);
		GameRegistry.addShapedRecipe(new ItemStack(blockLazer), "LLL", "LQG", "HLH", 'L', Blocks.log, 'H', Blocks.wooden_slab, 'G', Blocks.glass, 'Q', Items.quartz);
		GameRegistry.addShapedRecipe(new ItemStack(blockLazerCluster), "LLL", "LRL", "LLL", 'L', blockLazer, 'R', Items.redstone);
		
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		
		proxy.init();
	}
	
	@EventHandler
	public void postInit(final FMLPostInitializationEvent evt) {
		
	}

	public static String[] getWordWrappedString(final int maxWidth, final String string) {
		return WordUtils.wrap(string, maxWidth, "\n", false).replace("\\n", "\n").split("\n");
	}
}