package com.codingforcookies.betterrecords.src.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelLGSpeaker extends ModelBase {
	ModelRenderer wool;
	ModelRenderer base;

	public ModelLGSpeaker() {
		textureWidth = 128;
		textureHeight = 64;

		wool = new ModelRenderer(this, 48, 0);
		wool.addBox(-5F, -9F, -6.5F, 10, 22, 1);
		wool.setRotationPoint(0F, 10F, 0F);
		wool.setTextureSize(128, 64);
		wool.mirror = true;
		setRotation(wool, 0F, 0F, 0F);
		
		base = new ModelRenderer(this, 0, 0);
		base.addBox(-6F, 0F, -6F, 12, 24, 12);
		base.setRotationPoint(0F, 0F, 0F);
		base.setTextureSize(128, 64);
		base.mirror = true;
		setRotation(base, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		wool.render(f5);
		base.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}