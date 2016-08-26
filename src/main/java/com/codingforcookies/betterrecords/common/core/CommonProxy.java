package com.codingforcookies.betterrecords.common.core;

import com.codingforcookies.betterrecords.common.block.ModBlocks;
import com.codingforcookies.betterrecords.common.crafting.ModCrafingRecipes;
import com.codingforcookies.betterrecords.common.item.ModItems;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        ModBlocks.init();
        ModItems.init();
    }

    public void init(FMLInitializationEvent event) {
        ModCrafingRecipes.init();
    }

    public void postInit(FMLPostInitializationEvent event) {

    }
}
