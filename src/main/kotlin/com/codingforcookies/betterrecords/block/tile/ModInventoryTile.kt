package com.codingforcookies.betterrecords.block.tile

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack

abstract class ModInventoryTile : ModTile(), IInventory {

    override fun getField(id: Int) = 0
    override fun hasCustomName() = true

    override fun removeStackFromSlot(index: Int) = getStackInSlot(index)

    override fun decrStackSize(index: Int, count: Int) = getStackInSlot(index).apply {
        this?.let {
            if (this.count <= count) {
                setInventorySlotContents(index, ItemStack.EMPTY)
            } else {
                val splitStack = this.splitStack(count)
                if (splitStack.count == 0) {
                    setInventorySlotContents(index, ItemStack.EMPTY)
                }
                return splitStack
            }
        }
    }

    override fun isUsableByPlayer(player: EntityPlayer) =
            player.getDistanceSq(pos.x + 0.5, pos.y + 0.5, pos.z + 0.5) < 64

    override fun getFieldCount() = 0
    override fun openInventory(player: EntityPlayer?) { /* NO-OP */ }
    override fun setField(id: Int, value: Int) { /* NO-OP */ }
    override fun closeInventory(player: EntityPlayer?) { /* NO-OP */ }
    override fun clear() { /* NO-OP */ }
}