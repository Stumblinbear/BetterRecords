package com.codingforcookies.betterrecords.common.recipe;

import com.codingforcookies.betterrecords.common.BetterRecords;
import com.codingforcookies.betterrecords.common.item.ItemURLRecord;
import com.codingforcookies.betterrecords.common.item.ModItems;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import java.util.ArrayList;

public class RecipeMultiRecord implements IRecipe {

    @Override
    public boolean matches(InventoryCrafting par1InventoryCrafting, World par2World) {
        ArrayList<ItemStack> records = new ArrayList<ItemStack>();

        for(int i = 0; i < par1InventoryCrafting.getSizeInventory(); ++i) {
            ItemStack itemstack = par1InventoryCrafting.getStackInSlot(i);
            if(itemstack != null)
                if(itemstack.getItem() instanceof ItemURLRecord && itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("name"))
                    records.add(itemstack);
                else
                    return false;
        }

        return !records.isEmpty() && records.size() != 1;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting) {
        ArrayList<ItemStack> records = new ArrayList<ItemStack>();

        for(int k = 0; k < par1InventoryCrafting.getSizeInventory(); ++k) {
            ItemStack itemstack = par1InventoryCrafting.getStackInSlot(k);
            if(itemstack != null) {
                if(itemstack.getItem() instanceof ItemURLRecord && itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("name"))
                    records.add(itemstack);
                else
                    return null;
            }
        }

        if(records.isEmpty() || records.size() == 1)
            return null;
        else{
            ItemStack itemMultiRecord = new ItemStack(ModItems.itemURLMultiRecord);
            itemMultiRecord.setTagCompound(new NBTTagCompound());

            NBTTagList songs = new NBTTagList();

            for(ItemStack record : records) {
                NBTTagCompound song = new NBTTagCompound();
                song.setString("name", record.getTagCompound().getString("name"));
                song.setString("url", record.getTagCompound().getString("url"));
                song.setString("local", record.getTagCompound().getString("local"));
                songs.appendTag(song);
            }

            itemMultiRecord.getTagCompound().setTag("songs", songs);
            return itemMultiRecord;
        }
    }

    @Override
    public int getRecipeSize() {
        return 10;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(ModItems.itemURLRecord);
    }

    @Override
    public ItemStack[] getRemainingItems(InventoryCrafting inv) {
        inv.clear();
        return new ItemStack[] {};
    }
}
