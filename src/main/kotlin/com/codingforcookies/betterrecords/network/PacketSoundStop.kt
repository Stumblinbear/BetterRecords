package com.codingforcookies.betterrecords.network

import com.codingforcookies.betterrecords.client.sound.SoundHandler
import io.netty.buffer.ByteBuf
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext

class PacketSoundStop : IMessage {

    var pos: BlockPos? = null
    var dimension: Int? = null

    constructor()

    constructor(pos: BlockPos, dimension: Int) {
        this.pos = pos
        this.dimension = dimension
    }

    override fun toBytes(buf: ByteBuf) {
        buf.writeInt(pos!!.x)
        buf.writeInt(pos!!.y)
        buf.writeInt(pos!!.z)

        buf.writeInt(dimension!!)
    }

    override fun fromBytes(buf: ByteBuf) {
        pos = BlockPos(buf.readInt(), buf.readInt(), buf.readInt())

        dimension = buf.readInt()
    }

    class Handler : IMessageHandler<PacketSoundStop, IMessage> {

        override fun onMessage(message: PacketSoundStop, ctx: MessageContext): IMessage? {
            with(message) {
                SoundHandler.stopPlaying(pos!!, dimension!!)
            }

            return null
        }
    }
}