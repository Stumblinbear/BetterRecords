package com.codingforcookies.betterrecords.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFreqCrystal extends BetterItem {

    public ItemFreqCrystal(String name) {
        super(name);
        setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        return itemStack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack itemStack)  {
        if(itemStack.getTagCompound() != null && itemStack.getTagCompound().hasKey("local"))
            return itemStack.getTagCompound().getString("local");
        else
            return StatCollector.translateToLocal(getUnlocalizedName() + ".name");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemStack, int renderPass) {
        return (itemStack.getTagCompound() != null && itemStack.getTagCompound().hasKey("color") ? itemStack.getTagCompound().getInteger("color") : 0xFFFFFF);
    }
}
