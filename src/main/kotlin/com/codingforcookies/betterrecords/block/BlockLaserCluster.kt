package com.codingforcookies.betterrecords.block

import com.codingforcookies.betterrecords.api.wire.IRecordWire
import com.codingforcookies.betterrecords.block.tile.TileLaserCluster
import com.codingforcookies.betterrecords.client.handler.ClientRenderHandler
import com.codingforcookies.betterrecords.helper.ConnectionHelper
import com.codingforcookies.betterrecords.util.BetterUtils
import com.codingforcookies.betterrecords.handler.ConfigHandler
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World

class BlockLaserCluster(name: String) : ModBlock(Material.IRON, name) {

    init {
        setHardness(4.8f)
        setResistance(4.8f)
    }

    override fun getTileEntityClass() = TileLaserCluster::class

    override fun getBoundingBox(state: IBlockState, source: IBlockAccess, pos: BlockPos) =
            AxisAlignedBB(0.12, 0.0, 0.12, 0.88, 0.76, 0.88)

    override fun onBlockAdded(world: World, pos: BlockPos, state: IBlockState) =
        world.notifyBlockUpdate(pos, state, state, 3)

    //override fun getLightValue(state: IBlockState, access: IBlockAccess, pos: BlockPos): Int TODO: Value from flash

    override fun onBlockPlacedBy(world: World, pos: BlockPos, state: IBlockState, entityLiving: EntityLivingBase, itemStack: ItemStack?) {
        if (world.isRemote && !ConfigHandler.tutorials["lasercluster"]!!) {
            ClientRenderHandler.tutorialText = BetterUtils.getTranslatedString("tutorial.lasercluster")
            ClientRenderHandler.tutorialTime = System.currentTimeMillis() + 10000
            ConfigHandler.tutorials["lasercluster"] = true
        }
    }

    override fun removedByPlayer(state: IBlockState, world: World, pos: BlockPos, player: EntityPlayer, willHarvest: Boolean): Boolean {
        if (!world.isRemote) {
            (world.getTileEntity(pos) as? IRecordWire)?.let { te ->
                ConnectionHelper.clearConnections(world, te)
            }
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest)
    }
}
