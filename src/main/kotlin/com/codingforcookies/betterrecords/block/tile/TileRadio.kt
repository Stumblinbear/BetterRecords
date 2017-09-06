package com.codingforcookies.betterrecords.block.tile

import com.codingforcookies.betterrecords.api.wire.IRecordWire
import com.codingforcookies.betterrecords.block.tile.delegate.CopyOnSetDelegate
import com.codingforcookies.betterrecords.common.core.helper.ConnectionHelper
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound

class TileRadio : SimpleRecordWireHome(), IRecordWire {

    var crystal by CopyOnSetDelegate()
    override val record: ItemStack?
        get() { return crystal }

    var crystalFloaty = 0F

    var opening = false
    var openAmount = 0F

    override fun getName() = "Radio"

    override val songRadiusIncrease = 30F

    override fun update() {
        if (opening && openAmount < 0.268F) {
            openAmount += 0.04F
        } else if (!opening && openAmount > 0F) {
            openAmount -= 0.04F
        }

        if (openAmount > 0.268F) {
            openAmount = 0.268F
        } else if (openAmount < 0F) {
            openAmount = 0F
        }

        crystal?.let {
            crystalFloaty += 0.86F
        }

        super.update()
    }

    override fun readFromNBT(compound: NBTTagCompound) = compound.run {
        super.readFromNBT(compound)

        crystal = ItemStack.loadItemStackFromNBT(getCompoundTag("crystal"))
        opening = getBoolean("opening")
        connections = ConnectionHelper.unserializeConnections(getString("connections"))
        wireSystemInfo = ConnectionHelper.unserializeWireSystemInfo(getString("wireSystemInfo"))
        playRadius = getFloat("playRadius")
    }

    override fun writeToNBT(compound: NBTTagCompound) = compound.apply {
        super.writeToNBT(compound)

        setFloat("rotation", blockMetadata.toFloat())
        setTag("crystal", getStackTagCompound(crystal))
        setBoolean("opening", opening)
        setString("connections", ConnectionHelper.serializeConnections(connections))
        setString("wireSystemInfo", ConnectionHelper.serializeWireSystemInfo(wireSystemInfo))
        setFloat("playRadius", playRadius)

        return compound
    }

    fun getStackTagCompound(stack: ItemStack?): NBTTagCompound {
        val tag = NBTTagCompound()
        stack?.writeToNBT(tag)
        return tag
    }
}
