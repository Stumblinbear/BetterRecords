package com.codingforcookies.betterrecords

import com.codingforcookies.betterrecords.block.ModBlock
import com.codingforcookies.betterrecords.block.ModBlocks
import com.codingforcookies.betterrecords.common.core.handler.GuiHandler
import com.codingforcookies.betterrecords.common.crafting.ModCrafingRecipes
import com.codingforcookies.betterrecords.common.packets.ChannelHandler
import com.codingforcookies.betterrecords.common.packets.PacketHandler
import com.codingforcookies.betterrecords.handler.ConfigHandler
import com.codingforcookies.betterrecords.item.ModItem
import com.codingforcookies.betterrecords.item.ModItems
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.network.NetworkRegistry
import kotlin.reflect.full.declaredMemberProperties

open class CommonProxy {

    open fun preInit(event: FMLPreInitializationEvent) {
        ConfigHandler.loadConfig(event.suggestedConfigurationFile)

        ModBlocks::class.declaredMemberProperties
                .map { it.invoke(ModBlocks) }
                .filterIsInstance<ModBlock>()
                .forEach { it.register() }

        ModItems::class.declaredMemberProperties
                .map { it.invoke(ModItems) }
                .filterIsInstance<ModItem>()
                .forEach { it.register() }
    }

    open fun init(event: FMLInitializationEvent) {
        PacketHandler.channels = NetworkRegistry.INSTANCE.newChannel("BetterRecords", ChannelHandler())
        NetworkRegistry.INSTANCE.registerGuiHandler(BetterRecords, GuiHandler())
        ModCrafingRecipes.init()
    }

    open fun postInit(event: FMLPostInitializationEvent) {

    }
}
