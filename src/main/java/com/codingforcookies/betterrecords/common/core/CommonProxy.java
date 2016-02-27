package com.codingforcookies.betterrecords.common.core;

import com.codingforcookies.betterrecords.common.core.handler.GuiHandler;
import com.codingforcookies.betterrecords.common.block.ModBlocks;
import com.codingforcookies.betterrecords.common.crafting.ModCrafingRecipes;
import com.codingforcookies.betterrecords.common.item.ModItems;
import com.codingforcookies.betterrecords.common.packets.ChannelHandler;
import com.codingforcookies.betterrecords.common.packets.PacketHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {

    public void preInit() {
        ModBlocks.init();
        ModItems.init();
        ModCrafingRecipes.init();
    }

    public void init() {
        PacketHandler.channels = NetworkRegistry.INSTANCE.newChannel("BetterRecords", new ChannelHandler());
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
    }

    public void postInit() {

    }
}
