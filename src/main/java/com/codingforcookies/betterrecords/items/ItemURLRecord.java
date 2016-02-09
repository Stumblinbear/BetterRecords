package com.codingforcookies.betterrecords.items;

import java.util.List;

import com.codingforcookies.betterrecords.BetterRecords;
import com.codingforcookies.betterrecords.BetterUtils;
import com.codingforcookies.betterrecords.betterenums.IRecord;
import com.codingforcookies.betterrecords.betterenums.IRecordWireHome;
import com.codingforcookies.betterrecords.packets.PacketHandler;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

public class ItemURLRecord extends Item implements IRecord{

	private static IIcon iconBase, iconOverlay;

	public ItemURLRecord(){
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

	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses(){
		return true;
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister){
		iconBase = par1IconRegister.registerIcon(BetterRecords.ID + ":urlrecord");
		iconOverlay = par1IconRegister.registerIcon(BetterRecords.ID + ":urlrecord_overlay");
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int par1, int par2){
		return par2 == 0 ? iconBase : iconOverlay;
	}

	public boolean isRecordValid(ItemStack par1ItemStack){
		return par1ItemStack.getTagCompound() != null && par1ItemStack.getTagCompound().hasKey("name");
	}

	public void onRecordInserted(IRecordWireHome par1WireHome, ItemStack par2ItemStack){
		PacketHandler.sendRecordPlayToAllFromServer(par1WireHome.getTileEntity().getPos().getX(), par1WireHome.getTileEntity().getPos().getY(), par1WireHome.getTileEntity().getPos().getZ(), par1WireHome.getTileEntity().getWorld().provider.getDimensionId(), par1WireHome.getSongRadius(), par2ItemStack.getTagCompound().getString("name"), par2ItemStack.getTagCompound().getString("url"), par2ItemStack.getTagCompound().getString("local"), par2ItemStack.getTagCompound().hasKey("repeat") ? par2ItemStack.getTagCompound().getBoolean("repeat") : false, par2ItemStack.getTagCompound().hasKey("shuffle") ? par2ItemStack.getTagCompound().getBoolean("shuffle") : false);
	}
}