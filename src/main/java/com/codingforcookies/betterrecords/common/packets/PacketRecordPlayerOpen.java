package com.codingforcookies.betterrecords.common.packets;

import com.codingforcookies.betterrecords.block.tile.TileRecordPlayer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class PacketRecordPlayerOpen implements IPacket {
    int x, y, z, dimension;
    boolean open;

    public PacketRecordPlayerOpen() { }

    public PacketRecordPlayerOpen(int x, int y, int z, int dimension, boolean open) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dimension = dimension;
        this.open = open;
    }

    public void readBytes(ByteBuf bytes) {
        String recieved = ByteBufUtils.readUTF8String(bytes);
        String[] str = recieved.split("\\]");
        x = Integer.parseInt(str[0]);
        y = Integer.parseInt(str[1]);
        z = Integer.parseInt(str[2]);
        dimension = Integer.parseInt(str[3]);
        open = Boolean.parseBoolean(str[4]);
    }

    public void writeBytes(ByteBuf bytes) {
        ByteBufUtils.writeUTF8String(bytes, x + "]" + y + "]" + z + "]" + dimension + "]" + open);
    }

    public void executeClient(EntityPlayer player) {
        if(player.world.provider.getDimension() == dimension) {
            TileEntity te = player.world.getTileEntity(new net.minecraft.util.math.BlockPos(x, y, z));
            if(te == null || !(te instanceof TileRecordPlayer))
                return;

            TileRecordPlayer tileRecordPlayer = (TileRecordPlayer)te;
            tileRecordPlayer.opening = open;
        }
    }

    public void executeServer(EntityPlayer player) { }
}
