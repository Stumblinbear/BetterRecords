package com.codingforcookies.betterrecords.src.items;

import java.util.Random;

import com.codingforcookies.betterrecords.src.BetterRecords;
import com.codingforcookies.betterrecords.src.BetterUtils;
import com.codingforcookies.betterrecords.src.betterenums.ConnectionHelper;
import com.codingforcookies.betterrecords.src.betterenums.IRecord;
import com.codingforcookies.betterrecords.src.betterenums.IRecordWire;
import com.codingforcookies.betterrecords.src.betterenums.IRecordWireManipulator;
import com.codingforcookies.betterrecords.src.client.BetterEventHandler;
import com.codingforcookies.betterrecords.src.client.ClientProxy;
import com.codingforcookies.betterrecords.src.packets.PacketHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockRecordPlayer extends BlockContainer{

	public BlockRecordPlayer(){
		super(Material.wood);
		setBlockBounds(.025F, 0F, .025F, .975F, .975F, .975F);
	}

	public void onBlockAdded(World world, int x, int y, int z){
		super.onBlockAdded(world, x, y, z);
		world.markBlockForUpdate(x, y, z);
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float what, float these, float are){
		if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IRecordWireManipulator) return false;
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(tileEntity == null || !(tileEntity instanceof TileEntityRecordPlayer)) return false;
		TileEntityRecordPlayer tileEntityRecordPlayer = (TileEntityRecordPlayer) tileEntity;
		if(player.isSneaking()){
			if(world.getBlock(x, y + 1, z) == Blocks.air){
				tileEntityRecordPlayer.opening = !tileEntityRecordPlayer.opening;
				world.markBlockForUpdate(x, y, z);
				if(tileEntityRecordPlayer.opening) world.playSoundEffect(x, (double) y + 0.5D, z, "random.chestopen", 0.5F, world.rand.nextFloat() * 0.2F + 3F);
				else world.playSoundEffect(x, (double) y + 0.5D, z, "random.chestclosed", 0.5F, world.rand.nextFloat() * 0.2F + 3F);
			}
		}else if(tileEntityRecordPlayer.opening){
			if(tileEntityRecordPlayer.record != null){
				if(!world.isRemote) dropItem(world, x, y, z);
				tileEntityRecordPlayer.setRecord(null);
				world.markBlockForUpdate(x, y, z);
			}else if(player.getHeldItem() != null && (player.getHeldItem().getItem() == Items.diamond || (player.getHeldItem().getItem() instanceof IRecord && ((IRecord) player.getHeldItem().getItem()).isRecordValid(player.getHeldItem())))){
				if(player.getHeldItem().getItem() == Items.diamond){
					ItemStack itemStack = new ItemStack(BetterRecords.itemURLRecord);
					itemStack.stackTagCompound = new NBTTagCompound();
					itemStack.stackTagCompound.setString("name", "easteregg.ogg");
					itemStack.stackTagCompound.setString("url", "http://files.enjin.com/788858/SBear'sMods/Songs/easteregg.ogg");
					itemStack.stackTagCompound.setString("local", "Darude - Sandstorm");
					itemStack.stackTagCompound.setInteger("color", 0x53EAD7);
					tileEntityRecordPlayer.setRecord(itemStack);
					world.markBlockForUpdate(x, y, z);
					player.getHeldItem().stackSize--;
				}else{
					tileEntityRecordPlayer.setRecord(player.getHeldItem());
					world.markBlockForUpdate(x, y, z);
					player.getHeldItem().stackSize--;
					if(!world.isRemote) ((IRecord) player.getHeldItem().getItem()).onRecordInserted(tileEntityRecordPlayer, player.getHeldItem());
				}
			}
		}
		return true;
	}

	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityLiving, ItemStack itemStack){
		int rotation = MathHelper.floor_double((double) ((entityLiving.rotationYaw * 4.0f) / 360F) + 2.5D) & 3;
		world.setBlockMetadataWithNotify(i, j, k, rotation, 2);
		if(world.isRemote && !ClientProxy.tutorials.get("recordplayer")){
			BetterEventHandler.tutorialText = BetterUtils.getTranslatedString("tutorial.recordplayer");
			BetterEventHandler.tutorialTime = System.currentTimeMillis() + 10000;
			ClientProxy.tutorials.put("recordplayer", true);
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
		if(tileEntity == null || !(tileEntity instanceof TileEntityRecordPlayer)) return;
		TileEntityRecordPlayer tileEntityRecordPlayer = (TileEntityRecordPlayer) tileEntity;
		ItemStack item = tileEntityRecordPlayer.record;
		if(item != null){
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
			tileEntityRecordPlayer.record = null;
			PacketHandler.sendSoundStopToAllFromServer(tileEntityRecordPlayer.xCoord, tileEntityRecordPlayer.yCoord, tileEntityRecordPlayer.zCoord, world.provider.dimensionId);
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

	public boolean hasComparatorInputOverride(){
		return true;
	}

	public int getComparatorInputOverride(World p_149736_1_, int p_149736_2_, int p_149736_3_, int p_149736_4_, int p_149736_5_){
		return 5;
	}

	public TileEntity createNewTileEntity(World var1, int var2){
		return new TileEntityRecordPlayer();
	}
}