package com.codingforcookies.betterrecords.client;

import com.codingforcookies.betterrecords.common.BetterRecords;
import com.codingforcookies.betterrecords.common.item.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class BetterCreativeTab extends CreativeTabs {
    public BetterCreativeTab() {
        super("betterrecords");
    }

    public Item getTabIconItem() {
        return ModItems.itemURLRecord;
    }
}
