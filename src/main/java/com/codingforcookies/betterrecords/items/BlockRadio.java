package com.codingforcookies.betterrecords.items;

import java.util.Random;

import com.codingforcookies.betterrecords.BetterRecords;
import com.codingforcookies.betterrecords.BetterUtils;
import com.codingforcookies.betterrecords.betterenums.ConnectionHelper;
import com.codingforcookies.betterrecords.betterenums.IRecordWire;
import com.codingforcookies.betterrecords.betterenums.IRecordWireManipulator;
import com.codingforcookies.betterrecords.client.BetterEventHandler;
import com.codingforcookies.betterrecords.client.ClientProxy;
import com.codingforcookies.betterrecords.packets.PacketHandler;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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

public class BlockRadio extends BlockContainer{

	public BlockRadio(){
		super(Material.wood);
		setBlockBounds(0.13F, 0F, 0.2F, 0.87F, 0.98F, 0.8F);
	}

	public void setBlockBoundsBasedOnState(IBlockAccess block, int x, int y, int z){
		switch (block.getTileEntity(x, y, z).blockMetadata){
			case 0:
			case 2:
				setBlockBounds(0.13F, 0F, 0.2F, 0.87F, 0.98F, 0.8F);
				break;
			case 1:
			case 3:
				setBlockBounds(0.2F, 0F, 0.13F, 0.8F, 0.98F, 0.87F);
				break;
		}
	}

	public void onBlockAdded(World world, int x, int y, int z){
		super.onBlockAdded(world, x, y, z);
		world.markBlockForUpdate(x, y, z);
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float what, float these, float are){
		if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IRecordWireManipulator) return false;
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(tileEntity == null || !(tileEntity instanceof TileEntityRadio)) return false;
		TileEntityRadio tileEntityRadio = (TileEntityRadio) tileEntity;
		if(player.isSneaking()) {
			tileEntityRadio.opening = !tileEntityRadio.opening;
			world.markBlockForUpdate(x, y, z);
			if(tileEntityRadio.opening) world.playSoundEffect(x, (double) y + 0.5D, z, "random.chestopen", 0.2F, world.rand.nextFloat() * 0.2F + 3F);
			else world.playSoundEffect(x, (double) y + 0.5D, z, "random.chestclosed", 0.2F, world.rand.nextFloat() * 0.2F + 3F);
		}else if(tileEntityRadio.opening) {
			if(tileEntityRadio.crystal != null) {
				if(!world.isRemote) dropItem(world, x, y, z);
				tileEntityRadio.setCrystal(null);
				world.markBlockForUpdate(x, y, z);
			}else if(player.getHeldItem() != null && (player.getHeldItem().getItem() == BetterRecords.itemFreqCrystal && player.getHeldItem().stackTagCompound != null && player.getHeldItem().stackTagCompound.hasKey("url"))) {
				tileEntityRadio.setCrystal(player.getHeldItem());
				world.markBlockForUpdate(x, y, z);
				player.getHeldItem().stackSize--;
				if(!world.isRemote) PacketHandler.sendRadioPlayToAllFromServer(tileEntityRadio.xCoord, tileEntityRadio.yCoord, tileEntityRadio.zCoord, world.provider.dimensionId, tileEntityRadio.getSongRadius(), tileEntityRadio.crystal.stackTagCompound.getString("name"), tileEntityRadio.crystal.stackTagCompound.getString("url"));
			}
		}
		return true;
	}

	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityLiving, ItemStack itemStack){
		int rotation = MathHelper.floor_double((double) ((entityLiving.rotationYaw * 4.0f) / 360F) + 2.5D) & 3;
		world.setBlockMetadataWithNotify(i, j, k, rotation, 2);
		if(world.isRemote && !ClientProxy.tutorials.get("radio")) {
			BetterEventHandler.tutorialText = BetterUtils.getTranslatedString("tutorial.radio");
			BetterEventHandler.tutorialTime = System.currentTimeMillis() + 10000;
			ClientProxy.tutorials.put("radio", true);
		}
	}

	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest){
		if(world.isRemote) return super.removedByPlayer(world, player, x, y, z, willHarvest);
		TileEntity te = world.getTileEntity(x, y, z);
		if(te != null && te instanceof IRecordWire) ConnectionHelper.clearConnections(world, (IRecordWire) te);
		return super.removedByPlayer(world, player, x, y, z, willHarvest);
	}

	public void breakBlock(World world, int x, int y, int z, Block block, int meta){
		dropItem(world, x, y, z);
		super.breakBlock(world, x, y, z, block, meta);
	}

	private void dropItem(World world, int x, int y, int z){
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(tileEntity == null || !(tileEntity instanceof TileEntityRadio)) return;
		TileEntityRadio tileEntityRadio = (TileEntityRadio) tileEntity;
		ItemStack item = tileEntityRadio.crystal;
		if(item != null) {
			Random rand = new Random();
			float rx = rand.nextFloat() * 0.8F + 0.1F;
			float ry = rand.nextFloat() * 0.8F + 0.1F;
			float rz = rand.nextFloat() * 0.8F + 0.1F;
			EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(item.getItem(), item.stackSize, item.getItemDamage()));
			if(item.hasTagCompound()) entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
			entityItem.motionX = rand.nextGaussian() * 0.05F;
			entityItem.motionY = rand.nextGaussian() * 0.05F + 0.2F;
			entityItem.motionZ = rand.nextGaussian() * 0.05F;
			world.spawnEntityInWorld(entityItem);
			item.stackSize = 0;
			tileEntityRadio.crystal = null;
			PacketHandler.sendSoundStopToAllFromServer(tileEntityRadio.xCoord, tileEntityRadio.yCoord, tileEntityRadio.zCoord, world.provider.dimensionId);
		}
	}

	@SideOnly(Side.CLIENT)
	public int getRenderType(){
		return -1;
	}

	@SideOnly(Side.CLIENT)
	public boolean isOpaqueCube(){
		return false;
	}

	@SideOnly(Side.CLIENT)
	public boolean renderAsNormalBlock(){
		return false;
	}

	public TileEntity createNewTileEntity(World var1, int var2){
		return new TileEntityRadio();
	}
}