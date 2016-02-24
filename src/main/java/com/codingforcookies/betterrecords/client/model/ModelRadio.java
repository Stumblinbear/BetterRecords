package com.codingforcookies.betterrecords.client.model;

import java.awt.Color;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

public class ModelRadio extends ModelBase {
	ModelRenderer Crystal_1;
	ModelRenderer Crystal_2;
	ModelRenderer Crystal_3;
	ModelRenderer Crystal_4;
	ModelRenderer body1;
	ModelRenderer body2;
	ModelRenderer body3;
	ModelRenderer body4;
	ModelRenderer body5;
	ModelRenderer body6;
	ModelRenderer body7;
	ModelRenderer body8;
	ModelRenderer body9;
	ModelRenderer a1;
	ModelRenderer a2;
	ModelRenderer a3;
	ModelRenderer a4;
	ModelRenderer a5;
	ModelRenderer a6;
	ModelRenderer a7;
	ModelRenderer a8;
	ModelRenderer Button1;
	ModelRenderer Button1m1;
	ModelRenderer Button2;
	ModelRenderer Button2m1;
	ModelRenderer Glass;
	
	public ModelRadio() {
		Crystal_1 = new ModelRenderer(this, 1, 0);
		Crystal_1.setTextureSize(128, 128);
		Crystal_1.addBox(-1F, -1F, -1F, 2, 2, 2);
		Crystal_1.setRotationPoint(0F, 18F, -3F);
		setRotation(Crystal_1, 0F, 0F, 0F);

		Crystal_2 = new ModelRenderer(this, 1, 0);
		Crystal_2.setTextureSize(128, 128);
		Crystal_2.addBox(-1F, -1F, -1F, 2, 2, 2);
		Crystal_2.setRotationPoint(0F, 18F, -3F);
		setRotation(Crystal_2, 0F, -0.7853982F, 0F);

		Crystal_3 = new ModelRenderer(this, 1, 0);
		Crystal_3.setTextureSize(128, 128);
		Crystal_3.addBox(-1F, -1F, -1F, 2, 2, 2);
		Crystal_3.setRotationPoint(0F, 18F, -3F);
		setRotation(Crystal_3, 0F, 0F, 0F);

		Crystal_4 = new ModelRenderer(this, 1, 0);
		Crystal_4.setTextureSize(128, 128);
		Crystal_4.addBox(-1F, -1F, -1F, 2, 2, 2);
		Crystal_4.setRotationPoint(0F, 18F, -3F);
		setRotation(Crystal_4, 0F, 0F, 0F);

		body1 = new ModelRenderer(this, 74, 55);
		body1.setTextureSize(128, 128);
		body1.addBox(-7.5F, -3.5F, -6F, 15, 7, 12);
		body1.setRotationPoint(0F, 13F, 0F);
		setRotation(body1, 0F, 0F, 0F);

		body2 = new ModelRenderer(this, 76, 41);
		body2.setTextureSize(128, 128);
		body2.addBox(-7F, -1F, -6F, 14, 2, 12);
		body2.setRotationPoint(0F, 8.5F, 0F);
		setRotation(body2, 0F, 0F, 0F);

		body3 = new ModelRenderer(this, 80, 27);
		body3.setTextureSize(128, 128);
		body3.addBox(-6F, -1F, -6F, 12, 2, 12);
		body3.setRotationPoint(0F, 6.5F, 0F);
		setRotation(body3, 0F, 0F, 0F);

		body4 = new ModelRenderer(this, 85, 13);
		body4.setTextureSize(128, 128);
		body4.addBox(-4.5F, -1F, -6F, 9, 2, 12);
		body4.setRotationPoint(0F, 4.5F, 0F);
		setRotation(body4, 0F, 0F, 0F);

		body5 = new ModelRenderer(this, 91, 0);
		body5.setTextureSize(128, 128);
		body5.addBox(-2.5F, -0.5F, -6F, 5, 1, 12);
		body5.setRotationPoint(0F, 3F, 0F);
		setRotation(body5, 0F, 0F, 0F);

		body6 = new ModelRenderer(this, 78, 74);
		body6.setTextureSize(128, 128);
		body6.addBox(-7.5F, -3.5F, -4F, 15, 7, 8);
		body6.setRotationPoint(0F, 20F, 2F);
		setRotation(body6, 0F, 0F, 0F);

		body7 = new ModelRenderer(this, 29, 36);
		body7.setTextureSize(128, 128);
		body7.addBox(-2.5F, -3.5F, -2F, 5, 7, 4);
		body7.setRotationPoint(-5F, 20F, -4F);
		setRotation(body7, 0F, 0F, 0F);

		body8 = new ModelRenderer(this, 47, 36);
		body8.setTextureSize(128, 128);
		body8.addBox(-2.5F, -3.5F, -2F, 5, 7, 4);
		body8.setRotationPoint(5F, 20F, -4F);
		setRotation(body8, 0F, 0F, 0F);

		body9 = new ModelRenderer(this, 37, 50);
		body9.setTextureSize(128, 128);
		body9.addBox(-2.5F, -2F, -2F, 5, 4, 4);
		body9.setRotationPoint(0F, 21.5F, -4F);
		setRotation(body9, 0F, 0F, 0F);

		a1 = new ModelRenderer(this, 60, 15);
		a1.setTextureSize(128, 128);
		a1.addBox(-1F, -1F, -6.5F, 2, 2, 13);
		a1.setRotationPoint(-7F, 13F, 0F);
		setRotation(a1, 0F, 0F, 0F);

		a2 = new ModelRenderer(this, 60, 15);
		a2.setTextureSize(128, 128);
		a2.addBox(-1F, -1F, -6.5F, 2, 2, 13);
		a2.setRotationPoint(7F, 13F, 0F);
		setRotation(a2, 0F, 0F, 0F);
		
		a3 = new ModelRenderer(this, 61, 0);
		a3.setTextureSize(128, 128);
		a3.addBox(-1.5F, -0.5F, -7F, 3, 1, 14);
		a3.setRotationPoint(7F, 12F, 0F);
		setRotation(a3, 0F, 0F, 0F);
		
		a4 = new ModelRenderer(this, 61, 0);
		a4.setTextureSize(128, 128);
		a4.addBox(-1.5F, -0.5F, -7F, 3, 1, 14);
		a4.setRotationPoint(-7F, 12F, 0F);
		setRotation(a4, 0F, 0F, 0F);

		a5 = new ModelRenderer(this, 60, 15);
		a5.setTextureSize(128, 128);
		a5.addBox(-1F, -1F, -6.5F, 2, 2, 13);
		a5.setRotationPoint(-7F, 22.5F, 0F);
		setRotation(a5, 0F, 0F, 0F);

		a6 = new ModelRenderer(this, 61, 0);
		a6.setTextureSize(128, 128);
		a6.addBox(-1.5F, -0.5F, -7F, 3, 1, 14);
		a6.setRotationPoint(-7F, 23.5F, 0F);
		setRotation(a6, 0F, 0F, 0F);

		a7 = new ModelRenderer(this, 60, 15);
		a7.setTextureSize(128, 128);
		a7.addBox(-1F, -1F, -6.5F, 2, 2, 13);
		a7.setRotationPoint(7F, 22.5F, 0F);
		setRotation(a7, 0F, 0F, 0F);

		a8 = new ModelRenderer(this, 61, 0);
		a8.setTextureSize(128, 128);
		a8.addBox(-1.5F, -0.5F, -7F, 3, 1, 14);
		a8.setRotationPoint(7F, 23.5F, 0F);
		setRotation(a8, 0F, 0F, 0F);

		Button1 = new ModelRenderer(this, 15, 15);
		Button1.setTextureSize(128, 128);
		Button1.addBox(-1F, -1F, -0.5F, 2, 2, 1);
		Button1.setRotationPoint(-4.8F, 20.5F, -6F);
		setRotation(Button1, 0F, 0F, 0F);
		Button1m1 = new ModelRenderer(this, 16, 12);
		Button1m1.setTextureSize(128, 128);
		Button1m1.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1);
		Button1m1.setRotationPoint(-4.8F, 20.5F, -6.5F);
		setRotation(Button1m1, 0F, 0F, 0F);

