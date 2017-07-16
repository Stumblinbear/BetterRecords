package com.codingforcookies.betterrecords.block

import com.codingforcookies.betterrecords.api.wire.IRecordWire
import com.codingforcookies.betterrecords.block.tile.TileFrequencyTuner
import com.codingforcookies.betterrecords.client.core.handler.BetterEventHandler
import com.codingforcookies.betterrecords.block.tile.TileSpeaker
import com.codingforcookies.betterrecords.common.core.handler.ConfigHandler
import com.codingforcookies.betterrecords.common.core.helper.ConnectionHelper
import com.codingforcookies.betterrecords.common.util.BetterUtils
import net.minecraft.block.Block
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

class BlockSpeaker(name: String, meta: Int) : ModBlock(Material.WOOD, name) {
    var meta = 0

    init {
        this.meta = meta

        when (meta) {
            0 -> {
                setHardness(2f)
                setResistance(7.5f)
            }
            1 -> {
                setHardness(3f)
                setResistance(8f)
            }
            2 -> {
                setHardness(4f)
                setResistance(9.5f)
            }
        }
    }

    override fun onBlockAdded(world: World?, pos: BlockPos?, state: IBlockState?) {
        super.onBlockAdded(world, pos, state)
        world!!.notifyBlockUpdate(pos!!, state!!, state, 3)
    }

    override fun getBoundingBox(state: IBlockState?, block: IBlockAccess?, pos: BlockPos?): AxisAlignedBB {
        when (meta) {
            0 -> return AxisAlignedBB(0.26, 0.05, 0.25, 0.75, 0.65, 0.74)
            1 -> return AxisAlignedBB(0.2, 0.0, 0.2, 0.8, 0.88, 0.8)
            2 -> return AxisAlignedBB(0.12, 0.0, 0.12, 0.88, 1.51, 0.88)
            else -> return Block.FULL_BLOCK_AABB
        }
    }

    override fun onBlockPlacedBy(world: World?, pos: BlockPos?, state: IBlockState?, placer: EntityLivingBase?, stack: ItemStack?) {
        val tileEntity = world!!.getTileEntity(pos!!)
        if (tileEntity == null || tileEntity !is TileSpeaker) return
        tileEntity.rotation = placer!!.rotationYaw
        tileEntity.type = meta
        if (world.isRemote && !ConfigHandler.tutorials["speaker"]!!) {
            BetterEventHandler.tutorialText = BetterUtils.getTranslatedString("tutorial.speaker")
            BetterEventHandler.tutorialTime = System.currentTimeMillis() + 10000
            ConfigHandler.tutorials.put("speaker", true)
        }
    }

    override fun removedByPlayer(state: IBlockState, world: World, pos: BlockPos, player: EntityPlayer, willHarvest: Boolean): Boolean {
        if (world.isRemote) return super.removedByPlayer(state, world, pos, player, willHarvest)
        val te = world.getTileEntity(pos)
        if (te != null && te is IRecordWire) ConnectionHelper.clearConnections(world, te as IRecordWire?)
        return super.removedByPlayer(state, world, pos, player, willHarvest)
    }

    override fun getTileEntityClass() = TileSpeaker::class

    companion object {

        var speakers = arrayOf("sm", "md", "lg")
    }
}
