package com.codingforcookies.betterrecords.client.render

import org.lwjgl.opengl.GL11
import com.codingforcookies.betterrecords.ID
import com.codingforcookies.betterrecords.block.tile.TileLaser
import com.codingforcookies.betterrecords.client.model.ModelLaser
import com.codingforcookies.betterrecords.handler.ConfigHandler
import net.minecraft.client.renderer.GlStateManager.*
import net.minecraft.util.ResourceLocation
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer

class RenderLaser : TileEntitySpecialRenderer<TileLaser>() {

    val MODEL = ModelLaser()
    val TEXTURE = ResourceLocation(ID, "textures/models/laser.png")

    override fun renderTileEntityAt(te: TileLaser?, x: Double, y: Double, z: Double, scale: Float, destroyStage: Int) {

        pushMatrix()
        
        translate(x.toFloat() + 0.5f, y.toFloat() + 1.5f, z.toFloat() + 0.5f)
        rotate(180f, 0.0f, 0.0f, 1.0f)

        bindTexture(TEXTURE)

        val yaw = te?.yaw ?: 0F
        val pitch = te?.pitch ?: 0F
        MODEL.render(null, pitch, yaw, 0.0f, 0.0f, 0.0f, 0.0625f)

        rotate(-180f, 0.0f, 0.0f, 1.0f)
        translate(0.0f, -.926f, 0.0f)

        te?.let {
            if (te.bass != 0F && ConfigHandler.flashyMode > 0) {
                pushMatrix()

                disableTexture2D()
                enableBlend()
                disableCull()

                rotate(-te.yaw + 180f, 0f, 1f, 0f)
                rotate(-te.pitch + 90f, 1f, 0f, 0f)

                val length = te.length
                val width = te.bass / 400f

                glBegin(GL11.GL_QUADS)

                color(te.r, te.g, te.b, if (ConfigHandler.flashyMode == 1) .3f else .8f)

                glVertex3f(width, 0f, -width)
                glVertex3f(-width, 0f, -width)
                glVertex3f(-width, length.toFloat(), -width)
                glVertex3f(width, length.toFloat(), -width)

                glVertex3f(-width, 0f, width)
                glVertex3f(width, 0f, width)
                glVertex3f(width, length.toFloat(), width)
                glVertex3f(-width, length.toFloat(), width)

                glVertex3f(width, 0f, width)
                glVertex3f(width, 0f, -width)
                glVertex3f(width, length.toFloat(), -width)
                glVertex3f(width, length.toFloat(), width)

                glVertex3f(-width, 0f, -width)
                glVertex3f(-width, 0f, width)
                glVertex3f(-width, length.toFloat(), width)
                glVertex3f(-width, length.toFloat(), -width)

                glEnd()

                enableCull()
                disableBlend()
                enableTexture2D()

                GL11.glPopMatrix()

                color(1f, 1f, 1f, 1f)
            }
        }
        
        popMatrix()
    }
}
