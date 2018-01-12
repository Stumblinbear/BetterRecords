package com.codingforcookies.betterrecords.item

import com.codingforcookies.betterrecords.api.wire.IRecordWireHome
import com.codingforcookies.betterrecords.network.PacketHandler
import com.codingforcookies.betterrecords.network.PacketRecordPlay
import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraft.util.text.translation.I18n as ServerI18n

class ItemMultiRecord(name: String) : ItemRecord(name) {

    override fun isRecordValid(itemStack: ItemStack) =
            itemStack.hasTagCompound() && itemStack.tagCompound!!.hasKey("songs")

    override fun onRecordInserted(wireHome: IRecordWireHome, itemStack: ItemStack) {
        itemStack.tagCompound?.let {
            PacketHandler.sendToAll(PacketRecordPlay(
                    wireHome.tileEntity.pos,
                    wireHome.tileEntity.world.provider.dimension,
                    wireHome.songRadius,
                    it.getBoolean("repeat"),
                    it.getBoolean("shuffle"),
                    nbt = it
            ))
        }
    }

    @SideOnly(Side.CLIENT)
    override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<String>, flagIn: ITooltipFlag) {
        stack.tagCompound?.let {
            if (it.hasKey("songs")) {
                val tagList = it.getTagList("songs", 10)
                tagList.forEachIndexed { i, h ->
                    tooltip += I18n.format("item.betterrecords:multirecord.desc.song", i + 1, tagList.getCompoundTagAt(i).getString("local"))
                }
            }
            if (it.hasKey("repeat") && it.getBoolean("repeat")) {
                tooltip += "\u00a7e" + I18n.format("item.betterrecords:record.desc.repeat")
            }
            if (it.hasKey("shuffle") && it.getBoolean("shuffle")) {
                tooltip += "\u00a7e" + I18n.format("item.betterrecords:multirecord.desc.shuffle")
            }
        }
    }

    override fun getItemStackDisplayName(stack: ItemStack): String =
            ServerI18n.translateToLocal("$unlocalizedName.name")
}
