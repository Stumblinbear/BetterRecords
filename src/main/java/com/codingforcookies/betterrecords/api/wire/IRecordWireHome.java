package com.codingforcookies.betterrecords.api.wire;

import java.util.ArrayList;

import com.codingforcookies.betterrecords.api.connection.RecordConnection;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public interface IRecordWireHome {
	public void addTreble(float form);
	public void addBass(float form);
	
	public ArrayList<RecordConnection> getConnections();
	public void increaseAmount(IRecordWire wireComponent);
	public void decreaseAmount(IRecordWire wireComponent);
	public float getSongRadius();
	public TileEntity getTileEntity();
	public ItemStack getItemStack();
}