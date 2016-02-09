package com.codingforcookies.betterrecords;

import java.util.ArrayList;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import com.codingforcookies.betterrecords.items.ItemURLRecord;

public class RecipeMultiRecord implements IRecipe {
	public boolean matches(InventoryCrafting par1InventoryCrafting, World par2World) {
		ArrayList<ItemStack> records = new ArrayList<ItemStack>();
		
		for(int i = 0; i < par1InventoryCrafting.getSizeInventory(); ++i) {
			ItemStack itemstack = par1InventoryCrafting.getStackInSlot(i);
			if(itemstack != null)
				if(itemstack.getItem() instanceof ItemURLRecord && itemstack.stackTagCompound != null && itemstack.stackTagCompound.hasKey("name"))
					records.add(itemstack);
				else
					return false;
		}

		return !records.isEmpty() && records.size() != 1;
	}
	
	public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting) {
		ArrayList<ItemStack> records = new ArrayList<ItemStack>();
		
		for(int k = 0; k < par1InventoryCrafting.getSizeInventory(); ++k) {
			ItemStack itemstack = par1InventoryCrafting.getStackInSlot(k);
			if(itemstack != null) {
				if(itemstack.getItem() instanceof ItemURLRecord && itemstack.stackTagCompound != null && itemstack.stackTagCompound.hasKey("name"))
					records.add(itemstack);
				else
					return null;
			}
		}
		
		if(records.isEmpty() || records.size() == 1)
			return null;
		else{
			ItemStack itemMultiRecord = new ItemStack(BetterRecords.itemURLMultiRecord);
			itemMultiRecord.stackTagCompound = new NBTTagCompound();
			
			NBTTagList songs = new NBTTagList();
			
			for(ItemStack record : records) {
				NBTTagCompound song = new NBTTagCompound();
				song.setString("name", record.stackTagCompound.getString("name"));
				song.setString("url", record.stackTagCompound.getString("url"));
				song.setString("local", record.stackTagCompound.getString("local"));
				songs.appendTag(song);
			}
			
			itemMultiRecord.stackTagCompound.setTag("songs", songs);
			return itemMultiRecord;
		}
	}
	
	public int getRecipeSize() {
		return 10;
	}

	public ItemStack getRecipeOutput() {
		return null;
	}
}