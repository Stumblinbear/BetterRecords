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
    public boolean matches(InventoryCrafting inventoryCrafting, World world) {
        boolean record = false;
        boolean eye = false;

        for(int i = 0; i < inventoryCrafting.getSizeInventory(); ++i) {
            ItemStack itemStack = inventoryCrafting.getStackInSlot(i);
            if(itemStack == null)
                continue;

            if(itemStack.getItem() instanceof ItemRecord)
                if(record)
                    return false;
                else
                    record = true;
            else if(itemStack.getItem() == Items.ENDER_EYE)
                if(eye)
                    return false;
                else
                    eye = true;
        }

        return record && eye;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
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
