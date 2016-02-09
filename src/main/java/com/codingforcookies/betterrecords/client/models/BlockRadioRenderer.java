package com.codingforcookies.betterrecords.client.models;

import java.util.Map.Entry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import com.codingforcookies.betterrecords.StaticInfo;
import com.codingforcookies.betterrecords.betterenums.IRecordWireManipulator;
import com.codingforcookies.betterrecords.betterenums.RecordConnection;
import com.codingforcookies.betterrecords.items.TileEntityRadio;

public class BlockRadioRenderer extends TileEntitySpecialRenderer {
	public BlockRadioRenderer() { }
	
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
		if(!(te instanceof TileEntityRadio))
			return;
		
		TileEntityRadio tileEntityRadio = (TileEntityRadio)te;

		if(Minecraft.getMinecraft().thePlayer.getHeldItem() != null && Minecraft.getMinecraft().thePlayer.getHeldItem().getItem() instanceof IRecordWireManipulator) {
			GL11.glPushMatrix();
			{
				GL11.glTranslatef((float)x + .5F, (float)y + .5F, (float)z + .5F);
				
				if(tileEntityRadio.getConnections().size() != 0) {
					GL11.glColor3f(0F, 0F, 0F);
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					
					GL11.glLineWidth(2F);
					for(RecordConnection rec : tileEntityRadio.getConnections()) {
						int x1 = -(tileEntityRadio.xCoord - rec.x2);
						int y1 = -(tileEntityRadio.yCoord - rec.y2);
						int z1 = -(tileEntityRadio.zCoord - rec.z2);
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
	            GL11.glRotatef(-RenderManager.instance.playerViewY - 180F, 0F, 1F, 0F);
	            GL11.glColor3f(1F, 1F, 1F);
				int currentY = tileEntityRadio.wireSystemInfo.size() * -10 - 75;
				FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
				fontRenderer.drawString("Play Radius: " + tileEntityRadio.getSongRadius(), -fontRenderer.getStringWidth("Play Radius: " + tileEntityRadio.getSongRadius()) / 2, currentY, 0xFFFFFF);
				for(Entry<String, Integer> nfo : tileEntityRadio.wireSystemInfo.entrySet()) {
					currentY += 10;
					fontRenderer.drawString(nfo.getValue() + "x " + nfo.getKey(), -fontRenderer.getStringWidth(nfo.getValue() + "x " + nfo.getKey()) / 2, currentY, 0xFFFFFF);
				}
			}
			GL11.glPopMatrix();
		}
		
		GL11.glPushMatrix();
		{
			GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
			GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(te.blockMetadata * 90 + 180, 0.0F, 1.0F, 0.0F);
			bindTexture(StaticInfo.modelRadioRes);
			StaticInfo.modelRadio.render((Entity)null, tileEntityRadio.openAmount, tileEntityRadio.crystalFloaty, 0F, 0.0F, 0.0F, 0.0625F, tileEntityRadio.crystal);
		}
		GL11.glPopMatrix();
	}
}