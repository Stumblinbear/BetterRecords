package com.codingforcookies.betterrecords.network

import com.codingforcookies.betterrecords.block.tile.TileFrequencyTuner
import com.codingforcookies.betterrecords.block.tile.TileRecordEtcher
import com.codingforcookies.betterrecords.extensions.readBlockPos
import com.codingforcookies.betterrecords.extensions.writeBlockPos
import io.netty.buffer.ByteBuf
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.common.network.ByteBufUtils
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext

class PacketURLWrite @JvmOverloads constructor(
        var pos: BlockPos = BlockPos(0,0,0),
        var size: Int = -1,
        var name: String = "",
        var url: String = "",
        var local: String = "",
        // Color and author can actually be
        // considered optional
        var color: Int = -1,
        var author: String = ""
): IMessage {

    override fun toBytes(buf: ByteBuf) {
        writeBlockPos(buf, pos)

        buf.writeInt(size)
        ByteBufUtils.writeUTF8String(buf, name)
        ByteBufUtils.writeUTF8String(buf, url)
        ByteBufUtils.writeUTF8String(buf, local)

        buf.writeInt(color)
        ByteBufUtils.writeUTF8String(buf, author)
    }

    override fun fromBytes(buf: ByteBuf) {
        pos = readBlockPos(buf)

        size = buf.readInt()
        name = ByteBufUtils.readUTF8String(buf)
        url = ByteBufUtils.readUTF8String(buf)
        local = ByteBufUtils.readUTF8String(buf)

        color = buf.readInt()
        author = ByteBufUtils.readUTF8String(buf)

    }

    class Handler : IMessageHandler<PacketURLWrite, IMessage> {

        override fun onMessage(message: PacketURLWrite, ctx: MessageContext): IMessage? {
            val player = ctx.serverHandler.player
            val te = player.world.getTileEntity(message.pos)

            if (te == null || !(te is TileRecordEtcher || te is TileFrequencyTuner)) {
                return null
            }
            with (message) {
                if (te is TileRecordEtcher) {
                    val itemStack = te.record

                    if (!itemStack.isEmpty) {
                        if (!itemStack.hasTagCompound()) {
                            itemStack.tagCompound = NBTTagCompound()
                        }

                        with(itemStack.tagCompound!!) {
                            setString("name", name)
                            setString("url", url)
                            setString("local", local)
                            setInteger("size", size)

                            if (color != -1) {
                                setInteger("color", color)
                            }

                            if (author != "") {
                                setString("author", author)
                            }
                        }

                        val state = player.world.getBlockState(te.pos)
                        player.world.notifyBlockUpdate(te.pos, state, state, 3)
                    }

                } else if (te is TileFrequencyTuner) {
                    val itemStack = te.crystal

                    if (!itemStack.isEmpty) {
                        if (!itemStack.hasTagCompound()) {
                            itemStack.tagCompound = NBTTagCompound()
                        }

                        with (itemStack.tagCompound!!) {
                            setString("url", url)
                            setString("local", local)

                            if (color != -1) {
                                setInteger("color", color)
                            }
                        }

                        val state = player.world.getBlockState(te.pos)
                        player.world.notifyBlockUpdate(te.pos, state, state, 3)
                    }
                }
            }

            return null
        }
    }
}