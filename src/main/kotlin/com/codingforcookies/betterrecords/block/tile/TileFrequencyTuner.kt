package com.codingforcookies.betterrecords.block.tile

import com.codingforcookies.betterrecords.block.tile.delegate.CopyOnSetDelegate
import com.codingforcookies.betterrecords.item.ItemFrequencyCrystal
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ITickable
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class TileFrequencyTuner : ModInventoryTile(), IInventory, ITickable {

    var crystal by CopyOnSetDelegate()

    var crystalFloaty = 0F

    override fun getName() = "Frequency Tuner"

    override fun getSizeInventory() = 1
    override fun getInventoryStackLimit() = 1

    override fun getStackInSlot(index: Int) = crystal

    override fun isItemValidForSlot(index: Int, stack: ItemStack) =
            stack.item is ItemFrequencyCrystal && (!stack.hasTagCompound() || !stack.tagCompound!!.hasKey("url"))

    override fun setInventorySlotContents(index: Int, stack: ItemStack?) {
        crystal = stack
    }

    @SideOnly(Side.CLIENT)
    override fun update() {
        crystal?.let {
            crystalFloaty += 0.86F
        }
    }

    override fun readFromNBT(compound: NBTTagCompound) = compound.run {
        super.readFromNBT(compound)

        crystal = ItemStack.loadItemStackFromNBT(getCompoundTag("crystal"))
    }

    override fun writeToNBT(compound: NBTTagCompound) = compound.apply {
        super.writeToNBT(compound)

        setTag("crystal", getStackTagCompound(crystal))
    }

    fun getStackTagCompound(stack: ItemStack?): NBTTagCompound {
        val tag = NBTTagCompound()
        stack?.writeToNBT(tag)
        return tag
    }

}
