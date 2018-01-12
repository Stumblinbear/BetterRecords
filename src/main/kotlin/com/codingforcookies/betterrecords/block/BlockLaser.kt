package com.codingforcookies.betterrecords.block

import com.codingforcookies.betterrecords.api.wire.IRecordWire
import com.codingforcookies.betterrecords.block.tile.TileLaser
import com.codingforcookies.betterrecords.client.render.RenderLaser
import com.codingforcookies.betterrecords.helper.ConnectionHelper
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentTranslation
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World

class BlockLaser(name: String) : ModBlock(Material.IRON, name), TESRProvider<TileLaser>, ItemModelProvider  {

    init {
        setHardness(3.2f)
        setResistance(4.3f)
    }

    override fun getTileEntityClass() = TileLaser::class
    override fun getRenderClass() = RenderLaser::class

            override fun getBoundingBox(state: IBlockState?, block: IBlockAccess?, pos: BlockPos?) =
            AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 0.75, 0.74)

    override fun onBlockAdded(world: World?, pos: BlockPos?, state: IBlockState?) =
            world!!.notifyBlockUpdate(pos!!, state!!, state, 3)

    // override fun getLightValue(state: IBlockState, access: IBlockAccess, pos: BlockPos): Int TODO: value from flash

    override fun onBlockPlacedBy(world: World, pos: BlockPos, state: IBlockState, entityLiving: EntityLivingBase, itemStack: ItemStack) {
        (world.getTileEntity(pos) as? TileLaser)?.let { te ->
            te.pitch = entityLiving.rotationPitch
            te.yaw = entityLiving.rotationYaw
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

    override fun onBlockActivated(world: World, pos: BlockPos, state: IBlockState, player: EntityPlayer, hand: EnumHand, side: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        (world.getTileEntity(pos) as? TileLaser)?.let { te ->
            val length = te.length

            if (player.isSneaking && te.length > 0) {
                te.length--
            } else if (!player.isSneaking && te.length < 25) {
                te.length++
            }

            if (te.length != length && !world.isRemote) {
                val adjustment = if (te.length > length) "increase" else "decrease"
                player.sendMessage(TextComponentTranslation("msg.laserlength.$adjustment").appendText(" ${te.length}"))
            }
          return true
        }
        return false
    }
}
