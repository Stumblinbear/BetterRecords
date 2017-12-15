package com.codingforcookies.betterrecords.client.handler

import com.codingforcookies.betterrecords.client.sound.SoundHandler
import com.codingforcookies.betterrecords.extensions.glMatrix
import com.codingforcookies.betterrecords.handler.ConfigHandler
import com.codingforcookies.betterrecords.item.ItemWire
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.util.SoundCategory
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import org.lwjgl.opengl.GL11

object RenderEventHandler {

    @SubscribeEvent
    fun onRenderEvent(event: RenderWorldLastEvent) {
        val mc = Minecraft.getMinecraft()
        ItemWire.connection?.let {
            if (mc.player.heldItemMainhand.isEmpty || mc.player.heldItemMainhand.item !is ItemWire) {
                ItemWire.connection = null
            } else {
                glMatrix {
                    GlStateManager.disableTexture2D()

                    val dx = (mc.player.prevPosX + (mc.player.posX - mc.player.prevPosX) * event.partialTicks).toFloat()
                    val dy = (mc.player.prevPosY + (mc.player.posY - mc.player.prevPosY) * event.partialTicks).toFloat()
                    val dz = (mc.player.prevPosZ + (mc.player.posZ - mc.player.prevPosZ) * event.partialTicks).toFloat()
                    val x1 = -(dx - if (ItemWire.connection!!.fromHome) ItemWire.connection!!.x1 else ItemWire.connection!!.x2)
                    val y1 = -(dy - if (ItemWire.connection!!.fromHome) ItemWire.connection!!.y1 else ItemWire.connection!!.y2)
                    val z1 = -(dz - if (ItemWire.connection!!.fromHome) ItemWire.connection!!.z1 else ItemWire.connection!!.z2)

                    GlStateManager.translate(x1 + 0.5F, y1 + 0.5F, z1 + 0.5F)
                    GlStateManager.glLineWidth(2F)
                    GlStateManager.color(0F, 0F, 0F)

                    GlStateManager.glBegin(GL11.GL_LINE_STRIP)
                    GlStateManager.glVertex3f(0F, 0F, 0F)
                    GlStateManager.glVertex3f(0F, 3F, 0F)
                    GlStateManager.glEnd()

                    if (ConfigHandler.devMode && ItemWire.connection!!.fromHome) {
                        // TODO: Clean up this
                        if (SoundHandler.soundPlaying.containsKey(ItemWire.connection!!.x1.toString() + "," + ItemWire.connection!!.y1 + "," + ItemWire.connection!!.z1 + "," + mc.world.provider.dimension)) {
                            val radius = SoundHandler.soundPlaying[ItemWire.connection!!.x1.toString() + "," + ItemWire.connection!!.y1 + "," + ItemWire.connection!!.z1 + "," + mc.world.provider.dimension]!!.currentSong!!.playRadius

                            GlStateManager.disableCull()
                            GlStateManager.enableBlend()
                            GlStateManager.color(0.1F, 0.1F, 0.1F, 0.2F)

                            GlStateManager.glBegin(GL11.GL_LINE_STRIP)
                            GL11.glVertex2f(0F, 0F)
                            GL11.glVertex2f(0F, radius + 10F)
                            GlStateManager.glEnd()

                            val factor = Math.PI * 2 / 45

                            // TODO: Eliminate nested function
                            fun draw(rad: Float) {
                                var phi = 0.0
                                while (phi <= Math.PI / 1.05) {
                                    GL11.glBegin(GL11.GL_QUAD_STRIP)
                                    var theta = 0.0
                                    while (theta <= Math.PI * 2 + factor) {
                                        var x = rad.toDouble() * Math.sin(phi) * Math.cos(theta)
                                        var y = -rad * Math.cos(phi)
                                        var z = rad.toDouble() * Math.sin(phi) * Math.sin(theta)
                                        GL11.glVertex3d(x, y, z)
                                        x = rad.toDouble() * Math.sin(phi + factor) * Math.cos(theta)
                                        y = -rad * Math.cos(phi + factor)
                                        z = rad.toDouble() * Math.sin(phi + factor) * Math.sin(theta)
                                        GL11.glVertex3d(x, y, z)
                                        theta += factor
                                    }
                                    GL11.glEnd()
                                    phi += factor
                                }
                            }

                            draw(radius)
                            val volumeRadius = radius * (Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.MASTER) * Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.RECORDS))
                            GlStateManager.color(1F, 0.1F, 0.1F, 0.2F)
                            draw(volumeRadius)

                            GlStateManager.disableBlend()
                            GlStateManager.enableCull()
                        }
                    }
                    GlStateManager.color(1F, 1F, 1F)
                    GlStateManager.enableTexture2D()
                }
            }
        }
    }
}
