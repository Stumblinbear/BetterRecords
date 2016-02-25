package com.codingforcookies.betterrecords.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelStrobeLight extends ModelBase {
    ModelRenderer Base;
    ModelRenderer Light;
    ModelRenderer GlassTop;

    public ModelStrobeLight() {
        textureWidth = 64;
        textureHeight = 32;

        Base = new ModelRenderer(this, 0, 15);
        Base.addBox(-4F, -2F, -4F, 8, 3, 8);
        Base.setRotationPoint(0F, 23F, 0F);
        Base.setTextureSize(64, 32);
        Base.mirror = true;
        setRotation(Base, 0F, 0F, 0F);

        Light = new ModelRenderer(this, 28, 0);
        Light.addBox(-2F, -6F, -2F, 3, 4, 3);
        Light.setRotationPoint(0.5F, 23F, 0.5F);
        Light.setTextureSize(64, 32);
        Light.mirror = true;
        setRotation(Light, 0F, 0F, 0F);

        GlassTop = new ModelRenderer(this, 0, 0);
        GlassTop.addBox(-4F, -10F, -4F, 7, 8, 7);
        GlassTop.setRotationPoint(0.5F, 23F, 0.5F);
        GlassTop.setTextureSize(64, 32);
        GlassTop.mirror = true;
        setRotation(GlassTop, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);

        Base.render(f5);
        Light.render(f5);

        GL11.glPushMatrix();
        {
            GL11.glDisable(GL11.GL_CULL_FACE);
            GlassTop.render(f5);
            GL11.glEnable(GL11.GL_CULL_FACE);
        }
        GL11.glPopMatrix();
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
