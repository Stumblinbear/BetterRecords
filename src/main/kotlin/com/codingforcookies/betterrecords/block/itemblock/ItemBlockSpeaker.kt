package com.codingforcookies.betterrecords.block.itemblock

import com.codingforcookies.betterrecords.block.BlockSpeaker
import net.minecraft.block.Block
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack

class ItemBlockSpeaker(block: Block) : ItemBlock(block) {

    init {
        hasSubtypes = true
    }

    override fun getMetadata(damage: Int) = damage

    override fun getUnlocalizedName(stack: ItemStack) =
            super.getUnlocalizedName(stack) + "." +
                    BlockSpeaker.SpeakerSize.fromMeta(stack.metadata).toString().toLowerCase()
}