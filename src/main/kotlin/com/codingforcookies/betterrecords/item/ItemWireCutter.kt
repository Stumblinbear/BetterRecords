package com.codingforcookies.betterrecords.item

import com.codingforcookies.betterrecords.api.wire.IRecordWire
import com.codingforcookies.betterrecords.api.wire.IRecordWireHome
import com.codingforcookies.betterrecords.api.wire.IRecordWireManipulator
import com.codingforcookies.betterrecords.helper.ConnectionHelper
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class ItemWireCutter(name: String) : ModItem(name), IRecordWireManipulator {

    override fun onItemUse(player: EntityPlayer, world: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        val te = world.getTileEntity(pos)
        if (te == null || te !is IRecordWire || te is IRecordWireHome)
            return EnumActionResult.PASS

        if (world.isRemote)
            return EnumActionResult.PASS

        ConnectionHelper.clearConnections(te.world, te as IRecordWire)
        return EnumActionResult.PASS
    }
}
