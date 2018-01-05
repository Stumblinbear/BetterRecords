package com.codingforcookies.betterrecords.block

import com.codingforcookies.betterrecords.ID
import com.codingforcookies.betterrecords.api.wire.IRecordWire
import com.codingforcookies.betterrecords.block.tile.TileSpeaker
import com.codingforcookies.betterrecords.client.render.RenderSpeaker
import com.codingforcookies.betterrecords.helper.ConnectionHelper
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.properties.PropertyEnum
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.IStringSerializable
import net.minecraft.util.NonNullList
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.client.ForgeHooksClient
import net.minecraftforge.client.model.ModelLoader

class BlockSpeaker(name: String) : ModBlock(Material.WOOD, name), TESRProvider<TileSpeaker>, ItemModelProvider {

    companion object {
        val PROPERTYSIZE = PropertyEnum.create("size", SpeakerSize::class.java)
    }

    init {
        setHardness(when (0) {
            0 -> 2F
            1 -> 3F
            2 -> 4F
            else -> 2F // Uh oh
        })

        setResistance(when (0) {
            0 -> 7.5F
            1 -> 8F
            2 -> 9.5F
            else -> 7.5F // Uh oh
        })
    }

    override fun getTileEntityClass() = TileSpeaker::class
    override fun getRenderClass() = RenderSpeaker::class

    override fun getBoundingBox(state: IBlockState, block: IBlockAccess?, pos: BlockPos?) = getBouningBoxFromState(state)
    override fun getCollisionBoundingBox(state: IBlockState, worldIn: IBlockAccess?, pos: BlockPos?) = getBouningBoxFromState(state)

    private fun getBouningBoxFromState(state: IBlockState) = when (state.getValue(PROPERTYSIZE)) {
        SpeakerSize.SMALL -> AxisAlignedBB(0.26, 0.05, 0.25, 0.75, 0.65, 0.74)
        SpeakerSize.MEDIUM -> AxisAlignedBB(0.2, 0.0, 0.2, 0.8, 0.88, 0.8)
        SpeakerSize.LARGE -> AxisAlignedBB(0.12, 0.0, 0.12, 0.88, 1.51, 0.88)
        else -> Block.FULL_BLOCK_AABB
    }

    override fun onBlockAdded(world: World, pos: BlockPos, state: IBlockState) =
            world.notifyBlockUpdate(pos, state, state, 3)

    override fun onBlockPlacedBy(world: World, pos: BlockPos, state: IBlockState, placer: EntityLivingBase, stack: ItemStack) {
        (world.getTileEntity(pos) as? TileSpeaker)?.let { te ->
            te.rotation = placer.rotationYaw
            te.size = state.getValue(PROPERTYSIZE)
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

    override fun damageDropped(state: IBlockState) = state.getValue(PROPERTYSIZE).meta

    override fun getSubBlocks(itemIn: CreativeTabs, items: NonNullList<ItemStack>) {
        SpeakerSize.values().mapTo(items) {
            ItemStack(this, 1, it.meta)
        }
    }

    override fun getStateFromMeta(meta: Int) = this.defaultState.withProperty(PROPERTYSIZE, SpeakerSize.fromMeta(meta))
    override fun getMetaFromState(state: IBlockState) = state.getValue(PROPERTYSIZE).meta

    override fun createBlockState() = BlockStateContainer(this, PROPERTYSIZE)

    override fun registerItemModel(block: ModBlock) {
        val item = Item.getItemFromBlock(block)
        ModelLoader.setCustomModelResourceLocation(item, 0, ModelResourceLocation("$ID:itemblock/$name", "inventory"))
        ModelLoader.setCustomModelResourceLocation(item, 1, ModelResourceLocation("$ID:itemblock/$name", "inventory"))
        ModelLoader.setCustomModelResourceLocation(item, 2, ModelResourceLocation("$ID:itemblock/$name", "inventory"))
    }

    override fun registerTESRItemStacks(block: ModBlock) {
        val item = Item.getItemFromBlock(block)
        ForgeHooksClient.registerTESRItemStack(item, 0, getTileEntityClass().java)
        ForgeHooksClient.registerTESRItemStack(item, 1, getTileEntityClass().java)
        ForgeHooksClient.registerTESRItemStack(item, 2, getTileEntityClass().java)
    }

    enum class SpeakerSize(val meta: Int, val typeName: String) : IStringSerializable {
        SMALL(0, "small"),
        MEDIUM(1, "medium"),
        LARGE(2, "large");

        override fun getName() = typeName

        companion object {
            private val map = SpeakerSize.values().associateBy(SpeakerSize::meta)
            fun fromMeta(meta: Int) = map[meta] ?: SMALL
        }
    }
}
