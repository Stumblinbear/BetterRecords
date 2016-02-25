package com.codingforcookies.betterrecords.api.record;

import com.codingforcookies.betterrecords.api.wire.IRecordWireHome;
import net.minecraft.item.ItemStack;

public interface IRecord {
    boolean isRecordValid(ItemStack par1ItemStack);
    void onRecordInserted(IRecordWireHome par1WireHome, ItemStack par2ItemStack);
}