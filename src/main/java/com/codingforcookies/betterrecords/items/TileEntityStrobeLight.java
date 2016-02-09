package com.codingforcookies.betterrecords.src.items;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import com.codingforcookies.betterrecords.src.betterenums.ConnectionHelper;
import com.codingforcookies.betterrecords.src.betterenums.IRecordAmplitude;
import com.codingforcookies.betterrecords.src.betterenums.IRecordWire;
import com.codingforcookies.betterrecords.src.betterenums.RecordConnection;

public class TileEntityStrobeLight extends TileEntity implements IRecordWire, IRecordAmplitude {
	public ArrayList<RecordConnection> connections = null;
	public ArrayList<RecordConnection> getConnections() { return connections; }
	
	public float bass = 0;
	
	public void setTreble(float amplitude) { }
	public void setTreble(float amplitude, float r, float g, float b) { }
	public float getTreble() { return 0; }
	
	public void setBass(float amplitude) { this.bass = (amplitude < 8F ? 0F : amplitude); }
	public void setBass(float amplitude, float r, float g, float b) { this.bass = (amplitude < 5F ? 0F : amplitude); }
	public float getBass() { return bass; }
	
	public String getName() { return "Strobe Light"; }
	public float getSongRadiusIncrease() { return 0F; }
	
	public TileEntityStrobeLight() {
		connections = new ArrayList<RecordConnection>();
	}
	
	public void updateEntity() {
		if(bass > 0F)
			bass--;
		if(bass < 0F)
			bass = 0F;
	}
	
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		if(compound.hasKey("connections"))
			connections = ConnectionHelper.unserializeConnections(compound.getString("connections"));
	}

	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		compound.setString("connections", ConnectionHelper.serializeConnections(connections));
	}
	
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
        writeToNBT(nbt);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbt);
	}
	
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)  { 
		readFromNBT(pkt.func_148857_g());
	}
}