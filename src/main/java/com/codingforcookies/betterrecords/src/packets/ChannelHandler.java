package com.codingforcookies.betterrecords.src.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ChannelHandler extends FMLIndexedMessageToMessageCodec<IPacket> {
	public ChannelHandler() {
		addDiscriminator(0, PacketRecordPlayerPlay.class);
		addDiscriminator(1, PacketSoundStop.class);
		addDiscriminator(2, PacketRecordPlayerOpen.class);
		addDiscriminator(3, PacketURLWriter.class);
		addDiscriminator(4, PacketRadioPlay.class);
		addDiscriminator(5, PacketWireConnection.class);
	}

	public void encodeInto(ChannelHandlerContext ctx, IPacket packet, ByteBuf data) throws Exception {
		packet.writeBytes(data);
	}

	public void decodeInto(ChannelHandlerContext ctx, ByteBuf data, IPacket packet) {
		packet.readBytes(data);
		switch(FMLCommonHandler.instance().getEffectiveSide()) {
			case CLIENT:
				packet.executeClient(getClientPlayer());
				break;
			case SERVER:
				INetHandler netHandler = ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
				packet.executeServer(((NetHandlerPlayServer)netHandler).playerEntity);
				break;
		}
	}

	@SideOnly(Side.CLIENT)
	private EntityPlayer getClientPlayer() {
		return Minecraft.getMinecraft().thePlayer;
	}
}