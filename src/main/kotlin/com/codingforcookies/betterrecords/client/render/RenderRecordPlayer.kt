package com.codingforcookies.betterrecords.client.render

import org.lwjgl.opengl.GL11
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.block.model.ItemCameraTransforms
import com.codingforcookies.betterrecords.ID
import com.codingforcookies.betterrecords.api.wire.IRecordWireManipulator
import com.codingforcookies.betterrecords.block.tile.TileRecordPlayer
import net.minecraft.util.ResourceLocation
import com.codingforcookies.betterrecords.client.model.ModelRecordPlayer
import com.codingforcookies.betterrecords.client.render.helper.renderConnectionsAndInfo
import net.minecraft.client.renderer.GlStateManager.*
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer

class RenderRecordPlayer : TileEntitySpecialRenderer<TileRecordPlayer>() {

    val MODEL = ModelRecordPlayer()
    val TEXTURE = ResourceLocation(ID, "textures/models/recordplayer.png")

    override fun renderTileEntityAt(te: TileRecordPlayer?, x: Double, y: Double, z: Double, scale: Float, destroyStage: Int) {

        //region RENDER_BLOCK
        pushMatrix()

        translate(x.toFloat() + 0.5f, y.toFloat() + 1.5f, z.toFloat() + 0.5f)
        rotate(180f, 0.0f, 0.0f, 1.0f)

        val metadata = te?.blockMetadata ?: 0
        rotate(metadata * 90 + 180F, 0.0f, 1.0f, 0.0f)

        bindTexture(TEXTURE)

        val openAmount = te?.openAmount ?: 0F
        val needleLocation = te?.needleLocation ?: 0F
        val recordRotation = te?.recordRotation ?: 0F
        MODEL.render(null, openAmount, needleLocation, recordRotation, 0.0f, 0.0f, 0.0625f)

        popMatrix()
        //endregion RENDER_BLOCK

        te?.let {
            //region RENDER_RECORD
            te.recordEntity?.let {
                pushMatrix()

                translate(x.toFloat() + .5f, y.toFloat() + .76f, z.toFloat() + .5f)
                rotate(90f, 1f, 0f, 0f)
                rotate(te.recordRotation * 57.3f, 0f, 0f, 1f)
                disableLighting()
                disableCull()
                Minecraft.getMinecraft().renderItem.renderItem(te.recordEntity?.entityItem, ItemCameraTransforms.TransformType.NONE)
                enableLighting()
                enableCull()
                color(1f, 1f, 1f)

                popMatrix()
            }
            //endregion RENDER_RECORD

            renderConnectionsAndInfo(te, te.pos, x, y ,z)
        }
    }
}
