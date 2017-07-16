package com.codingforcookies.betterrecords.block.tile;

import com.codingforcookies.betterrecords.api.connection.RecordConnection;
import com.codingforcookies.betterrecords.api.wire.IRecordWire;
import com.codingforcookies.betterrecords.common.core.helper.ConnectionHelper;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;

public class TileSpeaker extends BetterTile implements IRecordWire {
    public ArrayList<RecordConnection> connections = null;
    public ArrayList<RecordConnection> getConnections() { return connections; }

    private static String[] prefix = new String[]{ "Small", "Medium", "Large" };
    private static float[] radius = new float[]{ 15F, 35F, 70F };

    public String getName() { return type == -1 ? "Invalid Speaker" : prefix[type] + " Speaker"; }
    public float getSongRadiusIncrease() { return type == -1 ? 0 : radius[type]; }

    public int type = -1;
    public float rotation = 0F;

    public TileSpeaker() {
        connections = new ArrayList<RecordConnection>();
    }

    public boolean canUpdate() {
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        if(compound.hasKey("type"))
            type = compound.getInteger("type");
        if(compound.hasKey("rotation"))
            rotation = compound.getInteger("rotation");
        if(compound.hasKey("connections"))
            connections = ConnectionHelper.unserializeConnections(compound.getString("connections"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);

        compound.setInteger("type", type);
        compound.setFloat("rotation", rotation);
        compound.setString("connections", ConnectionHelper.serializeConnections(connections));

        return compound;
    }
}
