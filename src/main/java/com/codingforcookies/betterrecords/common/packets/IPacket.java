package com.codingforcookies.betterrecords.common.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public interface IPacket {
    void readBytes(ByteBuf bytes);
    void writeBytes(ByteBuf bytes);
    void executeClient(EntityPlayer player);
    void executeServer(EntityPlayer player);
}