package com.codingforcookies.betterrecords.client.render;

import com.codingforcookies.betterrecords.client.core.handler.BetterEventHandler;
import com.codingforcookies.betterrecords.client.model.ModelStrobeLight;
import com.codingforcookies.betterrecords.common.BetterRecords;
import com.codingforcookies.betterrecords.common.block.tile.TileEntityStrobeLight;
import com.codingforcookies.betterrecords.common.core.handler.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class BlockStrobeLightRenderer extends TileEntitySpecialRenderer<TileEntityStrobeLight> {

    private static final ModelStrobeLight MODEL = new ModelStrobeLight();
    private static final ResourceLocation TEXTURE = new ResourceLocation(BetterRecords.ID, "textures/models/strobelight.png");

    @Override
    public void renderTileEntityAt(TileEntityStrobeLight te, double x, double y, double z, float scale, int destroyStage) {
        if(te == null) {
            GL11.glPushMatrix();
            {
                GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

                bindTexture(TEXTURE);
                MODEL.render(null, 0F, 0F, 0F, 0.0F, 0.0F, 0.0625F);

                GL11.glTranslatef(0.0F, 1.0F, 0.0F);
            }
            GL11.glPopMatrix();
            return;
        }

        GL11.glPushMatrix();
        {
            GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

            bindTexture(TEXTURE);
            MODEL.render(null, 0F, 0F, 0F, 0.0F, 0.0F, 0.0625F);

            GL11.glTranslatef(0.0F, 1.0F, 0.0F);

            if(te.bass != 0 && ConfigHandler.flashyMode > 0) {
                float incr = (float) (2 * Math.PI / 10);

                GL11.glPushMatrix();
                {
                    GL11.glRotatef(Minecraft.getMinecraft().getRenderManager().playerViewY - 180F, 0F, 1F, 0F);
                    GL11.glRotatef(Minecraft.getMinecraft().getRenderManager().playerViewX, 1F, 0F, 0F);

                    GL11.glDisable(GL11.GL_DEPTH_TEST);
                    GL11.glDisable(GL11.GL_LIGHTING);
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    GL11.glEnable(GL11.GL_BLEND);

                    for(float trans = .2F; trans < .6F; trans += .2F) {
                        GL11.glScalef(.9F, .9F, 1F);
                        GL11.glRotatef(20F, 0F, 0F, 1F);
                        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
                        {
                            GL11.glColor4f(1F, 1F, 1F, trans / (ConfigHandler.flashyMode == 1 ? 3F : 1F));
                            GL11.glVertex2f(0F, 0F);

                            for(int i = 0; i < 10; i++) {
                                float angle = incr * i;
                                float xx = (float)Math.cos(angle) * te.bass;
                                float yy = (float)Math.sin(angle) * te.bass;

                                GL11.glVertex2f(xx, yy);
                            }

                            GL11.glVertex2f(te.bass, 0F);
                        }
                        GL11.glEnd();
                    }

                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                    GL11.glEnable(GL11.GL_LIGHTING);
                    GL11.glEnable(GL11.GL_DEPTH_TEST);
                }
                GL11.glPopMatrix();

                GL11.glColor4f(1F, 1F, 1F, 1F);

                if(ConfigHandler.flashyMode > 1) {
                    Minecraft mc = Minecraft.getMinecraft();
                    float dist = (float)Math.sqrt(Math.pow(te.getPos().getX() - mc.thePlayer.posX, 2) + Math.pow(te.getPos().getY() - mc.thePlayer.posY, 2) + Math.pow(te.getPos().getZ() - mc.thePlayer.posZ, 2));
                    if(dist < 4 * te.bass) {
                        float newStrobe = Math.abs(dist - 4F * te.bass) / 100F;
                        if(newStrobe > 0F && BetterEventHandler.strobeLinger < newStrobe)
                            BetterEventHandler.strobeLinger = newStrobe;
                    }
                }
            }
        }
        GL11.glPopMatrix();
    }
}
