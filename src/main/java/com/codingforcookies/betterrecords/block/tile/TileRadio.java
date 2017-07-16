package com.codingforcookies.betterrecords.block.tile;

import com.codingforcookies.betterrecords.api.wire.IRecordWire;
import com.codingforcookies.betterrecords.common.core.helper.ConnectionHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileRadio extends SimpleRecordWireHome implements IRecordWire {

    @Override
    public String getName() {
        return "Radio";
    }

    @Override
    public ItemStack getItemStack() {
        return crystal;
    }

    @Override
    public float getSongRadiusIncrease() {
        return 30F;
    }

    public ItemStack crystal = null;
    public float crystalFloaty = 0F;

    public boolean opening = false;
    public float openAmount = 0F;

    public void setCrystal(ItemStack itemStack) {
        if(itemStack == null) {
            crystal = null;
            return;
        }

        crystal = itemStack.copy();
        crystal.stackSize = 1;
    }

    @Override
    public void update() {
        if (opening) {
            if (openAmount < 0.268F) {
                openAmount += 0.04F;
            }
        } else if (openAmount > 0F) {
            openAmount -= 0.04F;
        }

        if (openAmount > 0.268F) {
            openAmount = 0.268F;
        } else if (openAmount < 0F) {
            openAmount = 0F;
        }

        if (crystal != null) {
            crystalFloaty += 0.86F;
        }

        super.update();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        if(compound.hasKey("crystal"))
            setCrystal(ItemStack.loadItemStackFromNBT(compound.getCompoundTag("crystal")));
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
        compound.setTag("crystal", getStackTagCompound(crystal));
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
