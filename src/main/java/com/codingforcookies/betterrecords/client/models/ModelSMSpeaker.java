package com.codingforcookies.betterrecords.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSMSpeaker extends ModelBase {
	ModelRenderer wool;
	ModelRenderer base;

	public ModelSMSpeaker() {
		textureWidth = 64;
		textureHeight = 32;

		wool = new ModelRenderer(this, 32, 0);
		wool.addBox(-4F, 1F, -5.5F, 6, 8, 1);
		wool.setRotationPoint(0F, 14F, 0F);
		wool.setTextureSize(64, 32);
		wool.mirror = true;
		setRotation(wool, 0F, 0F, 0F);
		
		base = new ModelRenderer(this, 0, 0);
		base.addBox(-5F, 0F, -5F, 8, 10, 8);
		base.setRotationPoint(0F, 14F, 0F);
		base.setTextureSize(64, 32);
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