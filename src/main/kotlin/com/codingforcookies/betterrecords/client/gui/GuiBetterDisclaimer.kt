package com.codingforcookies.betterrecords.client.gui

import com.codingforcookies.betterrecords.ModConfig
import com.codingforcookies.betterrecords.extensions.glMatrix
import com.codingforcookies.betterrecords.extensions.glVertices
import com.codingforcookies.betterrecords.util.BetterUtils
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.resources.I18n
import org.lwjgl.opengl.GL11

class GuiBetterDisclaimer : GuiScreen() {

    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        super.mouseClicked(mouseX, mouseY, mouseButton)

        val x = width / 2 - 100
        val y = height / 2 - 35

        if (mouseX > x + 5 && mouseX < x + 45 && mouseY > y + 50 && mouseY < y + 65) {
            ModConfig.client.flashMode = 0
        } else if (mouseX > x + 50 && mouseX < x + 90 && mouseY > y + 50 && mouseY < y + 65) {
            ModConfig.client.flashMode = 1
        } else if (mouseX > x + 95 && mouseX < x + 150 && mouseY > y + 50 && mouseY < y + 65) {
            ModConfig.client.flashMode = 2
        } else if (mouseX > x + 155 && mouseX < x + 195 && mouseY > y + 50 && mouseY < y + 65) {
            ModConfig.client.flashMode = 3
        }

        if (ModConfig.client.flashMode != -1) {
            ModConfig.update()
            Minecraft.getMinecraft().displayGuiScreen(null)
        }

    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        val x = width / 2 - 100
        val y = height / 2 - 35

        glMatrix {
            GlStateManager.translate(x.toFloat(), y.toFloat(), 0F)
            GlStateManager.disableTexture2D()
            GlStateManager.enableBlend()

            glVertices(GL11.GL_QUADS) {
                GlStateManager.color(0F, 0F, 0F, 0.5F)
                GL11.glVertex2i(200, 0)
                GL11.glVertex2i(0, 0)
                GL11.glVertex2i(0, 70)
                GL11.glVertex2i(200, 70)
            }

            glVertices(GL11.GL_QUADS) {
                GlStateManager.color(1F, 1F, 1F, 0.75F)
                GL11.glVertex2i(45, 50)
                GL11.glVertex2i(5, 50)
                GL11.glVertex2i(5, 65)
                GL11.glVertex2i(45, 65)
            }

            glVertices(GL11.GL_QUADS) {
                GlStateManager.color(1F, 1F, 1F, 0.75F)
                GL11.glVertex2i(90, 50)
                GL11.glVertex2i(50, 50)
                GL11.glVertex2i(50, 65)
                GL11.glVertex2i(90, 65)
            }

            glVertices(GL11.GL_QUADS) {
                GlStateManager.color(1F, 1F, 1F, 0.75F)
                GL11.glVertex2i(150, 50)
                GL11.glVertex2i(95, 50)
                GL11.glVertex2i(95, 65)
                GL11.glVertex2i(150, 65)
            }

            glVertices(GL11.GL_QUADS) {
                GlStateManager.color(1F, 1F, 1F, 0.75F)
                GL11.glVertex2i(195, 50)
                GL11.glVertex2i(155, 50)
                GL11.glVertex2i(155, 65)
                GL11.glVertex2i(195, 65)
            }

            GlStateManager.enableBlend()
            GlStateManager.enableTexture2D()
        }

        val title = I18n.format("gui.betterrecords.disclaimer.title")
        fontRenderer.drawString(
                title,
                (width - fontRenderer.getStringWidth(title)) / 2,
                y + 6,
                0xFFFFFF
        )

        glMatrix {
            GlStateManager.scale(0.5F, 0.5F, 0F)

            BetterUtils.getWordWrappedString(75, I18n.format("gui.betterrecords.disclaimer.warning"))
                    .forEachIndexed { index, it ->
                        fontRenderer.drawString(it, x * 2 + 6, y * 2 + 42 + index * 12, 0xFFFFFF)
                    }
        }

        with (fontRenderer) {
            drawString(I18n.format("gui.betterrecords.disclaimer.flashmode.none"), x + 13, y + 54, 0x000000)
            drawString(I18n.format("gui.betterrecords.disclaimer.flashmode.low"), x + 62, y + 54, 0x000000)
            drawString(I18n.format("gui.betterrecords.disclaimer.flashmode.norm"), x + 107, y + 54, 0x000000)
            drawString(I18n.format("gui.betterrecords.disclaimer.flashmode.rave"), x + 165, y + 54, 0x000000)
        }

        if (mouseX in (x + 5)..(x + 45) && mouseY in (y + 50)..(y + 65)) {
            drawHoveringText(
                    listOf(I18n.format("gui.betterrecords.disclaimer.flashmode.none"),
                            "\u00a77" + I18n.format("gui.betterrecords.disclaimer.flashmode.none.line1")),
                    mouseX, mouseY, fontRenderer
            )
        } else if (mouseX in (x + 50)..(x + 90) && mouseY in (y + 50)..(y + 65)) {
            drawHoveringText(
                    listOf(I18n.format("gui.betterrecords.disclaimer.flashmode.low"),
                            "\u00a77" + I18n.format("gui.betterrecords.disclaimer.flashmode.low.line1")),
                    mouseX, mouseY, fontRenderer
            )
        } else if (mouseX in (x + 95)..(x + 150) && mouseY in (y + 50)..(y + 65)) {
            drawHoveringText(
                    listOf(I18n.format("gui.betterrecords.disclaimer.flashmode.norm"),
                            "\u00a77" + I18n.format("gui.betterrecords.disclaimer.flashmode.norm.line1")),
                    mouseX, mouseY, fontRenderer
            )
        } else if (mouseX in (x + 155)..(x + 195) && mouseY in (y + 50)..(y + 65)) {
            drawHoveringText(
                    listOf(I18n.format("gui.betterrecords.disclaimer.flashmode.rave"),
                            "\u00a77" + I18n.format("gui.betterrecords.disclaimer.flashmode.rave.line1"),
                            "\u00a77" + I18n.format("gui.betterrecords.disclaimer.flashmode.rave.line2"),
                            "\u00a77" + I18n.format("gui.betterrecords.disclaimer.flashmode.rave.line3"),
                            "\u00a77" + I18n.format("gui.betterrecords.disclaimer.flashmode.rave.line4"),
                            "\u00a77" + I18n.format("gui.betterrecords.disclaimer.flashmode.rave.line5")),
                    mouseX, mouseY, fontRenderer
            )
        }
    }
}
