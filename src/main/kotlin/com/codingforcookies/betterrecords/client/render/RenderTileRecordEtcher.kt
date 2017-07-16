package com.codingforcookies.betterrecords.client.render

import com.codingforcookies.betterrecords.ID
import com.codingforcookies.betterrecords.block.tile.TileRecordEtcher
import com.codingforcookies.betterrecords.client.model.ModelRecordEtcher
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager.*
import net.minecraft.client.renderer.block.model.ItemCameraTransforms
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.util.ResourceLocation

class RenderTileRecordEtcher : TileEntitySpecialRenderer<TileRecordEtcher>() {

    val MODEL = ModelRecordEtcher()
    val TEXTURE = ResourceLocation(ID, "textures/models/recordetcher.png")

    override fun renderTileEntityAt(te: TileRecordEtcher?, x: Double, y: Double, z: Double, partialTicks: Float, destroyStage: Int) {

        te?.recordEntity?.let {
            pushMatrix()

            translate(x + 0.5F, y + .65F, z + 0.5F)
            rotate(90F, 1F, 0F, 0F)
            rotate(te.recordRotation * 57.3F, 0F, 0F, 1F)

            Minecraft.getMinecraft().renderItem.renderItem(it.entityItem, ItemCameraTransforms.TransformType.NONE)

            popMatrix()
        }

        pushMatrix()
        translate(x + 0.5F, y + 1.5F, z + 0.5F)
        rotate(180F, 0F, 0F, 1F)
        bindTexture(TEXTURE)

        val needleLocation = te?.needleLocation ?: 0F
        val recordRotation = te?.recordRotation ?: 0F
        MODEL.render(null, needleLocation, recordRotation, 0F, 0F, 0F, 0.0625F)

        popMatrix()
    }
}
