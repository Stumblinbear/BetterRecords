package com.codingforcookies.betterrecords.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import com.codingforcookies.betterrecords.common.core.helper.ConnectionHelper;
import com.codingforcookies.betterrecords.api.wire.IRecordWire;
import com.codingforcookies.betterrecords.api.wire.IRecordWireHome;
import com.codingforcookies.betterrecords.api.wire.IRecordWireManipulator;

public class ItemRecordWireCutter extends Item implements IRecordWireManipulator {
    public ItemRecordWireCutter() {
        setMaxStackSize(1);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileEntity te = world.getTileEntity(pos);
        if(te == null || !(te instanceof IRecordWire) || te instanceof IRecordWireHome)
            return false;

        if(world.isRemote)
            return true;

        ConnectionHelper.clearConnections(te.getWorld(), (IRecordWire)te);
        return true;
    }
}