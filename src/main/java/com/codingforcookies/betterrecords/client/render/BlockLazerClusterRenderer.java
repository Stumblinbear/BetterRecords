package com.codingforcookies.betterrecords.client.render;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import com.codingforcookies.betterrecords.client.core.ClientProxy;
import com.codingforcookies.betterrecords.common.block.tile.TileEntityLazerCluster;

public class BlockLazerClusterRenderer extends TileEntitySpecialRenderer {

    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale, int destroyStage) {
        if(!(te instanceof TileEntityLazerCluster))
            return;

        TileEntityLazerCluster tileEntityLazerCluster = (TileEntityLazerCluster)te;

        GL11.glPushMatrix();
        {
            GL11.glTranslatef((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

            // DRAW MODEL

            if(tileEntityLazerCluster.bass != 0 && ClientProxy.flashyMode > 0) {
                GL11.glPushMatrix();
                {
                    GL11.glDisable(GL11.GL_LIGHTING);
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    GL11.glEnable(GL11.GL_BLEND);
                    RenderHelper.disableStandardItemLighting();

                    GL11.glLineWidth(tileEntityLazerCluster.bass / 2);

                    for(float pitch = 0F; pitch < 9F; pitch += 1F) {
                        GL11.glRotatef(200F / 3, 0F, 1F, 0F);
                        for(float yaw = 0F; yaw < 18F; yaw += 1F) {
                            GL11.glRotatef(200F / 9, 0F, 0F, 1F);
                            GL11.glBegin(GL11.GL_LINE_STRIP);
                            {
                                GL11.glColor4f(tileEntityLazerCluster.r, tileEntityLazerCluster.g, tileEntityLazerCluster.b, (ClientProxy.flashyMode == 1 ? .2F : .4F));
                                GL11.glVertex2f(0F, 0F);

                                float xx = (float)Math.cos(pitch * (Math.PI / 180)) * 20F;
                                float yy = (float)Math.sin(yaw * (Math.PI / 180)) * 20F;

                                GL11.glVertex2f(xx, yy);
                            }
                            GL11.glEnd();
                        }
                    }

                    GL11.glLineWidth(1F);

                    RenderHelper.enableStandardItemLighting();
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                    GL11.glEnable(GL11.GL_LIGHTING);
                }
                GL11.glPopMatrix();

                GL11.glColor4f(1F, 1F, 1F, 1F);
            }
        }
        GL11.glPopMatrix();
    }
}
