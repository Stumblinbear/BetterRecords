package com.codingforcookies.betterrecords.item;

import com.codingforcookies.betterrecords.api.connection.RecordConnection;
import com.codingforcookies.betterrecords.api.wire.IRecordWire;
import com.codingforcookies.betterrecords.api.wire.IRecordWireHome;
import com.codingforcookies.betterrecords.api.wire.IRecordWireManipulator;
import com.codingforcookies.betterrecords.common.core.helper.ConnectionHelper;
import com.codingforcookies.betterrecords.common.packets.PacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemWire extends BetterItem implements IRecordWireManipulator {

    public static RecordConnection connection;

    public ItemWire(String name) {
        super(name);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(!world.isRemote)
            return EnumActionResult.PASS;

        TileEntity te = world.getTileEntity(pos);
        if(te == null || !(te instanceof IRecordWire))
            return EnumActionResult.PASS;

        if(connection == null) {
            connection = new RecordConnection(pos.getX(), pos.getY(), pos.getZ(), te instanceof IRecordWireHome);
        }else{
            float x1 = -(float)(pos.getX() - (connection.fromHome ? connection.x1 : connection.x2));
            float y1 = -(float)(pos.getY() - (connection.fromHome ? connection.y1 : connection.y2));
            float z1 = -(float)(pos.getZ() - (connection.fromHome ? connection.z1 : connection.z2));

            if((int)Math.sqrt(Math.pow(x1, 2) + Math.pow(y1, 2) + Math.pow(z1, 2)) > 7 || connection.sameInitial(pos.getX(), pos.getY(), pos.getZ())) {
                connection = null;
                return EnumActionResult.PASS;
            }

            if(!connection.fromHome)
                connection.setConnection1(pos.getX(), pos.getY(), pos.getZ());
            else
                connection.setConnection2(pos.getX(), pos.getY(), pos.getZ());

            TileEntity te1 = world.getTileEntity(new BlockPos(connection.x1, connection.y1, connection.z1));
            TileEntity te2 = world.getTileEntity(new BlockPos(connection.x2, connection.y2, connection.z2));

            if(te2 instanceof IRecordWire) {
                if(!(te1 instanceof IRecordWireHome && te2 instanceof IRecordWireHome)) {
                    ConnectionHelper.addConnection(te.getWorld(), (IRecordWire)te1, connection, world.getBlockState(te.getPos()));
                    ConnectionHelper.addConnection(te.getWorld(), (IRecordWire)te2, connection, world.getBlockState(te.getPos()));
                    PacketHandler.sendWireConnectionFromClient(connection);
                    stack.stackSize--;
                }
            }

            connection = null;
        }
        return EnumActionResult.PASS;
    }
}
