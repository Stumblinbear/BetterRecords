package com.codingforcookies.betterrecords.crafting.recipe

import com.codingforcookies.betterrecords.item.ItemMultiRecord
import com.codingforcookies.betterrecords.item.ItemRecord
import net.minecraft.init.Blocks
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.NonNullList
import net.minecraft.world.World
import net.minecraftforge.registries.IForgeRegistryEntry

class RecipeRecordShuffle : IForgeRegistryEntry.Impl<IRecipe>(), IRecipe {

    override fun canFit(width: Int, height: Int) = width * height >= 2

    override fun matches(inventoryCrafting: InventoryCrafting, worldIn: World?): Boolean {
        var foundRecord = false
        var foundTorch = false

        (0 until inventoryCrafting.sizeInventory)
                .asSequence()
                .mapNotNull { inventoryCrafting.getStackInSlot(it) }
                .forEach {
                    if (it.item is ItemMultiRecord && it.hasTagCompound() && !foundRecord) {
                        foundRecord = true
                    } else if (it.item == Item.getItemFromBlock(Blocks.REDSTONE_TORCH) && !foundTorch) {
                        foundTorch = true
                    } else {
                        return false
                    }
                }

        return foundRecord && foundTorch
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
            tagCompound!!.setBoolean("shuffle", true)
        }
    }

    override fun getRecipeOutput(): ItemStack? {
        return null
    }

    override fun getRemainingItems(inv: InventoryCrafting): NonNullList<ItemStack> {
        inv.clear()
        return NonNullList.create()
    }
}
