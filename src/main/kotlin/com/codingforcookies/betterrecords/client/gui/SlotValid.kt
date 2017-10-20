package com.codingforcookies.betterrecords.client.gui

import net.minecraft.inventory.IInventory
import net.minecraft.inventory.Slot
import net.minecraft.item.ItemStack

class SlotValid(inventory: IInventory, index: Int, x: Int, y: Int) : Slot(inventory, index, x, y) {

    override fun isItemValid(itemStack: ItemStack?) =
            inventory.isItemValidForSlot(slotNumber, itemStack)
}
