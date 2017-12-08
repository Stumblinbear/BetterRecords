package com.codingforcookies.betterrecords.block

import com.codingforcookies.betterrecords.api.wire.IRecordWire
import com.codingforcookies.betterrecords.block.tile.TileSpeaker
import com.codingforcookies.betterrecords.client.handler.ClientRenderHandler
import com.codingforcookies.betterrecords.helper.ConnectionHelper
import com.codingforcookies.betterrecords.util.BetterUtils
import com.codingforcookies.betterrecords.handler.ConfigHandler
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World

class BlockSpeaker(name: String, val meta: Int) : ModBlock(Material.WOOD, name) {

    init {
        setHardness(when (meta) {
            0 -> 2F
            1 -> 3F
            2 -> 4F
            else -> 2F // Uh oh
        })

        setResistance(when (meta) {
            0 -> 7.5F
            1 -> 8F
            2 -> 9.5F
            else -> 7.5F // Uh oh
        })
    }

    override fun getTileEntityClass() = TileSpeaker::class

    override fun getBoundingBox(state: IBlockState?, block: IBlockAccess?, pos: BlockPos?) = when (meta) {
        0 -> AxisAlignedBB(0.26, 0.05, 0.25, 0.75, 0.65, 0.74)
        1 -> AxisAlignedBB(0.2, 0.0, 0.2, 0.8, 0.88, 0.8)
        2 -> AxisAlignedBB(0.12, 0.0, 0.12, 0.88, 1.51, 0.88)
        else -> Block.FULL_BLOCK_AABB
    }

    override fun onBlockAdded(world: World, pos: BlockPos, state: IBlockState) =
            world.notifyBlockUpdate(pos, state, state, 3)

    override fun onBlockPlacedBy(world: World, pos: BlockPos, state: IBlockState, placer: EntityLivingBase, stack: ItemStack) {
        (world.getTileEntity(pos) as? TileSpeaker)?.let { te ->
            te.rotation = placer.rotationYaw
            te.type = meta

            if (world.isRemote && !ConfigHandler.tutorials["speaker"]!!) {
                ClientRenderHandler.tutorialText = BetterUtils.getTranslatedString("tutorial.speaker")
                ClientRenderHandler.tutorialTime = System.currentTimeMillis() + 10000
                ConfigHandler.tutorials["speaker"] = true
            }
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
