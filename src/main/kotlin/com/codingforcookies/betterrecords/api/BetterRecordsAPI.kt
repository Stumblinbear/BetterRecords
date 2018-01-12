package com.codingforcookies.betterrecords.api

import net.minecraft.block.properties.PropertyEnum
import net.minecraft.util.EnumFacing

object BetterRecordsAPI {

    val CARDINAL_DIRECTIONS = PropertyEnum.create("facing", EnumFacing::class.java, EnumFacing.Plane.HORIZONTAL)
}