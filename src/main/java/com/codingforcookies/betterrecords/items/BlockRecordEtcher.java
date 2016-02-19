package com.codingforcookies.betterrecords.items;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import com.codingforcookies.betterrecords.BetterRecords;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRecordEtcher extends BlockContainer {
	public BlockRecordEtcher() {
		super(Material.wood);
		setBlockBounds(.065F, 0F, .065F, .935F, .875F, .935F);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state){
		super.onBlockAdded(world, pos, state);
		world.markBlockForUpdate(pos);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ){
		if(!(world.getTileEntity(pos) instanceof TileEntityRecordEtcher))
			return false;
		
		player.openGui(BetterRecords.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		dropItem(world, pos);
		super.breakBlock(world, pos, state);
	}
	
	private void dropItem(World world, BlockPos pos) {
		TileEntity tileEntity = world.getTileEntity(pos);
		if(tileEntity == null || !(tileEntity instanceof TileEntityRecordEtcher))
			return;
		
		TileEntityRecordEtcher tileEntityRecordEtcher = (TileEntityRecordEtcher)tileEntity;
		ItemStack item = tileEntityRecordEtcher.record;
		
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
			
			tileEntityRecordEtcher.record = null;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
    public int getRenderType() {
		return 2;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityRecordEtcher();
	}
}