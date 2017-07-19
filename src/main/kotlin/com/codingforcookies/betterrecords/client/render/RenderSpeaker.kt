package com.codingforcookies.betterrecords.client.render

import com.codingforcookies.betterrecords.ID
import com.codingforcookies.betterrecords.block.tile.TileSpeaker
import com.codingforcookies.betterrecords.client.model.ModelLGSpeaker
import com.codingforcookies.betterrecords.client.model.ModelMDSpeaker
import com.codingforcookies.betterrecords.client.model.ModelSMSpeaker
import net.minecraft.client.renderer.GlStateManager.*
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.util.ResourceLocation

class RenderSpeaker : TileEntitySpecialRenderer<TileSpeaker>() {

    val MODEL_SM = ModelSMSpeaker()
    val TEXTURE_SM = ResourceLocation(ID, "textures/models/smspeaker.png")

    val MODEL_MD = ModelMDSpeaker()
    val TEXTURE_MD = ResourceLocation(ID, "textures/models/mdspeaker.png")

    val MODEL_LG = ModelLGSpeaker()
    val TEXTURE_LG = ResourceLocation(ID, "textures/models/lgspeaker.png")

    override fun renderTileEntityAt(te: TileSpeaker?, x: Double, y: Double, z: Double, partialTicks: Float, destroyStage: Int) {
        var size = -1

        pushMatrix()

        translate(x + 0.5, y + 1.5, z + 0.5)
        rotate(180F, 0.0F, 0.0F, 1.0F)

        te?.let {
            rotate(te.rotation, 0F, 1F, 0F)
            size = te.type
        }

        bindTexture(when (size) {
            0 -> TEXTURE_SM
            1 -> TEXTURE_MD
            2 -> TEXTURE_LG
            else -> TEXTURE_MD
        })

        when (size) {
            0 -> MODEL_SM
            1 -> MODEL_MD
            2 -> MODEL_LG
            else -> MODEL_MD
        }.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F)

        popMatrix()
    }
}
