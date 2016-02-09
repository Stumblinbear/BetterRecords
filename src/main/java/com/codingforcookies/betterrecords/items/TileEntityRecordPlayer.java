package com.codingforcookies.betterrecords.items;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import com.codingforcookies.betterrecords.betterenums.ConnectionHelper;
import com.codingforcookies.betterrecords.betterenums.IRecordWire;
import com.codingforcookies.betterrecords.betterenums.IRecordWireHome;
import com.codingforcookies.betterrecords.betterenums.RecordConnection;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityRecordPlayer extends TileEntity implements IRecordWire, IRecordWireHome {
	public ArrayList<Float> formTreble = new ArrayList<Float>();
	public synchronized void addTreble(float form) { formTreble.add(form); }
	public ArrayList<Float> formBass = new ArrayList<Float>();
	public synchronized void addBass(float form) { formBass.add(form); }
	
	public ArrayList<RecordConnection> connections = null;
	public ArrayList<RecordConnection> getConnections() { return connections; }
	
	public HashMap<String, Integer> wireSystemInfo;
	private float playRadius = 0F;
	
	public void increaseAmount(IRecordWire wireComponent) {
		if(wireSystemInfo.containsKey(wireComponent.getName())) {
			wireSystemInfo.put(wireComponent.getName(), wireSystemInfo.get(wireComponent.getName()) + 1);
		}else
			wireSystemInfo.put(wireComponent.getName(), 1);
		playRadius += wireComponent.getSongRadiusIncrease();
	}
	
	public void decreaseAmount(IRecordWire wireComponent) {
		if(wireSystemInfo.containsKey(wireComponent.getName())) {
			wireSystemInfo.put(wireComponent.getName(), wireSystemInfo.get(wireComponent.getName()) - 1);
			if(wireSystemInfo.get(wireComponent.getName()) <= 0)
				wireSystemInfo.remove(wireComponent.getName());
			playRadius -= wireComponent.getSongRadiusIncrease();
		}
	}
	
	public float getSongRadius() {
		return getSongRadiusIncrease() + playRadius;
	}

	public TileEntity getTileEntity() { return this; }
	public ItemStack getItemStack() { return record; }
	
	public String getName() { return "Record Player"; }
	public float getSongRadiusIncrease() { return 40F; }
	
	public ItemStack record = null;
	public EntityItem recordEntity;
	
	public boolean opening = false;
	public float openAmount = 0F;

	public float needleLocation = 0F;
	public float recordRotation = 0F;
	
	public TileEntityRecordPlayer() {
		connections = new ArrayList<RecordConnection>();
		wireSystemInfo = new HashMap<String, Integer>();
	}
	
	public void setRecord(ItemStack par1ItemStack) {
		if(par1ItemStack == null) {
			record = null;
			recordEntity = null;
			recordRotation = 0F;
			return;
		}

		record = par1ItemStack.copy();
		record.stackSize = 1;
		recordEntity = new EntityItem(worldObj, xCoord, yCoord, zCoord, record);
		recordEntity.hoverStart = 0;
		recordEntity.rotationPitch = 0F;
		recordEntity.rotationYaw = 0F;
		recordRotation = 0F;
	}
	
	@SideOnly(Side.SERVER)
	public boolean canUpdate() {
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public void updateEntity() {
		super.updateEntity();
		
		if(opening) {
			if(openAmount > -0.8F)
				openAmount -= 0.08F;
		}else
			if(openAmount < 0F)
				openAmount += 0.12F;

		if(openAmount < -0.8F)
			openAmount = -0.8F;
		else if(openAmount > 0F)
			openAmount = 0F;

		if(record != null) {
			recordRotation += 0.08F;
			if(needleLocation < .3F)
				needleLocation += 0.01F;
			else
				needleLocation = .3F;
		}else
			if(needleLocation > 0F)
				needleLocation -= 0.01F;
			else
				needleLocation = 0F;
		
		if(worldObj.isRemote) {
			while(formTreble.size() > 2500)
				for(int i = 0; i < 25; i++) {
					formTreble.remove(0);
					formBass.remove(0);
				}
		}
	}

	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		if(compound.hasKey("rotation"))
			blockMetadata = compound.getInteger("rotation");
		if(compound.hasKey("record"))
			setRecord(ItemStack.loadItemStackFromNBT(compound.getCompoundTag("record")));
		if(compound.hasKey("opening"))
			opening = compound.getBoolean("opening");
		if(compound.hasKey("connections"))
			connections = ConnectionHelper.unserializeConnections(compound.getString("connections"));
		if(compound.hasKey("wireSystemInfo"))
			wireSystemInfo = ConnectionHelper.unserializeWireSystemInfo(compound.getString("wireSystemInfo"));
		if(compound.hasKey("playRadius"))
			playRadius = compound.getFloat("playRadius");
	}
	
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		compound.setFloat("rotation", blockMetadata);
		compound.setTag("record", getStackTagCompound(record));
		compound.setBoolean("opening", opening);
		compound.setString("connections", ConnectionHelper.serializeConnections(connections));
		compound.setString("wireSystemInfo", ConnectionHelper.serializeWireSystemInfo(wireSystemInfo));
		compound.setFloat("playRadius", playRadius);
	}

	public NBTTagCompound getStackTagCompound(ItemStack stack) {
		NBTTagCompound tag = new NBTTagCompound();
		if(stack != null)
			stack.writeToNBT(tag);
		return tag;
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