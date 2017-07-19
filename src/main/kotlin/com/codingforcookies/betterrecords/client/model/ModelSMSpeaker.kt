package com.codingforcookies.betterrecords.client.model

import net.minecraft.client.model.ModelBase
import net.minecraft.client.model.ModelRenderer
import net.minecraft.entity.Entity
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@SideOnly(Side.CLIENT)
class ModelSMSpeaker : ModelBase() {
    var wool: ModelRenderer
    var base: ModelRenderer

    init {
        textureWidth = 64
        textureHeight = 32

        this.wool = ModelRenderer(this, 96, 32).apply {
            setRotationPoint(0.0F, 14.0F, 0.0F)
            addBox(-3.0F, 1.0F, -4.5F, 6, 8, 1)
        }
        this.base = ModelRenderer(this, 64, 32).apply {
            setRotationPoint(0.0F, 14.0F, 0.0F)
            addBox(-4.0F, 0.0F, -4.0F, 8, 10, 8)
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
