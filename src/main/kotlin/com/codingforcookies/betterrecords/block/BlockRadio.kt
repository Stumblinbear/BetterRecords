package com.codingforcookies.betterrecords.block

import com.codingforcookies.betterrecords.api.BetterRecordsAPI
import com.codingforcookies.betterrecords.api.wire.IRecordWire
import com.codingforcookies.betterrecords.api.wire.IRecordWireManipulator
import com.codingforcookies.betterrecords.block.tile.TileFrequencyTuner
import com.codingforcookies.betterrecords.client.core.handler.BetterEventHandler
import com.codingforcookies.betterrecords.block.tile.TileRadio
import com.codingforcookies.betterrecords.common.core.handler.ConfigHandler
import com.codingforcookies.betterrecords.common.core.helper.ConnectionHelper
import com.codingforcookies.betterrecords.common.packets.PacketHandler
import com.codingforcookies.betterrecords.common.util.BetterUtils
import com.codingforcookies.betterrecords.item.ModItems
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.*
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World

import java.util.Random

class BlockRadio(name: String) : ModBlock(Material.WOOD, name) {

    init {
        setHardness(2f)
        setResistance(6.3f)
    }

    override fun getBoundingBox(state: IBlockState?, block: IBlockAccess?, pos: BlockPos?): AxisAlignedBB {
        when (getMetaFromState(state)) {
            0, 2 -> return AxisAlignedBB(0.13, 0.0, 0.2, 0.87, 0.98, 0.8)
            1, 3 -> return AxisAlignedBB(0.2, 0.0, 0.13, 0.8, 0.98, 0.87)
            else -> return Block.FULL_BLOCK_AABB
        }
    }

    override fun onBlockAdded(world: World?, pos: net.minecraft.util.math.BlockPos?, state: IBlockState?) {
        super.onBlockAdded(world, pos, state)
        world!!.notifyBlockUpdate(pos!!, state!!, state, 3)
    }

    override fun onBlockActivated(world: World?, pos: net.minecraft.util.math.BlockPos?, state: IBlockState?, player: EntityPlayer?, hand: EnumHand?, heldItem: ItemStack?, side: EnumFacing?, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        if (heldItem != null && heldItem.item is IRecordWireManipulator) return false
        val tileEntity = world!!.getTileEntity(pos!!)
        if (tileEntity == null || tileEntity !is TileRadio) return false
        val tileRadio = tileEntity as TileRadio?
        if (player!!.isSneaking) {
            tileRadio!!.opening = !tileRadio.opening
            world.notifyBlockUpdate(pos, state!!, state, 3)
            if (tileRadio.opening)
                world.playSound(pos.x.toDouble(), pos.y.toDouble() + 0.5, pos.z.toDouble(), SoundEvent.REGISTRY.getObject(ResourceLocation("block.chest.open")), SoundCategory.NEUTRAL, 0.2f, world.rand.nextFloat() * 0.2f + 3f, false)
            else
                world.playSound(pos.x.toDouble(), pos.y.toDouble() + 0.5, pos.z.toDouble(), SoundEvent.REGISTRY.getObject(ResourceLocation("block.chest.open")), SoundCategory.NEUTRAL, 0.2f, world.rand.nextFloat() * 0.2f + 3f, false)
        } else if (tileRadio!!.opening) {
            if (tileRadio.crystal != null) {
                if (!world.isRemote) dropItem(world, pos)
                tileRadio.crystal = null
                world.notifyBlockUpdate(pos, state!!, state, 3)
            } else if (heldItem != null && heldItem.item === ModItems.itemFrequencyCrystal && heldItem.tagCompound != null && heldItem.tagCompound!!.hasKey("url")) {
                tileRadio.crystal = heldItem
                world.notifyBlockUpdate(pos, state!!, state, 3)
                heldItem.stackSize--
                if (!world.isRemote) PacketHandler.sendRadioPlayToAllFromServer(tileRadio.pos.x, tileRadio.pos.y, tileRadio.pos.z, world.provider.dimension, tileRadio.songRadius, tileRadio.crystal!!.tagCompound!!.getString("name"), tileRadio.crystal!!.tagCompound!!.getString("url"))
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

    override fun onBlockPlacedBy(world: World?, pos: net.minecraft.util.math.BlockPos?, state: IBlockState?, placer: EntityLivingBase?, itemStack: ItemStack?) {
        world!!.setBlockState(pos!!, state!!.withProperty(BetterRecordsAPI.CARDINAL_DIRECTIONS, placer!!.horizontalFacing.opposite))
        if (world.isRemote && !ConfigHandler.tutorials["radio"]!!) {
            BetterEventHandler.tutorialText = BetterUtils.getTranslatedString("tutorial.radio")
            BetterEventHandler.tutorialTime = System.currentTimeMillis() + 10000
            ConfigHandler.tutorials.put("radio", true)
        }
    }

    override fun removedByPlayer(state: IBlockState, world: World, pos: net.minecraft.util.math.BlockPos, player: EntityPlayer, willHarvest: Boolean): Boolean {
        if (world.isRemote) return super.removedByPlayer(state, world, pos, player, willHarvest)
        val te = world.getTileEntity(pos)
        if (te != null && te is IRecordWire) ConnectionHelper.clearConnections(world, te as IRecordWire?)
        return super.removedByPlayer(state, world, pos, player, willHarvest)
    }

    override fun breakBlock(world: World, pos: net.minecraft.util.math.BlockPos, state: IBlockState) {
        dropItem(world, pos)
        super.breakBlock(world, pos, state)
    }

    private fun dropItem(world: World, pos: net.minecraft.util.math.BlockPos) {
        val tileEntity = world.getTileEntity(pos)
        if (tileEntity == null || tileEntity !is TileRadio) return
        val tileRadio = tileEntity as TileRadio?
        val item = tileRadio!!.crystal
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
            tileRadio.crystal = null
            PacketHandler.sendSoundStopToAllFromServer(tileRadio.pos.x, tileRadio.pos.y, tileRadio.pos.z, world.provider.dimension)
        }
    }

    override fun getTileEntityClass() = TileRadio::class
}
