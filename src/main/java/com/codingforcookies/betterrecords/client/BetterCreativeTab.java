package com.codingforcookies.betterrecords.client;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.codingforcookies.betterrecords.BetterRecords;

public class BetterCreativeTab extends CreativeTabs {
	public BetterCreativeTab() {
		super("betterrecords");
	}
	
	public Item getTabIconItem() {
		return BetterRecords.itemURLRecord;
	}
}