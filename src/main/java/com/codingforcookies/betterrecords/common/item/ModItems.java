package com.codingforcookies.betterrecords.common.item;

import net.minecraft.item.Item;

public final class ModItems {

    public static Item itemURLRecord;
    public static Item itemURLMultiRecord;
    public static Item itemFreqCrystal;

    public static Item itemRecordWire;
    public static Item itemRecordCutters;

    public static void init() {
        itemURLRecord = new ItemURLRecord("urlrecord");
        itemURLMultiRecord = new ItemURLMultiRecord("urlmultirecord");
        itemFreqCrystal = new ItemFreqCrystal("freqcrystal");
        itemRecordWire = new ItemRecordWire("recordwire");
        itemRecordCutters = new ItemRecordWireCutter("recordwirecutters");
    }
}
