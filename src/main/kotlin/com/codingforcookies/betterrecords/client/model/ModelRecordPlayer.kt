package com.codingforcookies.betterrecords.client.model

import com.codingforcookies.betterrecords.extensions.glMatrix
import net.minecraft.client.model.ModelBase
import net.minecraft.client.model.ModelRenderer
import net.minecraft.entity.Entity
import org.lwjgl.opengl.GL11

class ModelRecordPlayer : ModelBase() {
    internal var Lid: ModelRenderer
    internal var Case: ModelRenderer
    internal var Holder: ModelRenderer
    internal var Needle: ModelRenderer
    internal var NeedleStand: ModelRenderer

    init {
        textureWidth = 64
        textureHeight = 64

        Lid = ModelRenderer(this, 0, 27).apply {
            addBox(-7f, -3f, -14f, 14, 3, 14)
            setRotationPoint(0f, 12f, 7f)
            setTextureSize(64, 64)
            mirror = true
        }
        setRotation(Lid, 0f, 0f, 0f)

        Case = ModelRenderer(this, 0, 0).apply {
            addBox(0f, 0f, 0f, 15, 12, 15)
            setRotationPoint(-7.5f, 12f, -7.5f)
            setTextureSize(64, 64)
            mirror = true
        }
        setRotation(Case, 0f, 0f, 0f)

        Holder = ModelRenderer(this, 0, 0).apply {
            addBox(-0.5f, 0f, -0.5f, 1, 1, 1)
            setRotationPoint(0f, 11f, 0f)
            setTextureSize(64, 64)
            mirror = true
        }
        setRotation(Holder, 0f, 0f, 0f)

        Needle = ModelRenderer(this, 0, 44).apply {
            addBox(-0.5f, -0.5f, -0.5f, 1, 1, 8)
            setRotationPoint(5f, 11f, 5f)
            setTextureSize(64, 64)
            mirror = true
        }
        setRotation(Needle, 0f, 3f, 0f)

        NeedleStand = ModelRenderer(this, 0, 44).apply {
            addBox(-0.5f, -0.5f, -0.5f, 1, 1, 1)
            setRotationPoint(5f, 12f, 5f)
            setTextureSize(64, 64)
            mirror = true
        }
        setRotation(NeedleStand, 0f, 0f, 0f)
    }

    override fun render(entity: Entity?, f: Float, f1: Float, f2: Float, f3: Float, f4: Float, f5: Float) {
        super.render(entity, f, f1, f2, f3, f4, f5)
        setRotationAngles(f, f1, f2, f3, f4, f5, entity)

        Lid.rotateAngleX = f
        Needle.rotateAngleY = 3f + f1
        Holder.rotateAngleY = f2

        Needle.rotateAngleX = f2 / 10 % 0.025f

        glMatrix {
            GL11.glDisable(GL11.GL_CULL_FACE)
            Lid.render(f5)
            GL11.glEnable(GL11.GL_CULL_FACE)
        }

        Case.render(f5)
        NeedleStand.render(f5)
        Needle.render(f5)
        Holder.render(f5)
    }

    private fun setRotation(model: ModelRenderer, x: Float, y: Float, z: Float) {
        model.rotateAngleX = x
        model.rotateAngleY = y
        model.rotateAngleZ = z
    }
}
