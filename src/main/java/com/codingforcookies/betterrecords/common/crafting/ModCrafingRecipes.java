package com.codingforcookies.betterrecords.common.crafting;

import com.codingforcookies.betterrecords.common.block.ModBlocks;
import com.codingforcookies.betterrecords.common.crafting.recipe.*;
import com.codingforcookies.betterrecords.common.item.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;

public final class ModCrafingRecipes {

    public static void init() {
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
    }
}
