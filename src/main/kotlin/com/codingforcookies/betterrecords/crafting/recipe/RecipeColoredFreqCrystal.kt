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
    //If something changes, check RecipesArmorDyes

    override fun matches(inventoryCrafting: InventoryCrafting, world: World): Boolean {
        var foundCrystal = false
        var foundDye = false

        (0 until inventoryCrafting.sizeInventory)
                .asSequence()
                .mapNotNull { inventoryCrafting.getStackInSlot(it) }
                .forEach {
                    if (it.item is ItemFrequencyCrystal && !foundCrystal) {
                        foundCrystal = true
                    } else if (it.item == Items.DYE) {
                        foundDye = true
                    } else {
                        return false
                    }
                }

        return foundCrystal && foundDye
    }

    override fun getCraftingResult(inventoryCrafting: InventoryCrafting): ItemStack? {
        var itemToColor: ItemStack? = null
        var itemCrystal: ItemFrequencyCrystal? = null
        val aint = IntArray(3)
        var i = 0
        var j = 0
        var k: Int
        var currentColor: Int
        var f: Float
        var f1: Float
        var l1: Int

        k = 0
        while (k < inventoryCrafting.sizeInventory) {
            val itemstack1 = inventoryCrafting.getStackInSlot(k)
            if (itemstack1 != null) {
                if (itemstack1.item is ItemFrequencyCrystal) {
                    if (itemToColor != null)
                        return null

                    itemCrystal = itemstack1.item as ItemFrequencyCrystal

                    itemToColor = itemstack1.copy()
                    itemToColor!!.stackSize = 1

                    if (itemstack1.hasTagCompound() && itemstack1.tagCompound!!.hasKey("color")) {
                        currentColor = itemstack1.tagCompound!!.getInteger("color")
                        f = (currentColor shr 16 and 255).toFloat() / 255.0f
                        f1 = (currentColor shr 8 and 255).toFloat() / 255.0f
                        val f2 = (currentColor and 255).toFloat() / 255.0f
                        i = (i.toFloat() + Math.max(f, Math.max(f1, f2)) * 255.0f).toInt()
                        aint[0] = (aint[0].toFloat() + f * 255.0f).toInt()
                        aint[1] = (aint[1].toFloat() + f1 * 255.0f).toInt()
                        aint[2] = (aint[2].toFloat() + f2 * 255.0f).toInt()
                        ++j
                    }
                } else {
                    if (itemstack1.item !== Items.DYE)
                        return null

                    val afloat = EntitySheep.getDyeRgb(EnumDyeColor.byDyeDamage(itemstack1.itemDamage))
                    val j1 = (afloat[0] * 255.0f).toInt()
                    val k1 = (afloat[1] * 255.0f).toInt()
                    l1 = (afloat[2] * 255.0f).toInt()
                    i += Math.max(j1, Math.max(k1, l1))
                    aint[0] += j1
                    aint[1] += k1
                    aint[2] += l1
                    j++
                }
            }
            ++k
        }

        if (itemCrystal == null)
            return null
        else {
            k = aint[0] / j
            var i1 = aint[1] / j
            currentColor = aint[2] / j
            f = i.toFloat() / j.toFloat()
            f1 = Math.max(k, Math.max(i1, currentColor)).toFloat()
            k = (k.toFloat() * f / f1).toInt()
            i1 = (i1.toFloat() * f / f1).toInt()
            currentColor = (currentColor.toFloat() * f / f1).toInt()
            l1 = (k shl 8) + i1
            l1 = (l1 shl 8) + currentColor
            if (!itemToColor!!.hasTagCompound())
                itemToColor.tagCompound = NBTTagCompound()
            itemToColor.tagCompound!!.setInteger("color", l1)
            return itemToColor
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
