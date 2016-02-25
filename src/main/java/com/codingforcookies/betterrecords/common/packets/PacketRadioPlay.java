package com.codingforcookies.betterrecords.common.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

import com.codingforcookies.betterrecords.client.sound.SoundHandler;

import net.minecraftforge.fml.common.network.ByteBufUtils;

public class PacketRadioPlay implements IPacket {
    int x, y, z, dimension;
    float playRadius;
    String localName, url;

    public PacketRadioPlay() { }

    public PacketRadioPlay(int x, int y, int z, float playRadius, int dimension, String localName, String url) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.playRadius = playRadius;
        this.dimension = dimension;
        this.localName = localName;
        this.url = url;
    }

    public void readBytes(ByteBuf bytes) {
        String recieved = ByteBufUtils.readUTF8String(bytes);
        String[] str = recieved.split("\2477");
        x = Integer.parseInt(str[0]);
        y = Integer.parseInt(str[1]);
        z = Integer.parseInt(str[2]);
        playRadius = Float.parseFloat(str[3]);
        dimension = Integer.parseInt(str[4]);
        localName = str[5];
        url = str[6];
    }

    public void writeBytes(ByteBuf bytes) {
        ByteBufUtils.writeUTF8String(bytes, x + "\2477" + y + "\2477" + z + "\2477" + playRadius + "\2477" + dimension + "\2477" + localName + "\2477" + url);
    }

    public void executeClient(EntityPlayer player) {
        if(playRadius > 100000 || (float)Math.abs(Math.sqrt(Math.pow(player.posX - x, 2) + Math.pow(player.posY - y, 2) + Math.pow(player.posZ - z, 2))) < playRadius)
            SoundHandler.playSoundFromStream(x, y, z, dimension, playRadius, localName, url);
    }

    public void executeServer(EntityPlayer player) { }
}
