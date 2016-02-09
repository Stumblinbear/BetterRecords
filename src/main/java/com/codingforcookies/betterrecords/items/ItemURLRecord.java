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

	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack par1ItemStack){
		if(par1ItemStack.stackTagCompound != null && par1ItemStack.stackTagCompound.hasKey("local")) return par1ItemStack.stackTagCompound.getString("local");
		else return StatCollector.translateToLocal(getUnlocalizedName() + ".name");
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4){
		if(par1ItemStack.stackTagCompound != null && par1ItemStack.stackTagCompound.hasKey("author")) par3List.add(BetterUtils.getTranslatedString("item.record.by") + ": " + par1ItemStack.stackTagCompound.getString("author"));
		if(par1ItemStack.stackTagCompound != null && par1ItemStack.stackTagCompound.hasKey("size")) par3List.add(BetterUtils.getTranslatedString("item.record.size") + ": " + par1ItemStack.stackTagCompound.getString("size") + "mb");
		if(par1ItemStack.stackTagCompound != null && par1ItemStack.stackTagCompound.hasKey("repeat") ? par1ItemStack.stackTagCompound.getBoolean("repeat") : false){
			par3List.add("");
			par3List.add("\247e" + BetterUtils.getTranslatedString("item.record.repeatenabled"));
		}
	}

	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2){
		return(par2 == 0 ? 0xFFFFFF : (par1ItemStack.stackTagCompound != null && par1ItemStack.stackTagCompound.hasKey("color") ? par1ItemStack.stackTagCompound.getInteger("color") : 0xFFFFFF));
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
		return par1ItemStack.stackTagCompound != null && par1ItemStack.stackTagCompound.hasKey("name");
	}

	public void onRecordInserted(IRecordWireHome par1WireHome, ItemStack par2ItemStack){
		PacketHandler.sendRecordPlayToAllFromServer(par1WireHome.getTileEntity().xCoord, par1WireHome.getTileEntity().yCoord, par1WireHome.getTileEntity().zCoord, par1WireHome.getTileEntity().getWorldObj().provider.dimensionId, par1WireHome.getSongRadius(), par2ItemStack.stackTagCompound.getString("name"), par2ItemStack.stackTagCompound.getString("url"), par2ItemStack.stackTagCompound.getString("local"), par2ItemStack.stackTagCompound.hasKey("repeat") ? par2ItemStack.stackTagCompound.getBoolean("repeat") : false, par2ItemStack.stackTagCompound.hasKey("shuffle") ? par2ItemStack.stackTagCompound.getBoolean("shuffle") : false);
	}
}