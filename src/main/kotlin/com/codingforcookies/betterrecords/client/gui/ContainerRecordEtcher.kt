package com.codingforcookies.betterrecords.client.gui

import com.codingforcookies.betterrecords.block.tile.TileRecordEtcher
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container
import net.minecraft.inventory.Slot
import net.minecraft.item.ItemStack

class ContainerRecordEtcher(inventoryPlayer: InventoryPlayer, var tileEntity: TileRecordEtcher) : Container() {

    init {
        addSlotToContainer(SlotValid(tileEntity, 0, 17, 50))

        for (i in 0..2) {
            for (j in 0..8) {
                addSlotToContainer(Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18))
            }
        }

        for (i in 0..8) {
            addSlotToContainer(Slot(inventoryPlayer, i, 8 + i * 18, 142))
        }
    }

    override fun canInteractWith(player: EntityPlayer) =
            tileEntity.isUsableByPlayer(player)

    override fun transferStackInSlot(player: EntityPlayer?, slot: Int) =
            null
}
