package com.codingforcookies.betterrecords.src.items;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.codingforcookies.betterrecords.src.BetterRecords;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFrequencyTuner extends BlockContainer {
	public BlockFrequencyTuner() {
		super(Material.wood);
		setBlockBounds(.18F, 0F, .12F, .82F, .6F, .88F);
	}
	
	public void setBlockBoundsBasedOnState(IBlockAccess block, int x, int y, int z) {
		switch(block.getTileEntity(x, y, z).blockMetadata) {
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
	
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		world.markBlockForUpdate(x, y, z);
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float what, float these, float are) {
		if(!(world.getTileEntity(x, y, z) instanceof TileEntityFrequencyTuner))
			return false;
		
		player.openGui(BetterRecords.instance, 1, world, x, y, z);
		return true;
	}
	
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityLiving, ItemStack itemStack) {
		int rotation = MathHelper.floor_double((double)((entityLiving.rotationYaw * 4.0f) / 360F) + 2.5D) & 3;
		world.setBlockMetadataWithNotify(i, j, k, rotation, 2);
	}
	
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		dropItem(world, x, y, z);
		super.breakBlock(world, x, y, z, block, meta);
	}
	
	private void dropItem(World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(tileEntity == null || !(tileEntity instanceof TileEntityFrequencyTuner))
			return;
		
		TileEntityFrequencyTuner tileEntityFrequencyTuner = (TileEntityFrequencyTuner)tileEntity;
		ItemStack item = tileEntityFrequencyTuner.crystal;
		
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
			
			tileEntityFrequencyTuner.crystal = null;
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
		return new TileEntityFrequencyTuner();
	}
}