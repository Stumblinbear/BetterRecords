package com.codingforcookies.betterrecords.block

object ModBlocks {

    val blockRecordEtcher = BlockRecordEtcher("recordetcher").register()
    val blockRecordPlayer = BlockRecordPlayer("recordplayer").register()
    val blockFrequencyTuner = BlockFrequencyTuner("frequencytuner").register()
    val blockRadio = BlockRadio("radio").register()
    val blockSpeakerSM = BlockSpeaker("speaker.sm", 0).register()
    val blockSpeakerMD = BlockSpeaker("speaker.md", 1).register()
    val blockSpeakerLG = BlockSpeaker("speaker.lg", 2).register()
    val blockStrobeLight = BlockStrobeLight("Strobelight").register()
    val blockLaser = BlockLaser("laser").register()
    val blockLaserCluster = BlockLaserCluster("lasercluster").register()
}
