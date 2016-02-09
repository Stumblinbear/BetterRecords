package com.codingforcookies.betterrecords.betterenums;

import net.minecraft.item.ItemStack;

public interface IRecord {
	public boolean isRecordValid(ItemStack par1ItemStack);
	public void onRecordInserted(IRecordWireHome par1WireHome, ItemStack par2ItemStack);
}