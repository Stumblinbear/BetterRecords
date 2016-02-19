package com.codingforcookies.betterrecords.client;

import com.codingforcookies.betterrecords.BetterRecords;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TESRRenderHelper {

    @SubscribeEvent
    public void onModelBakeEvent(ModelBakeEvent event) {
        event.modelManager.getBlockModelShapes().registerBuiltInBlocks(BetterRecords.blockRecordEtcher);
        event.modelManager.getBlockModelShapes().registerBuiltInBlocks(BetterRecords.blockRecordPlayer);
        event.modelManager.getBlockModelShapes().registerBuiltInBlocks(BetterRecords.blockFrequencyTuner);
        event.modelManager.getBlockModelShapes().registerBuiltInBlocks(BetterRecords.blockRadio);
        event.modelManager.getBlockModelShapes().registerBuiltInBlocks(BetterRecords.blockSMSpeaker);
        event.modelManager.getBlockModelShapes().registerBuiltInBlocks(BetterRecords.blockMDSpeaker);
        event.modelManager.getBlockModelShapes().registerBuiltInBlocks(BetterRecords.blockLGSpeaker);
        event.modelManager.getBlockModelShapes().registerBuiltInBlocks(BetterRecords.blockStrobeLight);
        event.modelManager.getBlockModelShapes().registerBuiltInBlocks(BetterRecords.blockLazer);
        event.modelManager.getBlockModelShapes().registerBuiltInBlocks(BetterRecords.blockLazerCluster);
    }
}
