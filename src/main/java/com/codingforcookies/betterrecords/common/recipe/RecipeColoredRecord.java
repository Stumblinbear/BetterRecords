package com.codingforcookies.betterrecords.common.recipe;

import java.util.ArrayList;

import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.codingforcookies.betterrecords.common.item.ItemURLRecord;

public class RecipeColoredRecord implements IRecipe {
    //If something changes, check RecipesArmorDyes

    @Override
    public boolean matches(InventoryCrafting par1InventoryCrafting, World par2World) {
        ItemStack itemToColor = null;
        ArrayList<ItemStack> dyes = new ArrayList<ItemStack>();

        for(int i = 0; i < par1InventoryCrafting.getSizeInventory(); ++i) {
            ItemStack itemstack1 = par1InventoryCrafting.getStackInSlot(i);
            if(itemstack1 != null) {
                if(itemstack1.getItem() instanceof ItemURLRecord) {
                    if(itemToColor != null)
                        return false;
                    itemToColor = itemstack1;
                }else{
                    if(itemstack1.getItem() != Items.dye)
                        return false;
                    dyes.add(itemstack1);
                }
            }
        }

        return itemToColor != null && !dyes.isEmpty();
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting) {
        ItemStack itemToColor = null;
        ItemURLRecord itemRecord = null;
        int[] aint = new int[3];
        int i = 0;
        int j = 0;
        int k;
        int currentColor;
        float f;
        float f1;
        int l1;

        for(k = 0; k < par1InventoryCrafting.getSizeInventory(); ++k) {
            ItemStack itemstack1 = par1InventoryCrafting.getStackInSlot(k);
            if(itemstack1 != null) {
                if(itemstack1.getItem() instanceof ItemURLRecord) {
                    if(itemToColor != null)
                        return null;

                    itemRecord = (ItemURLRecord)itemstack1.getItem();

                    itemToColor = itemstack1.copy();
                    itemToColor.stackSize = 1;

                    if(itemstack1.hasTagCompound() && itemstack1.getTagCompound().hasKey("color")) {
                        currentColor = itemstack1.getTagCompound().getInteger("color");
                        f = (float)(currentColor >> 16 & 255) / 255.0F;
                        f1 = (float)(currentColor >> 8 & 255) / 255.0F;
                        float f2 = (float)(currentColor & 255) / 255.0F;
                        i = (int)((float)i + Math.max(f, Math.max(f1, f2)) * 255.0F);
                        aint[0] = (int)((float)aint[0] + f * 255.0F);
                        aint[1] = (int)((float)aint[1] + f1 * 255.0F);
                        aint[2] = (int)((float)aint[2] + f2 * 255.0F);
                        ++j;
                    }
                }else{
                    if(itemstack1.getItem() != Items.dye)
                        return null;

                    float[] afloat = EntitySheep.func_175513_a(EnumDyeColor.byDyeDamage(itemstack1.getItemDamage()));
                    int j1 = (int)(afloat[0] * 255.0F);
                    int k1 = (int)(afloat[1] * 255.0F);
                    l1 = (int)(afloat[2] * 255.0F);
                    i += Math.max(j1, Math.max(k1, l1));
                    aint[0] += j1;
                    aint[1] += k1;
                    aint[2] += l1;
                    j++;
                }
            }
        }

        if(itemRecord == null)
            return null;
        else{
            k = aint[0] / j;
            int i1 = aint[1] / j;
            currentColor = aint[2] / j;
            f = (float)i / (float)j;
            f1 = (float)Math.max(k, Math.max(i1, currentColor));
            k = (int)((float)k * f / f1);
            i1 = (int)((float)i1 * f / f1);
            currentColor = (int)((float)currentColor * f / f1);
            l1 = (k << 8) + i1;
            l1 = (l1 << 8) + currentColor;
            if(!itemToColor.hasTagCompound())
                itemToColor.setTagCompound(new NBTTagCompound());
            itemToColor.getTagCompound().setInteger("color", l1);
            return itemToColor;
        }
    }

    @Override
    public int getRecipeSize() {
        return 10;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }

    @Override
    public ItemStack[] getRemainingItems(InventoryCrafting inv) {
        inv.clear();
        return new ItemStack[] {};
    }
}
