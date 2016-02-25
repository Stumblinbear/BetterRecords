package com.codingforcookies.betterrecords.common;

import com.codingforcookies.betterrecords.client.BetterCreativeTab;
import com.codingforcookies.betterrecords.client.gui.GuiHandler;
import com.codingforcookies.betterrecords.common.block.*;
import com.codingforcookies.betterrecords.common.block.tile.*;
import com.codingforcookies.betterrecords.common.core.CommonProxy;
import com.codingforcookies.betterrecords.common.item.*;
import com.codingforcookies.betterrecords.common.packets.ChannelHandler;
import com.codingforcookies.betterrecords.common.packets.PacketHandler;
import com.codingforcookies.betterrecords.common.recipe.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;
import org.apache.commons.lang3.text.WordUtils;


@Mod(modid = BetterRecords.ID, version = "@VERSION@", useMetadata = true, name = "Better Records",
acceptableRemoteVersions = "@CHANGE_VERSION@", acceptedMinecraftVersions = "@MC_VERSION@", acceptableSaveVersions = "@CHANGE_VERSION@")
public class BetterRecords {
    public static final String ID = "betterrecords";
    public static final String VERSION = "@VERSION@";

    @Instance(value = ID)
    public static BetterRecords instance;

    @SidedProxy(clientSide = "com.codingforcookies.betterrecords.client.core.ClientProxy", serverSide = "com.codingforcookies.betterrecords.common.core.CommonProxy")
    public static CommonProxy proxy;

    public static final BetterCreativeTab recordsTab = new BetterCreativeTab();



    @EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        proxy.preInit();
    }

    @EventHandler
    public void init(final FMLInitializationEvent event) {
        PacketHandler.channels = NetworkRegistry.INSTANCE.newChannel("BetterRecords", new ChannelHandler());

        GameRegistry.addRecipe(new RecipeRecord());
        RecipeSorter.register("bettrecords:urlrecord", RecipeRecord.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");

        GameRegistry.addRecipe(new RecipeMultiRecord());
        RecipeSorter.register("betterrecords:urlmultirecord", RecipeMultiRecord.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");

        GameRegistry.addRecipe(new RecipeRecordRepeatable());
        RecipeSorter.register("betterrecords:urlrecord", RecipeRecordRepeatable.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");

        GameRegistry.addRecipe(new RecipeRecordShuffle());
        RecipeSorter.register("betterrecords:urlrecord", RecipeRecordShuffle.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");

        GameRegistry.addRecipe(new RecipeColoredFreqCrystal());
        RecipeSorter.register("betterrecords:freqcrystal", RecipeColoredFreqCrystal.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");

        GameRegistry.addRecipe(new RecipeColoredRecord());
        RecipeSorter.register("betterrecords:urlrecord", RecipeColoredRecord.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");

        GameRegistry.addShapedRecipe(new ItemStack(ModItems.itemFreqCrystal), "RQR", "QDQ", "RQR", 'R', Items.redstone, 'Q', Items.quartz, 'D', Items.diamond);

        GameRegistry.addShapedRecipe(new ItemStack(ModItems.itemRecordWire, 4), "WWW", "III", "WWW", 'I', Items.iron_ingot, 'W', new ItemStack(Blocks.wool, 1, 15));
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.itemRecordWire, 4), "WIW", "WIW", "WIW", 'I', Items.iron_ingot, 'W', new ItemStack(Blocks.wool, 1, 15));
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.itemRecordCutters), "I I", " I ", "WIW", 'I', Items.iron_ingot, 'W', new ItemStack(Blocks.wool, 1, 15));

        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.blockRecordEtcher), "HIH", "PQP", "PPP", 'H', Blocks.wooden_slab, 'I', Items.iron_ingot, 'P', Blocks.planks, 'Q', Items.quartz);
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.blockRecordPlayer), "GGG", "PDP", "PPP", 'G', Blocks.glass_pane, 'P', Blocks.planks, 'D', Blocks.diamond_block);
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.blockFrequencyTuner), "SHH", "PQP", "PIP", 'H', Blocks.wooden_slab, 'I', Items.iron_ingot, 'S', Items.stick, 'P', Blocks.planks, 'Q', ModItems.itemFreqCrystal);
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.blockRadio), "HIH", "PQP", "PHP", 'H', Blocks.wooden_slab, 'I', Items.iron_ingot, 'P', Blocks.planks, 'Q', ModItems.itemFreqCrystal);

        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.blockSMSpeaker), "LLW", "QDW", "LLW", 'L', Blocks.log, 'W', new ItemStack(Blocks.wool, 1, 15), 'D', Items.diamond, 'Q', Items.quartz);
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.blockMDSpeaker), "LLW", "ESW", "LLW", 'L', Blocks.log, 'W', new ItemStack(Blocks.wool, 1, 15), 'S', ModBlocks.blockSMSpeaker, 'E', Items.ender_eye);
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.blockLGSpeaker), "LLW", "CMW", "LLW", 'L', Blocks.log, 'W', new ItemStack(Blocks.wool, 1, 15), 'M', ModBlocks.blockMDSpeaker, 'C', Items.comparator);

        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.blockStrobeLight), "GGG", "GRG", "CTC", 'G', Blocks.glass, 'C', Items.comparator, 'R', Blocks.redstone_lamp, 'T', Blocks.redstone_torch);
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.blockLazer), "LLL", "LQG", "HLH", 'L', Blocks.log, 'H', Blocks.wooden_slab, 'G', Blocks.glass, 'Q', Items.quartz);
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.blockLazerCluster), "LLL", "LRL", "LLL", 'L', ModBlocks.blockLazer, 'R', Items.redstone);

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
