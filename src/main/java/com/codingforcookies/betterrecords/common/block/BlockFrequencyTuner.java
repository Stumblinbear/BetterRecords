package com.codingforcookies.betterrecords.common.block;

import com.codingforcookies.betterrecords.api.BetterRecordsAPI;
import com.codingforcookies.betterrecords.common.BetterRecords;
import com.codingforcookies.betterrecords.common.block.tile.TileEntityFrequencyTuner;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockFrequencyTuner extends BetterBlock {

    public BlockFrequencyTuner(String name) {
        super(Material.WOOD, name);
        setHardness(1.5F);
        setResistance(5.5F);

    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess block, BlockPos pos) {
        switch(getMetaFromState(state)) {
            case 0:
            case 2:
                return new AxisAlignedBB(.18F, 0F, .12F, .82F, .6F, .88F);
            case 1:
            case 3:
                return new AxisAlignedBB(.12F, 0F, .18F, .88F, .6F, .82F);
            default:
                return FULL_BLOCK_AABB;
        }
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        super.onBlockAdded(world, pos, state);
        world.notifyBlockUpdate(pos, state, state, 3);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(!(world.getTileEntity(pos) instanceof TileEntityFrequencyTuner))
            return false;

        player.openGui(BetterRecords.instance, 1, world, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BetterRecordsAPI.CARDINAL_DIRECTIONS);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(BetterRecordsAPI.CARDINAL_DIRECTIONS).getHorizontalIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(BetterRecordsAPI.CARDINAL_DIRECTIONS, EnumFacing.getHorizontal(meta));
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(BetterRecordsAPI.CARDINAL_DIRECTIONS, placer.getHorizontalFacing().getOpposite()));
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        dropItem(world, pos);
        super.breakBlock(world, pos, state);
    }

    private void dropItem(World world, BlockPos pos) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity == null || !(tileEntity instanceof TileEntityFrequencyTuner))
            return;

        TileEntityFrequencyTuner tileEntityFrequencyTuner = (TileEntityFrequencyTuner)tileEntity;
        ItemStack item = tileEntityFrequencyTuner.crystal;

        if(item != null) {
            Random rand = new Random();

            float rx = rand.nextFloat() * 0.8F + 0.1F;
            float ry = rand.nextFloat() * 0.8F + 0.1F;
            float rz = rand.nextFloat() * 0.8F + 0.1F;

            EntityItem entityItem = new EntityItem(world, pos.getX() + rx, pos.getY() + ry, pos.getZ() + rz, new ItemStack(item.getItem(), item.stackSize, item.getItemDamage()));

            if(item.hasTagCompound())
                entityItem.getEntityItem().setTagCompound((NBTTagCompound)item.getTagCompound().copy());

            entityItem.motionX = rand.nextGaussian() * 0.05F;
            entityItem.motionY = rand.nextGaussian() * 0.05F + 0.2F;
            entityItem.motionZ = rand.nextGaussian() * 0.05F;
            world.spawnEntityInWorld(entityItem);
            item.stackSize = 0;

            tileEntityFrequencyTuner.crystal = null;
        }
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return new TileEntityFrequencyTuner();
    }
}
