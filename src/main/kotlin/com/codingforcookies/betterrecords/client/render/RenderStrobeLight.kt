package com.codingforcookies.betterrecords.client.render

import com.codingforcookies.betterrecords.ID
import com.codingforcookies.betterrecords.ModConfig
import com.codingforcookies.betterrecords.block.tile.TileStrobeLight
import com.codingforcookies.betterrecords.client.model.ModelStrobeLight
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager.*
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11

class RenderStrobeLight : TileEntitySpecialRenderer<TileStrobeLight>() {

    val MODEL = ModelStrobeLight()
    val TEXTURE = ResourceLocation(ID, "textures/models/strobelight.png")

    override fun render(te: TileStrobeLight?, x: Double, y: Double, z: Double, scale: Float, destroyStage: Int, alpha: Float) {

        pushMatrix()
        
        translate(x.toFloat() + 0.5f, y.toFloat() + 1.5f, z.toFloat() + 0.5f)
        rotate(180f, 0.0f, 0.0f, 1.0f)

        bindTexture(TEXTURE)
        MODEL.render(null, 0f, 0f, 0f, 0.0f, 0.0f, 0.0625f)

        translate(0.0f, 1.0f, 0.0f)
        
        te?.let {

            if (te.bass != 0F && ModConfig.client.flashMode > 0) {
                val incr = (2 * Math.PI / 10).toFloat()

                pushMatrix()
                
                rotate(Minecraft.getMinecraft().renderManager.playerViewY - 180f, 0f, 1f, 0f)
                rotate(Minecraft.getMinecraft().renderManager.playerViewX, 1f, 0f, 0f)

                disableDepth()
                disableLighting()
                disableTexture2D()
                enableBlend()

                var trans = .2f
                while (trans < .6f) {
                    scale(.9f, .9f, 1f)
                    rotate(20f, 0f, 0f, 1f)
                    glBegin(GL11.GL_TRIANGLE_FAN)

                    color(1f, 1f, 1f, trans / if (ModConfig.client.flashMode == 1) 3f else 1f)
                    GL11.glVertex2f(0f, 0f)

                    for (i in 0..9) {
                        val angle = incr * i
                        val xx = Math.cos(angle.toDouble()).toFloat() * te.bass
                        val yy = Math.sin(angle.toDouble()).toFloat() * te.bass

                        GL11.glVertex2f(xx, yy)
                    }

                    GL11.glVertex2f(te.bass, 0f)

                    glEnd()
                    trans += .2f
                }

                disableBlend()
                enableTexture2D()
                enableLighting()
                enableDepth()
                
                popMatrix()

                color(1f, 1f, 1f, 1f)

                if (ModConfig.client.flashMode > 1) {
                    val mc = Minecraft.getMinecraft()
                    val dist = Math.sqrt(Math.pow(te.getPos().getX() - mc.player.posX, 2.0) + Math.pow(te.getPos().getY() - mc.player.posY, 2.0) + Math.pow(te.getPos().getZ() - mc.player.posZ, 2.0)).toFloat()
                    if (dist < 4 * te.bass) {
                        val newStrobe = Math.abs(dist - 4f * te.bass) / 100f
                        // TODO if (newStrobe > 0f && BetterEventHandler.strobeLinger < newStrobe)
                            //BetterEventHandler.strobeLinger = newStrobe
                    }
                }
            }
        }
        
        popMatrix()
    }
}
