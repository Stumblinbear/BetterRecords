package com.codingforcookies.betterrecords.block.tile

import com.codingforcookies.betterrecords.api.connection.RecordConnection
import com.codingforcookies.betterrecords.api.record.IRecordAmplitude
import com.codingforcookies.betterrecords.api.wire.IRecordWire
import com.codingforcookies.betterrecords.helper.ConnectionHelper
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ITickable

class TileStrobeLight : ModTile(), IRecordWire, IRecordAmplitude, ITickable {

    override var connections = mutableListOf<RecordConnection>()

    override var treble = 0F
    override var bass = 0F
        set(value) {
            field = if (value < 8F) 0F else value
        }

    override val songRadiusIncrease = 0F

    override fun getName() = "Strobe Light"

    override fun update() {
        if (bass > 0) bass--
        if (bass < 0) bass = 0F
    }

    override fun readFromNBT(compound: NBTTagCompound) = compound.run {
        super.readFromNBT(compound)

        connections = ConnectionHelper.unserializeConnections(getString("connections")).toMutableList()
    }

    override fun writeToNBT(compound: NBTTagCompound) = compound.apply {
        super.writeToNBT(compound)

        setString("connections", ConnectionHelper.serializeConnections(connections))
    }
}
