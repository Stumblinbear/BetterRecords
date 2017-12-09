package com.codingforcookies.betterrecords.crafting

import com.codingforcookies.betterrecords.crafting.recipe.*
import net.minecraft.item.crafting.IRecipe
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber
object CrafingRecipes {

    @SubscribeEvent
    fun registerRecipes(event: RegistryEvent.Register<IRecipe>) {
        event.registry.registerAll(
                RecipeMultiRecord(),
                RecipeRecordRepeatable(),
                RecipeRecordShuffle(),
                RecipeColoredFreqCrystal(),
                RecipeColoredRecord()
        )
    }

//    fun init() {
//
//        RecipeSorter.register("betterrecords:urlmultirecord", RecipeMultiRecord::class.java, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless")
//        GameRegistry.addRecipe(RecipeMultiRecord())
//
//        RecipeSorter.register("betterrecords:urlrecordrepeatable", RecipeRecordRepeatable::class.java, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless")
//        GameRegistry.addRecipe(RecipeRecordRepeatable())
//
//        RecipeSorter.register("betterrecords:urlrecordshuffle", RecipeRecordShuffle::class.java, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless")
//        GameRegistry.addRecipe(RecipeRecordShuffle())
//
//        RecipeSorter.register("betterrecords:freqcrystal", RecipeColoredFreqCrystal::class.java, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless")
//        GameRegistry.addRecipe(RecipeColoredFreqCrystal())
//
//        RecipeSorter.register("betterrecords:urlrecord", RecipeColoredRecord::class.java, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless")
//        GameRegistry.addRecipe(RecipeColoredRecord())

//        GameRegistry.addShapedRecipe(ItemStack(ModItems.itemFrequencyCrystal), "RQR", "QDQ", "RQR", 'R', Items.REDSTONE, 'Q', Items.QUARTZ, 'D', Items.DIAMOND)

//        GameRegistry.addRecipe(ShapelessOreRecipe(ItemStack(ModItems.itemRecord), "record", Items.ENDER_EYE))

//        GameRegistry.addShapedRecipe(ItemStack(ModItems.itemWire, 4), "WWW", "III", "WWW", 'I', Items.IRON_INGOT, 'W', ItemStack(Blocks.WOOL, 1, 15))
//        GameRegistry.addShapedRecipe(ItemStack(ModItems.itemWire, 4), "WIW", "WIW", "WIW", 'I', Items.IRON_INGOT, 'W', ItemStack(Blocks.WOOL, 1, 15))
//        GameRegistry.addShapedRecipe(ItemStack(ModItems.itemWireCutters), "I I", " I ", "WIW", 'I', Items.IRON_INGOT, 'W', ItemStack(Blocks.WOOL, 1, 15))

//        GameRegistry.addShapedRecipe(ItemStack(ModBlocks.blockRecordEtcher), "HIH", "PQP", "PPP", 'H', Blocks.WOODEN_SLAB, 'I', Items.IRON_INGOT, 'P', Blocks.PLANKS, 'Q', Items.QUARTZ)
//        GameRegistry.addShapedRecipe(ItemStack(ModBlocks.blockRecordPlayer), "GGG", "PDP", "PPP", 'G', Blocks.GLASS_PANE, 'P', Blocks.PLANKS, 'D', Blocks.DIAMOND_BLOCK)
//        GameRegistry.addShapedRecipe(ItemStack(ModBlocks.blockFrequencyTuner), "SHH", "PQP", "PIP", 'H', Blocks.WOODEN_SLAB, 'I', Items.IRON_INGOT, 'S', Items.STICK, 'P', Blocks.PLANKS, 'Q', ModItems.itemFrequencyCrystal)
//        GameRegistry.addShapedRecipe(ItemStack(ModBlocks.blockRadio), "HIH", "PQP", "PHP", 'H', Blocks.WOODEN_SLAB, 'I', Items.IRON_INGOT, 'P', Blocks.PLANKS, 'Q', ModItems.itemFrequencyCrystal)
//        GameRegistry.addShapedRecipe(ItemStack(ModBlocks.blockSpeakerSM), "LLW", "QDW", "LLW", 'L', Blocks.LOG, 'W', ItemStack(Blocks.WOOL, 1, 15), 'D', Items.DIAMOND, 'Q', Items.QUARTZ)
//        GameRegistry.addShapedRecipe(ItemStack(ModBlocks.blockSpeakerMD), "LLW", "ESW", "LLW", 'L', Blocks.LOG, 'W', ItemStack(Blocks.WOOL, 1, 15), 'S', ModBlocks.blockSpeakerSM, 'E', Items.ENDER_EYE)
//        GameRegistry.addShapedRecipe(ItemStack(ModBlocks.blockSpeakerLG), "LLW", "CMW", "LLW", 'L', Blocks.LOG, 'W', ItemStack(Blocks.WOOL, 1, 15), 'M', ModBlocks.blockSpeakerMD, 'C', Items.COMPARATOR)

//        GameRegistry.addShapedRecipe(ItemStack(ModBlocks.blockStrobeLight), "GGG", "GRG", "CTC", 'G', Blocks.GLASS, 'C', Items.COMPARATOR, 'R', Blocks.REDSTONE_LAMP, 'T', Blocks.REDSTONE_TORCH)
//        GameRegistry.addShapedRecipe(ItemStack(ModBlocks.blockLaser), "LLL", "LQG", "HLH", 'L', Blocks.LOG, 'H', Blocks.WOODEN_SLAB, 'G', Blocks.GLASS, 'Q', Items.QUARTZ)
//        GameRegistry.addShapedRecipe(ItemStack(ModBlocks.blockLaserCluster), "LLL", "LRL", "LLL", 'L', ModBlocks.blockLaser, 'R', Items.REDSTONE)
//    }
}
