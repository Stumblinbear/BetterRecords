package com.codingforcookies.betterrecords.block.tile

import com.codingforcookies.betterrecords.api.connection.RecordConnection
import com.codingforcookies.betterrecords.api.record.IRecordAmplitude
import com.codingforcookies.betterrecords.api.wire.IRecordWire
import com.codingforcookies.betterrecords.common.core.helper.ConnectionHelper
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ITickable
import java.util.*

class TileLaserCluster : BetterTile(), IRecordWire, IRecordAmplitude, ITickable {

    override var connections = mutableListOf<RecordConnection>()

    var r = 0F
    var g = 0F
    var b = 0F

    override var treble = 0F
    override var bass = 0F
        set(value) {
            field = value
            Random(value.toLong() + System.nanoTime()).run {
                r = nextFloat()
                g = nextFloat()
                b = nextFloat()
            }

            Random().nextInt().also {
                r += if (it == 0) .3f else -.1f
                g += if (it == 1) .3f else -.1f
                b += if (it == 2) .3f else -.1f
            }

            if (r < .2F) r += r
            if (g < .2F) g += g
            if (b < .2F) b += b
        }

    override fun getName() = "Laser Cluster"

    override val songRadiusIncrease = 0F

    override fun update() {
        if (bass > 0) bass--
        if (bass < 0) bass = 0F
    }

    override fun readFromNBT(compound: NBTTagCompound) = compound.run {
        super.readFromNBT(compound)

        connections = ConnectionHelper.unserializeConnections(getString("connections"))
    }

    override fun writeToNBT(compound: NBTTagCompound) = compound.apply {
        super.writeToNBT(compound)

        setString("connections", ConnectionHelper.serializeConnections(connections))
    }
}
