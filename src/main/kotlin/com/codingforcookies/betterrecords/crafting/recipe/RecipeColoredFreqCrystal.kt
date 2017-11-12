package com.codingforcookies.betterrecords.crafting.recipe

import com.codingforcookies.betterrecords.item.ItemFrequencyCrystal
import net.minecraft.entity.passive.EntitySheep
import net.minecraft.init.Items
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.EnumDyeColor
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World

class RecipeColoredFreqCrystal : IRecipe {

    override fun matches(inventoryCrafting: InventoryCrafting, world: World?): Boolean {
        var foundCrystal = false
        var foundDye = false

        (0 until inventoryCrafting.sizeInventory)
                .asSequence()
                .mapNotNull { inventoryCrafting.getStackInSlot(it) }
                .forEach {
                    if (it.item is ItemFrequencyCrystal && !foundCrystal) {
                        foundCrystal = true
                    } else if (it.item == Items.DYE && !foundDye) {
                        foundDye = true
                    } else {
                        return false
                    }
                }

        return foundCrystal && foundDye
    }

    override fun getCraftingResult(inventoryCrafting: InventoryCrafting): ItemStack? {
        var crystal: ItemStack? = null
        var color = -1

        (0 until inventoryCrafting.sizeInventory)
                .asSequence()
                .mapNotNull { inventoryCrafting.getStackInSlot(it) }
                .forEach {
                    if (it.item is ItemFrequencyCrystal) {
                        crystal = it
                    } else {
                        color = EnumDyeColor.byDyeDamage(it.itemDamage).mapColor.colorValue
                    }
                }

        return crystal?.copy()?.apply {
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
