package com.codingforcookies.betterrecords.common.core;

import com.codingforcookies.betterrecords.BetterRecords;
import com.codingforcookies.betterrecords.block.ModBlocks;
import com.codingforcookies.betterrecords.common.core.handler.ConfigHandler;
import com.codingforcookies.betterrecords.common.core.handler.GuiHandler;
import com.codingforcookies.betterrecords.common.crafting.ModCrafingRecipes;
import com.codingforcookies.betterrecords.item.ModItems;
import com.codingforcookies.betterrecords.common.packets.ChannelHandler;
import com.codingforcookies.betterrecords.common.packets.PacketHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        ConfigHandler.loadConfig(event.getSuggestedConfigurationFile());

        ModBlocks.init();
        ModItems.init();
    }

    public void init(FMLInitializationEvent event) {
        PacketHandler.channels = NetworkRegistry.INSTANCE.newChannel("BetterRecords", new ChannelHandler());
        NetworkRegistry.INSTANCE.registerGuiHandler(BetterRecords.INSTANCE, new GuiHandler());
        ModCrafingRecipes.init();
    }

    public void postInit(FMLPostInitializationEvent event) {

    }
}
