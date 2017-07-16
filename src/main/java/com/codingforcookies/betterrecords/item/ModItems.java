package com.codingforcookies.betterrecords.item;

import net.minecraft.item.Item;

public final class ModItems {

    public static Item itemURLRecord;
    public static Item itemURLMultiRecord;
    public static Item itemFreqCrystal;

    public static Item itemRecordWire;
    public static Item itemRecordCutters;

    public static void init() {
        itemURLRecord = new ItemRecord("urlrecord");
        itemURLMultiRecord = new ItemMultiRecord("urlmultirecord");
        itemFreqCrystal = new ItemFrequencyCrystal("freqcrystal");
        itemRecordWire = new ItemWire("recordwire");
        itemRecordCutters = new ItemWireCutter("recordwirecutters");
    }
}
