package com.codingforcookies.betterrecords.crafting.recipe

import com.codingforcookies.betterrecords.item.ItemFrequencyCrystal
import com.codingforcookies.betterrecords.item.ModItems
import net.minecraft.init.Items
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.EnumDyeColor
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.NonNullList
import net.minecraft.world.World
import net.minecraftforge.registries.IForgeRegistryEntry

class RecipeColoredFreqCrystal : IForgeRegistryEntry.Impl<IRecipe>(), IRecipe {

    init {
        setRegistryName("recipecoloredfrequencycrystal")
    }

    override fun canFit(width: Int, height: Int) = width * height >= 2

    override fun matches(inventoryCrafting: InventoryCrafting, world: World?): Boolean {
        var foundCrystal = false
        var foundDye = false

        (0 until inventoryCrafting.sizeInventory)
                .map { inventoryCrafting.getStackInSlot(it) }
                .filter { !it.isEmpty }
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
                .map { inventoryCrafting.getStackInSlot(it) }
                .filter { !it.isEmpty }
                .forEach {
                    if (it.item is ItemFrequencyCrystal) {
                        crystal = it
                    } else {
                        color = EnumDyeColor.byDyeDamage(it.itemDamage).colorValue
                    }
                }

        return crystal?.copy()?.apply {
            if (!hasTagCompound()) {
                tagCompound = NBTTagCompound()
            }
            tagCompound!!.setInteger("color", color)
        }
    }

    override fun getRecipeOutput() = ItemStack(ModItems.itemFrequencyCrystal)

    override fun getRemainingItems(inv: InventoryCrafting): NonNullList<ItemStack> {
        inv.clear()
        return NonNullList.create()
    }
}
