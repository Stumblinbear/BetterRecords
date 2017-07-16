package com.codingforcookies.betterrecords.client.render;

import com.codingforcookies.betterrecords.ConstantsKt;
import com.codingforcookies.betterrecords.client.model.ModelLazerCluster;
import com.codingforcookies.betterrecords.common.block.tile.TileEntityLazerCluster;
import com.codingforcookies.betterrecords.common.core.handler.ConfigHandler;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class BlockLazerClusterRenderer extends TileEntitySpecialRenderer<TileEntityLazerCluster> {

    private static final ModelLazerCluster MODEL = new ModelLazerCluster();
    private static final ResourceLocation TEXTURE = new ResourceLocation(ConstantsKt.ID, "textures/models/lazercluster.png");

    @Override
    public void renderTileEntityAt(TileEntityLazerCluster te, double x, double y, double z, float scale, int unknown) {
        if (te == null) {
            GL11.glPushMatrix();
            {
                GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
                GL11.glRotatef(180F, 180F, 0.0F, 1.0F);
                GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);

                bindTexture(TEXTURE);
                MODEL.render(null, 0, 0, 0, 0.0F, 0.0F, 0.0625F);

                GL11.glRotatef(-180F, -180F, 0.0F, 1.0F);
                GL11.glTranslatef(0.0F, -.926F, 0.0F);
            }
            GL11.glPopMatrix();
            return;
        }

        GL11.glPushMatrix();
        {
            GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

            bindTexture(TEXTURE);

            MODEL.render(null, 0F, 0F, 0F, 0.0F, 0.0F, 0.0625F);

            if(te.r != 0.0F && te.g != 0.0F && te.b != 0.0F) {
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glColor4f(te.r, te.g, te.b, (ConfigHandler.flashyMode == 1 ? .2F : .4F));
            }

            MODEL.renderEmitor(null, 0F, 0F, 0F, 0.0F, 0.0F, 0.0625F);
            if(te.r != 0.0F && te.g != 0.0F && te.b != 0.0F) {
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
            }

            GL11.glColor4f(1F, 1F, 1F, 1F);
            GL11.glEnable(GL11.GL_LIGHTING);

            GL11.glTranslatef(0.0F, 1.0F, 0.0F);

            if(te.bass != 0 && ConfigHandler.flashyMode > 0) {
                GL11.glPushMatrix();
                {
                    GL11.glDisable(GL11.GL_LIGHTING);
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    GL11.glEnable(GL11.GL_BLEND);
                    RenderHelper.disableStandardItemLighting();

                    GL11.glLineWidth(te.bass / 2);

                    for(float pitch = 0F; pitch < 9F; pitch += 1F) {
                        GL11.glRotatef(200F / 3, 0F, 1F, 0F);
                        for(float yaw = 0F; yaw < 18F; yaw += 1F) {
                            GL11.glRotatef(200F / 9, 0F, 0F, 1F);
                            GL11.glBegin(GL11.GL_LINE_STRIP);
                            {
                                GL11.glColor4f(te.r, te.g, te.b, (ConfigHandler.flashyMode == 1 ? .2F : .4F));
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
