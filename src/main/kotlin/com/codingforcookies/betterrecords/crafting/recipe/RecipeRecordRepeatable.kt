package com.codingforcookies.betterrecords.crafting.recipe

import com.codingforcookies.betterrecords.item.ItemRecord
import com.codingforcookies.betterrecords.item.ModItems
import net.minecraft.init.Items
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.NonNullList
import net.minecraft.world.World
import net.minecraftforge.registries.IForgeRegistryEntry

class RecipeRecordRepeatable : IForgeRegistryEntry.Impl<IRecipe>(), IRecipe {

    init {
        setRegistryName("reciperecordrepeatable")
    }

    override fun canFit(width: Int, height: Int) = width * height >= 2

    override fun matches(inventoryCrafting: InventoryCrafting, worldIn: World?): Boolean {
        var foundRecord = false
        var foundComparator = false

        (0 until inventoryCrafting.sizeInventory)
                .map { inventoryCrafting.getStackInSlot(it) }
                .filter { !it.isEmpty }
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
                        .map { inventoryCrafting.getStackInSlot(it) }
                        .filter { !it.isEmpty }
                        .find { it.item is ItemRecord }

        return record?.copy()?.apply {
            if (!hasTagCompound()) {
                tagCompound = NBTTagCompound()
            }
            tagCompound!!.setBoolean("repeat", true)
        }
    }

    override fun getRecipeOutput() = ItemStack(ModItems.itemRecord)

    override fun getRemainingItems(inv: InventoryCrafting): NonNullList<ItemStack> {
        inv.clear()
        return NonNullList.create()
    }
}
