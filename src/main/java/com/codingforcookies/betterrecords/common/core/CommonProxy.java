package com.codingforcookies.betterrecords.common.core;

import com.codingforcookies.betterrecords.common.BetterRecords;
import com.codingforcookies.betterrecords.common.block.ModBlocks;
import com.codingforcookies.betterrecords.common.core.handler.GuiHandler;
import com.codingforcookies.betterrecords.common.crafting.ModCrafingRecipes;
import com.codingforcookies.betterrecords.common.item.ModItems;
import com.codingforcookies.betterrecords.common.packets.ChannelHandler;
import com.codingforcookies.betterrecords.common.packets.PacketHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

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
