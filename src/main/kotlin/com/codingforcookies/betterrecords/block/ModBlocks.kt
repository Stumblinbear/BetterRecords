package com.codingforcookies.betterrecords.block

import com.codingforcookies.betterrecords.ID
import net.minecraft.block.Block
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = ID)
object ModBlocks {

    val blockRecordEtcher = BlockRecordEtcher("recordetcher")
    val blockRecordPlayer = BlockRecordPlayer("recordplayer")
    val blockFrequencyTuner = BlockFrequencyTuner("frequencytuner")
    val blockRadio = BlockRadio("radio")
    val blockSpeakerSM = BlockSpeaker("speaker.sm", 0)
    val blockSpeakerMD = BlockSpeaker("speaker.md", 1)
    val blockSpeakerLG = BlockSpeaker("speaker.lg", 2)
    val blockStrobeLight = BlockStrobeLight("strobelight")
    val blockLaser = BlockLaser("laser")
    val blockLaserCluster = BlockLaserCluster("lasercluster")

    @SubscribeEvent
    fun registerBlocks(event: RegistryEvent.Register<Block>) {
        event.registry.registerAll(
                blockRecordEtcher,
                blockRecordPlayer,
                blockFrequencyTuner,
                blockRadio,
                blockSpeakerSM,
                blockSpeakerMD,
                blockSpeakerLG,
                blockStrobeLight,
                blockLaser,
                blockLaserCluster
        )
    }
}
