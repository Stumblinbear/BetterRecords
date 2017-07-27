package com.codingforcookies.betterrecords.block.tile

import com.codingforcookies.betterrecords.api.wire.IRecordWire
import net.minecraft.entity.item.EntityItem
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound

class TileRecordPlayer : SimpleRecordWireHome(), IRecordWire {

    override fun getName() = "Record Player"

    override val songRadiusIncrease = 40F

    override var record: ItemStack? = null
        set(value) {
            value?.let {
                field = value.copy()
                field!!.stackSize = 1
                recordEntity = EntityItem(world, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), this.record!!)
                recordEntity!!.hoverStart = 0f
                recordEntity!!.rotationPitch = 0f
                recordEntity!!.rotationYaw = 0f
                recordRotation = 0f
                return
            }

            field = null
            recordEntity = null
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

        if (record != null) {
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

        record = ItemStack.loadItemStackFromNBT(getCompoundTag("record"))
        opening = getBoolean("opening")
    }

    override fun writeToNBT(compound: NBTTagCompound) = compound.apply {
        super.writeToNBT(compound)

        setFloat("rotation", blockMetadata.toFloat())
        setTag("record", getStackTagCompound(record))
        setBoolean("opening", opening)
    }

    fun getStackTagCompound(stack: ItemStack?): NBTTagCompound {
        val tag = NBTTagCompound()
        stack?.writeToNBT(tag)
        return tag
    }
}
