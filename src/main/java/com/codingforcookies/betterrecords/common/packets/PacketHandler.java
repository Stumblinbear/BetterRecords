package com.codingforcookies.betterrecords.common.packets;

import com.codingforcookies.betterrecords.api.connection.RecordConnection;
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

}
