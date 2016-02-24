package com.codingforcookies.betterrecords.client.render;

import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;

import com.codingforcookies.betterrecords.common.lib.StaticInfo;
import com.codingforcookies.betterrecords.api.wire.IRecordWireManipulator;
import com.codingforcookies.betterrecords.api.connection.RecordConnection;
import com.codingforcookies.betterrecords.common.block.tile.TileEntityRecordPlayer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

public class BlockRecordPlayerRenderer extends TileEntitySpecialRenderer {
	
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale, int destroyStage) {
		if(!(te instanceof TileEntityRecordPlayer)) {
			GL11.glPushMatrix();
			{
				GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
				GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
				bindTexture(StaticInfo.modelRecordPlayerRes);
				StaticInfo.modelRecordPlayer.render(null, 0, 0, 0, 0.0F, 0.0F, 0.0625F);
			}
			GL11.glPopMatrix();
			return;
		}
		
		TileEntityRecordPlayer tileEntityRecordPlayer = (TileEntityRecordPlayer)te;
		
		if(Minecraft.getMinecraft().thePlayer.getHeldItem() != null && Minecraft.getMinecraft().thePlayer.getHeldItem().getItem() instanceof IRecordWireManipulator) {
			GL11.glPushMatrix();
			{
				GL11.glTranslatef((float)x + .5F, (float)y + .5F, (float)z + .5F);
				
				if(tileEntityRecordPlayer.getConnections().size() != 0) {
					GL11.glColor3f(0F, 0F, 0F);
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					
					GL11.glLineWidth(2F);
					for(RecordConnection rec : tileEntityRecordPlayer.getConnections()){
						int x1 = -(tileEntityRecordPlayer.getPos().getX() - rec.x2);
						int y1 = -(tileEntityRecordPlayer.getPos().getY() - rec.y2);
						int z1 = -(tileEntityRecordPlayer.getPos().getZ() - rec.z2);
						GL11.glPushMatrix();
						{
							GL11.glBegin(GL11.GL_LINE_STRIP);
							{
								GL11.glVertex3f(0F, 0F, 0F);
								GL11.glVertex3f(x1, y1, z1);
							}
							GL11.glEnd();
						}
						GL11.glPopMatrix();
					}
					
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					GL11.glColor3f(1F, 1F, 1F);
				}
				
				GL11.glScalef(.01F, -.01F, .01F);
	            GL11.glRotatef(-Minecraft.getMinecraft().getRenderManager().playerViewY - 180F, 0F, 1F, 0F);

				
				if(tileEntityRecordPlayer.formTreble.size() != 0) {
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					
					GL11.glPushMatrix();
					{
						try {
							GL11.glColor3f(0F, 1F, 1F);
							float increment = 25F;
							int waveSize = tileEntityRecordPlayer.formTreble.size();
							float oldX = -50F;
							float oldY = -125F;
							float xIndex = -50;
							for(int i = 0; i < waveSize; i += increment) {
								if(tileEntityRecordPlayer.formTreble.size() < i)
									break;
								float scaledSample = tileEntityRecordPlayer.formTreble.get(i);
								
								float yIndex = scaledSample - 125F;
								GL11.glBegin(GL11.GL_LINE_STRIP);
								{
									GL11.glVertex3f(oldX, oldY, 0F);
									GL11.glVertex3f(xIndex, yIndex, 0F);
									
									oldX = xIndex;
									oldY = yIndex;
									xIndex++;
								}
								GL11.glEnd();
							}
	
							GL11.glColor3f(1F, 1F, 0F);
							waveSize = tileEntityRecordPlayer.formBass.size();
							oldX = -50F;
							oldY = -200F;
							xIndex = -50;
							for(int i = 0; i < waveSize; i += increment) {
								if(tileEntityRecordPlayer.formBass.size() < i)
									break;
								float scaledSample = tileEntityRecordPlayer.formBass.get(i);
								
								float yIndex = scaledSample - 200F;
								GL11.glBegin(GL11.GL_LINE_STRIP);
								{
									GL11.glVertex3f(oldX, oldY, 0F);
									GL11.glVertex3f(xIndex, yIndex, 0F);
									
									oldX = xIndex;
									oldY = yIndex;
									xIndex++;
								}
								GL11.glEnd();
							}
						} catch(Exception e) { System.err.println("Waveform error. This is normal, its due to a desync between the music thread, and the main thread!"); }
					}
					GL11.glPopMatrix();
					
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					GL11.glColor3f(1F, 1F, 1F);
				}
	            
	            
	            GL11.glColor3f(1F, 1F, 1F);
				int currentY = tileEntityRecordPlayer.wireSystemInfo.size() * -10 - 75;
				FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
				fontRenderer.drawString("Play Radius: " + tileEntityRecordPlayer.getSongRadius(), -fontRenderer.getStringWidth("Play Radius: " + tileEntityRecordPlayer.getSongRadius()) / 2, currentY, 0xFFFFFF);
				for(Entry<String, Integer> nfo : tileEntityRecordPlayer.wireSystemInfo.entrySet()) {
					currentY += 10;
					fontRenderer.drawString(nfo.getValue() + "x " + nfo.getKey(), -fontRenderer.getStringWidth(nfo.getValue() + "x " + nfo.getKey()) / 2, currentY, 0xFFFFFF);
				}
			}
			GL11.glPopMatrix();
		}
		
		if(tileEntityRecordPlayer.recordEntity != null) {
			GL11.glPushMatrix();
			{
				GL11.glTranslatef((float)x + .5F, (float)y + .76F, (float)z + .5F);
				GL11.glScalef(2F, 2F, 2F);
				GL11.glRotatef(90F, 1F, 0F, 0F);
				GL11.glRotatef(tileEntityRecordPlayer.recordRotation * 57.3F, 0F, 0F, 1F);
				GL11.glTranslatef(0F, -.35F, 0F);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_CULL_FACE);
				if(Minecraft.getMinecraft().gameSettings.fancyGraphics)
					Minecraft.getMinecraft().getRenderManager().renderEntityWithPosYaw(tileEntityRecordPlayer.recordEntity, 0, 0, 0, 0, 0);
				else{
					Minecraft.getMinecraft().gameSettings.fancyGraphics = true;
					Minecraft.getMinecraft().getRenderManager().renderEntityWithPosYaw(tileEntityRecordPlayer.recordEntity, 0, 0, 0, 0, 0);
					Minecraft.getMinecraft().gameSettings.fancyGraphics = false;
				}
				GL11.glEnable(GL11.GL_CULL_FACE);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glColor3f(1F, 1F, 1F);
			}
			GL11.glPopMatrix();
		}
		
		GL11.glPushMatrix();
		{
			GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
			GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(te.getBlockMetadata() * (90) + 180, 0.0F, 1.0F, 0.0F);
			bindTexture(StaticInfo.modelRecordPlayerRes);
			StaticInfo.modelRecordPlayer.render((Entity)null, tileEntityRecordPlayer.openAmount, tileEntityRecordPlayer.needleLocation, tileEntityRecordPlayer.recordRotation, 0.0F, 0.0F, 0.0625F);
		}
		GL11.glPopMatrix();
	}
}