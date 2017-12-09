package com.codingforcookies.betterrecords.crafting.recipe

import com.codingforcookies.betterrecords.item.ItemRecord
import com.codingforcookies.betterrecords.item.ModItems
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagList
import net.minecraft.util.NonNullList
import net.minecraft.world.World
import net.minecraftforge.registries.IForgeRegistryEntry

import java.util.ArrayList

class RecipeMultiRecord : IForgeRegistryEntry.Impl<IRecipe>(), IRecipe {

    override fun canFit(width: Int, height: Int) = width * height >= 2

    override fun matches(inventoryCrafting: InventoryCrafting, worldIn: World?): Boolean {
        var count = 0

        (0 until inventoryCrafting.sizeInventory)
                .asSequence()
                .mapNotNull { inventoryCrafting.getStackInSlot(it) }
                .forEach {
                    if (it.item is ItemRecord && it.hasTagCompound() && it.tagCompound!!.hasKey("name")) {
                        count++
                    } else {
                        return false
                    }
                }

        return count > 1
    }

    override fun getCraftingResult(inventoryCrafting: InventoryCrafting): ItemStack? {
        val records = ArrayList<ItemStack>()

        (0 until inventoryCrafting.sizeInventory)
                .mapNotNull { inventoryCrafting.getStackInSlot(it) }
                .forEach {
                    records.add(it)
                }

        return ItemStack(ModItems.itemMultiRecord).apply {
            tagCompound = NBTTagCompound()
            val songs = NBTTagList()

            records.forEach {
                songs.appendTag(NBTTagCompound().apply {
                    setString("name", it.tagCompound!!.getString("name"))
                    setString("url", it.tagCompound!!.getString("url"))
                    setString("local", it.tagCompound!!.getString("local"))
                })
            }

            tagCompound!!.setTag("songs", songs)
        }
    }

    override fun getRecipeOutput(): ItemStack? {
        return ItemStack(ModItems.itemRecord)
    }

    override fun getRemainingItems(inv: InventoryCrafting): NonNullList<ItemStack> {
        inv.clear()
        return NonNullList.create()
    }
}
