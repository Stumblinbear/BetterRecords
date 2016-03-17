package com.codingforcookies.betterrecords.client.core.handler;

import com.codingforcookies.betterrecords.common.BetterRecords;
import com.codingforcookies.betterrecords.common.block.ModBlocks;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TESRRenderHandler {

    @SubscribeEvent
    public void onModelBakeEvent(ModelBakeEvent event) {
        event.modelManager.getBlockModelShapes().registerBuiltInBlocks(ModBlocks.blockRecordEtcher);
        event.modelManager.getBlockModelShapes().registerBuiltInBlocks(ModBlocks.blockRecordPlayer);
        event.modelManager.getBlockModelShapes().registerBuiltInBlocks(ModBlocks.blockFrequencyTuner);
        event.modelManager.getBlockModelShapes().registerBuiltInBlocks(ModBlocks.blockRadio);
        event.modelManager.getBlockModelShapes().registerBuiltInBlocks(ModBlocks.blockSMSpeaker);
        event.modelManager.getBlockModelShapes().registerBuiltInBlocks(ModBlocks.blockMDSpeaker);
        event.modelManager.getBlockModelShapes().registerBuiltInBlocks(ModBlocks.blockLGSpeaker);
        event.modelManager.getBlockModelShapes().registerBuiltInBlocks(ModBlocks.blockStrobeLight);
        event.modelManager.getBlockModelShapes().registerBuiltInBlocks(ModBlocks.blockLazer);
        event.modelManager.getBlockModelShapes().registerBuiltInBlocks(ModBlocks.blockLazerCluster);
    }
}
