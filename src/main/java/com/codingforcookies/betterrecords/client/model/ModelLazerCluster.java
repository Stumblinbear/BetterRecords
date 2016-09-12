package com.codingforcookies.betterrecords.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelLazerCluster extends ModelBase {
    ModelRenderer Base;
    ModelRenderer Emitor;
    ModelRenderer Case;

    public ModelLazerCluster(){
        textureWidth = 64;
        textureHeight = 64;

        Base = new ModelRenderer(this, 0, 0);
        Base.addBox(-6F, 0F, -6F, 12, 3, 12);
        Base.setRotationPoint(0F, 21F, 0F);
        Base.setTextureSize(64, 64);
        Base.mirror = true;
        setRotation(Base, 0F, 0F, 0F);

        Emitor = new ModelRenderer(this, 0, 22);
        Emitor.addBox(0F, 0F, 0F, 6, 8, 6);
        Emitor.setRotationPoint(-3F, 13F, -3F);
        Emitor.setTextureSize(64, 64);
        Emitor.mirror = true;
        setRotation(Emitor, 0F, 0F, 0F);

        // Techne doesn't run on Mac, so I wrote this bit by hand.
        // Keep that in mind if you're updating from the model
        Case = new ModelRenderer(this, 0, 43);
        Case.addBox(-6F, -9F, -6F, 12, 9, 12);
        Case.setRotationPoint(0F, 21F, 0F);
        Case.setTextureSize(64, 64);
        Case.mirror = true;
        setRotation(Case, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5){
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        Base.render(f5);
        Case.render(f5);
    }

    public void renderEmitor(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        Emitor.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z){
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
