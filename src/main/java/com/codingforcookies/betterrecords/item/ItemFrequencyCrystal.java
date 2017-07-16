package com.codingforcookies.betterrecords.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFrequencyCrystal extends BetterItem {

    public ItemFrequencyCrystal(String name) {
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
}
