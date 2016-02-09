package com.codingforcookies.betterrecords.client.models;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import com.codingforcookies.betterrecords.StaticInfo;
import com.codingforcookies.betterrecords.items.TileEntityRecordSpeaker;

public class BlockRecordSpeakerRenderer extends TileEntitySpecialRenderer {
	public BlockRecordSpeakerRenderer() { }

	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
		if(!(te instanceof TileEntityRecordSpeaker))
			return;

		TileEntityRecordSpeaker tileEntityRecordSpeaker = (TileEntityRecordSpeaker)te;
		
		GL11.glPushMatrix();
		{
			GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
			GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(tileEntityRecordSpeaker.rotation, 0F, 1F, 0F);
			switch(tileEntityRecordSpeaker.type) {
				case 0:
					GL11.glTranslatef(.05F, 0F, .05F);
					bindTexture(StaticInfo.modelSMSpeakerRes);
					StaticInfo.modelSMSpeaker.render((Entity)null, 0F, 0F, 0F, 0.0F, 0.0F, 0.0625F);
					break;
				case 1:
					bindTexture(StaticInfo.modelMDSpeakerRes);
					StaticInfo.modelMDSpeaker.render((Entity)null, 0F, 0F, 0F, 0.0F, 0.0F, 0.0625F);
					break;
				case 2:
					bindTexture(StaticInfo.modelLGSpeakerRes);
					StaticInfo.modelLGSpeaker.render((Entity)null, 0F, 0F, 0F, 0.0F, 0.0F, 0.0625F);
					break;
				default:
					bindTexture(StaticInfo.modelLGSpeakerRes);
					StaticInfo.modelSMSpeaker.render((Entity)null, 0F, 0F, 0F, 0.0F, 0.0F, 0.0625F);
					break;
			}
		}
		GL11.glPopMatrix();
	}
}