package com.codingforcookies.betterrecords.crafting.recipe

import com.codingforcookies.betterrecords.item.ItemRecord
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

class RecipeColoredRecord : IForgeRegistryEntry.Impl<IRecipe>(), IRecipe {

    init {
        setRegistryName("recipecoloredrecord")
    }

    override fun canFit(width: Int, height: Int) = width * height >= 2

    override fun matches(inventoryCrafting: InventoryCrafting, world: World?): Boolean {
        var foundRecord = false
        var foundDye = false

        (0 until inventoryCrafting.sizeInventory)
                .map { inventoryCrafting.getStackInSlot(it) }
                .filter { !it.isEmpty }
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
                .map { inventoryCrafting.getStackInSlot(it) }
                .filter { !it.isEmpty }
                .forEach {
                    if (it.item is ItemRecord && record == null) {
                        record = it
                    } else {
                        color = EnumDyeColor.byDyeDamage(it.itemDamage).colorValue
                    }
                }

        return record?.copy()?.apply {
            if (!hasTagCompound()) {
                tagCompound = NBTTagCompound()
            }
            tagCompound!!.setInteger("color", color)
        }
    }

    override fun getRecipeOutput() = ItemStack(ModItems.itemRecord)

    override fun getRemainingItems(inv: InventoryCrafting): NonNullList<ItemStack> {
        inv.clear()
        return NonNullList.create()
    }
}
