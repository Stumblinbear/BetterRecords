package com.codingforcookies.betterrecords.item;

import com.codingforcookies.betterrecords.api.record.IRecord;
import com.codingforcookies.betterrecords.api.wire.IRecordWireHome;
import com.codingforcookies.betterrecords.common.packets.PacketHandler;
import com.codingforcookies.betterrecords.common.util.BetterUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemRecord extends BetterItem implements IRecord {

    public ItemRecord(String name) {
        super(name);
        setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack itemStack) {
        if(itemStack.getTagCompound() != null && itemStack.getTagCompound().hasKey("local")) return itemStack.getTagCompound().getString("local");
        else return I18n.translateToLocal(getUnlocalizedName() + ".name");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        if(itemStack.getTagCompound() != null && itemStack.getTagCompound().hasKey("author")) tooltip.add(BetterUtils.getTranslatedString("item.record.by") + ": " + itemStack.getTagCompound().getString("author"));
        if(itemStack.getTagCompound() != null && itemStack.getTagCompound().hasKey("size")) tooltip.add(BetterUtils.getTranslatedString("item.record.size") + ": " + itemStack.getTagCompound().getString("size") + "mb");
        if(itemStack.getTagCompound() != null && itemStack.getTagCompound().hasKey("repeat") && itemStack.getTagCompound().getBoolean("repeat")){
            tooltip.add("");
            tooltip.add("\247e" + BetterUtils.getTranslatedString("item.record.repeatenabled"));
        }
    }

    @Override
    public boolean isRecordValid(ItemStack itemStack) {
        return itemStack.getTagCompound() != null && itemStack.getTagCompound().hasKey("name");
    }

    @Override
    public void onRecordInserted(IRecordWireHome wireHome, ItemStack itemStack) {
        PacketHandler.sendRecordPlayToAllFromServer(wireHome.getTileEntity().getPos().getX(), wireHome.getTileEntity().getPos().getY(), wireHome.getTileEntity().getPos().getZ(), wireHome.getTileEntity().getWorld().provider.getDimension(), wireHome.getSongRadius(), itemStack.getTagCompound().getString("name"), itemStack.getTagCompound().getString("url"), itemStack.getTagCompound().getString("local"), itemStack.getTagCompound().hasKey("repeat") ? itemStack.getTagCompound().getBoolean("repeat") : false, itemStack.getTagCompound().hasKey("shuffle") ? itemStack.getTagCompound().getBoolean("shuffle") : false);
    }
}
