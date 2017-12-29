package com.codingforcookies.betterrecords

import com.codingforcookies.betterrecords.handler.ConfigHandler
import com.codingforcookies.betterrecords.handler.GuiHandler
import com.codingforcookies.betterrecords.library.Libraries
import com.codingforcookies.betterrecords.network.PacketHandler
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.network.NetworkRegistry

open class CommonProxy {

    open fun preInit(event: FMLPreInitializationEvent) {
        ConfigHandler.loadConfig(event.suggestedConfigurationFile)

        PacketHandler.init()
    }

    open fun init(event: FMLInitializationEvent) {
        NetworkRegistry.INSTANCE.registerGuiHandler(BetterRecords, GuiHandler())

        Libraries.init()
    }

    open fun postInit(event: FMLPostInitializationEvent) {

    }
}
