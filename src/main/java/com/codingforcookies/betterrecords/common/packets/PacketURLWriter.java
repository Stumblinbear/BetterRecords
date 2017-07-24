package com.codingforcookies.betterrecords.common.packets;

import com.codingforcookies.betterrecords.block.tile.TileFrequencyTuner;
import com.codingforcookies.betterrecords.block.tile.TileRecordEtcher;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class PacketURLWriter implements IPacket {
    int x, y, z, size, color = -999;
    String name, url, localName, author;

    public PacketURLWriter() { }

    public PacketURLWriter(int x, int y, int z, String name, String url, String localName, int size) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
        this.url = url;
        this.localName = localName;
        this.size = size;
    }

    public PacketURLWriter(int x, int y, int z, String name, String url, String localName, int size, int color, String author) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
        this.url = url;
        this.localName = localName;
        this.size = size;
        this.color = color;
        this.author = author;
    }

    public void readBytes(ByteBuf bytes) {
        String recieved = ByteBufUtils.readUTF8String(bytes);
        String[] str = recieved.split("\2477");
        x = Integer.parseInt(str[0]);
        y = Integer.parseInt(str[1]);
        z = Integer.parseInt(str[2]);
        name = str[3];
        url = str[4];
        localName = str[5];
        size = Integer.parseInt(str[6]);

        if(str.length > 7) {
            color = Integer.parseInt(str[7]);
            author = str[8];
        }
    }

    public void writeBytes(ByteBuf bytes) {
        ByteBufUtils.writeUTF8String(bytes, x + "\2477" + y + "\2477" + z + "\2477" + name + "\2477" + url + "\2477" + localName + "\2477" + size + (color != -999 ? "\2477" + color + "\2477" + author : ""));
    }

    public void executeClient(EntityPlayer player) { }

    public void executeServer(EntityPlayer player) {
        TileEntity te = player.world.getTileEntity(new net.minecraft.util.math.BlockPos(x, y, z));
        if(te == null || !(te instanceof TileRecordEtcher || te instanceof TileFrequencyTuner))
            return;

        if(te instanceof TileRecordEtcher) {
            TileRecordEtcher tileRecordEtcher = (TileRecordEtcher)te;
            ItemStack itemStack = tileRecordEtcher.record;
            if(itemStack != null) {
                if(itemStack.getTagCompound() == null)
                    itemStack.setTagCompound(new NBTTagCompound());
                itemStack.getTagCompound().setString("name", name);
                itemStack.getTagCompound().setString("url", url);
                itemStack.getTagCompound().setString("local", localName);
                itemStack.getTagCompound().setInteger("size", size);
                if(color != -999) {
                    itemStack.getTagCompound().setInteger("color", color);
                    itemStack.getTagCompound().setString("author", author);
                }
                IBlockState state = player.world.getBlockState(te.getPos());
                player.world.notifyBlockUpdate(te.getPos(), state, state, 3);
            }
        }else if(te instanceof TileFrequencyTuner) {
            TileFrequencyTuner tileEntityFrequencyTuner = (TileFrequencyTuner)te;
            ItemStack itemStack = tileEntityFrequencyTuner.getCrystal();
            if(itemStack != null) {
                if(itemStack.getTagCompound() == null)
                    itemStack.setTagCompound(new NBTTagCompound());
                itemStack.getTagCompound().setString("url", url);
                itemStack.getTagCompound().setString("local", localName);
                if(color != -999)
                    itemStack.getTagCompound().setInteger("color", color);
                IBlockState state = player.world.getBlockState(te.getPos());
                player.world.notifyBlockUpdate(te.getPos(), state, state, 3);
            }
        }
    }
}
