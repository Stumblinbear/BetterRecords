package com.codingforcookies.betterrecords.client.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotValid extends Slot {

    public SlotValid(IInventory iInventory, int index, int x, int y) {
        super(iInventory, index, x, y);
    }

    public boolean isItemValid(ItemStack itemStack) {
        return inventory.isItemValidForSlot(slotNumber, itemStack);
    }
}
