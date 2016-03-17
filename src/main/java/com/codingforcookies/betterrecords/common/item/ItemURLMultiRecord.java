package com.codingforcookies.betterrecords.common.item;

import com.codingforcookies.betterrecords.api.wire.IRecordWireHome;
import com.codingforcookies.betterrecords.common.packets.PacketHandler;
import com.codingforcookies.betterrecords.common.util.BetterUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemURLMultiRecord extends ItemURLRecord {

    public ItemURLMultiRecord(String name) {
        super(name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack itemStack){
        return StatCollector.translateToLocal(getUnlocalizedName() + ".name");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        if(itemStack.getTagCompound() != null && itemStack.getTagCompound().hasKey("songs")){
            NBTTagList songList = itemStack.getTagCompound().getTagList("songs", 10);
            for(int i = 0; i < songList.tagCount(); i++)
                tooltip.add(BetterUtils.getTranslatedString("item.record.song") + " #" + (i + 1) + ": " + songList.getCompoundTagAt(i).getString("local"));
        }
        if(itemStack.getTagCompound() != null && itemStack.getTagCompound().hasKey("repeat") ? itemStack.getTagCompound().getBoolean("repeat") : false){
            if(!tooltip.contains("\247e" + BetterUtils.getTranslatedString("item.record.shuffleenabled"))) tooltip.add("");
            tooltip.add("\247e" + BetterUtils.getTranslatedString("item.record.repeatenabled"));
        }
        if(itemStack.getTagCompound() != null && itemStack.getTagCompound().hasKey("shuffle") ? itemStack.getTagCompound().getBoolean("shuffle") : false){
            if(!tooltip.contains("\247e" + BetterUtils.getTranslatedString("item.record.repeatenabled"))) tooltip.add("");
            tooltip.add("\247e" + BetterUtils.getTranslatedString("item.record.shuffleenabled"));
        }
    }

    @Override
    public boolean isRecordValid(ItemStack itemStack){
        return itemStack.getTagCompound() != null && itemStack.getTagCompound().hasKey("songs");
    }

    @Override
    public void onRecordInserted(IRecordWireHome wireHome, ItemStack itemStack){
        PacketHandler.sendRecordPlayToAllFromServer(wireHome.getTileEntity().getPos().getX(), wireHome.getTileEntity().getPos().getY(), wireHome.getTileEntity().getPos().getZ(), wireHome.getTileEntity().getWorld().provider.getDimensionId(), wireHome.getSongRadius(), itemStack.getTagCompound(), itemStack.getTagCompound().hasKey("repeat") ? itemStack.getTagCompound().getBoolean("repeat") : false, itemStack.getTagCompound().hasKey("shuffle") ? itemStack.getTagCompound().getBoolean("shuffle") : false);
    }
}
