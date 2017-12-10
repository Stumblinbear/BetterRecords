package com.codingforcookies.betterrecords

import com.codingforcookies.betterrecords.handler.GuiHandler
import com.codingforcookies.betterrecords.common.packets.ChannelHandler
import com.codingforcookies.betterrecords.common.packets.PacketHandler
import com.codingforcookies.betterrecords.handler.ConfigHandler
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.network.NetworkRegistry

open class CommonProxy {

    open fun preInit(event: FMLPreInitializationEvent) {
        ConfigHandler.loadConfig(event.suggestedConfigurationFile)
    }

    open fun init(event: FMLInitializationEvent) {
        PacketHandler.channels = NetworkRegistry.INSTANCE.newChannel("BetterRecords", ChannelHandler())
        NetworkRegistry.INSTANCE.registerGuiHandler(BetterRecords, GuiHandler())
    }

    open fun postInit(event: FMLPostInitializationEvent) {

    }
}
