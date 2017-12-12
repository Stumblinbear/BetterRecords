package com.codingforcookies.betterrecords.block.tile

import com.codingforcookies.betterrecords.api.wire.IRecordWire
import com.codingforcookies.betterrecords.helper.ConnectionHelper
import net.minecraft.entity.item.EntityItem
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound

class TileRecordPlayer : SimpleRecordWireHome(), IRecordWire {

    override fun getName() = "Record Player"

    override val songRadiusIncrease = 40F

    override var record = ItemStack.EMPTY
        set(value) {
            field = value.copy()
            recordEntity = EntityItem(world, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), field)
            recordEntity?.hoverStart= 0F
            recordEntity?.rotationPitch = 0F
            recordEntity?.rotationYaw = 0F
            recordRotation = 0F
        }

    var recordEntity: EntityItem? = null

    var opening = false
    var openAmount = 0f

    var needleLocation = 0f
    var recordRotation = 0f

    override fun update() {
        if (opening) {
            if (openAmount > -0.8f) {
                openAmount -= 0.08f
            }
        } else if (openAmount < 0f) {
            openAmount += 0.12f
        }

        if (openAmount < -0.8f) {
            openAmount = -0.8f
        } else if (openAmount > 0f) {
            openAmount = 0f
        }

        if (!record.isEmpty) {
            recordRotation += 0.08f
            if (needleLocation < .3f) {
                needleLocation += 0.01f
            } else {
                needleLocation = .3f
            }
        } else if (needleLocation > 0f) {
            needleLocation -= 0.01f
        } else {
            needleLocation = 0f
        }

        super.update()
    }

    override fun readFromNBT(compound: NBTTagCompound) = compound.run {
        super.readFromNBT(compound)

        record = ItemStack(getCompoundTag("record"))
        opening = getBoolean("opening")

        connections = ConnectionHelper.unserializeConnections(getString("connections")).toMutableList()
        wireSystemInfo = ConnectionHelper.unserializeWireSystemInfo(compound.getString("wireSystemInfo"))

        playRadius = getFloat("playRadius")
    }

    override fun writeToNBT(compound: NBTTagCompound) = compound.apply {
        super.writeToNBT(compound)

        setTag("record", getStackTagCompound(record))
        setBoolean("opening", opening)

        setString("connections", ConnectionHelper.serializeConnections(connections))
        setString("wireSystemInfo", ConnectionHelper.serializeWireSystemInfo(wireSystemInfo))

        setFloat("playRadius", playRadius)
    }

    fun getStackTagCompound(stack: ItemStack?): NBTTagCompound {
        val tag = NBTTagCompound()
        stack?.writeToNBT(tag)
        return tag
    }
}
