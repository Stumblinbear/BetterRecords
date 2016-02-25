package com.codingforcookies.betterrecords.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFreqCrystal extends Item {

    public ItemFreqCrystal() {
        setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        return par1ItemStack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack par1ItemStack)  {
        if(par1ItemStack.getTagCompound() != null && par1ItemStack.getTagCompound().hasKey("local"))
            return par1ItemStack.getTagCompound().getString("local");
        else
            return StatCollector.translateToLocal(getUnlocalizedName() + ".name");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
        return (par1ItemStack.getTagCompound() != null && par1ItemStack.getTagCompound().hasKey("color") ? par1ItemStack.getTagCompound().getInteger("color") : 0xFFFFFF);
    }
}