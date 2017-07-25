package com.codingforcookies.betterrecords.block

import com.codingforcookies.betterrecords.api.BetterRecordsAPI
import com.codingforcookies.betterrecords.BetterRecords
import com.codingforcookies.betterrecords.block.tile.TileFrequencyTuner
import com.codingforcookies.betterrecords.block.tile.TileRadio
import com.codingforcookies.betterrecords.common.packets.PacketHandler
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World

import java.util.Random
import kotlin.reflect.KClass

class BlockFrequencyTuner(name: String) : ModBlockDirectional(Material.WOOD, name) {

    init {
        setHardness(1.5f)
        setResistance(5.5f)
    }

    override fun getTileEntityClass() = TileFrequencyTuner::class

    override fun onBlockAdded(world: World, pos: BlockPos, state: IBlockState) =
            world.notifyBlockUpdate(pos, state, state, 3)

    override fun getBoundingBox(state: IBlockState, block: IBlockAccess, pos: BlockPos) = when (getMetaFromState(state)) {
        0, 2 -> AxisAlignedBB(.18, 0.0, .12, .82, .6, .88)
        1, 3 -> AxisAlignedBB(.12, 0.0, .18, .88, .6, .82)
        else -> Block.FULL_BLOCK_AABB
    }

    override fun onBlockActivated(world: World, pos: BlockPos, state: IBlockState, player: EntityPlayer, hand: EnumHand, heldItem: ItemStack?, side: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        (world.getTileEntity(pos) as? TileFrequencyTuner)?.let {
            player.openGui(BetterRecords, 1, world, pos.x, pos.y, pos.z)
            return true
        }
        return false
    }

    override fun breakBlock(world: World, pos: BlockPos, state: IBlockState) {
        dropItem(world, pos)
        super.breakBlock(world, pos, state)
    }

    private fun dropItem(world: World, pos: BlockPos) {
        (world.getTileEntity(pos) as? TileFrequencyTuner)?.let { te ->
            te.crystal?.let {
                val random = Random()
                val rx = random.nextDouble() * 0.8F + 0.1F
                val ry = random.nextDouble() * 0.8F + 0.1F
                val rz = random.nextDouble() * 0.8F + 0.1F

                val entityItem = EntityItem(world, pos.x + rx, pos.y + ry, pos.z + rz, ItemStack(it.item, it.stackSize, it.itemDamage))
                if (it.hasTagCompound()) entityItem.entityItem.tagCompound = it.tagCompound!!.copy()
                entityItem.motionX = random.nextGaussian() * 0.05F
                entityItem.motionY = random.nextGaussian() * 0.05F + 0.2F
                entityItem.motionZ = random.nextGaussian() * 0.05F

                world.spawnEntity(entityItem)
                it.stackSize = 0
                te.crystal = null
            }
        }
    }
}
