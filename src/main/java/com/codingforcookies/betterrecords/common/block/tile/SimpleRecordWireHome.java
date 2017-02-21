package com.codingforcookies.betterrecords.common.block.tile;

import com.codingforcookies.betterrecords.api.connection.RecordConnection;
import com.codingforcookies.betterrecords.api.wire.IRecordWire;
import com.codingforcookies.betterrecords.api.wire.IRecordWireHome;
import com.codingforcookies.betterrecords.common.core.handler.ConfigHandler;
import net.minecraft.util.ITickable;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class SimpleRecordWireHome extends BetterTile implements IRecordWireHome, ITickable {

    public ArrayList<Float> formTreble = new ArrayList<Float>();
    public ArrayList<Float> formBass = new ArrayList<Float>();

    @Override
    public synchronized void addTreble(float form) {
        formTreble.add(form);
    }

    @Override
    public synchronized void addBass(float form) {
        formBass.add(form);
    }

    ArrayList<RecordConnection> connections = new ArrayList<RecordConnection>();

    @Override
    public ArrayList<RecordConnection> getConnections() {
        return connections;
    }

    public HashMap<String, Integer> wireSystemInfo = new HashMap<String, Integer>();

    float playRadius = 0F;

    @Override
    public void increaseAmount(IRecordWire wireComponent) {
        if (wireSystemInfo.containsKey(wireComponent.getName())) {
            wireSystemInfo.put(wireComponent.getName(), wireSystemInfo.get(wireComponent.getName()) + 1);
        } else {
            wireSystemInfo.put(wireComponent.getName(), 1);
        }
        playRadius += wireComponent.getSongRadiusIncrease();
    }

    @Override
    public void decreaseAmount(IRecordWire wireComponent) {
        if(wireSystemInfo.containsKey(wireComponent.getName())) {
            wireSystemInfo.put(wireComponent.getName(), wireSystemInfo.get(wireComponent.getName()) - 1);
            if(wireSystemInfo.get(wireComponent.getName()) <= 0) {
                wireSystemInfo.remove(wireComponent.getName());
            }
            playRadius -= wireComponent.getSongRadiusIncrease();
        }
    }

    @Override
    public void update() {
        if(world.isRemote) {
            while (formTreble.size() > 2500)
                for (int i = 0; i < 25; i++) {
                    formTreble.remove(0);
                    formBass.remove(0);
                }
        }
    }

    abstract float getSongRadiusIncrease();

    @Override
    public float getSongRadius() {
        float radius = getSongRadiusIncrease() + playRadius;
        int maxRadius = ConfigHandler.maxSpeakerRadius;
        return (radius <= maxRadius || maxRadius == -1) ? radius : maxRadius;
    }

    @Override
    public TileEntity getTileEntity() {
        return this;
    }

    @SideOnly(Side.SERVER)
    public boolean canUpdate() {
        return false;
    }
}
