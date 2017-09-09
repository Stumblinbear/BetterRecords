package com.codingforcookies.betterrecords.crafting.recipe

import com.codingforcookies.betterrecords.item.ItemRecord
import com.codingforcookies.betterrecords.item.ModItems
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagList
import net.minecraft.world.World

import java.util.ArrayList

class RecipeMultiRecord : IRecipe {

    override fun matches(inventoryCrafting: InventoryCrafting, worldIn: World): Boolean {
        var count = 0

        (0 until inventoryCrafting.sizeInventory)
                .asSequence()
                .mapNotNull { inventoryCrafting.getStackInSlot(it) }
                .forEach {
                    if (it.item is ItemRecord
                            && it.hasTagCompound()
                            && it.tagCompound!!.hasKey("name")) count++
                    else return false
                }

        return count > 1
    }

    override fun getCraftingResult(inventoryCrafting: InventoryCrafting): ItemStack? {
        val records = ArrayList<ItemStack>()

        for (k in 0..inventoryCrafting.sizeInventory - 1) {
            val itemstack = inventoryCrafting.getStackInSlot(k)
            if (itemstack != null) {
                if (itemstack.item is ItemRecord && itemstack.tagCompound != null && itemstack.tagCompound!!.hasKey("name"))
                    records.add(itemstack)
                else
                    return null
            }
        }

        if (records.isEmpty() || records.size == 1)
            return null
        else {
            val itemMultiRecord = ItemStack(ModItems.itemMultiRecord)
            itemMultiRecord.tagCompound = NBTTagCompound()

            val songs = NBTTagList()

            for (record in records) {
                val song = NBTTagCompound()
                song.setString("name", record.tagCompound!!.getString("name"))
                song.setString("url", record.tagCompound!!.getString("url"))
                song.setString("local", record.tagCompound!!.getString("local"))
                songs.appendTag(song)
            }

            itemMultiRecord.tagCompound!!.setTag("songs", songs)
            return itemMultiRecord
        }
    }

    override fun getRecipeSize(): Int {
        return 10
    }

    override fun getRecipeOutput(): ItemStack? {
        return ItemStack(ModItems.itemRecord)
    }

    override fun getRemainingItems(inv: InventoryCrafting): Array<ItemStack> {
        inv.clear()
        return arrayOf()
    }
}
