package com.codingforcookies.betterrecords.src.items;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import com.codingforcookies.betterrecords.src.BetterRecords;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityFrequencyTuner extends TileEntity implements IInventory {
	public ItemStack crystal = null;
	public float crystalFloaty = 0F;
	
	public TileEntityFrequencyTuner() { }

	public void setRecord(ItemStack par1ItemStack) {
		if(par1ItemStack != null)
			crystal = par1ItemStack.copy();
		else
			crystal = null;
	}
	
	@SideOnly(Side.SERVER)
	public boolean canUpdate() {
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public void updateEntity() {
		super.updateEntity();
		
		if(crystal != null)
			crystalFloaty += 0.86F;
	}

	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		if(compound.hasKey("rotation"))
			blockMetadata = compound.getInteger("rotation");
		if(compound.hasKey("crystal"))
			setRecord(ItemStack.loadItemStackFromNBT(compound.getCompoundTag("crystal")));
	}
	
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		compound.setInteger("rotation", blockMetadata);
		compound.setTag("crystal", getStackTagCompound(crystal));
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
	
	public int getSizeInventory() {
		return 1;
	}
	
	public ItemStack getStackInSlot(int slot) {
		return crystal;
	}
	
	public ItemStack decrStackSize(int slot, int amt) {
		ItemStack stack = getStackInSlot(slot);
		if(stack != null)
			if(stack.stackSize <= amt)
				setInventorySlotContents(slot, null);
			else
				stack = stack.splitStack(amt);
				if(stack.stackSize == 0)
					setInventorySlotContents(slot, null);
		return stack;
	}
	
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack stack = getStackInSlot(slot);
		if(stack != null)
			setInventorySlotContents(slot, null);
		return stack;
	}
	
	public void setInventorySlotContents(int slot, ItemStack itemStack) {
		setRecord(itemStack);
	}
	
	public String getInventoryName() {
		return "Frequency Tuner";
	}
	
	public boolean hasCustomInventoryName() {
		return true;
	}
	
	public int getInventoryStackLimit() {
		return 1;
	}
	
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}
	
	public void openInventory() { }
	
	public void closeInventory() { }
	
	public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
		return itemStack.getItem() == BetterRecords.itemFreqCrystal && (!itemStack.hasTagCompound() || !itemStack.stackTagCompound.hasKey("url"));
	}
}