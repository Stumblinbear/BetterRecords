package com.codingforcookies.betterrecords.network

import com.codingforcookies.betterrecords.client.sound.Sound
import com.codingforcookies.betterrecords.client.sound.SoundHandler
import com.codingforcookies.betterrecords.extensions.forEachTag
import io.netty.buffer.ByteBuf
import net.minecraft.client.Minecraft
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.common.network.ByteBufUtils
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext

class PacketRecordPlay : IMessage {

    var pos: BlockPos? = null
    var dimension: Int? = null
    var playRadius: Float? = null


    val sounds = mutableListOf<Sound>()

    var repeat: Boolean? = null
    var shuffle: Boolean? = null

    constructor()

    constructor(pos: BlockPos, dimension: Int, playRadius: Float, name: String,
                url: String, local: String, repeat: Boolean, shuffle: Boolean) {
        this.pos = pos
        this.dimension = dimension
        this.playRadius = playRadius

        sounds.add(Sound().setInfo(name, url, local))

        this.repeat = repeat
        this.shuffle = shuffle
    }

    constructor(pos: BlockPos, dimension: Int, playRadius: Float, nbt: NBTTagCompound,
                repeat: Boolean, shuffle: Boolean) {

        this.pos = pos
        this.dimension = dimension
        this.playRadius = playRadius

        nbt.getTagList("songs", 10).forEachTag {
            sounds += Sound().setInfo(
                    it.getString("name"),
                    it.getString("url"),
                    it.getString("local")
            )
        }

        this.repeat = repeat
        this.shuffle = shuffle
    }

    override fun toBytes(buf: ByteBuf) {
        buf.writeInt(pos!!.x)
        buf.writeInt(pos!!.y)
        buf.writeInt(pos!!.z)

        buf.writeInt(dimension!!)

        buf.writeFloat(playRadius!!)

        // Write the amount of sounds we're going to send,
        // to rebuild on the other side.
        buf.writeInt(sounds.size)
        sounds.forEach {
            ByteBufUtils.writeUTF8String(buf, it.toString())
        }

        buf.writeBoolean(repeat!!)
        buf.writeBoolean(shuffle!!)
    }

    override fun fromBytes(buf: ByteBuf) {
        pos = BlockPos(buf.readInt(), buf.readInt(), buf.readInt())

        dimension = buf.readInt()

        playRadius = buf.readFloat()

        val amount = buf.readInt()
        for (i in 1..amount) {
            sounds.add(Sound.fromString(ByteBufUtils.readUTF8String(buf)))
        }

        repeat = buf.readBoolean()
        shuffle = buf.readBoolean()
    }

    class Handler : IMessageHandler<PacketRecordPlay, IMessage> {

        override fun onMessage(message: PacketRecordPlay, ctx: MessageContext): IMessage? {
            val player = Minecraft.getMinecraft().player

            with(message) {
                if (playRadius!! > 100000 || Math.abs(Math.sqrt(Math.pow(player.posX - pos!!.x, 2.0) + Math.pow(player.posY - pos!!.y, 2.0) + Math.pow(player.posZ - pos!!.z, 2.0))).toFloat() < playRadius!!) {
                    SoundHandler.playSound(pos!!.x, pos!!.y, pos!!.z, dimension!!, playRadius!!, sounds, repeat!!, shuffle!!)
                }
            }

            return null
        }

    }
}
