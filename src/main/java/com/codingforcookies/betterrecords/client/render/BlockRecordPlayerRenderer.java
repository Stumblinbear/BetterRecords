package com.codingforcookies.betterrecords.client.render;

import com.codingforcookies.betterrecords.api.connection.RecordConnection;
import com.codingforcookies.betterrecords.api.wire.IRecordWireManipulator;
import com.codingforcookies.betterrecords.common.block.tile.TileEntityRecordPlayer;
import com.codingforcookies.betterrecords.common.lib.StaticInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.lwjgl.opengl.GL11;

import java.util.Map.Entry;

public class BlockRecordPlayerRenderer extends TileEntitySpecialRenderer<TileEntityRecordPlayer> {

    @Override
    public void renderTileEntityAt(TileEntityRecordPlayer te, double x, double y, double z, float scale, int destroyStage) {
        if(te == null) {
            GL11.glPushMatrix();
            {
                GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
                bindTexture(StaticInfo.modelRecordPlayerRes);
                StaticInfo.modelRecordPlayer.render(null, 0, 0, 0, 0.0F, 0.0F, 0.0625F);
            }
            GL11.glPopMatrix();
            return;
        }

        if(Minecraft.getMinecraft().thePlayer.getHeldItemMainhand() != null && Minecraft.getMinecraft().thePlayer.getHeldItemMainhand().getItem() instanceof IRecordWireManipulator) {
            GL11.glPushMatrix();
            {
                GL11.glTranslatef((float)x + .5F, (float)y + .5F, (float)z + .5F);

                if(te.getConnections().size() != 0) {
                    GL11.glColor3f(0F, 0F, 0F);
                    GL11.glDisable(GL11.GL_TEXTURE_2D);

                    GL11.glLineWidth(2F);
                    for(RecordConnection rec : te.getConnections()){
                        int x1 = -(te.getPos().getX() - rec.x2);
                        int y1 = -(te.getPos().getY() - rec.y2);
                        int z1 = -(te.getPos().getZ() - rec.z2);
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


                if(te.formTreble.size() != 0) {
                    GL11.glDisable(GL11.GL_TEXTURE_2D);

                    GL11.glPushMatrix();
                    {
                        try {
                            GL11.glColor3f(0F, 1F, 1F);
                            float increment = 25F;
                            int waveSize = te.formTreble.size();
                            float oldX = -50F;
                            float oldY = -125F;
                            float xIndex = -50;
                            for(int i = 0; i < waveSize; i += increment) {
                                if(te.formTreble.size() < i)
                                    break;
                                float scaledSample = te.formTreble.get(i);

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
                            waveSize = te.formBass.size();
                            oldX = -50F;
                            oldY = -200F;
                            xIndex = -50;
                            for(int i = 0; i < waveSize; i += increment) {
                                if(te.formBass.size() < i)
                                    break;
                                float scaledSample = te.formBass.get(i);

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
                int currentY = te.wireSystemInfo.size() * -10 - 75;
                FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
                fontRenderer.drawString("Play Radius: " + te.getSongRadius(), -fontRenderer.getStringWidth("Play Radius: " + te.getSongRadius()) / 2, currentY, 0xFFFFFF);
                for(Entry<String, Integer> nfo : te.wireSystemInfo.entrySet()) {
                    currentY += 10;
                    fontRenderer.drawString(nfo.getValue() + "x " + nfo.getKey(), -fontRenderer.getStringWidth(nfo.getValue() + "x " + nfo.getKey()) / 2, currentY, 0xFFFFFF);
                }
            }
            GL11.glPopMatrix();
        }

        if(te.recordEntity != null) {
            GL11.glPushMatrix();
            {
                GL11.glTranslatef((float)x + .5F, (float)y + .76F, (float)z + .5F);
                GL11.glRotatef(90F, 1F, 0F, 0F);
                GL11.glRotatef(te.recordRotation * 57.3F, 0F, 0F, 1F);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_CULL_FACE);
                if(Minecraft.getMinecraft().gameSettings.fancyGraphics)
                    Minecraft.getMinecraft().getRenderItem().renderItem(te.recordEntity.getEntityItem(), ItemCameraTransforms.TransformType.NONE);
                else{
                    Minecraft.getMinecraft().gameSettings.fancyGraphics = true;
                    Minecraft.getMinecraft().getRenderItem().renderItem(te.recordEntity.getEntityItem(), ItemCameraTransforms.TransformType.NONE);
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
            StaticInfo.modelRecordPlayer.render(null, te.openAmount, te.needleLocation, te.recordRotation, 0.0F, 0.0F, 0.0625F);
        }
        GL11.glPopMatrix();
    }
}
