package com.codingforcookies.betterrecords.common.item;

import java.util.List;

import com.codingforcookies.betterrecords.common.util.BetterUtils;
import com.codingforcookies.betterrecords.api.wire.IRecordWireHome;
import com.codingforcookies.betterrecords.common.packets.PacketHandler;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;

public class ItemURLMultiRecord extends ItemURLRecord{

    //private static IIcon iconBase, iconOverlay;

    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack par1ItemStack){
        return StatCollector.translateToLocal(getUnlocalizedName() + ".name");
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4){
        if(par1ItemStack.getTagCompound() != null && par1ItemStack.getTagCompound().hasKey("songs")){
            NBTTagList songList = par1ItemStack.getTagCompound().getTagList("songs", 10);
            for(int i = 0; i < songList.tagCount(); i++)
                par3List.add(BetterUtils.getTranslatedString("item.record.song") + " #" + (i + 1) + ": " + songList.getCompoundTagAt(i).getString("local"));
        }
        if(par1ItemStack.getTagCompound() != null && par1ItemStack.getTagCompound().hasKey("repeat") ? par1ItemStack.getTagCompound().getBoolean("repeat") : false){
            if(!par3List.contains("\247e" + BetterUtils.getTranslatedString("item.record.shuffleenabled"))) par3List.add("");
            par3List.add("\247e" + BetterUtils.getTranslatedString("item.record.repeatenabled"));
        }
        if(par1ItemStack.getTagCompound() != null && par1ItemStack.getTagCompound().hasKey("shuffle") ? par1ItemStack.getTagCompound().getBoolean("shuffle") : false){
            if(!par3List.contains("\247e" + BetterUtils.getTranslatedString("item.record.repeatenabled"))) par3List.add("");
            par3List.add("\247e" + BetterUtils.getTranslatedString("item.record.shuffleenabled"));
        }
    }

//    @SideOnly(Side.CLIENT)
//    public void registerIcons(IIconRegister par1IconRegister){
//        iconBase = par1IconRegister.registerIcon(BetterRecords.ID + ":urlmultirecord");
//        iconOverlay = par1IconRegister.registerIcon(BetterRecords.ID + ":urlrecord_overlay");
//    }
//
//    @SideOnly(Side.CLIENT)
//    public IIcon getIconFromDamageForRenderPass(int par1, int par2){
//        return par2 == 0 ? iconBase : iconOverlay;
//    }

    public boolean isRecordValid(ItemStack par1ItemStack){
        return par1ItemStack.getTagCompound() != null && par1ItemStack.getTagCompound().hasKey("songs");
    }

    public void onRecordInserted(IRecordWireHome par1WireHome, ItemStack par2ItemStack){
        PacketHandler.sendRecordPlayToAllFromServer(par1WireHome.getTileEntity().getPos().getX(), par1WireHome.getTileEntity().getPos().getY(), par1WireHome.getTileEntity().getPos().getZ(), par1WireHome.getTileEntity().getWorld().provider.getDimensionId(), par1WireHome.getSongRadius(), par2ItemStack.getTagCompound(), par2ItemStack.getTagCompound().hasKey("repeat") ? par2ItemStack.getTagCompound().getBoolean("repeat") : false, par2ItemStack.getTagCompound().hasKey("shuffle") ? par2ItemStack.getTagCompound().getBoolean("shuffle") : false);
    }
}