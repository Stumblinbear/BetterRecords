package com.codingforcookies.betterrecords.block

import net.minecraft.block.Block
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.fml.common.registry.GameRegistry
import kotlin.reflect.KClass

interface TileEntityProvider<T> where T : TileEntity {

    fun getTileEntityClass(): KClass<out T>

    fun registerTileEntity(block: Block) {
        GameRegistry.registerTileEntity(getTileEntityClass().java, block.registryName.toString())
    }

}
