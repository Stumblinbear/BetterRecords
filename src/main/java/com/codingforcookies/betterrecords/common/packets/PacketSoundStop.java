package com.codingforcookies.betterrecords.common.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

import com.codingforcookies.betterrecords.client.sound.SoundHandler;

import net.minecraftforge.fml.common.network.ByteBufUtils;

public class PacketSoundStop implements IPacket {
	int x, y, z, dimension;
	
	public PacketSoundStop() { }
	
	public PacketSoundStop(int x, int y, int z, int dimension) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.dimension = dimension;
	}
	
	public void readBytes(ByteBuf bytes) {
		String recieved = ByteBufUtils.readUTF8String(bytes);
		String[] str = recieved.split("\\]");
		x = Integer.parseInt(str[0]);
		y = Integer.parseInt(str[1]);
		z = Integer.parseInt(str[2]);
		dimension = Integer.parseInt(str[3]);
	}
	
	public void writeBytes(ByteBuf bytes) {
		ByteBufUtils.writeUTF8String(bytes, x + "]" + y + "]" + z + "]" + dimension);
	}
	
	public void executeClient(EntityPlayer player) {
		if(SoundHandler.soundPlaying.containsKey(x + "," + y + "," + z + "," + dimension))
			SoundHandler.soundPlaying.remove(x + "," + y + "," + z + "," + dimension);
	}
	
	public void executeServer(EntityPlayer player) { }
}