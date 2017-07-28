package com.codingforcookies.betterrecords.item

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumHand
import net.minecraft.util.text.translation.I18n
import net.minecraft.world.World

class ItemFrequencyCrystal(name: String) : ModItem(name) {

    init {
        maxStackSize = 1
    }

    override fun onItemRightClick(itemStack: ItemStack, world: World, player: EntityPlayer, hand: EnumHand) =
            ActionResult(EnumActionResult.PASS, itemStack)

    override fun getItemStackDisplayName(stack: ItemStack) =
            if (stack.hasTagCompound() && stack.tagCompound!!.hasKey("local")) {
                stack.tagCompound!!.getString("local")
            } else {
                I18n.translateToLocal("${unlocalizedName}.name")
            }
}