package com.codingforcookies.betterrecords.common.crafting.recipe;

import com.codingforcookies.betterrecords.common.item.ModItems;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RecipeRecord implements IRecipe {

    @Override
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

    @Override
    public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting) {
        return new ItemStack(ModItems.itemURLRecord);
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
