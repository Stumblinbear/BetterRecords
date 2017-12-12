package com.codingforcookies.betterrecords.block

import com.codingforcookies.betterrecords.ID
import com.codingforcookies.betterrecords.block.tile.*
import com.codingforcookies.betterrecords.client.render.*
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.registry.GameRegistry
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties

@Mod.EventBusSubscriber(modid = ID)
object ModBlocks {

    val blockRecordEtcher = BlockRecordEtcher("recordetcher")
    val blockRecordPlayer = BlockRecordPlayer("recordplayer")
    val blockFrequencyTuner = BlockFrequencyTuner("frequencytuner")
    val blockRadio = BlockRadio("radio")
    val blockSpeakerSM = BlockSpeaker("speaker.sm", 0)
    // val blockSpeakerMD = BlockSpeaker("speaker.md", 1)
    // val blockSpeakerLG = BlockSpeaker("speaker.lg", 2)
    val blockStrobeLight = BlockStrobeLight("strobelight")
    val blockLaser = BlockLaser("laser")
    val blockLaserCluster = BlockLaserCluster("lasercluster")

    // TODO: Use reflection to register declared fields

    @JvmStatic
    @SubscribeEvent
    fun registerBlocks(event: RegistryEvent.Register<Block>) {
        event.registry.registerAll(
                blockRecordEtcher,
                blockRecordPlayer,
                blockFrequencyTuner,
                blockRadio,
                blockSpeakerSM,
                //blockSpeakerMD,
                //blockSpeakerLG,
                blockStrobeLight,
                blockLaser,
                blockLaserCluster
        )
    }

    @JvmStatic
    @SubscribeEvent
    fun registerItems(event: RegistryEvent.Register<Item>) {
        arrayOf(
                blockRecordEtcher,
                blockRecordPlayer,
                blockFrequencyTuner,
                blockRadio,
                blockSpeakerSM,
                blockStrobeLight,
                blockLaser,
                blockLaserCluster
        ).map {
            ItemBlock(it).setRegistryName(it.registryName)
        }.forEach(event.registry::register)
    }

    @JvmStatic
    @SubscribeEvent
    fun registerModels(event: ModelRegistryEvent) {
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
    }
}
