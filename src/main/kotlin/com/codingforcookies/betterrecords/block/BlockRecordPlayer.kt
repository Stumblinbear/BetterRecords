package com.codingforcookies.betterrecords.block

import com.codingforcookies.betterrecords.api.BetterRecordsAPI
import com.codingforcookies.betterrecords.api.record.IRecord
import com.codingforcookies.betterrecords.api.wire.IRecordWire
import com.codingforcookies.betterrecords.api.wire.IRecordWireManipulator
import com.codingforcookies.betterrecords.block.tile.TileRecordPlayer
import com.codingforcookies.betterrecords.client.core.handler.BetterEventHandler
import com.codingforcookies.betterrecords.common.core.helper.ConnectionHelper
import com.codingforcookies.betterrecords.common.packets.PacketHandler
import com.codingforcookies.betterrecords.common.util.BetterUtils
import com.codingforcookies.betterrecords.handler.ConfigHandler
import com.codingforcookies.betterrecords.item.ModItems
import net.minecraft.block.material.Material
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.*
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import java.util.*

class BlockRecordPlayer(name: String) : ModBlock(Material.WOOD, name) {

    init {
        setHardness(1f)
        setResistance(5f)
    }

    override fun getBoundingBox(state: IBlockState?, block: IBlockAccess?, pos: BlockPos?): AxisAlignedBB {
        return AxisAlignedBB(.025, 0.0, .025, .975, .975, .975)
    }

    override fun onBlockAdded(world: World?, pos: BlockPos?, state: IBlockState?) {
        super.onBlockAdded(world, pos, state)
        world!!.notifyBlockUpdate(pos!!, state!!, state, 3)
    }

    override fun onBlockActivated(world: World?, pos: BlockPos?, state: IBlockState?, player: EntityPlayer?, hand: EnumHand?, heldItem: ItemStack?, side: EnumFacing?, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        if (heldItem != null && heldItem.item is IRecordWireManipulator) return false
        val tileEntity = world!!.getTileEntity(pos!!)
        if (tileEntity == null || tileEntity !is TileRecordPlayer) return false
        val tileRecordPlayer = tileEntity as TileRecordPlayer?
        if (player!!.isSneaking) {
            if (world.getBlockState(pos.add(0, 1, 0)).block === Blocks.AIR) {
                if (!world.isRemote) {
                    tileRecordPlayer!!.opening = !tileRecordPlayer.opening
                }
                world.notifyBlockUpdate(pos, state!!, state, 3)
                if (tileRecordPlayer!!.opening)
                    world.playSound(pos.x.toDouble(), pos.y.toDouble() + 0.5, pos.z.toDouble(), SoundEvent.REGISTRY.getObject(ResourceLocation("block.chest.open")), SoundCategory.NEUTRAL, 0.2f, world.rand.nextFloat() * 0.2f + 3f, false)
                else
                    world.playSound(pos.x.toDouble(), pos.y.toDouble() + 0.5, pos.z.toDouble(), SoundEvent.REGISTRY.getObject(ResourceLocation("block.chest.open")), SoundCategory.NEUTRAL, 0.2f, world.rand.nextFloat() * 0.2f + 3f, false)
            }
        } else if (tileRecordPlayer!!.opening) {
            if (tileRecordPlayer.record != null) {
                if (!world.isRemote) dropItem(world, pos)
                tileRecordPlayer.record = null
                world.notifyBlockUpdate(pos, state!!, state, 3)
            } else if (heldItem != null && (heldItem.item === Items.DIAMOND || heldItem.item is IRecord && (heldItem.item as IRecord).isRecordValid(heldItem))) {
                if (heldItem.item === Items.DIAMOND) {
                    val itemStack = ItemStack(ModItems.itemRecord)
                    itemStack.tagCompound = NBTTagCompound()
                    itemStack.tagCompound!!.setString("name", "easteregg.ogg")
                    itemStack.tagCompound!!.setString("url", "http://files.enjin.com/788858/SBear'sMods/Songs/easteregg.ogg")
                    itemStack.tagCompound!!.setString("local", "Darude - Sandstorm")
                    itemStack.tagCompound!!.setInteger("color", 0x53EAD7)
                    tileRecordPlayer.record = itemStack
                    world.notifyBlockUpdate(pos, state!!, state, 3)
                    heldItem.stackSize--
                } else {
                    tileRecordPlayer.record = heldItem
                    world.notifyBlockUpdate(pos, state!!, state, 3)
                    if (!world.isRemote) (heldItem.item as IRecord).onRecordInserted(tileRecordPlayer, heldItem)
                    heldItem.stackSize--
                }
            }
        }
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

    override fun onBlockPlacedBy(world: World?, pos: net.minecraft.util.math.BlockPos?, state: IBlockState?, placer: EntityLivingBase?, stack: ItemStack?) {
        world!!.setBlockState(pos!!, state!!.withProperty(BetterRecordsAPI.CARDINAL_DIRECTIONS, placer!!.horizontalFacing.opposite))
        if (world.isRemote && !ConfigHandler.tutorials["recordplayer"]!!) {
            BetterEventHandler.tutorialText = BetterUtils.getTranslatedString("tutorial.recordplayer")
            BetterEventHandler.tutorialTime = System.currentTimeMillis() + 10000
            ConfigHandler.tutorials.put("recordplayer", true)
        }
    }

    override fun removedByPlayer(state: IBlockState, world: World, pos: BlockPos, player: EntityPlayer, willHarvest: Boolean): Boolean {
        if (world.isRemote) return super.removedByPlayer(state, world, pos, player, willHarvest)
        val te = world.getTileEntity(pos)
        if (te != null && te is IRecordWire) ConnectionHelper.clearConnections(world, te as IRecordWire?)
        return super.removedByPlayer(state, world, pos, player, willHarvest)
    }

    override fun breakBlock(world: World, pos: BlockPos, state: IBlockState) {
        dropItem(world, pos)
        super.breakBlock(world, pos, state)
    }


    private fun dropItem(world: World, pos: BlockPos) {
        val tileEntity = world.getTileEntity(pos)
        if (tileEntity == null || tileEntity !is TileRecordPlayer) return
        val tileRecordPlayer = tileEntity as TileRecordPlayer?
        val item = tileRecordPlayer!!.record
        if (item != null) {
            val rand = Random()
            val rx = rand.nextFloat() * 0.8f + 0.1f
            val ry = rand.nextFloat() * 0.8f + 0.1f
            val rz = rand.nextFloat() * 0.8f + 0.1f
            val entityItem = EntityItem(world, (pos.x + rx).toDouble(), (pos.y + ry).toDouble(), (pos.z + rz).toDouble(), ItemStack(item.item, item.stackSize, item.itemDamage))
            if (item.hasTagCompound()) entityItem.entityItem.tagCompound = item.tagCompound!!.copy()
            entityItem.motionX = rand.nextGaussian() * 0.05f
            entityItem.motionY = rand.nextGaussian() * 0.05f + 0.2f
            entityItem.motionZ = rand.nextGaussian() * 0.05f
            world.spawnEntity(entityItem)
            item.stackSize = 0
            tileRecordPlayer.record = null
            PacketHandler.sendSoundStopToAllFromServer(tileRecordPlayer.pos.x, tileRecordPlayer.pos.y, tileRecordPlayer.pos.z, world.provider.dimension)
        }
    }

    override fun getTileEntityClass() = TileRecordPlayer::class
}
