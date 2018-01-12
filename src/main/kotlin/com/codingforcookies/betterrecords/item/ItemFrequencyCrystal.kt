package com.codingforcookies.betterrecords.item

import net.minecraft.item.ItemStack
import net.minecraft.util.text.translation.I18n as ServerI18n

class ItemFrequencyCrystal(name: String) : ModItem(name) {

    init {
        maxStackSize = 1
    }

    override fun getItemStackDisplayName(stack: ItemStack) =
            if (stack.hasTagCompound() && stack.tagCompound!!.hasKey("local")) {
                stack.tagCompound!!.getString("local")
            } else {
                ServerI18n.translateToLocal("$unlocalizedName.name")
            }
}