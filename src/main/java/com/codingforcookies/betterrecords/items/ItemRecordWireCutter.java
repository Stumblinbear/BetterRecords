package com.codingforcookies.betterrecords.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.codingforcookies.betterrecords.betterenums.ConnectionHelper;
import com.codingforcookies.betterrecords.betterenums.IRecordWire;
import com.codingforcookies.betterrecords.betterenums.IRecordWireHome;
import com.codingforcookies.betterrecords.betterenums.IRecordWireManipulator;

public class ItemRecordWireCutter extends Item implements IRecordWireManipulator {
	public ItemRecordWireCutter() {
		setMaxStackSize(1);
	}
	
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par3EntityPlayer, World par3World, int x, int y, int z, int side, float px, float py, float pz) {
		TileEntity te = par3World.getTileEntity(x, y, z);
		if(te == null || !(te instanceof IRecordWire) || te instanceof IRecordWireHome)
			return false;
		
		if(par3World.isRemote)
			return true;
		
		ConnectionHelper.clearConnections(te.getWorldObj(), (IRecordWire)te);
		return true;
	}
}