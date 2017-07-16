package com.codingforcookies.betterrecords.client.render;

import com.codingforcookies.betterrecords.ConstantsKt;
import com.codingforcookies.betterrecords.client.model.ModelLazer;
import com.codingforcookies.betterrecords.block.tile.TileLaser;
import com.codingforcookies.betterrecords.common.core.handler.ConfigHandler;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderLaser extends TileEntitySpecialRenderer<TileLaser> {

    private static final ModelLazer MODEL = new ModelLazer();
    private static final ResourceLocation TEXTURE = new ResourceLocation(ConstantsKt.ID, "textures/models/lazer.png");

    @Override
    public void renderTileEntityAt(TileLaser te, double x, double y, double z, float scale, int destroyStage) {
        if(te == null) {
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
            MODEL.render(null, te.bass != 0 ? 1F : 0F, te.yaw, te.pitch, 0.0F, 0.0F, 0.0625F);

            GL11.glRotatef(-180F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef(0.0F, -.926F, 0.0F);

            if(te.bass != 0 && ConfigHandler.flashyMode > 0) {
                GL11.glPushMatrix();
                {
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glDisable(GL11.GL_CULL_FACE);

                    GL11.glRotatef(-te.yaw + 180F, 0F, 1F, 0F);
                    GL11.glRotatef(-te.pitch + 90F, 1F, 0F, 0F);

                    int length = te.length;

                    float yaw = te.yaw;
                    float pitch = te.pitch;
                    /*float startX = tileEntityLazer.getPos().getX() - 0.5F;
                    float startY = tileEntityLazer.getPos().getY();
                    float startZ = tileEntityLazer.getPos().getZ() + .5F;
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
                    float width = te.bass / 400F;
                    GL11.glBegin(GL11.GL_QUADS);
                    {
                        GL11.glColor4f(te.r, te.g, te.b, (ConfigHandler.flashyMode == 1 ? .3F : .8F));

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
