package com.codingforcookies.betterrecords.block

import com.codingforcookies.betterrecords.ID
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraftforge.client.model.ModelLoader

interface ItemModelProvider {

    fun registerItemModel(block: ModBlock) {
        val item = Item.getItemFromBlock(block)
        ModelLoader.setCustomModelResourceLocation(item, 0, ModelResourceLocation("$ID:itemblock/${block.name}", "inventory"))
    }
}
