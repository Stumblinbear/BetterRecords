package com.codingforcookies.betterrecords.client.model

import net.minecraft.client.model.ModelBase
import net.minecraft.client.model.ModelRenderer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.entity.Entity
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.awt.Color

@SideOnly(Side.CLIENT)
class ModelFrequencyTuner : ModelBase() {
    var Stand: ModelRenderer
    var Crystal_3: ModelRenderer
    var Platform: ModelRenderer
    var TunerBridge: ModelRenderer
    var TunerWeight: ModelRenderer
    var Crystal_4: ModelRenderer
    var Crystal_1: ModelRenderer
    var Crystal_2: ModelRenderer
    var Tuner: ModelRenderer
    var Base: ModelRenderer

    init {
        textureWidth = 64
        textureHeight = 32

        this.Stand = ModelRenderer(this, 44, 0).apply {
            setRotationPoint(0.0F, 15.0F, 0.0F)
            addBox(-0.5F, -2.5F, 4.0F, 1, 3, 1)
        }
        this.Crystal_3 = ModelRenderer(this, 1, 0).apply {
            setRotationPoint(0.0F, 18.0F, -3.0F)
            addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2)
        }
        this.Platform = ModelRenderer(this, 0, 21).apply {
            setRotationPoint(0.0F, 15.0F, 0.0F)
            addBox(-4.0F, -1.0F, -5.0F, 8, 1, 8)
        }
        this.TunerBridge = ModelRenderer(this, 44, 4).apply {
            setRotationPoint(0.0F, 15.0F, 0.0F)
            addBox(-0.5F, -1.0F, 4.0F, 1, 6, 1)
        }
        this.setRotationAngles(this.TunerBridge, 1.0410009745512285F, 0.0F, 0.0F)
        this.TunerWeight = ModelRenderer(this, 48, 0).apply {
            setRotationPoint(0.0F, 15.0F, 0.0F)
            addBox(-1.5F, -2.0F, 5.5F, 3, 1, 2)
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
        this.Tuner = ModelRenderer(this, 48, 3).apply {
            setRotationPoint(0.0F, 15.0F, 0.0F)
            addBox(-0.5F, -2.0F, 3.0F, 1, 1, 2)
        }
        this.setRotationAngles(this.Tuner, 1.0297440589395728F, 0.0F, 0.0F)
        this.Base = ModelRenderer(this, 0, 0).apply {
            setRotationPoint(0.0F, 15.0F, 0.0F)
            addBox(-5.0F, 0.0F, -6.0F, 10, 9, 12)
        }
    }

    fun render(entity: Entity?, f: Float, f1: Float, f2: Float, f3: Float, f4: Float, f5: Float, crystal: ItemStack) {
        super.render(entity, f, f1, f2, f3, f4, f5)
        setRotationAngles(f, f1, f2, f3, f4, f5, entity)

       if (!crystal?.isEmpty) {
            GlStateManager.pushMatrix()

            GlStateManager.enableBlend()

            if (crystal.hasTagCompound() && crystal.tagCompound!!.hasKey("color")) {
                val color = Color(crystal.tagCompound!!.getInteger("color"))
                GlStateManager.color(color.red / 255f, color.green / 255f, color.blue / 255f, .6f)
            } else
                GlStateManager.color(1f, 1f, 1f, .6f)

            GlStateManager.scale(.7f, .7f, .7f)
            GlStateManager.translate(0f, .05f, -.1f)
            GlStateManager.rotate(f, 0f, 1f, 0f)
            GlStateManager.translate(0f, 0f, .18f)

            Crystal_1.render(f5)
            Crystal_2.render(f5)
            Crystal_3.render(f5)
            Crystal_4.render(f5)

            GlStateManager.color(1.0f, 1.0f, 1.0f)
            GlStateManager.disableBlend()

            GlStateManager.popMatrix()
        }

        Base.render(f5)
        Platform.render(f5)
        Stand.render(f5)
        TunerBridge.render(f5)
        TunerWeight.render(f5)
        Tuner.render(f5)
    }

    private fun setRotationAngles(model: ModelRenderer, x: Float, y: Float, z: Float) {
        model.rotateAngleX = x
        model.rotateAngleY = y
        model.rotateAngleZ = z
    }
}
