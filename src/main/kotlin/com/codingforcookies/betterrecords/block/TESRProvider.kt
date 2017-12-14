package com.codingforcookies.betterrecords.block


import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.item.Item
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.client.ForgeHooksClient
import net.minecraftforge.fml.client.registry.ClientRegistry
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

interface TESRProvider<T> : TileEntityProvider<T> where T : TileEntity {

    fun getRenderClass(): KClass<out TileEntitySpecialRenderer<T>>

    fun bindTESR() {
        ClientRegistry.bindTileEntitySpecialRenderer(getTileEntityClass().java, getRenderClass().createInstance())
    }

    fun registerTESRItemStacks(block: ModBlock) {
        val item = Item.getItemFromBlock(block)
        ForgeHooksClient.registerTESRItemStack(item, 0, getTileEntityClass().java)
    }
}
