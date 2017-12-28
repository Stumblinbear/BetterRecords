package com.codingforcookies.betterrecords.api.record

import com.codingforcookies.betterrecords.api.wire.IRecordWireHome
import net.minecraft.item.ItemStack

interface IRecord {

    fun isRecordValid(itemStack: ItemStack): Boolean

    fun onRecordInserted(wireHome: IRecordWireHome, itemStack: ItemStack)
}
