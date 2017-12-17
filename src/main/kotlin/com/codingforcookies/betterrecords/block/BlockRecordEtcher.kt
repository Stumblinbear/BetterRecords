package com.codingforcookies.betterrecords.block

import com.codingforcookies.betterrecords.BetterRecords
import com.codingforcookies.betterrecords.block.tile.TileRecordEtcher
import com.codingforcookies.betterrecords.client.render.RenderRecordEtcher
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import java.util.*
import kotlin.reflect.KClass

class BlockRecordEtcher(name: String) : ModBlock(Material.WOOD, name), TESRProvider<TileRecordEtcher>, ItemModelProvider  {

    init {
        setHardness(1.5f)
        setResistance(5.5f)
    }

    override fun getTileEntityClass() = TileRecordEtcher::class
    override fun getRenderClass() = RenderRecordEtcher::class

    override fun getBoundingBox(state: IBlockState?, block: IBlockAccess?, pos: BlockPos?) =
        AxisAlignedBB(.065, 0.0, .065, .935, .875, .935)

    override fun onBlockAdded(world: World, pos: BlockPos, state: IBlockState) =
        world.notifyBlockUpdate(pos, state, state, 3)

    override fun onBlockActivated(world: World, pos: BlockPos, state: IBlockState, player: EntityPlayer, hand: EnumHand, side: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
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

        val item = tileEntity.record

        if (!item.isEmpty) {
            val rand = Random()

            val rx = rand.nextFloat() * 0.8f + 0.1f
            val ry = rand.nextFloat() * 0.8f + 0.1f
            val rz = rand.nextFloat() * 0.8f + 0.1f

            val entityItem = EntityItem(world, (pos.x + rx).toDouble(), (pos.y + ry).toDouble(), (pos.z + rz).toDouble(), ItemStack(item.item, item.count, item.itemDamage))

            if (item.hasTagCompound())
                entityItem.item.tagCompound = item.tagCompound!!.copy()

            entityItem.motionX = rand.nextGaussian() * 0.05f
            entityItem.motionY = rand.nextGaussian() * 0.05f + 0.2f
            entityItem.motionZ = rand.nextGaussian() * 0.05f
            world.spawnEntity(entityItem)
            item.count = 0

            tileEntity.record = ItemStack.EMPTY
        }
    }
}
