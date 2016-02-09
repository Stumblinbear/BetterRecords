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
import com.codingforcookies.betterrecords.betterenums.RecordConnection;
import com.codingforcookies.betterrecords.packets.PacketHandler;

public class ItemRecordWire extends Item implements IRecordWireManipulator {
	public static RecordConnection connection;
	
	public ItemRecordWire() {
		
	}
	
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par3EntityPlayer, World par3World, int x, int y, int z, int side, float px, float py, float pz) {
		if(!par3World.isRemote)
			return true;
		
		TileEntity te = par3World.getTileEntity(x, y, z);
		if(te == null || !(te instanceof IRecordWire))
			return false;
		
		if(connection == null) {
			connection = new RecordConnection(x, y, z, te instanceof IRecordWireHome);
		}else{
			float x1 = -(float)(x - (connection.fromHome ? connection.x1 : connection.x2));
			float y1 = -(float)(y - (connection.fromHome ? connection.y1 : connection.y2));
			float z1 = -(float)(z - (connection.fromHome ? connection.z1 : connection.z2));
			
			if((int)Math.sqrt(Math.pow(x1, 2) + Math.pow(y1, 2) + Math.pow(z1, 2)) > 7 || connection.sameInitial(x, y, z)) {
				connection = null;
				return true;
			}
			
			if(!connection.fromHome)
				connection.setConnection1(x, y, z);
			else
				connection.setConnection2(x, y, z);
			
			TileEntity te1 = par3World.getTileEntity(connection.x1, connection.y1, connection.z1);
			TileEntity te2 = par3World.getTileEntity(connection.x2, connection.y2, connection.z2);
			
			if(te2 instanceof IRecordWire) {
				if(!(te1 instanceof IRecordWireHome && te2 instanceof IRecordWireHome)) {
					ConnectionHelper.addConnection(te.getWorldObj(), (IRecordWire)te1, connection);
					ConnectionHelper.addConnection(te.getWorldObj(), (IRecordWire)te2, connection);
					PacketHandler.sendWireConnectionFromClient(connection);
					par1ItemStack.stackSize--;
				}
			}
			
			connection = null;
		}
		return true;
	}
}