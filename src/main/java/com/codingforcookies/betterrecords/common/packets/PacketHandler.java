package com.codingforcookies.betterrecords.common.packets;

import com.codingforcookies.betterrecords.api.connection.RecordConnection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.FMLEmbeddedChannel;
import net.minecraftforge.fml.common.network.FMLOutboundHandler;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumMap;

public class PacketHandler{

    public static EnumMap<Side, FMLEmbeddedChannel> channels;

    public static void sendWireConnectionFromClient(RecordConnection connection){
        channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
        channels.get(Side.CLIENT).writeOutbound(new PacketWireConnection(connection));
    }

    public static void sendURLWriteFromClient(int x, int y, int z, String name, String url, String localName, int size, int color, String author){
        channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
        channels.get(Side.CLIENT).writeOutbound(new PacketURLWriter(x, y, z, name, url, localName, size, color, author));
    }

    public static void sendURLWriteFromClient(int x, int y, int z, String name, String url, String localName, int size){
        channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
        channels.get(Side.CLIENT).writeOutbound(new PacketURLWriter(x, y, z, name, url, localName, size));
    }

    public static void sendRecordPlayToAllFromServer(int x, int y, int z, int dimension, float playRadius, NBTTagCompound nbt, boolean repeat, boolean shuffle){
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
        //channels.get(Side.SERVER).writeOutbound(new PacketRecordPlayerPlay(x, y, z, playRadius, dimension, nbt, repeat, shuffle));
    }

}
