package com.codingforcookies.betterrecords.item

import com.codingforcookies.betterrecords.api.record.IRecord
import com.codingforcookies.betterrecords.api.wire.IRecordWireHome
import com.codingforcookies.betterrecords.client.sound.Sound
import com.codingforcookies.betterrecords.network.PacketHandler
import com.codingforcookies.betterrecords.network.PacketRecordPlay
import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemStack
import net.minecraft.world.World

open class ItemRecord(name: String) : ModItem(name), IRecord {

    init {
        maxStackSize = 1
    }

    override fun isRecordValid(itemStack: ItemStack) =
        itemStack.hasTagCompound() && itemStack.tagCompound!!.hasKey("name")

    override fun onRecordInserted(wireHome: IRecordWireHome, itemStack: ItemStack) {
        itemStack.tagCompound?.let {
            PacketHandler.sendToAll(PacketRecordPlay(
                    wireHome.tileEntity.pos,
                    wireHome.tileEntity.world.provider.dimension,
                    wireHome.songRadius,
                    it.getBoolean("repeat"),
                    it.getBoolean("shuffle"),
                    sound = Sound().setInfo(
                            it.getString("name"),
                            it.getString("url"),
                            it.getString("local")
                    )
            ))
        }
    }

    override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<String>, flagIn: ITooltipFlag) {
        stack.tagCompound?.let {
            tooltip += I18n.format("item.record.by") + ": " + it.getString("author")
            tooltip += I18n.format("item.record.size") + ": " + it.getInteger("size") + "mb"
            if (it.getBoolean("repeat")) {
                tooltip += ""
                tooltip.add("\u00a7e" + I18n.format("item.record.repeatenabled"))
            }
        }
    }

    override fun getItemStackDisplayName(stack: ItemStack) =
            if (stack.hasTagCompound() && stack.tagCompound!!.hasKey("local")) {
                stack.tagCompound!!.getString("local")
            } else {
                I18n.format("$unlocalizedName.name")
            }
}
