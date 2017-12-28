package com.codingforcookies.betterrecords.api

import net.minecraft.block.properties.PropertyEnum
import net.minecraft.util.EnumFacing
import net.minecraftforge.fml.common.API

object BetterRecordsAPI {

    val CARDINAL_DIRECTIONS = PropertyEnum.create("facing", EnumFacing::class.java, EnumFacing.Plane.HORIZONTAL)
}