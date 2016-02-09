package com.codingforcookies.betterrecords.src.items;

import com.codingforcookies.betterrecords.src.BetterRecords;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityRecordEtcher extends TileEntity implements IInventory {
	public ItemStack record = null;
	public EntityItem recordEntity;
	
	public float recordRotation = 0F;
	
	public float needleLocation = 0F;
	public boolean needleOut = true;
	
	public TileEntityRecordEtcher() { }

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
		recordRotation = 0F;
	}
	
	@SideOnly(Side.SERVER)
	public boolean canUpdate() {
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public void updateEntity() {
		super.updateEntity();
		
		if(record != null) {
			recordRotation += 0.08F;
			if(needleOut)
				if(needleLocation < .3F)
					needleLocation += 0.001F;
				else
					needleOut = false;
			else
				if(needleLocation > 0F)
					needleLocation -= 0.001F;
				else
					needleOut = true;
		}else
			if(needleLocation > 0F)
				needleLocation -= 0.005F;
			else
				needleLocation = 0F;
	}

	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		if(compound.hasKey("rotation"))
			blockMetadata = compound.getInteger("rotation");
		if(compound.hasKey("record"))
			setRecord(ItemStack.loadItemStackFromNBT(compound.getCompoundTag("record")));
	}
	
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		compound.setInteger("rotation", blockMetadata);
		compound.setTag("record", getStackTagCompound(record));
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
		return record;
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
		return "Record Etcher";
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
		return itemStack.getItem() == BetterRecords.itemURLRecord && (!itemStack.hasTagCompound() || !itemStack.stackTagCompound.hasKey("url"));
	}
}