package com.codingforcookies.betterrecords.client.render

import com.codingforcookies.betterrecords.ID
import com.codingforcookies.betterrecords.block.tile.TileRadio
import com.codingforcookies.betterrecords.client.model.ModelRadio
import com.codingforcookies.betterrecords.client.render.helper.renderConnectionsAndInfo
import net.minecraft.client.renderer.GlStateManager.*
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.util.ResourceLocation

class RenderRadio : TileEntitySpecialRenderer<TileRadio>() {

    val MODEL = ModelRadio()
    val TEXTURE = ResourceLocation(ID, "textures/models/radio.png")

    override fun render(te: TileRadio?, x: Double, y: Double, z: Double, partialTicks: Float, destroyStage: Int, alpha: Float) {
        pushMatrix()

        translate(x + 0.5, y + 1.5, z + 0.5)
        rotate(180F, 0.0F, 0.0F, 1.0F)

        te?.let {
            rotate(te.blockMetadata * 90 + 180F, 0.0F, 1.0F, 0.0F)
        }

        bindTexture(TEXTURE)

        val openAmount = te?.openAmount ?: 0F
        val crystalFloat = te?.crystalFloaty ?: 0F
        MODEL.render(null, openAmount, crystalFloat, 0F, 0F, 0F, 0.0625F, te?.crystal)

        popMatrix()

        te?.let {
            renderConnectionsAndInfo(te, te.pos, x , y ,z)
        }
    }
}
