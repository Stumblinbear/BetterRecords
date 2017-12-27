package com.codingforcookies.betterrecords.extensions

import io.netty.buffer.ByteBuf
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.common.network.simpleimpl.IMessage

fun IMessage.writeBlockPos(buf: ByteBuf, pos: BlockPos) {
    buf.writeInt(pos.x)
    buf.writeInt(pos.y)
    buf.writeInt(pos.z)
}

fun IMessage.readBlockPos(buf: ByteBuf): BlockPos {
    return BlockPos(buf.readInt(), buf.readInt(), buf.readInt())
}
