package com.codingforcookies.betterrecords.block

import com.codingforcookies.betterrecords.api.wire.IRecordWire
import com.codingforcookies.betterrecords.block.tile.TileFrequencyTuner
import com.codingforcookies.betterrecords.client.core.handler.BetterEventHandler
import com.codingforcookies.betterrecords.block.tile.TileLaser
import com.codingforcookies.betterrecords.block.tile.TileSpeaker
import com.codingforcookies.betterrecords.common.core.handler.ConfigHandler
import com.codingforcookies.betterrecords.common.core.helper.ConnectionHelper
import com.codingforcookies.betterrecords.common.util.BetterUtils
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentTranslation
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World

class BlockLaser(name: String) : ModBlock(Material.IRON, name) {

    init {
        setHardness(3.2f)
        setResistance(4.3f)
    }

    override fun getLightValue(state: IBlockState, access: IBlockAccess, pos: BlockPos): Int {
        val te = access.getTileEntity(pos)
        if (te == null || te !is IRecordWire) return 0
        BetterUtils.markBlockDirty(te.world, te.pos)
        return if ((te as IRecordWire).connections.size > 0) 5 else 0
    }

    override fun getBoundingBox(state: IBlockState?, block: IBlockAccess?, pos: BlockPos?): AxisAlignedBB {
        return AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 0.75, 0.74)
    }

    override fun onBlockAdded(world: World?, pos: BlockPos?, state: IBlockState?) {
        super.onBlockAdded(world, pos, state)
        world!!.notifyBlockUpdate(pos!!, state!!, state, 3)
    }

    override fun onBlockPlacedBy(world: World?, pos: BlockPos?, state: IBlockState?, entityLiving: EntityLivingBase?, itemStack: ItemStack?) {
        val te = world!!.getTileEntity(pos!!)
        if (te != null && te is TileLaser) {
            te.pitch = entityLiving!!.rotationPitch
            te.yaw = entityLiving.rotationYaw
        }
        if (world.isRemote && !ConfigHandler.tutorials["lazer"]!!) {
            BetterEventHandler.tutorialText = BetterUtils.getTranslatedString("tutorial.lazer")
            BetterEventHandler.tutorialTime = System.currentTimeMillis() + 10000
            ConfigHandler.tutorials.put("lazer", true)
        }
    }

    override fun removedByPlayer(state: IBlockState, world: World, pos: BlockPos, player: EntityPlayer, willHarvest: Boolean): Boolean {
        if (world.isRemote) return super.removedByPlayer(state, world, pos, player, willHarvest)
        val te = world.getTileEntity(pos)
        if (te != null && te is IRecordWire) ConnectionHelper.clearConnections(world, te as IRecordWire?)
        return super.removedByPlayer(state, world, pos, player, willHarvest)
    }

    override fun onBlockActivated(world: World?, pos: BlockPos?, state: IBlockState?, player: EntityPlayer?, hand: EnumHand?, heldItem: ItemStack?, side: EnumFacing?, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        val tileEntity = world!!.getTileEntity(pos!!)
        if (tileEntity == null || tileEntity !is TileLaser) return false
        val tileLaser = tileEntity as TileLaser?
        val length = tileLaser!!.length.toFloat()
        if (player!!.isSneaking) {
            if (tileLaser.length > 0) {
                tileLaser.length--
            }
        } else {
            if (tileLaser.length < 25) {
                tileLaser.length++
            }
        }
        if (tileLaser.length.toFloat() != length && !world.isRemote) {
            player.sendMessage(TextComponentTranslation("msg.lazerlength." + if (tileLaser.length > length) "increase" else "decrease").appendText(" " + tileLaser.length))
        }
        return true
    }

    override fun getTileEntityClass() = TileLaser::class
}
