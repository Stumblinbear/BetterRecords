package com.codingforcookies.betterrecords.common.core;

import com.codingforcookies.betterrecords.common.block.ModBlocks;
import com.codingforcookies.betterrecords.common.crafting.ModCrafingRecipes;
import com.codingforcookies.betterrecords.common.item.ModItems;

public class CommonProxy {

    public void preInit() {
        ModBlocks.init();
        ModItems.init();
    }

    public void init() {
        ModCrafingRecipes.init();
    }

    public void postInit() {

    }
}
