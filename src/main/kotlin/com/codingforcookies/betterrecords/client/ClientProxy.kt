package com.codingforcookies.betterrecords.client

import com.codingforcookies.betterrecords.CommonProxy
import com.codingforcookies.betterrecords.api.song.LibrarySong
import com.codingforcookies.betterrecords.block.ModBlock
import com.codingforcookies.betterrecords.block.tile.*
import com.codingforcookies.betterrecords.client.core.handler.BetterEventHandler
import com.codingforcookies.betterrecords.client.render.*
import com.codingforcookies.betterrecords.client.sound.SoundHandler
import com.codingforcookies.betterrecords.item.ModItem
import com.codingforcookies.betterrecords.item.ModItems
import net.minecraft.block.Block
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.color.IItemColor
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

class ClientProxy : CommonProxy() {

    companion object {
        /**
         * 0 = Unchecked
         * 1 = Single Player
         * 2 = Multi Player
         */
        var lastCheckType = 0
        val localLibrary = File(Minecraft.getMinecraft().mcDataDir, "betterrecords/localLibrary.json")

        val defaultLibrary = mutableListOf<LibrarySong>()
        var encodings = mutableListOf<String>(
                "audio/ogg", "application/ogg",
                "audio/mpeg", "audio/mpeg; charset=UTF-8",
                "application/octet-stream",
                "audio/wav", "audio/x-wav")
    }

    override fun preInit(event: FMLPreInitializationEvent) {
        super.preInit(event)

        ClientRegistry.bindTileEntitySpecialRenderer(TileRecordEtcher::class.java, RenderRecordEtcher())
        ClientRegistry.bindTileEntitySpecialRenderer(TileFrequencyTuner::class.java, RenderFrequencyTuner())
        ClientRegistry.bindTileEntitySpecialRenderer(TileLaserCluster::class.java, RenderLaserCluster())
        ClientRegistry.bindTileEntitySpecialRenderer(TileLaser::class.java, RenderLaser())
        ClientRegistry.bindTileEntitySpecialRenderer(TileRadio::class.java, RenderRadio())
        ClientRegistry.bindTileEntitySpecialRenderer(TileStrobeLight::class.java, RenderStrobeLight())
        ClientRegistry.bindTileEntitySpecialRenderer(TileRecordPlayer::class.java, RenderRecordPlayer())
        ClientRegistry.bindTileEntitySpecialRenderer(TileSpeaker::class.java, RenderSpeaker())

        Block.REGISTRY
                .filterIsInstance<ModBlock>()
                .forEach(ModBlock::registerTESR)

        SoundHandler.initalize()
    }

    override fun init(event: FMLInitializationEvent) {
        super.init(event)

        if (!localLibrary.exists()) {
            localLibrary.createNewFile()

            BufferedWriter(FileWriter(localLibrary)).run {
                write("{}")
                close()
            }
        }

        MinecraftForge.EVENT_BUS.register(BetterEventHandler())

        Item.REGISTRY
                .filterIsInstance<ModItem>()
                .forEach(ModItem::registerRender)
    }

    override fun postInit(event: FMLPostInitializationEvent) {
        super.postInit(event)

        val color = object : IItemColor {
            override fun getColorFromItemstack(stack: ItemStack, tintIndex: Int): Int {
                return if (stack.hasTagCompound() && stack.tagCompound!!.hasKey("color")) {
                    stack.tagCompound!!.getInteger("color")
                } else {
                    0xFFFFFF
                }
            }
        }

        Minecraft.getMinecraft().itemColors.run {
            registerItemColorHandler(color, ModItems.itemRecord)
            registerItemColorHandler(color, ModItems.itemMultiRecord)
            registerItemColorHandler(color, ModItems.itemFrequencyCrystal)
        }
    }
}