package com.codingforcookies.betterrecords.block

import com.codingforcookies.betterrecords.api.BetterRecordsAPI
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

abstract class ModBlockDirectional(material: Material, name: String) : ModBlock(material, name) {

    override fun createBlockState() = BlockStateContainer(this as Block, BetterRecordsAPI.CARDINAL_DIRECTIONS)
    override fun getMetaFromState(state: IBlockState) = state.getValue(BetterRecordsAPI.CARDINAL_DIRECTIONS).horizontalIndex
    override fun getStateFromMeta(meta: Int) = defaultState.withProperty(BetterRecordsAPI.CARDINAL_DIRECTIONS, EnumFacing.getHorizontal(meta))

    override fun onBlockPlacedBy(world: World, pos: BlockPos, state: IBlockState, placer: EntityLivingBase, stack: ItemStack) {
        world.setBlockState(pos, state.withProperty(BetterRecordsAPI.CARDINAL_DIRECTIONS, placer.horizontalFacing.opposite))
    }
}
