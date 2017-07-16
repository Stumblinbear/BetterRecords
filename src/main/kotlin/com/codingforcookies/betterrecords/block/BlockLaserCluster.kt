package com.codingforcookies.betterrecords.block

import com.codingforcookies.betterrecords.api.wire.IRecordWire
import com.codingforcookies.betterrecords.block.tile.TileFrequencyTuner
import com.codingforcookies.betterrecords.client.core.handler.BetterEventHandler
import com.codingforcookies.betterrecords.block.tile.TileLaserCluster
import com.codingforcookies.betterrecords.common.core.handler.ConfigHandler
import com.codingforcookies.betterrecords.common.core.helper.ConnectionHelper
import com.codingforcookies.betterrecords.common.util.BetterUtils
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World

class BlockLaserCluster(name: String) : ModBlock(Material.IRON, name) {

    override fun getBoundingBox(state: IBlockState?, source: IBlockAccess?, pos: BlockPos?) =
            AxisAlignedBB(0.12, 0.0, 0.12, 0.88, 0.76, 0.88)

    init {
        setHardness(4.8f)
        setResistance(4.8f)
    }

    override fun getLightValue(state: IBlockState, access: IBlockAccess, pos: BlockPos): Int {
        val te = access.getTileEntity(pos)
        if (te == null || te !is IRecordWire) return 0
        BetterUtils.markBlockDirty(te.world, te.pos)
        return if ((te as IRecordWire).connections.size > 0) 5 else 0
    }

    override fun onBlockAdded(world: World?, pos: net.minecraft.util.math.BlockPos?, state: IBlockState?) {
        super.onBlockAdded(world, pos, state)
        world!!.notifyBlockUpdate(pos!!, state!!, state, 3)
    }

    override fun onBlockPlacedBy(world: World?, pos: net.minecraft.util.math.BlockPos?, state: IBlockState?, entityLiving: EntityLivingBase?, itemStack: ItemStack?) {
        if (world!!.isRemote && !ConfigHandler.tutorials["lazercluster"]!!) {
            BetterEventHandler.tutorialText = BetterUtils.getTranslatedString("tutorial.lazercluster")
            BetterEventHandler.tutorialTime = System.currentTimeMillis() + 10000
            ConfigHandler.tutorials.put("lazercluster", true)
        }
    }

    override fun removedByPlayer(state: IBlockState, world: World, pos: BlockPos, player: EntityPlayer, willHarvest: Boolean): Boolean {
        if (world.isRemote) return super.removedByPlayer(state, world, pos, player, willHarvest)
        val te = world.getTileEntity(pos)
        if (te != null && te is IRecordWire) ConnectionHelper.clearConnections(world, te as IRecordWire?)
        return super.removedByPlayer(state, world, pos, player, willHarvest)
    }

    override fun getTileEntityClass() = TileLaserCluster::class
}
