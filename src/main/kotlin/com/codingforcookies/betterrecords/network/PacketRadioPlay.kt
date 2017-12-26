package com.codingforcookies.betterrecords.network

import com.codingforcookies.betterrecords.client.sound.SoundHandler
import io.netty.buffer.ByteBuf
import net.minecraft.client.Minecraft
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.common.network.ByteBufUtils
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext

class PacketRadioPlay : IMessage {

    var pos: BlockPos? = null
    var dimension: Int? = null

    var playRadius: Float? = null

    var local: String? = null
    var url: String? = null

    constructor()

    constructor(pos: BlockPos, dimension: Int, playRadius: Float,
                local: String, url: String) {
        this.pos = pos
        this.dimension = dimension
        this.playRadius = playRadius
        this.local = local
        this.url = url
    }

    override fun toBytes(buf: ByteBuf) {
        buf.writeInt(pos!!.x)
        buf.writeInt(pos!!.y)
        buf.writeInt(pos!!.z)
        buf.writeInt(dimension!!)

        buf.writeFloat(playRadius!!)

        ByteBufUtils.writeUTF8String(buf, local!!)
        ByteBufUtils.writeUTF8String(buf, url!!)
    }

    override fun fromBytes(buf: ByteBuf) {
        pos = BlockPos(buf.readInt(), buf.readInt(), buf.readInt())
        dimension = buf.readInt()

        playRadius = buf.readFloat()

        local = ByteBufUtils.readUTF8String(buf)
        url = ByteBufUtils.readUTF8String(buf)
    }

    class Handler : IMessageHandler<PacketRadioPlay, IMessage> {

        override fun onMessage(message: PacketRadioPlay, ctx: MessageContext): IMessage? {
            val player = Minecraft.getMinecraft().player

            with (message) {
                if (playRadius!! > 100000 || Math.abs(Math.sqrt(Math.pow(player.posX - pos!!.x, 2.0) + Math.pow(player.posY - pos!!.y, 2.0) + Math.pow(player.posZ - pos!!.z, 2.0))).toFloat() < playRadius!!) {
                    SoundHandler.playSoundFromStream(pos!!, dimension!!, playRadius!!, local!!, url!!)
                }
            }

            return null
        }
    }
}