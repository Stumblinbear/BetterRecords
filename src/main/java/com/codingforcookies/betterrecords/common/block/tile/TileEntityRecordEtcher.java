package com.codingforcookies.betterrecords.common.block.tile;

import com.codingforcookies.betterrecords.common.BetterRecords;

import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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

public class TileEntityRecordEtcher extends TileEntity implements IInventory, ITickable {
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
        recordEntity = new EntityItem(worldObj, pos.getX(), pos.getY(), pos.getZ(), record);
        recordEntity.hoverStart = 0;
        recordRotation = 0F;
    }

    @SideOnly(Side.SERVER)
    public boolean canUpdate() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void tick() {
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

        //if(compound.hasKey("rotation"))
        //    blockMetadata = compound.getInteger("rotation");
        if(compound.hasKey("record"))
            setRecord(ItemStack.loadItemStackFromNBT(compound.getCompoundTag("record")));
    }

    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setInteger("rotation", getBlockMetadata());
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
        return new S35PacketUpdateTileEntity(pos, 1, nbt);
    }

    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)  {
        readFromNBT(pkt.getNbtCompound());
        Minecraft.getMinecraft().renderGlobal.markBlockForUpdate(pos);
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

    @Override
    public ItemStack removeStackFromSlot(int slot) {
        return getStackInSlot(slot);
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

    @Override
    public IChatComponent getDisplayName() {
        return new ChatComponentText("Record Etcher");
    }

    @Override
    public String getName() {
        return "Record Etcher";
    }

    @Override
    public boolean hasCustomName() {
        return true;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return worldObj.getTileEntity(pos) == this && player.getDistanceSq(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) < 64;
    }

    @Override
    public void openInventory(EntityPlayer player) { }

    @Override
    public void closeInventory(EntityPlayer player) { }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
        return itemStack.getItem() == BetterRecords.itemURLRecord && (!itemStack.hasTagCompound() || !itemStack.getTagCompound().hasKey("url"));
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {
    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {

    }
}