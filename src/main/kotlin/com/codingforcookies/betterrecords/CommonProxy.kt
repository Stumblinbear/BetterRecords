package com.codingforcookies.betterrecords

import com.codingforcookies.betterrecords.block.ModBlocks
import com.codingforcookies.betterrecords.common.core.handler.ConfigHandler
import com.codingforcookies.betterrecords.common.core.handler.GuiHandler
import com.codingforcookies.betterrecords.common.crafting.ModCrafingRecipes
import com.codingforcookies.betterrecords.common.packets.ChannelHandler
import com.codingforcookies.betterrecords.common.packets.PacketHandler
import com.codingforcookies.betterrecords.item.ModItems
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.network.NetworkRegistry

open class CommonProxy {

    open fun preInit(event: FMLPreInitializationEvent) {
        ConfigHandler.loadConfig(event.suggestedConfigurationFile)

        ModBlocks
        ModItems
    }

    open fun init(event: FMLInitializationEvent) {
        PacketHandler.channels = NetworkRegistry.INSTANCE.newChannel("BetterRecords", ChannelHandler())
        NetworkRegistry.INSTANCE.registerGuiHandler(BetterRecords, GuiHandler())
        ModCrafingRecipes.init()
    }

    open fun postInit(event: FMLPostInitializationEvent) {

    }
}
