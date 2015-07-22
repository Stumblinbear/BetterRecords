package com.codingforcookies.betterrecords.src.items;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import com.codingforcookies.betterrecords.src.betterenums.ConnectionHelper;
import com.codingforcookies.betterrecords.src.betterenums.IRecordWire;
import com.codingforcookies.betterrecords.src.betterenums.RecordConnection;

public class TileEntityRecordSpeaker extends TileEntity implements IRecordWire {
	public ArrayList<RecordConnection> connections = null;
	public ArrayList<RecordConnection> getConnections() { return connections; }

	private static String[] prefix = new String[]{ "Small", "Medium", "Large" };
	private static float[] radius = new float[]{ 15F, 35F, 70F };
	
	public String getName() { return type == -1 ? "Invalid Speaker" : prefix[type] + " Speaker"; }
	public float getSongRadiusIncrease() { return type == -1 ? 0 : radius[type]; }
	
	public int type = -1;
	public float rotation = 0F;
	
	public TileEntityRecordSpeaker() {
		connections = new ArrayList<RecordConnection>();
	}
	
	public boolean canUpdate() {
		return false;
	}
	
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		if(compound.hasKey("type"))
			type = compound.getInteger("type");
		if(compound.hasKey("rotation"))
			rotation = compound.getInteger("rotation");
		if(compound.hasKey("connections"))
			connections = ConnectionHelper.unserializeConnections(compound.getString("connections"));
	}

	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		compound.setInteger("type", type);
		compound.setFloat("rotation", rotation);
		compound.setString("connections", ConnectionHelper.serializeConnections(connections));
	}
	
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
        writeToNBT(nbt);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbt);
	}
	
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)  { 
		readFromNBT(pkt.func_148857_g());
		Minecraft.getMinecraft().renderGlobal.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
	} 
}