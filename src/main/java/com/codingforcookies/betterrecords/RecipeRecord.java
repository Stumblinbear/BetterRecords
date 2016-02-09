package com.codingforcookies.betterrecords;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RecipeRecord implements IRecipe {
	public boolean matches(InventoryCrafting par1InventoryCrafting, World par2World) {
		boolean record = false;
		boolean eye = false;
		
		for(int i = 0; i < par1InventoryCrafting.getSizeInventory(); ++i) {
			ItemStack itemStack = par1InventoryCrafting.getStackInSlot(i);
			if(itemStack == null)
				continue;
			else if(itemStack.getItem() instanceof ItemRecord)
				if(record)
					return false;
				else
					record = true;
			else if(itemStack.getItem() == Items.ender_eye)
				if(eye)
					return false;
				else
					eye = true;
		}
		
		return record && eye;
	}
	
	public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting) {
		return new ItemStack(BetterRecords.itemURLRecord);
	}
	
	public int getRecipeSize() {
		return 10;
	}

	public ItemStack getRecipeOutput() {
		return new ItemStack(BetterRecords.itemURLRecord);
	}

	//TODO
	public ItemStack[] getRemainingItems(InventoryCrafting inv) {
		return null;
	}
}