package com.codingforcookies.betterrecords.block

import com.codingforcookies.betterrecords.ID
import com.codingforcookies.betterrecords.block.itemblock.ItemBlockSpeaker
import com.codingforcookies.betterrecords.block.tile.*
import com.codingforcookies.betterrecords.client.render.*
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = ID)
object ModBlocks {

    val blockRecordEtcher = BlockRecordEtcher("recordetcher")
    val blockRecordPlayer = BlockRecordPlayer("recordplayer")
    val blockFrequencyTuner = BlockFrequencyTuner("frequencytuner")
    val blockRadio = BlockRadio("radio")
    val blockSpeaker = BlockSpeaker("speaker")
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
                blockSpeaker,
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
                blockStrobeLight,
                blockLaser,
                blockLaserCluster
        ).map {
            ItemBlock(it).setRegistryName(it.registryName)
        }.forEach(event.registry::register)

        // Register the speaker specially since it has variants
        event.registry.register(ItemBlockSpeaker(blockSpeaker).setRegistryName(blockSpeaker.registryName))

        Block.REGISTRY
                .filterIsInstance<ModBlock>()
                .forEach(ModBlock::registerItemModel)
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
