package com.codingforcookies.betterrecords.common.item;

import com.codingforcookies.betterrecords.api.wire.IRecordWire;
import com.codingforcookies.betterrecords.api.wire.IRecordWireHome;
import com.codingforcookies.betterrecords.api.wire.IRecordWireManipulator;
import com.codingforcookies.betterrecords.common.core.helper.ConnectionHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemRecordWireCutter extends BetterItem implements IRecordWireManipulator {

    public ItemRecordWireCutter(String name) {
        super(name);
        setMaxStackSize(1);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileEntity te = world.getTileEntity(pos);
        if(te == null || !(te instanceof IRecordWire) || te instanceof IRecordWireHome)
            return EnumActionResult.PASS;

        if(world.isRemote)
            return EnumActionResult.PASS;

        ConnectionHelper.clearConnections(te.getWorld(), (IRecordWire)te, world.getBlockState(pos));
        return EnumActionResult.PASS;
    }
}
