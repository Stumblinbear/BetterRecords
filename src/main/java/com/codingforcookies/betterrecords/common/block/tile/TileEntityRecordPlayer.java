package com.codingforcookies.betterrecords.common.block.tile;

import com.codingforcookies.betterrecords.api.wire.IRecordWire;
import com.codingforcookies.betterrecords.common.core.helper.ConnectionHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityRecordPlayer extends SimpleRecordWireHome implements IRecordWire {

    @Override
    public String getName() {
        return "Record Player";
    }

    @Override
    public ItemStack getItemStack() {
        return record;
    }

    @Override
    public float getSongRadiusIncrease() {
        return 40F;
    }

    public ItemStack record = null;
    public EntityItem recordEntity;

    public boolean opening = false;
    public float openAmount = 0F;

    public float needleLocation = 0F;
    public float recordRotation = 0F;

    public void setRecord(ItemStack itemStack) {
        if(itemStack == null) {
            record = null;
            recordEntity = null;
            recordRotation = 0F;
            return;
        }

        record = itemStack.copy();
        record.stackSize = 1;
        recordEntity = new EntityItem(worldObj, pos.getX(), pos.getY(), pos.getZ(), record);
        recordEntity.hoverStart = 0;
        recordEntity.rotationPitch = 0F;
        recordEntity.rotationYaw = 0F;
        recordRotation = 0F;
    }

    @Override
    public void update() {
        if (opening) {
            if (openAmount > -0.8F) {
                openAmount -= 0.08F;
            }
        } else if (openAmount < 0F) {
            openAmount += 0.12F;
        }

        if (openAmount < -0.8F) {
            openAmount = -0.8F;
        } else if (openAmount > 0F) {
            openAmount = 0F;
        }

        if (record != null) {
            recordRotation += 0.08F;
            if (needleLocation < .3F) {
                needleLocation += 0.01F;
            } else {
                needleLocation = .3F;
            }
        } else if (needleLocation > 0F) {
            needleLocation -= 0.01F;
        } else {
            needleLocation = 0F;
        }

        super.update();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

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

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setFloat("rotation", getBlockMetadata());
        compound.setTag("record", getStackTagCompound(record));
        compound.setBoolean("opening", opening);
        compound.setString("connections", ConnectionHelper.serializeConnections(connections));
        compound.setString("wireSystemInfo", ConnectionHelper.serializeWireSystemInfo(wireSystemInfo));
        compound.setFloat("playRadius", playRadius);

        return compound;
    }

    public NBTTagCompound getStackTagCompound(ItemStack stack) {
        NBTTagCompound tag = new NBTTagCompound();
        if(stack != null)
            stack.writeToNBT(tag);
        return tag;
    }
}
