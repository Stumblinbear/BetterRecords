package com.codingforcookies.betterrecords.client.gui.parts

import com.codingforcookies.betterrecords.extensions.glVertices
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraftforge.fml.client.config.GuiButtonExt
import org.lwjgl.opengl.GL11
import java.awt.Color

class GuiButtonLibrary(id: Int, xPos: Int, yPos: Int, width: Int, height: Int, displayString: String, var color: Int)
    : GuiButtonExt(id, xPos, yPos, width, height, displayString) {

    override fun drawButton(mc: Minecraft, mouseX: Int, mouseY: Int, partial: Float) {
        super.drawButton(mc, mouseX, mouseY, partial)

        if (visible) {
            GlStateManager.disableTexture2D()
            glVertices(GL11.GL_QUADS) {
                with (Color(color)) {
                    GlStateManager.color(red / 255F, green / 255F, blue / 255F, .50F)
                }

                val topY = y + 2 // two from the top
                val bottomY = y + height - 3 // three from the bottom
                val rightX = x + width - 2 // two from the right
                val leftX = rightX - 2 // Draw two wide

//                GL11.glVertex2i(leftX, topY) // Top Left
//                GL11.glVertex2i(leftX, bottomY) // Bottom Left
//                GL11.glVertex2i(rightX, bottomY) // Bottom Right
//                GL11.glVertex2i(rightX, topY) // Top Right

                // TEST
                GL11.glVertex2i(x, y) // Top Left
                GL11.glVertex2i(x, y + height) // Bottom Left
                GL11.glVertex2i(x + width, y + height) // Bottom Right
                GL11.glVertex2i(x + width, y) // Top Right
            }
            GlStateManager.enableTexture2D()
        }
    }

}