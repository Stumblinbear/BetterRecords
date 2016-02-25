package com.codingforcookies.betterrecords.common.item;

import com.codingforcookies.betterrecords.api.record.IRecord;
import com.codingforcookies.betterrecords.api.wire.IRecordWireHome;
import com.codingforcookies.betterrecords.common.packets.PacketHandler;
import com.codingforcookies.betterrecords.common.util.BetterUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemURLRecord extends BetterItem implements IRecord {

    public ItemURLRecord(String name){
        super(name);
        setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack par1ItemStack){
        if(par1ItemStack.getTagCompound() != null && par1ItemStack.getTagCompound().hasKey("local")) return par1ItemStack.getTagCompound().getString("local");
        else return StatCollector.translateToLocal(getUnlocalizedName() + ".name");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4){
        if(par1ItemStack.getTagCompound() != null && par1ItemStack.getTagCompound().hasKey("author")) par3List.add(BetterUtils.getTranslatedString("item.record.by") + ": " + par1ItemStack.getTagCompound().getString("author"));
        if(par1ItemStack.getTagCompound() != null && par1ItemStack.getTagCompound().hasKey("size")) par3List.add(BetterUtils.getTranslatedString("item.record.size") + ": " + par1ItemStack.getTagCompound().getString("size") + "mb");
        if(par1ItemStack.getTagCompound() != null && par1ItemStack.getTagCompound().hasKey("repeat") ? par1ItemStack.getTagCompound().getBoolean("repeat") : false){
            par3List.add("");
            par3List.add("\247e" + BetterUtils.getTranslatedString("item.record.repeatenabled"));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2){
        return(par2 == 0 ? 0xFFFFFF : (par1ItemStack.getTagCompound() != null && par1ItemStack.getTagCompound().hasKey("color") ? par1ItemStack.getTagCompound().getInteger("color") : 0xFFFFFF));
    }

    @Override
    public boolean isRecordValid(ItemStack par1ItemStack){
        return par1ItemStack.getTagCompound() != null && par1ItemStack.getTagCompound().hasKey("name");
    }

    @Override
    public void onRecordInserted(IRecordWireHome par1WireHome, ItemStack par2ItemStack){
        PacketHandler.sendRecordPlayToAllFromServer(par1WireHome.getTileEntity().getPos().getX(), par1WireHome.getTileEntity().getPos().getY(), par1WireHome.getTileEntity().getPos().getZ(), par1WireHome.getTileEntity().getWorld().provider.getDimensionId(), par1WireHome.getSongRadius(), par2ItemStack.getTagCompound().getString("name"), par2ItemStack.getTagCompound().getString("url"), par2ItemStack.getTagCompound().getString("local"), par2ItemStack.getTagCompound().hasKey("repeat") ? par2ItemStack.getTagCompound().getBoolean("repeat") : false, par2ItemStack.getTagCompound().hasKey("shuffle") ? par2ItemStack.getTagCompound().getBoolean("shuffle") : false);
    }
}
