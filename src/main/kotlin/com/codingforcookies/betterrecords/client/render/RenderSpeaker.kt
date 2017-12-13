package com.codingforcookies.betterrecords.client.render

import com.codingforcookies.betterrecords.ID
import com.codingforcookies.betterrecords.block.BlockSpeaker
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

    override fun render(te: TileSpeaker?, x: Double, y: Double, z: Double, partialTicks: Float, destroyStage: Int, alpha: Float) {


        pushMatrix()

        translate(x + 0.5, y + 1.5, z + 0.5)
        rotate(180F, 0.0F, 0.0F, 1.0F)

        te?.let {
            rotate(te.rotation, 0F, 1F, 0F)
        }

        val size = when(te) {
            null -> BlockSpeaker.SpeakerSize.MEDIUM
            else -> te.size
        }

        bindTexture(when (size) {
            BlockSpeaker.SpeakerSize.SMALL -> TEXTURE_SM
            BlockSpeaker.SpeakerSize.MEDIUM -> TEXTURE_MD
            BlockSpeaker.SpeakerSize.LARGE -> TEXTURE_LG
        })

        when (size) {
            BlockSpeaker.SpeakerSize.SMALL -> MODEL_SM
            BlockSpeaker.SpeakerSize.MEDIUM -> MODEL_MD
            BlockSpeaker.SpeakerSize.LARGE -> MODEL_LG
        }.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F)

        popMatrix()
    }
}
