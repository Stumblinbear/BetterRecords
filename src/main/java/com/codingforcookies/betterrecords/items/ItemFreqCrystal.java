package com.codingforcookies.betterrecords.items;

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
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		return par1ItemStack;
	}
	
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack par1ItemStack)  {
		if(par1ItemStack.stackTagCompound != null && par1ItemStack.stackTagCompound.hasKey("local"))
			return par1ItemStack.stackTagCompound.getString("local");
		else
			return StatCollector.translateToLocal(getUnlocalizedName() + ".name");
	}
	
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		return (par1ItemStack.stackTagCompound != null && par1ItemStack.stackTagCompound.hasKey("color") ? par1ItemStack.stackTagCompound.getInteger("color") : 0xFFFFFF);
	}
}