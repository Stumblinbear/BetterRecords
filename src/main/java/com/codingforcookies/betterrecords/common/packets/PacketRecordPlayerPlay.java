package com.codingforcookies.betterrecords.common.packets;

import java.util.ArrayList;
import java.util.List;

import com.codingforcookies.betterrecords.client.sound.Sound;
import com.codingforcookies.betterrecords.client.sound.SoundHandler;

import net.minecraftforge.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class PacketRecordPlayerPlay implements IPacket {
    int x, y, z, dimension;
    float playRadius;
    boolean repeat;
    boolean shuffle;

    List<Sound> sounds = null;

    public PacketRecordPlayerPlay() { }

    public PacketRecordPlayerPlay(int x, int y, int z, float playRadius, int dimension, String name, String url, String local, boolean repeat, boolean shuffle){
        this.x = x;
        this.y = y;
        this.z = z;
        this.playRadius = playRadius;
        this.dimension = dimension;
        this.repeat = repeat;
        this.shuffle = shuffle;

        sounds = new ArrayList<Sound>();
        sounds.add(new Sound().setInfo(name, url, local));
    }

    public PacketRecordPlayerPlay(int x, int y, int z, float playRadius, int dimension, NBTTagCompound nbt, boolean repeat, boolean shuffle){
        this.x = x;
        this.y = y;
        this.z = z;
        this.playRadius = playRadius;
        this.dimension = dimension;
        this.repeat = repeat;
        this.shuffle = shuffle;

        sounds = new ArrayList<Sound>();
        NBTTagList songList = nbt.getTagList("songs", 10);
        for(int i = 0; i < songList.tagCount(); i++)
            sounds.add(new Sound().setInfo(songList.getCompoundTagAt(i).getString("name"), songList.getCompoundTagAt(i).getString("url"), songList.getCompoundTagAt(i).getString("local")));
    }

    public void readBytes(ByteBuf bytes) {
        String recieved = ByteBufUtils.readUTF8String(bytes);
        String[] str = recieved.split("\2477");
        x = Integer.parseInt(str[0]);
        y = Integer.parseInt(str[1]);
        z = Integer.parseInt(str[2]);
        playRadius = Float.parseFloat(str[3]);
        dimension = Integer.parseInt(str[4]);
        repeat = Boolean.parseBoolean(str[5]);
        shuffle = Boolean.parseBoolean(str[6]);

        sounds = new ArrayList<Sound>();
        for(String strr : str[7].split("\2478")){
            Sound snd = Sound.fromString(strr);
            snd.x = x;
            snd.y = y;
            snd.z = z;
            snd.dimension = dimension;
            snd.playRadius = playRadius;
            sounds.add(snd);
        }
    }

    public void writeBytes(ByteBuf bytes) {
        String sndStr = "";
        if(sounds != null)
            for(Sound snd : sounds)
                sndStr += snd.toString() + "\2478";
        ByteBufUtils.writeUTF8String(bytes, x + "\2477" + y + "\2477" + z + "\2477" + playRadius + "\2477" + dimension + "\2477" + repeat + "\2477" + shuffle + "\2477" + sndStr.substring(0, sndStr.length() - 2));
    }

    public void executeClient(EntityPlayer player) {
        if(playRadius > 100000 || (float)Math.abs(Math.sqrt(Math.pow(player.posX - x, 2) + Math.pow(player.posY - y, 2) + Math.pow(player.posZ - z, 2))) < playRadius) {
            SoundHandler.playSound(x, y, z, dimension, playRadius, sounds, repeat, shuffle);
        }
    }

    public void executeServer(EntityPlayer player) { }
}