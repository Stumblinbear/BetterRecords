package com.codingforcookies.betterrecords.item

import com.codingforcookies.betterrecords.api.wire.IRecordWireHome
import com.codingforcookies.betterrecords.common.packets.PacketHandler
import com.codingforcookies.betterrecords.common.util.BetterUtils
import com.codingforcookies.betterrecords.extensions.forEachIndexed
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.text.translation.I18n

class ItemMultiRecord(name: String) : ItemRecord(name) {

    override fun getItemStackDisplayName(stack: ItemStack) =
            I18n.translateToLocal("$unlocalizedName.name")

    override fun isRecordValid(itemStack: ItemStack) =
            itemStack.hasTagCompound() && itemStack.tagCompound!!.hasKey("songs")

    override fun onRecordInserted(wireHome: IRecordWireHome, itemStack: ItemStack) {
        itemStack.tagCompound?.let {
            PacketHandler.sendRecordPlayToAllFromServer(
                    wireHome.tileEntity.pos.x,
                    wireHome.tileEntity.pos.y,
                    wireHome.tileEntity.pos.z,
                    wireHome.tileEntity.world.provider.dimension,
                    wireHome.songRadius,
                    itemStack.tagCompound,
                    it.getBoolean("repeat"),
                    it.getBoolean("shuffle")
            )
        }
    }

    override fun addInformation(stack: ItemStack, player: EntityPlayer, tooltip: MutableList<String>, advanced: Boolean) {
        stack.tagCompound?.let {
            if (it.hasKey("songs")) {
                it.getTagList("songs", 10).forEachIndexed { i, _ ->
                    tooltip += BetterUtils.getTranslatedString("item.record.song") + "${i + 1}: ${it.getString("local")}"
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
