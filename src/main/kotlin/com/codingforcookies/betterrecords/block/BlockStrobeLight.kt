package com.codingforcookies.betterrecords.block

import com.codingforcookies.betterrecords.api.record.IRecordAmplitude
import com.codingforcookies.betterrecords.api.wire.IRecordWire
import com.codingforcookies.betterrecords.block.tile.TileStrobeLight
import com.codingforcookies.betterrecords.client.render.RenderStrobeLight
import com.codingforcookies.betterrecords.helper.ConnectionHelper
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World

class BlockStrobeLight(name: String) : ModBlock(Material.IRON, name), TESRProvider<TileStrobeLight>, ItemModelProvider  {

    init {
        setHardness(2.75f)
        setResistance(4f)
    }

    override fun getTileEntityClass() = TileStrobeLight::class
    override fun getRenderClass() = RenderStrobeLight::class

    override fun getBoundingBox(state: IBlockState?, block: IBlockAccess?, pos: BlockPos?): AxisAlignedBB {
        return AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 0.75, 0.74)
    }

    override fun getLightValue(state: IBlockState, access: IBlockAccess, pos: BlockPos): Int {
        val te = access.getTileEntity(pos)
        if (te == null || te !is IRecordWire || te !is IRecordAmplitude) return 0
        return if ((te as IRecordWire).connections.size > 0) 5 else 0
    }

    override fun onBlockAdded(world: World?, pos: BlockPos?, state: IBlockState?) {
        super.onBlockAdded(world, pos, state)
        world!!.notifyBlockUpdate(pos!!, state!!, state, 3)
    }

    override fun removedByPlayer(state: IBlockState, world: World, pos: net.minecraft.util.math.BlockPos, player: EntityPlayer, willHarvest: Boolean): Boolean {
        if (world.isRemote) return super.removedByPlayer(state, world, pos, player, willHarvest)
        val te = world.getTileEntity(pos)
        if (te != null && te is IRecordWire) ConnectionHelper.clearConnections(world, te as IRecordWire)
        return super.removedByPlayer(state, world, pos, player, willHarvest)
    }
}
