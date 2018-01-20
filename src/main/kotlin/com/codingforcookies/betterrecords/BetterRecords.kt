package com.codingforcookies.betterrecords

import com.codingforcookies.betterrecords.item.ModItems
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.SidedProxy
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Mod(modid = ID, name = NAME, version = VERSION, modLanguageAdapter = LANGUAGE_ADAPTER, dependencies = DEPENDENCIES)
object BetterRecords {

    val logger: Logger = LogManager.getLogger(ID)

    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
    lateinit var proxy: CommonProxy

    val creativeTab = object : CreativeTabs(ID) {
        override fun getTabIconItem() = ItemStack(ModItems.itemRecord)
    }

    @Mod.EventHandler fun preInit(event: FMLPreInitializationEvent) = proxy.preInit(event)
    @Mod.EventHandler fun init(event: FMLInitializationEvent) = proxy.init(event)
    @Mod.EventHandler fun postInit(event: FMLPostInitializationEvent) = proxy.postInit(event)
}
