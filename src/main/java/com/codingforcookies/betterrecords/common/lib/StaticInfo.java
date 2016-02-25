package com.codingforcookies.betterrecords.common.lib;

import com.codingforcookies.betterrecords.client.model.*;
import com.codingforcookies.betterrecords.common.BetterRecords;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class StaticInfo {
    public static final ResourceLocation GUIRecordEtcher = new ResourceLocation(BetterRecords.ID, "textures/gui/recordetcher.png");
    public static final ResourceLocation GUIFrequencyTuner = new ResourceLocation(BetterRecords.ID, "textures/gui/frequencytuner.png");

    public static final ModelRecordEtcher modelRecordEtcher = new ModelRecordEtcher();
    public static final ResourceLocation modelRecordEtcherRes = new ResourceLocation(BetterRecords.ID, "textures/models/recordetcher.png");
    public static final ModelRecordPlayer modelRecordPlayer = new ModelRecordPlayer();
    public static final ResourceLocation modelRecordPlayerRes = new ResourceLocation(BetterRecords.ID, "textures/models/recordplayer.png");
    public static final ModelFrequencyTuner modelFrequencyTuner = new ModelFrequencyTuner();
    public static final ResourceLocation modelFrequencyTunerRes = new ResourceLocation(BetterRecords.ID, "textures/models/frequencytuner.png");
    public static final ModelRadio modelRadio = new ModelRadio();
    public static final ResourceLocation modelRadioRes = new ResourceLocation(BetterRecords.ID, "textures/models/radio.png");

    public static final ModelSMSpeaker modelSMSpeaker = new ModelSMSpeaker();
    public static final ResourceLocation modelSMSpeakerRes = new ResourceLocation(BetterRecords.ID, "textures/models/smspeaker.png");
    public static final ModelMDSpeaker modelMDSpeaker = new ModelMDSpeaker();
    public static final ResourceLocation modelMDSpeakerRes = new ResourceLocation(BetterRecords.ID, "textures/models/mdspeaker.png");
    public static final ModelLGSpeaker modelLGSpeaker = new ModelLGSpeaker();
    public static final ResourceLocation modelLGSpeakerRes = new ResourceLocation(BetterRecords.ID, "textures/models/lgspeaker.png");

    public static final ModelStrobeLight modelStrobeLight = new ModelStrobeLight();
    public static ResourceLocation modelStrobeLightRes = new ResourceLocation(BetterRecords.ID, "textures/models/strobelight.png");
    public static final ModelLazer modelLazer = new ModelLazer();
    public static ResourceLocation modelLazerRes = new ResourceLocation(BetterRecords.ID, "textures/models/lazer.png");

    public static final PropertyEnum<EnumFacing> CARDINAL_DIRECTIONS = PropertyEnum.create("facing", EnumFacing.class, EnumFacing.Plane.HORIZONTAL);
}
