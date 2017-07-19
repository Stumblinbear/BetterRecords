package com.codingforcookies.betterrecords.client.model

import net.minecraft.client.model.ModelBase
import net.minecraft.client.model.ModelRenderer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.entity.Entity
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import org.lwjgl.opengl.GL11

@SideOnly(Side.CLIENT)
class ModelMDSpeaker : ModelBase() {
    var wool: ModelRenderer
    var base: ModelRenderer

    init {
        textureWidth = 64
        textureHeight = 32

        this.wool = ModelRenderer(this, 104, 32).apply {
            setRotationPoint(0.0F, 10.0F, 0.0F)
            addBox(-4.0F, 1.0F, -5.5F, 8, 12, 1)
        }
        this.base = ModelRenderer(this, 64, 32).apply {
            setRotationPoint(0.0F, 10.0F, 0.0F)
            addBox(-5.0F, 0.0F, -5.0F, 10, 14, 10)
        }
    }

    override fun render(entity: Entity?, limbSwing: Float, limbSwingAmount: Float, ageInTick: Float, rotationYaw: Float, rotationPitch: Float, scale: Float) {
        this.wool.render(scale)
        this.base.render(scale)
    }

    fun setRotationAngles(modelRenderer: ModelRenderer, x: Float, y: Float, z: Float) {
        modelRenderer.rotateAngleX = x
        modelRenderer.rotateAngleY = y
        modelRenderer.rotateAngleZ = z
    }
}
