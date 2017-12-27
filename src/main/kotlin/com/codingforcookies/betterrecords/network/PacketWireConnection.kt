package com.codingforcookies.betterrecords.network

import com.codingforcookies.betterrecords.api.connection.RecordConnection
import com.codingforcookies.betterrecords.api.wire.IRecordWire
import com.codingforcookies.betterrecords.api.wire.IRecordWireHome
import com.codingforcookies.betterrecords.helper.ConnectionHelper
import io.netty.buffer.ByteBuf
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.common.network.ByteBufUtils
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext

class PacketWireConnection @JvmOverloads constructor(
        var connection: RecordConnection = RecordConnection(0, 0, 0, true)
) : IMessage {

    override fun toBytes(buf: ByteBuf) {
        ByteBufUtils.writeUTF8String(buf, connection.toString())
    }

    override fun fromBytes(buf: ByteBuf) {
        connection = RecordConnection(ByteBufUtils.readUTF8String(buf))
    }

    class Handler : IMessageHandler<PacketWireConnection, IMessage> {

        override fun onMessage(message: PacketWireConnection, ctx: MessageContext): IMessage? {
            val player = ctx.serverHandler.player

            with(message) {
                val te1 = player.world.getTileEntity(BlockPos(connection.x1, connection.y1, connection.z1))
                val te2 = player.world.getTileEntity(BlockPos(connection.x2, connection.y2, connection.z2))

                if (te1 is IRecordWire && te2 is IRecordWire) {
                    if (!(te1 is IRecordWireHome && te2 is IRecordWireHome)) {
                        ConnectionHelper.addConnection(player.world, te1, connection, player.world.getBlockState(te1.pos))
                        ConnectionHelper.addConnection(player.world, te2, connection, player.world.getBlockState(te2.pos))
                    }
                }
            }

            return null
        }
    }
}