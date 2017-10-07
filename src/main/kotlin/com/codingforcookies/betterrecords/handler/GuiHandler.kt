package com.codingforcookies.betterrecords.handler

import com.codingforcookies.betterrecords.client.gui.*
import com.codingforcookies.betterrecords.block.tile.TileFrequencyTuner
import com.codingforcookies.betterrecords.block.tile.TileRecordEtcher
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.IGuiHandler

class GuiHandler : IGuiHandler {

    override fun getServerGuiElement(id: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int) = when (id) {
        0 -> ContainerRecordEtcher(player.inventory, world.getTileEntity(BlockPos(x, y, z)) as TileRecordEtcher)
        1 -> ContainerFrequencyTuner(player.inventory, world.getTileEntity(BlockPos(x, y, z)) as TileFrequencyTuner)
        else -> null
    }

    override fun getClientGuiElement(id: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int) = when(id) {
        0 -> GuiRecordEtcher(player.inventory, world.getTileEntity(BlockPos(x, y, z)) as TileRecordEtcher)
        1 -> GuiFrequencyTuner(player.inventory, world.getTileEntity(BlockPos(x, y, z)) as TileFrequencyTuner)
        2 -> GuiBetterDisclaimer()
        else -> null
    }
}
