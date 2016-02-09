package com.codingforcookies.betterrecords.src.client.models;

import org.lwjgl.opengl.GL11;

import com.codingforcookies.betterrecords.src.StaticInfo;
import com.codingforcookies.betterrecords.src.client.ClientProxy;
import com.codingforcookies.betterrecords.src.items.TileEntityLazer;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

public class BlockLazerRenderer extends TileEntitySpecialRenderer {
	public BlockLazerRenderer() { }

	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
		if(!(te instanceof TileEntityLazer))
			return;
		
		TileEntityLazer tileEntityLazer = (TileEntityLazer)te;
		
		GL11.glPushMatrix();
		{
			GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
			GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
			
			bindTexture(StaticInfo.modelLazerRes);
			StaticInfo.modelLazer.render((Entity)null, tileEntityLazer.bass != 0 ? 1F : 0F, tileEntityLazer.yaw, tileEntityLazer.pitch, 0.0F, 0.0F, 0.0625F);
			
			GL11.glRotatef(-180F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(0.0F, -.926F, 0.0F);
			
			if(tileEntityLazer.bass != 0 && ClientProxy.flashyMode > 0) {
				GL11.glPushMatrix();
				{
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glDisable(GL11.GL_CULL_FACE);
					
					GL11.glRotatef(-tileEntityLazer.yaw + 180F, 0F, 1F, 0F);
					GL11.glRotatef(-tileEntityLazer.pitch + 90F, 1F, 0F, 0F);
					
					int length = tileEntityLazer.length;

					float yaw = tileEntityLazer.yaw;
					float pitch = tileEntityLazer.pitch;
					/*float startX = tileEntityLazer.xCoord - 0.5F;
					float startY = tileEntityLazer.yCoord;
					float startZ = tileEntityLazer.zCoord + .5F;
					float stopX = (float)(startX + length * Math.cos(yaw) * Math.cos(pitch));
					float stopY = (float)(startY + length * Math.sin(yaw));
					float stopZ = (float)(startZ + length * Math.cos(yaw) * Math.sin(pitch));
					
					float distance = (float)Math.round(Math.sqrt(Math.pow(stopX - startX, 2) + Math.pow(stopY - startY, 2) + Math.pow(stopZ - startZ, 2)));
					
					for(float check = 1F; check < distance; check += .1F) {
						length = check;
						int posX = (int)(startX + check * Math.cos(yaw) * Math.cos(pitch));
						int posY = (int)(startY + check * Math.sin(yaw)) + 1;
						int posZ = (int)(startZ + check * Math.cos(yaw) * Math.sin(pitch));
						Block block = tileEntityLazer.getWorldObj().getBlock(posX, posY, posZ);
						tileEntityLazer.getWorldObj().setBlock(posX, posY, posZ, Blocks.gold_block);
						if(!(block instanceof BlockAir)) {
							System.out.println(block.getLocalizedName());
							tileEntityLazer.getWorldObj().setBlock(posX, posY, posZ, Blocks.gold_block);
						}
					}
					*/
					float width = tileEntityLazer.bass / 400F;
					GL11.glBegin(GL11.GL_QUADS);
					{
						GL11.glColor4f(tileEntityLazer.r, tileEntityLazer.g, tileEntityLazer.b, (ClientProxy.flashyMode == 1 ? .3F : .8F));
						
						GL11.glVertex3f(width, 0F, -width);
						GL11.glVertex3f(-width, 0F, -width);
						GL11.glVertex3f(-width, length, -width);
						GL11.glVertex3f(width, length, -width);

						GL11.glVertex3f(-width, 0F, width);
						GL11.glVertex3f(width, 0F, width);
						GL11.glVertex3f(width, length, width);
						GL11.glVertex3f(-width, length, width);

						GL11.glVertex3f(width, 0F, width);
						GL11.glVertex3f(width, 0F, -width);
						GL11.glVertex3f(width, length, -width);
						GL11.glVertex3f(width, length, width);

						GL11.glVertex3f(-width, 0F, -width);
						GL11.glVertex3f(-width, 0F, width);
						GL11.glVertex3f(-width, length, width);
						GL11.glVertex3f(-width, length, -width);
					}
					GL11.glEnd();
					
					GL11.glEnable(GL11.GL_CULL_FACE);
					GL11.glDisable(GL11.GL_BLEND);
					GL11.glEnable(GL11.GL_TEXTURE_2D);
				}
				GL11.glPopMatrix();
				
				GL11.glColor4f(1F, 1F, 1F, 1F);
			}
		}
		GL11.glPopMatrix();
	}
}