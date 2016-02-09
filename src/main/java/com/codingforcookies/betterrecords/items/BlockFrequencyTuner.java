package com.codingforcookies.betterrecords.items;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.codingforcookies.betterrecords.BetterRecords;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFrequencyTuner extends BlockContainer {
	public BlockFrequencyTuner() {
		super(Material.wood);
		setBlockBounds(.18F, 0F, .12F, .82F, .6F, .88F);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess block, BlockPos pos) {
		switch(block.getTileEntity(pos).getBlockMetadata()) {
			case 0:
			case 2:
				setBlockBounds(.18F, 0F, .12F, .82F, .6F, .88F);
				break;
			case 1:
			case 3:
				setBlockBounds(.12F, 0F, .18F, .88F, .6F, .82F);
				break;
		}
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		super.func_181087_e(world, pos);
		world.markBlockForUpdate(new BlockPos(pos.getX(), pos.getY(), pos.getZ()));
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!(world.getTileEntity(pos) instanceof TileEntityFrequencyTuner))
			return false;
		
		player.openGui(BetterRecords.instance, 1, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	//TODO
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		int rotation = MathHelper.floor_double((double)((placer.rotationYaw * 4.0f) / 360F) + 2.5D) & 3;
		//world.setBlockMetadataWithNotify(i, j, k, rotation, 2);
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
	@SideOnly(Side.CLIENT)
    public int getRenderType() {
		return -1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isOpaqueCube() {
		return false;
	}

	//TODO
	@SideOnly(Side.CLIENT)
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityFrequencyTuner();
	}
}