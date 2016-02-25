package com.codingforcookies.betterrecords.common.block.tile;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import com.codingforcookies.betterrecords.common.core.helper.ConnectionHelper;
import com.codingforcookies.betterrecords.api.wire.IRecordWire;
import com.codingforcookies.betterrecords.api.wire.IRecordWireHome;
import com.codingforcookies.betterrecords.api.connection.RecordConnection;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityRadio extends TileEntity implements IRecordWire, IRecordWireHome, ITickable {
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
    public ItemStack getItemStack() { return crystal; }

    public String getName() { return "Radio"; }
    public float getSongRadiusIncrease() { return 30F; }
    public boolean canConnect() { return true; }

    public ItemStack crystal = null;
    public float crystalFloaty = 0F;

    public boolean opening = false;
    public float openAmount = 0F;

    public TileEntityRadio() {
        connections = new ArrayList<RecordConnection>();
        wireSystemInfo = new HashMap<String, Integer>();
    }

    public void setCrystal(ItemStack par1ItemStack) {
        if(par1ItemStack == null) {
            crystal = null;
            return;
        }

        crystal = par1ItemStack.copy();
        crystal.stackSize = 1;
    }

    @SideOnly(Side.SERVER)
    public boolean canUpdate() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void tick() {
        if(opening) {
            if(openAmount < 0.268F)
                openAmount += 0.04F;
        }else
            if(openAmount > 0F)
                openAmount -= 0.04F;

        if(openAmount > 0.268F)
            openAmount = 0.268F;
        else if(openAmount < 0F)
            openAmount = 0F;

        if(crystal != null)
            crystalFloaty += 0.86F;

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

        //if(compound.hasKey("rotation"))
        //    blockMetadata = compound.getInteger("rotation");
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

    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setFloat("rotation", getBlockMetadata());
        compound.setTag("crystal", getStackTagCompound(crystal));
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
        return new S35PacketUpdateTileEntity(pos, 1, nbt);
    }

    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)  {
        readFromNBT(pkt.getNbtCompound());
        Minecraft.getMinecraft().renderGlobal.markBlockForUpdate(pos);
    }
}