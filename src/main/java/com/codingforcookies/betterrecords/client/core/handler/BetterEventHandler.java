package com.codingforcookies.betterrecords.client.core.handler;

import com.codingforcookies.betterrecords.client.sound.FileDownloader;
import com.codingforcookies.betterrecords.client.sound.SoundHandler;
import com.codingforcookies.betterrecords.handler.ConfigHandler;
import com.codingforcookies.betterrecords.util.BetterUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class BetterEventHandler{

    public static String tutorialText = "";
    public static long tutorialTime = 0;
    public static float strobeLinger = 0F;

    @SubscribeEvent
    public void onClientRender(TickEvent.RenderTickEvent event){
        if(event.phase == TickEvent.Phase.END){
            Minecraft mc = Minecraft.getMinecraft();
            ScaledResolution res = new ScaledResolution(mc);
            int width = res.getScaledWidth();
            int height = res.getScaledHeight();
            FontRenderer fontRenderer = mc.fontRendererObj;
            mc.entityRenderer.setupOverlayRendering();
            if(strobeLinger > 0F){
                GL11.glPushMatrix();
                {
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glBegin(GL11.GL_QUADS);
                    {
                        GL11.glColor4f(1F, 1F, 1F, strobeLinger);
                        GL11.glVertex2f(width, 0);
                        GL11.glVertex2f(0, 0);
                        GL11.glVertex2f(0, height);
                        GL11.glVertex2f(width, height);
                    }
                    GL11.glEnd();
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                }
                GL11.glPopMatrix();
                strobeLinger -= (ConfigHandler.INSTANCE.getFlashyMode() < 3 ? 0.01F : 0.2F);
            }
            if(!tutorialText.equals("")){
                if(tutorialTime > System.currentTimeMillis()){
                    String[] str = BetterUtils.INSTANCE.getWordWrappedString(70, tutorialText);
                    long difference = tutorialTime - System.currentTimeMillis();
                    if(difference > 9000) difference = 10000 - difference;
                    else if(difference > 1000) difference = 1000;
                    GL11.glPushMatrix();
                    {
                        GL11.glDisable(GL11.GL_TEXTURE_2D);
                        GL11.glTranslatef(width / 2 - 100, -50 + (difference / 20), 0F);
                        GL11.glEnable(GL11.GL_BLEND);
                        GL11.glBegin(GL11.GL_QUADS);
                        {
                            GL11.glColor4f(0F, 0F, 0F, .75F);
                            GL11.glVertex2f(180, 0);
                            GL11.glVertex2f(0, 0);
                            GL11.glVertex2f(0, 4 + str.length * 5);
                            GL11.glVertex2f(180, 4 + str.length * 5);
                        }
                        GL11.glEnd();
                        GL11.glDisable(GL11.GL_BLEND);
                        GL11.glEnable(GL11.GL_TEXTURE_2D);
                        GL11.glScalef(.5F, .5F, 0F);
                        for(int i = 0; i < str.length; i++)
                            fontRenderer.drawStringWithShadow(str[i], 180 - fontRenderer.getStringWidth(str[i]) / 2, 5 + i * 10, 0xFFFFFF);
                    }
                    GL11.glPopMatrix();
                }else{
                    tutorialText = "";
                    tutorialTime = 0;
                }
            }
            if(FileDownloader.isDownloading){
                GL11.glPushMatrix();
                {
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    GL11.glTranslatef(width / 2 - 50, height - height / 4 + 26, 0F);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glBegin(GL11.GL_QUADS);
                    {
                        GL11.glColor4f(0F, 0F, 0F, .8F);
                        GL11.glVertex2f(100, 0);
                        GL11.glVertex2f(0, 0);
                        GL11.glVertex2f(0, 4);
                        GL11.glVertex2f(100, 4);
                    }
                    GL11.glEnd();
                    GL11.glBegin(GL11.GL_QUADS);
                    {
                        GL11.glColor4f(1F, 1F, 1F, .5F);
                        GL11.glVertex2f(FileDownloader.downloadPercent * 100F, 0);
                        GL11.glVertex2f(0, 0);
                        GL11.glVertex2f(0, 4);
                        GL11.glVertex2f(FileDownloader.downloadPercent * 100F, 4);
                    }
                    GL11.glEnd();
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                }
                GL11.glPopMatrix();
                fontRenderer.drawStringWithShadow(BetterUtils.INSTANCE.getTranslatedString("overlay.downloading") + ": " + FileDownloader.nowDownloading, width / 2 - fontRenderer.getStringWidth(BetterUtils.INSTANCE.getTranslatedString("overlay.downloading") + ": " + FileDownloader.nowDownloading) / 2, height - height / 4 + 15, 0xFFFF33);
            }
            if(!SoundHandler.nowPlaying.equals("")){
                if(SoundHandler.nowPlaying.startsWith("Error:")){
                    fontRenderer.drawStringWithShadow(SoundHandler.nowPlaying, width / 2 - fontRenderer.getStringWidth(SoundHandler.nowPlaying) / 2, height - height / 4, 0x990000);
                    return;
                }else if(SoundHandler.nowPlaying.startsWith("Info:")){
                    fontRenderer.drawStringWithShadow(SoundHandler.nowPlaying, width / 2 - fontRenderer.getStringWidth(SoundHandler.nowPlaying) / 2, height - height / 4, 0xFFFF33);
                    return;
                }
                float f3 = (float) SoundHandler.nowPlayingInt;
                int l1 = Color.HSBtoRGB(f3 / 50.0F, 0.7F, 0.6F) & 16777215;
                int k1 = (int) (f3 * 255.0F / 20.0F);
                if(k1 > 255) k1 = 255;
                fontRenderer.drawStringWithShadow(BetterUtils.INSTANCE.getTranslatedString("overlay.nowplaying") + ": " + SoundHandler.nowPlaying, width / 2 - fontRenderer.getStringWidth(BetterUtils.INSTANCE.getTranslatedString("overlay.nowplaying") + ": " + SoundHandler.nowPlaying) / 2, height - height / 4, l1 + (k1 << 24 & -16777216));
            }
        }
    }
}
