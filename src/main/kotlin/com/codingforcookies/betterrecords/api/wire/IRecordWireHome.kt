package com.codingforcookies.betterrecords.api.wire

import com.codingforcookies.betterrecords.api.connection.RecordConnection
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity

interface  IRecordWireHome {

    fun addTreble(form: Float)
    fun addBass(form: Float)

    val connections: MutableList<RecordConnection>

    fun increaseAmount(wireComponent: IRecordWire)
    fun decreaseAmount(wireComponent: IRecordWire)

    val songRadius: Float

    val tileEntity: TileEntity
    val record: ItemStack

    var wireSystemInfo: HashMap<String, Int>
}
