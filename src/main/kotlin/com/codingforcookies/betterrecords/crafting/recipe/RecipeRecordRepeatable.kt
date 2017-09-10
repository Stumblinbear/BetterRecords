package com.codingforcookies.betterrecords.crafting.recipe

import com.codingforcookies.betterrecords.item.ItemRecord
import net.minecraft.init.Items
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World

class RecipeRecordRepeatable : IRecipe {

    override fun matches(inventoryCrafting: InventoryCrafting, worldIn: World): Boolean {
        var foundRecord = false
        var foundComparator = false

        (0 until inventoryCrafting.sizeInventory)
                .asSequence()
                .mapNotNull { inventoryCrafting.getStackInSlot(it) }
                .forEach {
                    if (it.item is ItemRecord && !foundRecord) {
                        foundRecord = true
                    } else if (it.item == Items.COMPARATOR && !foundComparator) {
                        foundComparator = true
                    } else {
                        return false
                    }
                }

        return foundRecord && foundComparator
    }

    override fun getCraftingResult(inventoryCrafting: InventoryCrafting): ItemStack? {
        val record =
                (0 until inventoryCrafting.sizeInventory)
                .mapNotNull { inventoryCrafting.getStackInSlot(it) }
                .find { it.item is ItemRecord }

        return record?.copy()?.apply {
            if (!hasTagCompound()) {
                tagCompound = NBTTagCompound()
            }
            tagCompound!!.setBoolean("repeat", true)
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
