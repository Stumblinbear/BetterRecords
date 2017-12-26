package com.codingforcookies.betterrecords.network

import com.codingforcookies.betterrecords.NETWORK_CHANNEL
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
import net.minecraftforge.fml.relauncher.Side.CLIENT

object PacketHandler {

    private val HANDLER = SimpleNetworkWrapper(NETWORK_CHANNEL)

    fun init() {
      HANDLER.registerMessage(PacketRecordPlay.Handler::class.java, PacketRecordPlay::class.java, 0, CLIENT)
    }

    fun sendToAll(msg: IMessage) {
      HANDLER.sendToAll(msg)
    }
}