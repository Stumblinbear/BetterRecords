package com.codingforcookies.betterrecords.block.tile

import com.codingforcookies.betterrecords.api.connection.RecordConnection
import com.codingforcookies.betterrecords.api.wire.IRecordWire
import com.codingforcookies.betterrecords.common.core.helper.ConnectionHelper
import com.codingforcookies.betterrecords.extensions.get
import com.codingforcookies.betterrecords.extensions.set
import net.minecraft.nbt.NBTTagCompound

import java.util.ArrayList

class TileSpeaker : BetterTile(), IRecordWire {

    companion object {
        private val prefixes = arrayOf("Small", "Medium", "Large")
        private val radius = floatArrayOf(15f, 35f, 70f)
    }

    var type = -1
    var rotation = 0f

    override var connections = mutableListOf<RecordConnection>()

    override fun getName() = when (type) {
        -1   -> "Invalid Speaker"
        else -> prefixes[type] + " Speaker"
    }

    override fun getSongRadiusIncrease() = when (type) {
        -1   -> 0F
        else -> radius[type]
    }

    override fun readFromNBT(compound: NBTTagCompound) = compound.run {
        super.readFromNBT(compound)

        type = getInteger("type")
        rotation = getFloat("rotation")
        connections = ConnectionHelper.unserializeConnections(getString("connections"))
    }

    override fun writeToNBT(compound: NBTTagCompound) = compound.apply {
        super.writeToNBT(compound)

        set("type", type)
        set("rotation", rotation)
        set("connections", ConnectionHelper.serializeConnections(connections))
    }

}
