package com.codingforcookies.betterrecords.client.model

import net.minecraft.client.model.ModelBase
import net.minecraft.client.model.ModelRenderer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.entity.Entity
import net.minecraft.item.ItemStack
import org.lwjgl.opengl.GL11
import java.awt.Color

class ModelRadio : ModelBase() {
    internal var Crystal_1: ModelRenderer
    internal var Crystal_2: ModelRenderer
    internal var Crystal_3: ModelRenderer
    internal var Crystal_4: ModelRenderer
    internal var body1: ModelRenderer
    internal var body2: ModelRenderer
    internal var body3: ModelRenderer
    internal var body4: ModelRenderer
    internal var body5: ModelRenderer
    internal var body6: ModelRenderer
    internal var body7: ModelRenderer
    internal var body8: ModelRenderer
    internal var body9: ModelRenderer
    internal var a1: ModelRenderer
    internal var a2: ModelRenderer
    internal var a3: ModelRenderer
    internal var a4: ModelRenderer
    internal var a5: ModelRenderer
    internal var a6: ModelRenderer
    internal var a7: ModelRenderer
    internal var a8: ModelRenderer
    internal var Button1: ModelRenderer
    internal var Button1m1: ModelRenderer
    internal var Button2: ModelRenderer
    internal var Button2m1: ModelRenderer
    internal var Glass: ModelRenderer

