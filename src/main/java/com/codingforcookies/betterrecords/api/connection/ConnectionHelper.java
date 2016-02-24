package com.codingforcookies.betterrecords.api.connection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import com.codingforcookies.betterrecords.api.wire.IRecordWire;
import com.codingforcookies.betterrecords.api.wire.IRecordWireHome;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.codingforcookies.betterrecords.common.BetterRecords;

public class ConnectionHelper {
	public static String serializeConnections(ArrayList<RecordConnection> rec) {
		if(rec.size() == 0)
			return "";
		
		String ret = "";
		
		for(RecordConnection recc : rec)
			ret += recc.toString() + "]";
		
		return ret.substring(0, ret.length() - 1);
	}
	
	public static ArrayList<RecordConnection> unserializeConnections(String rec) {
		if(rec.trim().equals(""))
			return new ArrayList<RecordConnection>();
		
		String[] recc = rec.split("]");
		ArrayList<RecordConnection> ret = new ArrayList<RecordConnection>();
		
		for(String str : recc)
			ret.add(new RecordConnection(str));
		
		return ret;
	}
	
	public static String serializeWireSystemInfo(HashMap<String, Integer> wireSystemInfo) {
		if(wireSystemInfo.size() == 0)
			return "";
		
		String ret = "";
		
		for(Entry<String, Integer> entry : wireSystemInfo.entrySet())
			ret += entry.getKey() + "," + entry.getValue() + "]";
		
		return ret.substring(0, ret.length() - 1);
	}
	
	public static HashMap<String, Integer> unserializeWireSystemInfo(String wireSystemInfo) {
		if(wireSystemInfo.trim().equals(""))
			return new HashMap<String, Integer>();
					
		
		String[] wsi = wireSystemInfo.split("]");
		HashMap<String, Integer> ret = new HashMap<String, Integer>();
		
		for(String str : wsi) {
			String[] split = str.split(",");
			ret.put(split[0], Integer.parseInt(split[1]));
		}
		
		return ret;
	}
	
	public static void addConnection(World world, IRecordWire iRecordWire, RecordConnection rec) {
		for(int i = 0; i < iRecordWire.getConnections().size(); i++)
			if(iRecordWire.getConnections().get(i).same(rec))
				return;

		iRecordWire.getConnections().add(rec);
		world.markBlockForUpdate(new BlockPos(((TileEntity)iRecordWire).getPos().getX(), ((TileEntity)iRecordWire).getPos().getY(), ((TileEntity)iRecordWire).getPos().getZ()));

		TileEntity te = world.getTileEntity(new BlockPos(rec.x1, rec.y1, rec.z1));
		if(te != null && te instanceof IRecordWireHome && te != iRecordWire) {
			((IRecordWireHome)te).increaseAmount(iRecordWire);
			world.markBlockForUpdate(new BlockPos(te.getPos().getX(), te.getPos().getY(), te.getPos().getZ()));
		}
	}
	
	public static void removeConnection(World world, IRecordWire iRecordWire, RecordConnection rec) {
		for(int i = 0; i < iRecordWire.getConnections().size(); i++)
			if(iRecordWire.getConnections().get(i).same(rec)) {
				TileEntity te = world.getTileEntity(new BlockPos(iRecordWire.getConnections().get(i).x1, iRecordWire.getConnections().get(i).y1, iRecordWire.getConnections().get(i).z1));
				
				if(te != null && te instanceof IRecordWireHome && te != iRecordWire) {
					((IRecordWireHome)te).decreaseAmount(iRecordWire);
					world.markBlockForUpdate(new BlockPos(te.getPos().getX(), te.getPos().getY(), te.getPos().getZ()));
					
					Random rand = new Random();
					
					float rx = rand.nextFloat() * 0.8F + 0.1F;
					float ry = rand.nextFloat() * 0.8F + 0.1F;
					float rz = rand.nextFloat() * 0.8F + 0.1F;
					
					EntityItem entityItem = new EntityItem(world, te.getPos().getX() + rx, te.getPos().getY() + ry, te.getPos().getZ() + rz, new ItemStack(BetterRecords.itemRecordWire));
					
					entityItem.motionX = rand.nextGaussian() * 0.05F;
					entityItem.motionY = rand.nextGaussian() * 0.05F + 0.2F;
					entityItem.motionZ = rand.nextGaussian() * 0.05F;
					world.spawnEntityInWorld(entityItem);
					
					removeConnection(world, (IRecordWire)te, rec);
				}
				
				iRecordWire.getConnections().remove(i);
				break;
			}
	}
	
	public static void clearConnections(World world, IRecordWire iRecordWire) {
		while(iRecordWire.getConnections().size() != 0) {
			RecordConnection rec = iRecordWire.getConnections().get(0);
			TileEntity te = world.getTileEntity(new BlockPos(rec.x1, rec.y1, rec.z1));
			if(te != null && te instanceof IRecordWire) {
				removeConnection(world, iRecordWire, rec);
			}else{
				System.err.println("Warning on clearing connections: Attached block is not a member of IRecordWire! This may cause ghost connections until a relog!");
				iRecordWire.getConnections().remove(0);
			}
		}
		world.markBlockForUpdate(new BlockPos(((TileEntity)iRecordWire).getPos().getX(), ((TileEntity)iRecordWire).getPos().getY(), ((TileEntity)iRecordWire).getPos().getZ()));
	}
}