package com.codingforcookies.betterrecords.crafting.recipe

import com.codingforcookies.betterrecords.item.ItemRecord
import net.minecraft.init.Items
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.EnumDyeColor
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World

class RecipeColoredRecord : IRecipe {

     override fun matches(inventoryCrafting: InventoryCrafting, world: World): Boolean {
        var foundRecord = false
        var foundDye = false

        (0 until inventoryCrafting.sizeInventory)
                .asSequence()
                .mapNotNull { inventoryCrafting.getStackInSlot(it) }
                .forEach {
                    if (it.item is ItemRecord && !foundRecord) {
                        foundRecord = true
                    } else if (it.item == Items.DYE && !foundDye) {
                        foundDye = true
                    } else {
                        return false
                    }
                }

        return foundRecord && foundDye
    }

    override fun getCraftingResult(inventoryCrafting: InventoryCrafting): ItemStack? {
        var record: ItemStack? = null
        var color = -1

        (0 until inventoryCrafting.sizeInventory)
                .asSequence()
                .mapNotNull { inventoryCrafting.getStackInSlot(it) }
                .forEach {
                    if (it.item is ItemRecord && record == null) {
                        record = it
                    } else {
                        color = EnumDyeColor.byDyeDamage(it.itemDamage).mapColor.colorValue
                    }
                }

        return record?.copy()?.apply {
            if (!hasTagCompound()) {
                tagCompound = NBTTagCompound()
            }
            tagCompound!!.setInteger("color", color)
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
