package com.codingforcookies.betterrecords.src.client;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.codingforcookies.betterrecords.src.BetterRecords;

public class BetterCreativeTab extends CreativeTabs {
	public BetterCreativeTab() {
		super("betterrecords");
	}
	
	public Item getTabIconItem() {
		return BetterRecords.itemURLRecord;
	}
}