package com.codingforcookies.betterrecords.block.tile

import net.minecraft.block.state.IBlockState
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.NetworkManager
import net.minecraft.network.play.server.SPacketUpdateTileEntity
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

abstract class ModTile : TileEntity() {

    override fun shouldRefresh(world: World, pos: BlockPos, oldState: IBlockState, newState: IBlockState) =
            oldState.block != newState.block

    override fun onDataPacket(net: NetworkManager, pkt: SPacketUpdateTileEntity) =
            readFromNBT(pkt.nbtCompound)

    override fun getUpdateTag() = writeToNBT(NBTTagCompound())

    override fun getUpdatePacket(): SPacketUpdateTileEntity {
        val nbt = NBTTagCompound()
        writeToNBT(nbt)
        return SPacketUpdateTileEntity(pos, 1, nbt)
    }
}
