package com.codingforcookies.betterrecords.common.item;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFreqCrystal extends BetterItem implements IItemColor {

    public ItemFreqCrystal(String name) {
        super(name);
        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand) {
        return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack itemStack)  {
        if(itemStack.getTagCompound() != null && itemStack.getTagCompound().hasKey("local"))
            return itemStack.getTagCompound().getString("local");
        else
            return I18n.translateToLocal(getUnlocalizedName() + ".name");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemstack(ItemStack itemStack, int renderPass) {
        return (itemStack.getTagCompound() != null && itemStack.getTagCompound().hasKey("color") ? itemStack.getTagCompound().getInteger("color") : 0xFFFFFF);
    }
}
