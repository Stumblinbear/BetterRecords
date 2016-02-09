package com.codingforcookies.betterrecords.src.items;

import com.codingforcookies.betterrecords.src.BetterUtils;
import com.codingforcookies.betterrecords.src.betterenums.ConnectionHelper;
import com.codingforcookies.betterrecords.src.betterenums.IRecordWire;
import com.codingforcookies.betterrecords.src.client.BetterEventHandler;
import com.codingforcookies.betterrecords.src.client.ClientProxy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRecordSpeaker extends BlockContainer{

	public static String[] speakers = new String[]{"sm", "md", "lg"};
	public int meta = 0;

	public BlockRecordSpeaker(int meta){
		super(Material.wood);
		this.meta = meta;
	}

	public void onBlockAdded(World world, int x, int y, int z){
		super.onBlockAdded(world, x, y, z);
		world.markBlockForUpdate(x, y, z);
	}

	public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, int x, int y, int z){
		switch (meta){
			case 0:
				setBlockBounds(0.26F, 0.05F, 0.25F, 0.75F, 0.65F, 0.74F);
				break;
			case 1:
				setBlockBounds(0.2F, 0.0F, 0.2F, 0.8F, 0.88F, 0.8F);
				break;
			case 2:
				setBlockBounds(0.12F, 0.0F, 0.12F, 0.88F, 1.51F, 0.88F);
				break;
			default:
				break;
		}
	}

	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack){
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(tileEntity == null || !(tileEntity instanceof TileEntityRecordSpeaker)) return;
		((TileEntityRecordSpeaker) tileEntity).rotation = entityLiving.rotationYaw;
		((TileEntityRecordSpeaker) tileEntity).type = meta;
		if(world.isRemote && !ClientProxy.tutorials.get("speaker")) {
			BetterEventHandler.tutorialText = BetterUtils.getTranslatedString("tutorial.speaker");
			BetterEventHandler.tutorialTime = System.currentTimeMillis() + 10000;
			ClientProxy.tutorials.put("speaker", true);
		}
	}

	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest){
		if(world.isRemote) return super.removedByPlayer(world, player, x, y, z, willHarvest);
		TileEntity te = world.getTileEntity(x, y, z);
		if(te != null && te instanceof IRecordWire) ConnectionHelper.clearConnections(world, (IRecordWire) te);
		return super.removedByPlayer(world, player, x, y, z, willHarvest);
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
		return new TileEntityRecordSpeaker();
	}
}