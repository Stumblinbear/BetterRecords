package com.codingforcookies.betterrecords.block.tile;

import com.codingforcookies.betterrecords.common.item.ModItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileRecordEtcher extends BetterTile implements IInventory, ITickable {
    public ItemStack record = null;
    public EntityItem recordEntity;

    public float recordRotation = 0F;

    public float needleLocation = 0F;
    public boolean needleOut = true;

    public TileRecordEtcher() { }

    public void setRecord(ItemStack itemStack) {
        if(itemStack == null) {
            record = null;
            recordEntity = null;
            recordRotation = 0F;
            return;
        }

        record = itemStack.copy();
        record.stackSize = 1;
        recordEntity = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), record);
        recordEntity.hoverStart = 0;
        recordRotation = 0F;
    }

    @SideOnly(Side.SERVER)
    public boolean canUpdate() {
        return false;
    }

    @Override
    public void update() {
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

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setInteger("rotation", getBlockMetadata());
        compound.setTag("record", getStackTagCompound(record));

        return compound;
    }

    public NBTTagCompound getStackTagCompound(ItemStack stack) {
        NBTTagCompound tag = new NBTTagCompound();
        if(stack != null)
            stack.writeToNBT(tag);
        return tag;
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
    public ITextComponent getDisplayName() {
        return new TextComponentString("Record Etcher");
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
    public boolean isUsableByPlayer(EntityPlayer player) {
        return world.getTileEntity(pos) == this && player.getDistanceSq(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) < 64;
    }

    @Override
    public void openInventory(EntityPlayer player) { }

    @Override
    public void closeInventory(EntityPlayer player) { }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
        return itemStack.getItem() == ModItems.itemURLRecord && (!itemStack.hasTagCompound() || !itemStack.getTagCompound().hasKey("url"));
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
