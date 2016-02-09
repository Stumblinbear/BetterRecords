package com.codingforcookies.betterrecords.items;

import com.codingforcookies.betterrecords.BetterUtils;
import com.codingforcookies.betterrecords.betterenums.ConnectionHelper;
import com.codingforcookies.betterrecords.betterenums.IRecordWire;
import com.codingforcookies.betterrecords.client.BetterEventHandler;
import com.codingforcookies.betterrecords.client.ClientProxy;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLazer extends BlockContainer{

	public BlockLazer(){
		super(Material.iron);
		setBlockBounds(0.25F, 0F, 0.25F, 0.75F, 0.75F, 0.74F);
	}

	public int getLightValue(IBlockAccess world, int x, int y, int z){
		TileEntity te = world.getTileEntity(x, y, z);
		if(te == null || !(te instanceof IRecordWire)) return 0;
		BetterUtils.markBlockDirty(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord);
		return(((IRecordWire) te).getConnections().size() > 0 ? 5 : 0);
	}

	public void onBlockAdded(World world, int x, int y, int z){
		super.onBlockAdded(world, x, y, z);
		world.markBlockForUpdate(x, y, z);
	}

	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack){
		TileEntity te = world.getTileEntity(x, y, z);
		if(te != null && te instanceof TileEntityLazer){
			((TileEntityLazer) te).pitch = entityLiving.rotationPitch;
			((TileEntityLazer) te).yaw = entityLiving.rotationYaw;
		}
		if(world.isRemote && !ClientProxy.tutorials.get("lazer")){
			BetterEventHandler.tutorialText = BetterUtils.getTranslatedString("tutorial.lazer");
			BetterEventHandler.tutorialTime = System.currentTimeMillis() + 10000;
			ClientProxy.tutorials.put("lazer", true);
		}
	}

	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest){
		if(world.isRemote) return super.removedByPlayer(world, player, x, y, z, willHarvest);
		TileEntity te = world.getTileEntity(x, y, z);
		if(te != null && te instanceof IRecordWire) ConnectionHelper.clearConnections(world, (IRecordWire) te);
		return super.removedByPlayer(world, player, x, y, z, willHarvest);
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float what, float these, float are){
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(tileEntity == null || !(tileEntity instanceof TileEntityLazer)) return false;
		TileEntityLazer tileEntityLazer = (TileEntityLazer) tileEntity;
		float length = tileEntityLazer.length;
		if(player.isSneaking()){
			if(tileEntityLazer.length > 0){
				tileEntityLazer.length--;
			}
		}else{
			if(tileEntityLazer.length < 25){
				tileEntityLazer.length++;
			}
		}
		if(tileEntityLazer.length != length && !world.isRemote){
			player.addChatMessage(new ChatComponentTranslation("msg.lazerlength." + (tileEntityLazer.length > length ? "increase" : "decrease")).appendText(" " + tileEntityLazer.length));
		}
		return true;
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
		return new TileEntityLazer();
	}
}