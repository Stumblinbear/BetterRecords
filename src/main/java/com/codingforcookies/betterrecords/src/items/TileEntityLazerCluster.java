package com.codingforcookies.betterrecords.src.items;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import com.codingforcookies.betterrecords.src.betterenums.ConnectionHelper;
import com.codingforcookies.betterrecords.src.betterenums.IRecordAmplitude;
import com.codingforcookies.betterrecords.src.betterenums.IRecordWire;
import com.codingforcookies.betterrecords.src.betterenums.RecordConnection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityLazerCluster extends TileEntity implements IRecordWire, IRecordAmplitude {
	public ArrayList<RecordConnection> connections = null;
	public ArrayList<RecordConnection> getConnections() { return connections; }
	
	public float bass = 0;
	public float r, g, b;
	
	public void setTreble(float amplitude) { }
	public void setTreble(float amplitude, float r, float g, float b) { }
	public float getTreble() { return 0; }
	
	public void setBass(float amplitude) {
		this.bass = amplitude;
		
		r = new Random((long)amplitude + System.nanoTime()).nextFloat();
		g = new Random((long)amplitude + System.nanoTime()).nextFloat();
		b = new Random((long)amplitude + System.nanoTime()).nextFloat();

		int colorNum = new Random().nextInt(2);
		r += colorNum == 0 ? .3F : -.1F;
		g += colorNum == 1 ? .3F : -.1F;
		b += colorNum == 2 ? .3F : -.1F;

		if(r < .2F)
			r += r;
		if(g < .2F)
			g += g;
		if(b < .2F)
			b += b;
	}
	public void setBass(float amplitude, float r, float g, float b) {
		this.bass = amplitude;
		this.r = r;
		this.g = g;
		this.b = b;
	}
	public float getBass() { return bass; }
	
	public String getName() { return "Lazer Cluster"; }
	public float getSongRadiusIncrease() { return 0F; }
	
	public TileEntityLazerCluster() {
		connections = new ArrayList<RecordConnection>();
	}
	
	@SideOnly(Side.SERVER)
	public boolean canUpdate() {
		return false;
	}
	
	public void updateEntity() {
		super.updateEntity();
		
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