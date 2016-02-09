package com.codingforcookies.betterrecords.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotValid extends Slot {
	public SlotValid(IInventory par1IInventory, int index, int x, int y) {
		super(par1IInventory, index, x, y);
	}
	
    public boolean isItemValid(ItemStack par1ItemStack) {
        return inventory.isItemValidForSlot(slotNumber, par1ItemStack);
    }
}