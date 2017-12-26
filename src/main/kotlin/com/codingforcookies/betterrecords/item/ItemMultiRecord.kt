package com.codingforcookies.betterrecords.item

import com.codingforcookies.betterrecords.api.wire.IRecordWireHome
import com.codingforcookies.betterrecords.network.PacketHandler
import com.codingforcookies.betterrecords.network.PacketRecordPlay
import com.codingforcookies.betterrecords.util.BetterUtils
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemStack
import net.minecraft.util.text.translation.I18n
import net.minecraft.world.World

class ItemMultiRecord(name: String) : ItemRecord(name) {

    override fun getItemStackDisplayName(stack: ItemStack) =
            I18n.translateToLocal("$unlocalizedName.name")

    override fun isRecordValid(itemStack: ItemStack) =
            itemStack.hasTagCompound() && itemStack.tagCompound!!.hasKey("songs")

    override fun onRecordInserted(wireHome: IRecordWireHome, itemStack: ItemStack) {
        itemStack.tagCompound?.let {
            PacketHandler.sendToAll(PacketRecordPlay(
                    wireHome.tileEntity.pos,
                    wireHome.tileEntity.world.provider.dimension,
                    wireHome.songRadius,
                    it,
                    it.getBoolean("repeat"),
                    it.getBoolean("shuffle")
            ))
        }
    }

    override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<String>, flagIn: ITooltipFlag) {
        stack.tagCompound?.let {
            if (it.hasKey("songs")) {
                val tagList = it.getTagList("songs", 10)
                tagList.forEachIndexed { i, h ->
                    tooltip += BetterUtils.getTranslatedString("item.record.song") + " ${i + 1}: ${tagList.getCompoundTagAt(i).getString("local")}"
                }
            }
            if (it.hasKey("repeat") && it.getBoolean("repeat")) {
                tooltip += "\u00a7e" + BetterUtils.getTranslatedString("item.record.repeatenabled")
            }
            if (it.hasKey("shuffle") && it.getBoolean("shuffle")) {
                tooltip += "\u00a7e" + BetterUtils.getTranslatedString("item.record.shuffleenabled")
            }
        }
    }
}
