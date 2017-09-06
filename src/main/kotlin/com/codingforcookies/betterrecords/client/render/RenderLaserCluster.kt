package com.codingforcookies.betterrecords.client.render

import org.lwjgl.opengl.GL11
import net.minecraft.client.renderer.RenderHelper
import com.codingforcookies.betterrecords.ID
import com.codingforcookies.betterrecords.block.tile.TileLaserCluster
import com.codingforcookies.betterrecords.client.model.ModelLaserCluster
import com.codingforcookies.betterrecords.handler.ConfigHandler
import net.minecraft.client.renderer.GlStateManager.*
import net.minecraft.util.ResourceLocation
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer

class RenderLaserCluster : TileEntitySpecialRenderer<TileLaserCluster>() {

    val MODEL = ModelLaserCluster()
    val TEXTURE = ResourceLocation(ID, "textures/models/lasercluster.png")

    override fun renderTileEntityAt(te: TileLaserCluster?, x: Double, y: Double, z: Double, scale: Float, unknown: Int) {

        pushMatrix()

        translate(x.toFloat() + 0.5f, y.toFloat() + 1.5f, z.toFloat() + 0.5f)
        rotate(180f, 0.0f, 0.0f, 1.0f)

        bindTexture(TEXTURE)

        MODEL.render(null, 0f, 0f, 0f, 0.0f, 0.0f, 0.0625f)

        MODEL.renderEmitor(null, 0f, 0f, 0f, 0.0f, 0.0f, 0.0625f)

        te?.let {

            if (te.r != 0.0f && te.g != 0.0f && te.b != 0.0f) {
                disableTexture2D()
                enableBlend()
                color(te.r, te.g, te.b, if (ConfigHandler.flashyMode == 1) .2f else .4f)
            }

            //MODEL.renderEmitter(null, 0f, 0f, 0f, 0.0f, 0.0f, 0.0625f)
            if (te.r != 0.0f && te.g != 0.0f && te.b != 0.0f) {
                disableBlend()
                enableTexture2D()
            }

            color(1f, 1f, 1f, 1f)
            enableLighting()

            translate(0.0f, 1.0f, 0.0f)

            if (te.bass != 0F && ConfigHandler.flashyMode > 0) {
                pushMatrix()

                disableLighting()
                disableTexture2D()
                enableBlend()
                RenderHelper.disableStandardItemLighting()

                glLineWidth(te.bass / 2)

                var pitch = 0f
                while (pitch < 9f) {
                    rotate(200f / 3, 0f, 1f, 0f)
                    var yaw = 0f
                    while (yaw < 18f) {
                        rotate(200f / 9, 0f, 0f, 1f)
                        glBegin(GL11.GL_LINE_STRIP)
                        run {
                            color(te.r, te.g, te.b, if (ConfigHandler.flashyMode == 1) .2f else .4f)
                            GL11.glVertex2f(0f, 0f)

                            val xx = Math.cos(pitch * (Math.PI / 180)).toFloat() * 20f
                            val yy = Math.sin(yaw * (Math.PI / 180)).toFloat() * 20f

                            GL11.glVertex2f(xx, yy)
                        }
                        glEnd()
                        yaw += 1f
                    }
                    pitch += 1f
                }

                glLineWidth(1F)

                RenderHelper.enableStandardItemLighting()
                disableBlend()
                enableTexture2D()
                enableLighting()

                popMatrix()

                color(1f, 1f, 1f, 1f)
            }
        }

        popMatrix()
    }
}
