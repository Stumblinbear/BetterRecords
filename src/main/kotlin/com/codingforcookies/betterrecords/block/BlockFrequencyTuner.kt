package com.codingforcookies.betterrecords.block

import com.codingforcookies.betterrecords.api.BetterRecordsAPI
import com.codingforcookies.betterrecords.BetterRecords
import com.codingforcookies.betterrecords.block.tile.TileFrequencyTuner
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

class BlockFrequencyTuner(name: String) : ModBlock(Material.WOOD, name) {

    init {
        setHardness(1.5f)
        setResistance(5.5f)

    }

    override fun getBoundingBox(state: IBlockState?, block: IBlockAccess?, pos: BlockPos?): AxisAlignedBB {
        when (getMetaFromState(state)) {
            0, 2 -> return AxisAlignedBB(.18, 0.0, .12, .82, .6, .88)
            1, 3 -> return AxisAlignedBB(.12, 0.0, .18, .88, .6, .82)
            else -> return Block.FULL_BLOCK_AABB
        }
    }

    override fun onBlockAdded(world: World?, pos: BlockPos?, state: IBlockState?) {
        super.onBlockAdded(world, pos, state)
        world!!.notifyBlockUpdate(pos!!, state!!, state, 3)
    }

    override fun onBlockActivated(world: World?, pos: BlockPos?, state: IBlockState?, player: EntityPlayer?, hand: EnumHand?, heldItem: ItemStack?, side: EnumFacing?, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        if (world!!.getTileEntity(pos!!) !is TileFrequencyTuner)
            return false

        player!!.openGui(BetterRecords, 1, world, pos.x, pos.y, pos.z)
        return true
    }

    public override fun createBlockState(): BlockStateContainer {
        return BlockStateContainer(this, BetterRecordsAPI.CARDINAL_DIRECTIONS)
    }

    override fun getMetaFromState(state: IBlockState?): Int {
        return state!!.getValue(BetterRecordsAPI.CARDINAL_DIRECTIONS).horizontalIndex
    }

    override fun getStateFromMeta(meta: Int): IBlockState {
        return defaultState.withProperty(BetterRecordsAPI.CARDINAL_DIRECTIONS, EnumFacing.getHorizontal(meta))
    }

    override fun onBlockPlacedBy(world: World?, pos: BlockPos?, state: IBlockState?, placer: EntityLivingBase?, stack: ItemStack?) {
        world!!.setBlockState(pos!!, state!!.withProperty(BetterRecordsAPI.CARDINAL_DIRECTIONS, placer!!.horizontalFacing.opposite))
    }

    override fun breakBlock(world: World, pos: BlockPos, state: IBlockState) {
        dropItem(world, pos)
        super.breakBlock(world, pos, state)
    }

    private fun dropItem(world: World, pos: BlockPos) {
        val tileEntity = world.getTileEntity(pos)
        if (tileEntity == null || tileEntity !is TileFrequencyTuner)
            return

        val tileEntityFrequencyTuner = tileEntity as TileFrequencyTuner?
        val item = tileEntityFrequencyTuner!!.crystal

        if (item != null) {
            val rand = Random()

            val rx = rand.nextFloat() * 0.8f + 0.1f
            val ry = rand.nextFloat() * 0.8f + 0.1f
            val rz = rand.nextFloat() * 0.8f + 0.1f

            val entityItem = EntityItem(world, (pos.x + rx).toDouble(), (pos.y + ry).toDouble(), (pos.z + rz).toDouble(), ItemStack(item.item, item.stackSize, item.itemDamage))

            if (item.hasTagCompound())
                entityItem.entityItem.tagCompound = item.tagCompound!!.copy()

            entityItem.motionX = rand.nextGaussian() * 0.05f
            entityItem.motionY = rand.nextGaussian() * 0.05f + 0.2f
            entityItem.motionZ = rand.nextGaussian() * 0.05f
            world.spawnEntity(entityItem)
            item.stackSize = 0

            tileEntityFrequencyTuner.crystal = null
        }
    }

    override fun getTileEntityClass() = TileFrequencyTuner::class
}
