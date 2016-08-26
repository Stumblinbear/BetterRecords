package com.codingforcookies.betterrecords.api.record;

import com.codingforcookies.betterrecords.api.wire.IRecordWireHome;
import net.minecraft.item.ItemStack;

public interface IRecord {
    boolean isRecordValid(ItemStack itemStack);
    void onRecordInserted(IRecordWireHome wireHome, ItemStack itemStack);
}
