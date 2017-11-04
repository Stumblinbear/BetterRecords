package com.codingforcookies.betterrecords.client.handler

import com.codingforcookies.betterrecords.BetterRecords
import com.codingforcookies.betterrecords.handler.ConfigHandler
import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

object ClientTickHandler {

    @SubscribeEvent
    fun showDisclaimer(event: TickEvent.ClientTickEvent) {
        if (event.phase == TickEvent.Phase.END) {
            Minecraft.getMinecraft().player?.let {
                if (ConfigHandler.flashyMode < 0) {
                    it.openGui(BetterRecords, 2, Minecraft.getMinecraft().world, 0, 0, 0)
                }
            }
        }
    }
}
