package com.codingforcookies.betterrecords.client.gui

import com.codingforcookies.betterrecords.block.tile.TileFrequencyTuner
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container
import net.minecraft.inventory.Slot
import net.minecraft.item.ItemStack

class ContainerFrequencyTuner(inventoryPlayer: InventoryPlayer, var tileEntity: TileFrequencyTuner) : Container() {

    init {
        // Slot 0
        addSlotToContainer(SlotValid(tileEntity, 0, 17, 50))

        // Slots 1-27
        for (i in 0..2) {
            for (j in 0..8) {
                addSlotToContainer(Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18))
            }
        }

        // Slots 28-36
        for (i in 0..8) {
            addSlotToContainer(Slot(inventoryPlayer, i, 8 + i * 18, 142))
        }
    }

    override fun canInteractWith(player: EntityPlayer) =
            tileEntity.isUsableByPlayer(player)

    override fun transferStackInSlot(player: EntityPlayer?, slotIndex: Int): ItemStack {
        val slot = inventory[slotIndex]

        if (slotIndex > 0) {
            // Player Inventory
            if (inventorySlots[0].isItemValid(slot)) {
                mergeItemStack(slot, 0, 1, false)
            }
        }  else {
            // Etcher Inventory
            mergeItemStack(slot, 1, 37, false)
        }


        return ItemStack.EMPTY
    }
}
