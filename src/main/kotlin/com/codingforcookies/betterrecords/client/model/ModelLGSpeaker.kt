package com.codingforcookies.betterrecords.client.model

import net.minecraft.client.model.ModelBase
import net.minecraft.client.model.ModelRenderer
import net.minecraft.entity.Entity
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@SideOnly(Side.CLIENT)
class ModelLGSpeaker : ModelBase() {
    var wool: ModelRenderer
    var base: ModelRenderer

    init {
        textureWidth = 128
        textureHeight = 64

        this.wool = ModelRenderer(this, 176, 64).apply {
            setRotationPoint(0.0F, 0.0F, 0.0F)
            addBox(-5.0F, 1.0F, -6.5F, 10, 22, 1)
            mirror = true
        }
        this.base = ModelRenderer(this, 128, 64).apply {
            setRotationPoint(0.0F, 0.0F, 0.0F)
            addBox(-6.0F, 0.0F, -6.0F, 12, 24, 12)
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
