package com.codingforcookies.betterrecords.client.models;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelRecordPlayer extends ModelBase {
	ModelRenderer Lid;
	ModelRenderer Case;
	ModelRenderer Holder;
	ModelRenderer Needle;
	ModelRenderer NeedleStand;
	
	public ModelRecordPlayer() {
		textureWidth = 64;
		textureHeight = 64;
		
		Lid = new ModelRenderer(this, 0, 27);
		Lid.addBox(-7F, -3F, -14F, 14, 3, 14);
		Lid.setRotationPoint(0F, 12F, 7F);
		Lid.setTextureSize(64, 64);
		Lid.mirror = true;
		setRotation(Lid, 0F, 0F, 0F);
		
		Case = new ModelRenderer(this, 0, 0);
		Case.addBox(0F, 0F, 0F, 15, 12, 15);
		Case.setRotationPoint(-7.5F, 12F, -7.5F);
		Case.setTextureSize(64, 64);
		Case.mirror = true;
		setRotation(Case, 0F, 0F, 0F);
		
		Holder = new ModelRenderer(this, 0, 0);
		Holder.addBox(-0.5F, 0F, -0.5F, 1, 1, 1);
		Holder.setRotationPoint(0F, 11F, 0F);
		Holder.setTextureSize(64, 64);
		Holder.mirror = true;
		setRotation(Holder, 0F, 0F, 0F);
		
		Needle = new ModelRenderer(this, 0, 44);
		Needle.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 8);
		Needle.setRotationPoint(5F, 11F, 5F);
		Needle.setTextureSize(64, 64);
		Needle.mirror = true;
		setRotation(Needle, 0F, 3F, 0F);
		
		NeedleStand = new ModelRenderer(this, 0, 44);
		NeedleStand.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1);
		NeedleStand.setRotationPoint(5F, 12F, 5F);
		NeedleStand.setTextureSize(64, 64);
		NeedleStand.mirror = true;
		setRotation(NeedleStand, 0F, 0F, 0F);
	}
	
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		
		Lid.rotateAngleX = f;
		Needle.rotateAngleY = 3F + f1;
		Holder.rotateAngleY = f2;
		
		Needle.rotateAngleX = f2 / 10 % 0.025F;
		
		GL11.glPushMatrix();
		{
			GL11.glDisable(GL11.GL_CULL_FACE);
			Lid.render(f5);
			GL11.glEnable(GL11.GL_CULL_FACE);
		}
		GL11.glPopMatrix();
		
		Case.render(f5);
		NeedleStand.render(f5);
		Needle.render(f5);
		Holder.render(f5);
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}