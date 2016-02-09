package com.codingforcookies.betterrecords.client.models;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class ModelFrequencyTuner extends ModelBase {
	ModelRenderer Crystal_1;
	ModelRenderer Crystal_2;
	ModelRenderer Crystal_3;
	ModelRenderer Crystal_4;
	ModelRenderer Base;
	ModelRenderer Platform;
	ModelRenderer Stand;
	ModelRenderer TunerBridge;
	ModelRenderer TunerWeight;
	ModelRenderer Tuner;

	public ModelFrequencyTuner() {
		textureWidth = 64;
		textureHeight = 32;
		
		Crystal_1 = new ModelRenderer(this, 1, 0);
		Crystal_1.setTextureSize(64, 32);
		Crystal_1.addBox(-1F, -1F, -1F, 2, 2, 2);
		Crystal_1.setRotationPoint(0F, 18F, -3F);
		setRotation(Crystal_1, 0F, 0F, 0F);

		Crystal_2 = new ModelRenderer(this, 1, 0);
		Crystal_2.setTextureSize(64, 32);
		Crystal_2.addBox(-1F, -1F, -1F, 2, 2, 2);
		Crystal_2.setRotationPoint(0F, 18F, -3F);
		setRotation(Crystal_2, 0F, -0.7853982F, 0F);

		Crystal_3 = new ModelRenderer(this, 1, 0);
		Crystal_3.setTextureSize(64, 32);
		Crystal_3.addBox(-1F, -1F, -1F, 2, 2, 2);
		Crystal_3.setRotationPoint(0F, 18F, -3F);
		setRotation(Crystal_3, 0F, 0F, 0F);

		Crystal_4 = new ModelRenderer(this, 1, 0);
		Crystal_4.setTextureSize(64, 32);
		Crystal_4.addBox(-1F, -1F, -1F, 2, 2, 2);
		Crystal_4.setRotationPoint(0F, 18F, -3F);
		setRotation(Crystal_4, 0F, 0F, 0F);

		Base = new ModelRenderer(this, 0, 0);
		Base.addBox(-5F, 0F, -6F, 10, 9, 12);
		Base.setRotationPoint(0F, 15F, 0F);
		Base.setTextureSize(64, 32);
		Base.mirror = true;
		setRotation(Base, 0F, 0F, 0F);
		
		Platform = new ModelRenderer(this, 0, 21);
		Platform.addBox(-4F, -1F, -5F, 8, 1, 8);
		Platform.setRotationPoint(0F, 15F, 0F);
		Platform.setTextureSize(64, 32);
		Platform.mirror = true;
		setRotation(Platform, 0F, 0F, 0F);
		
		Stand = new ModelRenderer(this, 44, 0);
		Stand.addBox(-0.5F, -2.5F, 4F, 1, 3, 1);
		Stand.setRotationPoint(0F, 15F, 0F);
		Stand.setTextureSize(64, 32);
		Stand.mirror = true;
		setRotation(Stand, 0F, 0F, 0F);
		
		TunerBridge = new ModelRenderer(this, 44, 4);
		TunerBridge.addBox(-0.5F, -1F, 4F, 1, 6, 1);
		TunerBridge.setRotationPoint(0F, 15F, 0F);
		TunerBridge.setTextureSize(64, 32);
		TunerBridge.mirror = true;
		setRotation(TunerBridge, 1.041001F, 0F, 0F);
		
		TunerWeight = new ModelRenderer(this, 48, 0);
		TunerWeight.addBox(-1.5F, -2F, 5.5F, 3, 1, 2);
		TunerWeight.setRotationPoint(0F, 15F, 0F);
		TunerWeight.setTextureSize(64, 32);
		TunerWeight.mirror = true;
		setRotation(TunerWeight, 0F, 0F, 0F);
		
		Tuner = new ModelRenderer(this, 48, 3);
		Tuner.addBox(-0.5F, -2F, 3F, 1, 1, 2);
		Tuner.setRotationPoint(0F, 15F, 0F);
		Tuner.setTextureSize(64, 32);
		Tuner.mirror = true;
		setRotation(Tuner, 1.029744F, 0F, 0F);
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, ItemStack crystal) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		
		if(crystal != null) {
			GL11.glPushMatrix();
			{
				GL11.glEnable(GL11.GL_BLEND);
				if(crystal.stackTagCompound != null && crystal.stackTagCompound.hasKey("color")) {
					Color color = new Color(crystal.stackTagCompound.getInteger("color"));
					GL11.glColor4f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, .6F);
				}else
					GL11.glColor4f(1F, 1F, 1F, .6F);
				GL11.glScalef(.7F, .7F, .7F);
				GL11.glTranslatef(0F, .05F, -.1F);
				GL11.glRotatef(f, 0F, 1F, 0F);
				GL11.glTranslatef(0F, 0F, .18F);
				Crystal_1.render(f5);
				Crystal_2.render(f5);
				Crystal_3.render(f5);
				Crystal_4.render(f5);
				GL11.glColor3d(1F, 1F, 1F);
				GL11.glDisable(GL11.GL_BLEND);
			}
			GL11.glPopMatrix();
		}
		
		Base.render(f5);
		Platform.render(f5);
		Stand.render(f5);
		TunerBridge.render(f5);
		TunerWeight.render(f5);
		Tuner.render(f5);
	}
}