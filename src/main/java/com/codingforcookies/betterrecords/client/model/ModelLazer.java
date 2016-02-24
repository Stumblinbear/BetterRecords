package com.codingforcookies.betterrecords.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

public class ModelLazer extends ModelBase {
	ModelRenderer Stand;
	ModelRenderer Front;
	ModelRenderer Base;
	ModelRenderer Box;

	public ModelLazer() {
		textureWidth = 64;
		textureHeight = 32;
		
		Stand = new ModelRenderer(this, 0, 16);
		Stand.addBox(-1.5F, -6F, -1.5F, 3, 6, 3);
		Stand.setRotationPoint(0F, 21F, 0F);
		Stand.setTextureSize(64, 32);
		Stand.mirror = true;
		setRotation(Stand, 0F, 0F, 0F);
		
		Front = new ModelRenderer(this, 0, 25);
		Front.addBox(-2F, -2F, -6F, 4, 4, 1);
		Front.setRotationPoint(0F, 15F, 0F);
		Front.setTextureSize(64, 32);
		Front.mirror = true;
		setRotation(Front, 0F, 0F, 0F);
		
		Base = new ModelRenderer(this, 12, 16);
		Base.addBox(-4F, 0F, -4F, 8, 3, 8);
		Base.setRotationPoint(0F, 21F, 0F);
		Base.setTextureSize(64, 32);
		Base.mirror = true;
		setRotation(Base, 0F, 0F, 0F);
		
		Box = new ModelRenderer(this, 0, 0);
		Box.addBox(-3F, -3F, -5F, 6, 6, 10);
		Box.setRotationPoint(0F, 15F, 0F);
		Box.setTextureSize(64, 32);
		Box.mirror = true;
		setRotation(Box, 0F, 0F, 0F);
	}
	
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		
		Stand.render(f5);
		Base.render(f5);
		
		GL11.glPushMatrix();
		{
			GL11.glRotatef(f1, 0F, 1F, 0F);
			GL11.glTranslatef(0F, .95F, 0F);
			GL11.glRotatef(-f2, 1F, 0F, 0F);
			GL11.glTranslatef(0F, -.95F, 0F);
			Front.render(f5);
			Box.render(f5);
		}
		GL11.glPopMatrix();
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
