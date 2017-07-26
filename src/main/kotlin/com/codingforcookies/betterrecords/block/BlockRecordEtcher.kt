package com.codingforcookies.betterrecords.block

import com.codingforcookies.betterrecords.BetterRecords
import com.codingforcookies.betterrecords.block.tile.TileRecordEtcher
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumHand
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.util.EnumFacing
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World

import java.util.Random

class BlockRecordEtcher(name: String) : ModBlock(Material.WOOD, name) {

    init {
        setHardness(1.5f)
        setResistance(5.5f)
    }

    override fun getTileEntityClass() = TileRecordEtcher::class

    override fun getBoundingBox(state: IBlockState?, block: IBlockAccess?, pos: BlockPos?) =
        AxisAlignedBB(.065, 0.0, .065, .935, .875, .935)

    override fun onBlockAdded(world: World, pos: BlockPos, state: IBlockState) =
        world.notifyBlockUpdate(pos, state, state, 3)

    override fun onBlockActivated(world: World, pos: BlockPos, state: IBlockState, player: EntityPlayer, hand: EnumHand, heldItem: ItemStack?, side: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        (world.getTileEntity(pos) as? TileRecordEtcher)?.let {
            player.openGui(BetterRecords, 0, world, pos.x, pos.y, pos.z)
            return true
        }
        return false
    }

    override fun breakBlock(world: World, pos: BlockPos, state: IBlockState) {
        dropItem(world, pos)
        super.breakBlock(world, pos, state)
    }

    private fun dropItem(world: World, pos: net.minecraft.util.math.BlockPos) {
        val tileEntity = world.getTileEntity(pos)
        if (tileEntity == null || tileEntity !is TileRecordEtcher)
            return

        val tileRecordEtcher = tileEntity as TileRecordEtcher?
        val item = tileRecordEtcher!!.record

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

            tileRecordEtcher.record = null
        }
    }
}
