package com.codingforcookies.betterrecords.client.core.handler;

import com.codingforcookies.betterrecords.common.block.ModBlocks;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TESRRenderHandler {

    @SubscribeEvent
    public void onModelBakeEvent(ModelBakeEvent event) {
        event.getModelManager().getBlockModelShapes().registerBuiltInBlocks(ModBlocks.blockRecordEtcher);
        event.getModelManager().getBlockModelShapes().registerBuiltInBlocks(ModBlocks.blockRecordPlayer);
        event.getModelManager().getBlockModelShapes().registerBuiltInBlocks(ModBlocks.blockFrequencyTuner);
        event.getModelManager().getBlockModelShapes().registerBuiltInBlocks(ModBlocks.blockRadio);
        event.getModelManager().getBlockModelShapes().registerBuiltInBlocks(ModBlocks.blockSMSpeaker);
        event.getModelManager().getBlockModelShapes().registerBuiltInBlocks(ModBlocks.blockMDSpeaker);
        event.getModelManager().getBlockModelShapes().registerBuiltInBlocks(ModBlocks.blockLGSpeaker);
        event.getModelManager().getBlockModelShapes().registerBuiltInBlocks(ModBlocks.blockStrobeLight);
        event.getModelManager().getBlockModelShapes().registerBuiltInBlocks(ModBlocks.blockLazer);
        event.getModelManager().getBlockModelShapes().registerBuiltInBlocks(ModBlocks.blockLazerCluster);
    }
}
