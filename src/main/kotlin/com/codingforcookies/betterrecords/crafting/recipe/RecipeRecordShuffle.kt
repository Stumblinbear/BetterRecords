package com.codingforcookies.betterrecords.crafting.recipe

import com.codingforcookies.betterrecords.item.ItemMultiRecord
import com.codingforcookies.betterrecords.item.ItemRecord
import net.minecraft.init.Blocks
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World

class RecipeRecordShuffle : IRecipe {

    override fun matches(inventoryCrafting: InventoryCrafting, world: World): Boolean {
        var record: ItemStack? = null
        var shuffle = false
        for (i in 0..inventoryCrafting.sizeInventory - 1) {
            val itemstack = inventoryCrafting.getStackInSlot(i)
            if (itemstack != null) {
                if (itemstack.item is ItemMultiRecord && itemstack.tagCompound != null)
                    if (record != null)
                        return false
                    else
                        record = itemstack
                else if (itemstack.item === Item.getItemFromBlock(Blocks.REDSTONE_TORCH))
                    if (shuffle)
                        return false
                    else
                        shuffle = true
                else
                    return false
            }
        }
        return record != null && shuffle
    }

    override fun getCraftingResult(inventoryCrafting: InventoryCrafting): ItemStack? {
        var record: ItemStack? = null
        var shuffle = false
        for (i in 0..inventoryCrafting.sizeInventory - 1) {
            val itemstack = inventoryCrafting.getStackInSlot(i)
            if (itemstack != null) {
                if (itemstack.item is ItemRecord && itemstack.tagCompound != null)
                    if (record != null)
                        return null
                    else
                        record = itemstack
                else if (itemstack.item === Item.getItemFromBlock(Blocks.REDSTONE_TORCH))
                    if (shuffle)
                        return null
                    else
                        shuffle = true
                else
                    return null
            }
        }
        if (record == null || !shuffle)
            return null
        else {
            val newRecord = ItemStack.copyItemStack(record)
            if (newRecord.tagCompound == null) newRecord.tagCompound = NBTTagCompound()
            newRecord.tagCompound!!.setBoolean("shuffle", true)
            return newRecord
        }
    }

    override fun getRecipeSize(): Int {
        return 10
    }

    override fun getRecipeOutput(): ItemStack? {
        return null
    }

    override fun getRemainingItems(inv: InventoryCrafting): Array<ItemStack> {
        inv.clear()
        return arrayOf()
    }
}
