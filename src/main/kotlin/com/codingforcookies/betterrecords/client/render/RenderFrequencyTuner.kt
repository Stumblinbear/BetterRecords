package com.codingforcookies.betterrecords.client.render

import com.codingforcookies.betterrecords.ID
import com.codingforcookies.betterrecords.block.tile.TileFrequencyTuner
import net.minecraft.util.ResourceLocation
import com.codingforcookies.betterrecords.client.model.ModelFrequencyTuner
import net.minecraft.client.renderer.GlStateManager.*

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer

class RenderFrequencyTuner : TileEntitySpecialRenderer<TileFrequencyTuner>() {

    val MODEL = ModelFrequencyTuner()
    val TEXTURE = ResourceLocation(ID, "textures/models/frequencytuner.png")

    override fun renderTileEntityAt(te: TileFrequencyTuner?, x: Double, y: Double, z: Double, partialTicks: Float, destroyStage: Int) {

        pushMatrix()

        translate(x + 0.5F, y + 1.5F, z + 0.5F)
        rotate(180F, 0F, 0F, 1F)

        te?.let {
            rotate(te.blockMetadata * 90 + 180.toFloat(), 0F, 1F, 0F)
        }

        bindTexture(TEXTURE)

        val crystalFloat = te?.crystalFloaty ?: 0F
        MODEL.render(null, crystalFloat, 0F, 0F, 0F, 0F, 0.0625F, te?.crystal)

        popMatrix()
    }
}
