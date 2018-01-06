package com.codingforcookies.betterrecords.client.model

import net.minecraft.client.model.ModelBase
import net.minecraft.client.model.ModelRenderer
import net.minecraft.client.renderer.GlStateManager.*
import net.minecraft.entity.Entity
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@SideOnly(Side.CLIENT)
class ModelLaser : ModelBase() {
    var Stand: ModelRenderer
    var Front: ModelRenderer
    var Box: ModelRenderer
    var Base: ModelRenderer

    init {
        textureWidth = 64
        textureHeight = 32

        this.Stand = ModelRenderer(this, 0, 16).apply {
            setRotationPoint(0.0F, 21.0F, 0.0F)
            addBox(-1.5F, -6.0F, -1.5F, 3, 6, 3)
        }
        this.Front = ModelRenderer(this, 0, 25).apply {
            setRotationPoint(0.0F, 15.0F, 0.0F)
            addBox(-2.0F, -2.0F, -6.0F, 4, 4, 1)
        }
        this.Box = ModelRenderer(this, 0, 0).apply {
            setRotationPoint(0.0F, 15.0F, 0.0F)
            addBox(-3.0F, -3.0F, -5.0F, 6, 6, 10)
        }
        this.Base = ModelRenderer(this, 12, 16).apply {
            setRotationPoint(0.0F, 21.0F, 0.0F)
            addBox(-4.0F, 0.0F, -4.0F, 8, 3, 8)
        }
    }

    override fun render(entity: Entity?, pitch: Float, yaw: Float, f2: Float, f3: Float, f4: Float, scale: Float) {
        super.render(entity, pitch, yaw, f2, f3, f4, scale)

        Stand.render(scale)
        Base.render(scale)

        pushMatrix()

        rotate(yaw, 0F, 1F, 0F)
        translate(0F, .95F, 0F)
        rotate(-pitch, 1F, 0F, 0F)
        translate(0F, -.95F, 0F)

        Front.render(scale)
        Box.render(scale)

        popMatrix()
    }

    fun setRotationAngles(modelRenderer: ModelRenderer, x: Float, y: Float, z: Float) {
        modelRenderer.rotateAngleX = x
        modelRenderer.rotateAngleY = y
        modelRenderer.rotateAngleZ = z
    }
}
