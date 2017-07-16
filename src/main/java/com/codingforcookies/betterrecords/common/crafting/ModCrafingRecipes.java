package com.codingforcookies.betterrecords.common.crafting;

import com.codingforcookies.betterrecords.block.ModBlocks;
import com.codingforcookies.betterrecords.common.crafting.recipe.*;
import com.codingforcookies.betterrecords.item.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;

public final class ModCrafingRecipes {

    public static void init() {

        RecipeSorter.register("bettrecords:urlrecord", RecipeRecord.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
        GameRegistry.addRecipe(new RecipeRecord());

        RecipeSorter.register("betterrecords:urlmultirecord", RecipeMultiRecord.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
        GameRegistry.addRecipe(new RecipeMultiRecord());

        RecipeSorter.register("betterrecords:urlrecordrepeatable", RecipeRecordRepeatable.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
        GameRegistry.addRecipe(new RecipeRecordRepeatable());

        RecipeSorter.register("betterrecords:urlrecordshuffle", RecipeRecordShuffle.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
        GameRegistry.addRecipe(new RecipeRecordShuffle());

        RecipeSorter.register("betterrecords:freqcrystal", RecipeColoredFreqCrystal.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
        GameRegistry.addRecipe(new RecipeColoredFreqCrystal());

        RecipeSorter.register("betterrecords:urlrecord", RecipeColoredRecord.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
        GameRegistry.addRecipe(new RecipeColoredRecord());

        GameRegistry.addShapedRecipe(new ItemStack(ModItems.itemFreqCrystal), "RQR", "QDQ", "RQR", 'R', Items.REDSTONE, 'Q', Items.QUARTZ, 'D', Items.DIAMOND);

        GameRegistry.addShapedRecipe(new ItemStack(ModItems.itemRecordWire, 4), "WWW", "III", "WWW", 'I', Items.IRON_INGOT, 'W', new ItemStack(Blocks.WOOL, 1, 15));
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.itemRecordWire, 4), "WIW", "WIW", "WIW", 'I', Items.IRON_INGOT, 'W', new ItemStack(Blocks.WOOL, 1, 15));
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.itemRecordCutters), "I I", " I ", "WIW", 'I', Items.IRON_INGOT, 'W', new ItemStack(Blocks.WOOL, 1, 15));

        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.blockRecordEtcher), "HIH", "PQP", "PPP", 'H', Blocks.WOODEN_SLAB, 'I', Items.IRON_INGOT, 'P', Blocks.PLANKS, 'Q', Items.QUARTZ);
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.blockRecordPlayer), "GGG", "PDP", "PPP", 'G', Blocks.GLASS_PANE, 'P', Blocks.PLANKS, 'D', Blocks.DIAMOND_BLOCK);
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.blockFrequencyTuner), "SHH", "PQP", "PIP", 'H', Blocks.WOODEN_SLAB, 'I', Items.IRON_INGOT, 'S', Items.STICK, 'P', Blocks.PLANKS, 'Q', ModItems.itemFreqCrystal);
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.blockRadio), "HIH", "PQP", "PHP", 'H', Blocks.WOODEN_SLAB, 'I', Items.IRON_INGOT, 'P', Blocks.PLANKS, 'Q', ModItems.itemFreqCrystal);

        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.blockSMSpeaker), "LLW", "QDW", "LLW", 'L', Blocks.LOG, 'W', new ItemStack(Blocks.WOOL, 1, 15), 'D', Items.DIAMOND, 'Q', Items.QUARTZ);
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.blockMDSpeaker), "LLW", "ESW", "LLW", 'L', Blocks.LOG, 'W', new ItemStack(Blocks.WOOL, 1, 15), 'S', ModBlocks.blockSMSpeaker, 'E', Items.ENDER_EYE);
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.blockLGSpeaker), "LLW", "CMW", "LLW", 'L', Blocks.LOG, 'W', new ItemStack(Blocks.WOOL, 1, 15), 'M', ModBlocks.blockMDSpeaker, 'C', Items.COMPARATOR);

        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.blockStrobeLight), "GGG", "GRG", "CTC", 'G', Blocks.GLASS, 'C', Items.COMPARATOR, 'R', Blocks.REDSTONE_LAMP, 'T', Blocks.REDSTONE_TORCH);
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.blockLazer), "LLL", "LQG", "HLH", 'L', Blocks.LOG, 'H', Blocks.WOODEN_SLAB, 'G', Blocks.GLASS, 'Q', Items.QUARTZ);
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.blockLazerCluster), "LLL", "LRL", "LLL", 'L', ModBlocks.blockLazer, 'R', Items.REDSTONE);
    }
}
