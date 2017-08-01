package com.codingforcookies.betterrecords.client.model

import net.minecraft.client.model.ModelBase
import net.minecraft.client.model.ModelRenderer
import net.minecraft.entity.Entity

class ModelLaserCluster : ModelBase() {
    internal var Base: ModelRenderer
    internal var Emitor: ModelRenderer
    internal var Case: ModelRenderer

    init {
        textureWidth = 64
        textureHeight = 64

        this.Emitor = ModelRenderer(this, 0, 22).apply {
            setRotationPoint(-3.0F, 13.0F, -3.0F)
            addBox(0.0F, 0.0F, 0.0F, 6, 8, 6)
        }
        this.Base = ModelRenderer(this, 0, 0).apply {
            setRotationPoint(0.0F, 21.0F, 0.0F)
            addBox(-6.0F, 0.0F, -6.0F, 12, 3, 12)
        }
        this.Case = ModelRenderer(this, 0, 43).apply {
            setRotationPoint(0.0F, 21.0F, 0.0F)
            addBox(-6.0F, -9.0F, -6.0F, 12, 9, 12)
        }
    }

    override fun render(entity: Entity?, f: Float, f1: Float, f2: Float, f3: Float, f4: Float, f5: Float) {
        super.render(entity, f, f1, f2, f3, f4, f5)
        setRotationAngles(f, f1, f2, f3, f4, f5, entity)
        Base.render(f5)
        Case.render(f5)
    }

    fun renderEmitor(entity: Entity?, f: Float, f1: Float, f2: Float, f3: Float, f4: Float, f5: Float) {
        super.render(entity, f, f1, f2, f3, f4, f5)
        setRotationAngles(f, f1, f2, f3, f4, f5, entity)
        Emitor.render(f5)
    }

    private fun setRotationAngles(model: ModelRenderer, x: Float, y: Float, z: Float) {
        model.rotateAngleX = x
        model.rotateAngleY = y
        model.rotateAngleZ = z
    }
}
