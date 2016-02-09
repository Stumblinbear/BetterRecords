package com.codingforcookies.betterrecords.items;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.codingforcookies.betterrecords.BetterRecords;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRecordEtcher extends BlockContainer {
	public BlockRecordEtcher() {
		super(Material.wood);
		setBlockBounds(.065F, 0F, .065F, .935F, .875F, .935F);
	}
	
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		world.markBlockForUpdate(x, y, z);
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float what, float these, float are) {
		if(!(world.getTileEntity(x, y, z) instanceof TileEntityRecordEtcher))
			return false;
		
		player.openGui(BetterRecords.instance, 0, world, x, y, z);
		return true;
	}
	
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		dropItem(world, x, y, z);
		super.breakBlock(world, x, y, z, block, meta);
	}
	
	private void dropItem(World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(tileEntity == null || !(tileEntity instanceof TileEntityRecordEtcher))
			return;
		
		TileEntityRecordEtcher tileEntityRecordEtcher = (TileEntityRecordEtcher)tileEntity;
		ItemStack item = tileEntityRecordEtcher.record;
		
		if(item != null) {
			Random rand = new Random();
			
			float rx = rand.nextFloat() * 0.8F + 0.1F;
			float ry = rand.nextFloat() * 0.8F + 0.1F;
			float rz = rand.nextFloat() * 0.8F + 0.1F;
			
			EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(item.getItem(), item.stackSize, item.getItemDamage()));
			
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
	
	@SideOnly(Side.CLIENT)
    public int getRenderType() {
		return -1;
	}
	
	@SideOnly(Side.CLIENT)
	public boolean isOpaqueCube() {
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityRecordEtcher();
	}
}