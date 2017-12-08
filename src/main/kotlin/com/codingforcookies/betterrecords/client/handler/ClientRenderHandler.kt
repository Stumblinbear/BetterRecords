package com.codingforcookies.betterrecords.client.handler

import com.codingforcookies.betterrecords.client.sound.FileDownloader
import com.codingforcookies.betterrecords.client.sound.SoundHandler
import com.codingforcookies.betterrecords.extensions.glMatrix
import com.codingforcookies.betterrecords.handler.ConfigHandler
import com.codingforcookies.betterrecords.util.BetterUtils
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import org.lwjgl.opengl.GL11
import java.awt.Color

object ClientRenderHandler {

    var tutorialText = ""
    var tutorialTime: Long = 0
    var strobeLinger = 0f

    @SubscribeEvent
    fun onClientRender(event: TickEvent.RenderTickEvent) {
        if (event.phase == TickEvent.Phase.END) {
            val mc = Minecraft.getMinecraft()
            val res = ScaledResolution(mc)
            val width = res.scaledWidth
            val height = res.scaledHeight
            val fontRenderer = mc.fontRendererObj
            mc.entityRenderer.setupOverlayRendering()
            if (strobeLinger > 0f) {
                glMatrix {
                        GL11.glDisable(GL11.GL_TEXTURE_2D)
                        GL11.glEnable(GL11.GL_BLEND)

                        GL11.glBegin(GL11.GL_QUADS)
                        GL11.glColor4f(1f, 1f, 1f, strobeLinger)
                        GL11.glVertex2f(width.toFloat(), 0f)
                        GL11.glVertex2f(0f, 0f)
                        GL11.glVertex2f(0f, height.toFloat())
                        GL11.glVertex2f(width.toFloat(), height.toFloat())
                        GL11.glEnd()

                        GL11.glDisable(GL11.GL_BLEND)
                        GL11.glEnable(GL11.GL_TEXTURE_2D)
                }
                strobeLinger -= if (ConfigHandler.flashyMode < 3) 0.01f else 0.2f
            }
            if (tutorialText != "") {
                if (tutorialTime > System.currentTimeMillis()) {
                    val str = BetterUtils.getWordWrappedString(70, tutorialText)
                    var difference = tutorialTime - System.currentTimeMillis()
                    if (difference > 9000)
                        difference = 10000 - difference
                    else if (difference > 1000) difference = 1000
                    glMatrix {
                        GL11.glDisable(GL11.GL_TEXTURE_2D)
                        GL11.glTranslatef((width / 2 - 100).toFloat(), (-50 + difference / 20).toFloat(), 0f)
                        GL11.glEnable(GL11.GL_BLEND)

                        GL11.glBegin(GL11.GL_QUADS)
                        GL11.glColor4f(0f, 0f, 0f, .75f)
                        GL11.glVertex2f(180f, 0f)
                        GL11.glVertex2f(0f, 0f)
                        GL11.glVertex2f(0f, (4 + str.size * 5).toFloat())
                        GL11.glVertex2f(180f, (4 + str.size * 5).toFloat())
                        GL11.glEnd()

                        GL11.glDisable(GL11.GL_BLEND)
                        GL11.glEnable(GL11.GL_TEXTURE_2D)
                        GL11.glScalef(.5f, .5f, 0f)
                        for (i in str.indices)
                            fontRenderer.drawStringWithShadow(str[i], (180 - fontRenderer.getStringWidth(str[i]) / 2).toFloat(), (5 + i * 10).toFloat(), 0xFFFFFF)
                    }
                    GL11.glPopMatrix()
                } else {
                    tutorialText = ""
                    tutorialTime = 0
                }
            }
            if (FileDownloader.isDownloading) {
                glMatrix {
                    GL11.glDisable(GL11.GL_TEXTURE_2D)
                    GL11.glTranslatef((width / 2 - 50).toFloat(), (height - height / 4 + 26).toFloat(), 0f)
                    GL11.glEnable(GL11.GL_BLEND)

                    GL11.glBegin(GL11.GL_QUADS)
                    GL11.glColor4f(0f, 0f, 0f, .8f)
                    GL11.glVertex2f(100f, 0f)
                    GL11.glVertex2f(0f, 0f)
                    GL11.glVertex2f(0f, 4f)
                    GL11.glVertex2f(100f, 4f)
                    GL11.glEnd()

                    GL11.glBegin(GL11.GL_QUADS)
                    GL11.glColor4f(1f, 1f, 1f, .5f)
                    GL11.glVertex2f(FileDownloader.downloadPercent * 100f, 0f)
                    GL11.glVertex2f(0f, 0f)
                    GL11.glVertex2f(0f, 4f)
                    GL11.glVertex2f(FileDownloader.downloadPercent * 100f, 4f)
                    GL11.glEnd()

                    GL11.glDisable(GL11.GL_BLEND)
                    GL11.glEnable(GL11.GL_TEXTURE_2D)
                }
                GL11.glPopMatrix()
                fontRenderer.drawStringWithShadow(BetterUtils.getTranslatedString("overlay.downloading") + ": " + FileDownloader.nowDownloading, (width / 2 - fontRenderer.getStringWidth(BetterUtils.getTranslatedString("overlay.downloading") + ": " + FileDownloader.nowDownloading) / 2).toFloat(), (height - height / 4 + 15).toFloat(), 0xFFFF33)
            }
            if (SoundHandler.nowPlaying != "") {
                if (SoundHandler.nowPlaying.startsWith("Error:")) {
                    fontRenderer.drawStringWithShadow(SoundHandler.nowPlaying, (width / 2 - fontRenderer.getStringWidth(SoundHandler.nowPlaying) / 2).toFloat(), (height - height / 4).toFloat(), 0x990000)
                    return
                } else if (SoundHandler.nowPlaying.startsWith("Info:")) {
                    fontRenderer.drawStringWithShadow(SoundHandler.nowPlaying, (width / 2 - fontRenderer.getStringWidth(SoundHandler.nowPlaying) / 2).toFloat(), (height - height / 4).toFloat(), 0xFFFF33)
                    return
                }
                val f3 = SoundHandler.nowPlayingInt.toFloat()
                val l1 = Color.HSBtoRGB(f3 / 50.0f, 0.7f, 0.6f) and 16777215
                var k1 = (f3 * 255.0f / 20.0f).toInt()
                if (k1 > 255) k1 = 255
                fontRenderer.drawStringWithShadow(BetterUtils.getTranslatedString("overlay.nowplaying") + ": " + SoundHandler.nowPlaying, (width / 2 - fontRenderer.getStringWidth(BetterUtils.getTranslatedString("overlay.nowplaying") + ": " + SoundHandler.nowPlaying) / 2).toFloat(), (height - height / 4).toFloat(), l1 + (k1 shl 24 and -16777216))
            }
        }
    }
}
