package com.codingforcookies.betterrecords.item

import com.codingforcookies.betterrecords.api.record.IRecord
import com.codingforcookies.betterrecords.api.wire.IRecordWireHome
import com.codingforcookies.betterrecords.common.packets.PacketHandler
import com.codingforcookies.betterrecords.common.util.BetterUtils
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.text.translation.I18n

open class ItemRecord(name: String) : ModItem(name), IRecord {

    init {
        maxStackSize = 1
    }

    override fun isRecordValid(itemStack: ItemStack) =
        itemStack.hasTagCompound() && itemStack.tagCompound!!.hasKey("name")

    override fun onRecordInserted(wireHome: IRecordWireHome, itemStack: ItemStack) {
        itemStack.tagCompound?.let {
            PacketHandler.sendRecordPlayToAllFromServer(
                    wireHome.tileEntity.pos.x,
                    wireHome.tileEntity.pos.y,
                    wireHome.tileEntity.pos.z,
                    wireHome.tileEntity.world.provider.dimension,
                    wireHome.songRadius,
                    it.getString("name"),
                    it.getString("url"),
                    it.getString("local"),
                    it.getBoolean("repeat"),
                    it.getBoolean("shuffle")
            )
        }
    }

    override fun addInformation(stack: ItemStack, playerIn: EntityPlayer, tooltip: MutableList<String>, advanced: Boolean) {
        stack.tagCompound?.let {
            tooltip += BetterUtils.getTranslatedString("item.record.by") + ": " + it.getString("author")
            tooltip += BetterUtils.getTranslatedString("item.record.size") + ": " + it.getString("size") + "mb"
            if (it.getBoolean("repeat")) {
                tooltip += ""
                tooltip.add("\u00a7e" + BetterUtils.getTranslatedString("item.record.repeatenabled"))
            }
        }
    }

    override fun getItemStackDisplayName(stack: ItemStack) =
            if (stack.hasTagCompound() && stack.tagCompound!!.hasKey("local")) {
                stack.tagCompound!!.getString("local")
            } else {
                I18n.translateToLocal("${unlocalizedName}.name")
            }
}
