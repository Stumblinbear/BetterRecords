package com.codingforcookies.betterrecords.src.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelRecordEtcher extends ModelBase {
	ModelRenderer blockCase;
	ModelRenderer holder;
	ModelRenderer frontLeftPeg;
	ModelRenderer backRightPeg;
	ModelRenderer backLeftPeg;
	ModelRenderer frontRightPeg;
	ModelRenderer top;
	ModelRenderer topCase;
	ModelRenderer etcher;
	
	public ModelRecordEtcher() {
		textureWidth = 64;
		textureHeight = 64;
		
		blockCase = new ModelRenderer(this, 0, 0);
		blockCase.addBox(-7F, 0F, -7F, 14, 10, 14);
		blockCase.setRotationPoint(0F, 14F, 0F);
		blockCase.setTextureSize(64, 64);
		blockCase.mirror = true;
		setRotation(blockCase, 0F, 0F, 0F);
		
		holder = new ModelRenderer(this, 0, 4);
		holder.addBox(-0.5F, -1F, -0.5F, 1, 1, 1);
		holder.setRotationPoint(0F, 14F, 0F);
		holder.setTextureSize(64, 64);
		holder.mirror = true;
		setRotation(holder, 0F, 0F, 0F);
		
		frontLeftPeg = new ModelRenderer(this, 0, 0);
		frontLeftPeg.addBox(6F, -3F, -7F, 1, 3, 1);
		frontLeftPeg.setRotationPoint(0F, 14F, 0F);
		frontLeftPeg.setTextureSize(64, 64);
		frontLeftPeg.mirror = false;
		setRotation(frontLeftPeg, 0F, 0F, 0F);
		
		backRightPeg = new ModelRenderer(this, 0, 0);
		backRightPeg.addBox(-7F, -3F, 6F, 1, 3, 1);
		backRightPeg.setRotationPoint(0F, 14F, 0F);
		backRightPeg.setTextureSize(64, 64);
		backRightPeg.mirror = true;
		setRotation(backRightPeg, 0F, 0F, 0F);
		
		backLeftPeg = new ModelRenderer(this, 0, 0);
		backLeftPeg.addBox(6F, -3F, 6F, 1, 3, 1);
		backLeftPeg.setRotationPoint(0F, 14F, 0F);
		backLeftPeg.setTextureSize(64, 64);
		backLeftPeg.mirror = false;
		setRotation(backLeftPeg, 0F, 0F, 0F);
		
		frontRightPeg = new ModelRenderer(this, 0, 0);
		frontRightPeg.addBox(-7F, -3F, -7F, 1, 3, 1);
		frontRightPeg.setRotationPoint(0F, 14F, 0F);
		frontRightPeg.setTextureSize(64, 64);
		frontRightPeg.mirror = true;
		setRotation(frontRightPeg, 0F, 0F, 0F);
		
		top = new ModelRenderer(this, 0, 24);
		top.addBox(-7F, -4F, -7F, 14, 1, 14);
		top.setRotationPoint(0F, 14F, 0F);
		top.setTextureSize(64, 64);
		top.mirror = true;
		setRotation(top, 0F, 0F, 0F);
		
		topCase = new ModelRenderer(this, 0, 39);
		topCase.addBox(-4F, -5F, -4F, 8, 1, 8);
		topCase.setRotationPoint(0F, 14F, 0F);
		topCase.setTextureSize(64, 64);
		topCase.mirror = true;
		setRotation(topCase, 0F, 0F, 0F);
		
		etcher = new ModelRenderer(this, 0, 39);
		etcher.addBox(-0.5F, -5F, -0.5F, 1, 3, 1);
		etcher.setRotationPoint(0F, 14F, 0F);
		etcher.setTextureSize(64, 64);
		etcher.mirror = true;
		setRotation(etcher, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		
		etcher.offsetX = f;
		etcher.offsetY = (f < .015F ? f * 4 : .06F);
		
		holder.rotateAngleY = 3F + f1;
		
		blockCase.render(f5);
		holder.render(f5);
		frontLeftPeg.render(f5);
		backRightPeg.render(f5);
		backLeftPeg.render(f5);
		frontRightPeg.render(f5);
		top.render(f5);
		topCase.render(f5);
		etcher.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}