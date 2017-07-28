package com.codingforcookies.betterrecords.client.handler

import com.codingforcookies.betterrecords.block.tile.TileSpeaker
import net.minecraft.client.renderer.GlStateManager
import net.minecraftforge.client.event.DrawBlockHighlightEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT)
class SpeakerBoundingBoxRenderHandler {

    @SubscribeEvent
    fun onSpeakerRender(event: DrawBlockHighlightEvent) {
        val pos = event.target.blockPos

        (event.player.world.getTileEntity(pos) as? TileSpeaker)?.let { te ->

            GlStateManager.pushMatrix()

            // TODO: Translate to location
            GlStateManager.rotate(te.rotation, 0F, 1F, 0F)
            event.context.drawSelectionBox(event.player, event.target, 0, event.partialTicks)

            GlStateManager.popMatrix()

            event.isCanceled = true
        }
    }
}