    init {
        textureWidth = 128
        textureHeight = 128

        this.Crystal_3 = ModelRenderer(this, 1, 0).apply {
            setRotationPoint(0.0F, 18.0F, -3.0F)
            addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2)
        }
        this.Button1 = ModelRenderer(this, 15, 15).apply {
            setRotationPoint(-4.8F, 20.5F, -6.0F)
            addBox(-1.0F, -1.0F, -0.5F, 2, 2, 1)
        }
        this.Crystal_4 = ModelRenderer(this, 1, 0).apply {
            setRotationPoint(0.0F, 18.0F, -3.0F)
            addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2)
        }
        this.Crystal_1 = ModelRenderer(this, 1, 0).apply {
            setRotationPoint(0.0F, 18.0F, -3.0F)
            addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2)
        }
        this.Crystal_2 = ModelRenderer(this, 1, 0).apply {
            setRotationPoint(0.0F, 18.0F, -3.0F)
            addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2)
        }
        this.setRotationAngles(this.Crystal_2, 0.0F, -0.7853981633974483F, 0.0F)
        this.Glass = ModelRenderer(this, 13, 20).apply {
            setRotationPoint(0.0F, 18.0F, -6.0F)
            addBox(-2.5F, -1.5F, 0.0F, 5, 3, 0)
        }
        this.body2 = ModelRenderer(this, 76, 41).apply {
            setRotationPoint(0.0F, 8.5F, 0.0F)
            addBox(-7.0F, -1.0F, -6.0F, 14, 2, 12)
        }
        this.body3 = ModelRenderer(this, 80, 27).apply {
            setRotationPoint(0.0F, 6.5F, 0.0F)
            addBox(-6.0F, -1.0F, -6.0F, 12, 2, 12)
        }
        this.body4 = ModelRenderer(this, 85, 13).apply {
            setRotationPoint(0.0F, 4.5F, 0.0F)
            addBox(-4.5F, -1.0F, -6.0F, 9, 2, 12)
        }
        this.body5 = ModelRenderer(this, 91, 0).apply {
            setRotationPoint(0.0F, 3.0F, 0.0F)
            addBox(-2.5F, -0.5F, -6.0F, 5, 1, 12)
        }
        this.body6 = ModelRenderer(this, 78, 74).apply {
            setRotationPoint(0.0F, 20.0F, 2.0F)
            addBox(-7.5F, -3.5F, -4.0F, 15, 7, 8)
        }
        this.body7 = ModelRenderer(this, 29, 36).apply {
            setRotationPoint(-5.0F, 20.0F, -4.0F)
            addBox(-2.5F, -3.5F, -2.0F, 5, 7, 4)
        }
        this.body8 = ModelRenderer(this, 47, 36).apply {
            setRotationPoint(5.0F, 20.0F, -4.0F)
            addBox(-2.5F, -3.5F, -2.0F, 5, 7, 4)
        }
        this.body9 = ModelRenderer(this, 37, 50).apply {
            setRotationPoint(0.0F, 21.5F, -4.0F)
            addBox(-2.5F, -2.0F, -2.0F, 5, 4, 4)
        }
        this.body1 = ModelRenderer(this, 74, 55).apply {
            setRotationPoint(0.0F, 13.0F, 0.0F)
            addBox(-7.5F, -3.5F, -6.0F, 15, 7, 12)
        }
        this.Button1m1 = ModelRenderer(this, 16, 12).apply {
            setRotationPoint(-4.8F, 20.5F, -6.5F)
            addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1)
        }
        this.Button2m1 = ModelRenderer(this, 16, 12).apply {
            setRotationPoint(5.0F, 20.5F, -6.5F)
            addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1)
        }
        this.a1 = ModelRenderer(this, 60, 15).apply {
            setRotationPoint(-7.0F, 13.0F, 0.0F)
            addBox(-1.0F, -1.0F, -6.5F, 2, 2, 13)
        }
        this.a2 = ModelRenderer(this, 60, 15).apply {
            setRotationPoint(7.0F, 13.0F, 0.0F)
            addBox(-1.0F, -1.0F, -6.5F, 2, 2, 13)
        }
        this.a3 = ModelRenderer(this, 61, 0).apply {
            setRotationPoint(7.0F, 12.0F, 0.0F)
            addBox(-1.5F, -0.5F, -7.0F, 3, 1, 14)
        }
        this.a4 = ModelRenderer(this, 61, 0).apply {
            setRotationPoint(-7.0F, 12.0F, 0.0F)
            addBox(-1.5F, -0.5F, -7.0F, 3, 1, 14)
        }
        this.a5 = ModelRenderer(this, 60, 15).apply {
            setRotationPoint(-7.0F, 22.5F, 0.0F)
            addBox(-1.0F, -1.0F, -6.5F, 2, 2, 13)
        }
        this.a6 = ModelRenderer(this, 61, 0).apply {
            setRotationPoint(-7.0F, 23.5F, 0.0F)
            addBox(-1.5F, -0.5F, -7.0F, 3, 1, 14)
        }
        this.a7 = ModelRenderer(this, 60, 15).apply {
            setRotationPoint(7.0F, 22.5F, 0.0F)
            addBox(-1.0F, -1.0F, -6.5F, 2, 2, 13)
        }
        this.a8 = ModelRenderer(this, 61, 0).apply {
            setRotationPoint(7.0F, 23.5F, 0.0F)
            addBox(-1.5F, -0.5F, -7.0F, 3, 1, 14)
        }
        this.Button2 = ModelRenderer(this, 15, 15).apply {
            setRotationPoint(4.8F, 20.5F, -6.0F)
            addBox(-1.0F, -1.0F, -0.5F, 2, 2, 1)
        }
    }

    

    fun render(entity: Entity?, f: Float, f1: Float, f2: Float, f3: Float, f4: Float, f5: Float, crystal: ItemStack?) {
        super.render(entity, f, f1, f2, f3, f4, f5)
        setRotationAngles(f, f1, f2, f3, f4, f5, entity)

        GlStateManager.pushMatrix()
        
        GlStateManager.translate(0f, .42f, 0f)
        GL11.glScalef(.7f, .715f, .7f)
        if (crystal != null) {
            GlStateManager.pushMatrix()

            GlStateManager.enableBlend()
            if (crystal.tagCompound!!.hasKey("color")) {
                val color = Color(crystal.tagCompound!!.getInteger("color"))
                GlStateManager.color(color.red / 255f, color.green / 255f, color.blue / 255f, .6f)
            } else
                GlStateManager.color(1f, 1f, 1f, .6f)
            GlStateManager.translate(0f, 0f, -.18f)
            GlStateManager.rotate(f1, 0f, 1f, 0f)
            GlStateManager.translate(0f, 0f, .18f)
            Crystal_1.render(f5)
            Crystal_2.render(f5)
            Crystal_3.render(f5)
            Crystal_4.render(f5)
            GlStateManager.color(1.0f, 1.0f, 1.0f)
            GlStateManager.disableBlend()

            GlStateManager.popMatrix()
        }

        body1.render(f5)
        body2.render(f5)
        body3.render(f5)
        body4.render(f5)
        body5.render(f5)
        body6.render(f5)
        body7.render(f5)
        body8.render(f5)
        body9.render(f5)

        a1.render(f5)
        a4.render(f5)
        a2.render(f5)
        a3.render(f5)
        a7.render(f5)
        a8.render(f5)
        a5.render(f5)
        a6.render(f5)

        Button1.render(f5)
        Button1m1.render(f5)

        Button2.render(f5)
        Button2m1.render(f5)

        GlStateManager.pushMatrix()
        
            GlStateManager.translate(f, 0f, 0.01f)
            Glass.render(f5)

        GlStateManager.popMatrix()
        
        GlStateManager.popMatrix()
    }

    private fun setRotationAngles(model: ModelRenderer, x: Float, y: Float, z: Float) {
        model.rotateAngleX = x
        model.rotateAngleY = y
        model.rotateAngleZ = z
    }
}
