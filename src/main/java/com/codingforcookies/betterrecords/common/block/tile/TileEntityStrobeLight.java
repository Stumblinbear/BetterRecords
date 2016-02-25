package com.codingforcookies.betterrecords.common.block.tile;

import com.codingforcookies.betterrecords.api.connection.RecordConnection;
import com.codingforcookies.betterrecords.api.record.IRecordAmplitude;
import com.codingforcookies.betterrecords.api.wire.IRecordWire;
import com.codingforcookies.betterrecords.common.core.helper.ConnectionHelper;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;

public class TileEntityStrobeLight extends TileEntity implements IRecordWire, IRecordAmplitude, ITickable {
    public ArrayList<RecordConnection> connections = null;
    public ArrayList<RecordConnection> getConnections() { return connections; }

    public float bass = 0;

    public void setTreble(float amplitude) { }
    public void setTreble(float amplitude, float r, float g, float b) { }
    public float getTreble() { return 0; }

    public void setBass(float amplitude) { this.bass = (amplitude < 8F ? 0F : amplitude); }
    public void setBass(float amplitude, float r, float g, float b) { this.bass = (amplitude < 5F ? 0F : amplitude); }
    public float getBass() { return bass; }

    public String getName() { return "Strobe Light"; }
    public float getSongRadiusIncrease() { return 0F; }

    public TileEntityStrobeLight() {
        connections = new ArrayList<RecordConnection>();
    }

    @Override
    public void tick() {
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
        return new S35PacketUpdateTileEntity(pos, 1, nbt);
    }

    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)  {
        readFromNBT(pkt.getNbtCompound());
    }
}