		Button2 = new ModelRenderer(this, 15, 15);
		Button2.setTextureSize(128, 128);
		Button2.addBox(-1F, -1F, -0.5F, 2, 2, 1);
		Button2.setRotationPoint(4.8F, 20.5F, -6F);
		setRotation(Button2, 0F, 0F, 0F);

		Button2m1 = new ModelRenderer(this, 16, 12);
		Button2m1.setTextureSize(128, 128);
		Button2m1.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1);
		Button2m1.setRotationPoint(5F, 20.5F, -6.5F);
		setRotation(Button2m1, 0F, 0F, 0F);

		Glass = new ModelRenderer(this, 13, 20);
		Glass.setTextureSize(128, 128);
		Glass.addBox(-2.5F, -1.5F, 0F, 5, 3, 0);
		Glass.setRotationPoint(0F, 18F, -6F);
		setRotation(Glass, 0F, 0F, 0F);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, ItemStack crystal) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		
		GL11.glPushMatrix();
		{
			GL11.glTranslatef(0F, .42F, 0F);
			GL11.glScalef(.7F, .715F, .7F);
			if(crystal != null) {
				GL11.glPushMatrix();
				{
					GL11.glEnable(GL11.GL_BLEND);
					if(crystal.getTagCompound().hasKey("color")) {
						Color color = new Color(crystal.getTagCompound().getInteger("color"));
						GL11.glColor4f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, .6F);
					}else
						GL11.glColor4f(1F, 1F, 1F, .6F);
					GL11.glTranslatef(0F, 0F, -.18F);
					GL11.glRotatef(f1, 0F, 1F, 0F);
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
	
			body1.render(f5);
			body2.render(f5);
			body3.render(f5);
			body4.render(f5);
			body5.render(f5);
			body6.render(f5);
			body7.render(f5);
			body8.render(f5);
			body9.render(f5);
			
			a1.render(f5);
			a4.render(f5);
			a2.render(f5);
			a3.render(f5);
			a7.render(f5);
			a8.render(f5);
			a5.render(f5);
			a6.render(f5);
			
			Button1.render(f5);
			Button1m1.render(f5);
			
			Button2.render(f5);
			Button2m1.render(f5);
			
			GL11.glPushMatrix();
			{
				GL11.glTranslatef(f, 0F, 0.01F);
				Glass.render(f5);
			}
			GL11.glPopMatrix();
		}
		GL11.glPopMatrix();
	}
}
