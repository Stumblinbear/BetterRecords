package com.codingforcookies.betterrecords.client.render.helper

import com.codingforcookies.betterrecords.api.wire.IRecordWireHome
import com.codingforcookies.betterrecords.api.wire.IRecordWireManipulator
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.util.math.BlockPos
import org.lwjgl.opengl.GL11

fun renderConnectionsAndInfo(te: IRecordWireHome, pos: BlockPos, x: Double, y: Double, z: Double) {
    (Minecraft.getMinecraft().player.heldItemMainhand.item as? IRecordWireManipulator)?.let {
        GlStateManager.pushMatrix()

        GlStateManager.translate(x + 0.5, y + 0.5, z + 0.5)

        //region RENDER_CONNECTIONS
        if (te.connections.size > 0) {
            GlStateManager.color(0F, 0F, 0F)
            GlStateManager.disableTexture2D()

            GlStateManager.glLineWidth(2F)

            for (rec in te.connections) {
                val x1 = -(pos.x - rec.x2).toFloat()
                val y1 = -(pos.y - rec.y2).toFloat()
                val z1 = -(pos.z - rec.z2).toFloat()

                GlStateManager.pushMatrix()

                GlStateManager.glBegin(GL11.GL_LINE_STRIP)
                GlStateManager.glVertex3f(0F, 0F, 0F)
                GlStateManager.glVertex3f(x1, y1, z1)
                GlStateManager.glEnd()

                GlStateManager.popMatrix()
            }

            GlStateManager.enableTexture2D()
            GlStateManager.color(1F, 1F, 1F)
        }
        //endregion RENDER_CONNECTIONS

        GlStateManager.scale(0.01F, -0.01F, 0.01F)
        GlStateManager.rotate(-Minecraft.getMinecraft().renderManager.playerViewY - 180F, 0F, 1F, 0F)

        GlStateManager.color(1F, 1F, 1F)
        var currentY = te.wireSystemInfo.size * -10 - 75
        val fontRenderer = Minecraft.getMinecraft().fontRenderer
        val radiusString = "Play Radius: ${te.songRadius}"
        fontRenderer.drawString(radiusString, -fontRenderer.getStringWidth(radiusString) / 2, currentY, 0xFFFFFF)
        for (info in te.wireSystemInfo.entries) {
            currentY += 10
            val infoString = "${info.value}x ${info.key}"
            fontRenderer.drawString(infoString, -fontRenderer.getStringWidth(infoString) / 2, currentY, 0xFFFFFF)
        }

        GlStateManager.popMatrix()
    }
}
