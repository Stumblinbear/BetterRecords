package com.codingforcookies.betterrecords.client.core.handler;

import com.codingforcookies.betterrecords.api.connection.RecordConnection;
import com.codingforcookies.betterrecords.api.wire.IRecordWireHome;
import com.codingforcookies.betterrecords.client.sound.FileDownloader;
import com.codingforcookies.betterrecords.client.sound.SoundHandler;
import com.codingforcookies.betterrecords.client.sound.SoundManager;
import com.codingforcookies.betterrecords.BetterRecords;
import com.codingforcookies.betterrecords.common.core.handler.ConfigHandler;
import com.codingforcookies.betterrecords.item.ItemWire;
import com.codingforcookies.betterrecords.common.util.BetterUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Map.Entry;

public class BetterEventHandler{

    public static String tutorialText = "";
    public static long tutorialTime = 0;
    public static float strobeLinger = 0F;

    @SubscribeEvent
    public void onDrawBlockHighlight(DrawBlockHighlightEvent event){
        if(!event.getTarget().typeOfHit.equals(RayTraceResult.Type.BLOCK)) return;
        Minecraft mc = Minecraft.getMinecraft();
        if(ItemWire.Companion.getConnection() != null){
            float dx = (float) (mc.player.prevPosX + (mc.player.posX - mc.player.prevPosX) * event.getPartialTicks());
            float dy = (float) (mc.player.prevPosY + (mc.player.posY - mc.player.prevPosY) * event.getPartialTicks());
            float dz = (float) (mc.player.prevPosZ + (mc.player.posZ - mc.player.prevPosZ) * event.getPartialTicks());
            float x1 = -(float) (event.getTarget().getBlockPos().getX() - (ItemWire.Companion.getConnection().fromHome ? ItemWire.Companion.getConnection().x1 : ItemWire.Companion.getConnection().x2));
            float y1 = -(float) (event.getTarget().getBlockPos().getY() - (ItemWire.Companion.getConnection().fromHome ? ItemWire.Companion.getConnection().y1 : ItemWire.Companion.getConnection().y2));
            float z1 = -(float) (event.getTarget().getBlockPos().getZ() - (ItemWire.Companion.getConnection().fromHome ? ItemWire.Companion.getConnection().z1 : ItemWire.Companion.getConnection().z2));
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glLineWidth(2F);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDepthMask(false);
            Block j = mc.world.getBlockState(new BlockPos(event.getTarget().getBlockPos().getX(), event.getTarget().getBlockPos().getY(), event.getTarget().getBlockPos().getZ())).getBlock();
            //TODO
            //j.setBlockBoundsBasedOnState(mc.world, new BlockPos(event.getTarget().getBlockPos().getX(), event.getTarget().getBlockPos().getY(), event.getTarget().getBlockPos().getZ()));
            TileEntity t = mc.world.getTileEntity(new BlockPos(event.getTarget().getBlockPos().getX(), event.getTarget().getBlockPos().getY(), event.getTarget().getBlockPos().getZ()));
            //RenderGlobal.drawOutlinedBoundingBox(j.getSelectedBoundingBoxFromPool(mc.world, event.getTarget().blockX, event.getTarget().blockY, event.getTarget().blockZ).expand(0.002D, 0.002D, 0.002D).getOffsetBoundingBox(-dx, -dy, -dz), ((t instanceof IRecordWire ? (ItemWire.connection.fromHome ? t instanceof IRecordWireHome : !(t instanceof IRecordWireHome)) : false) || Math.sqrt(Math.pow(x1, 2) + Math.pow(y1, 2) + Math.pow(z1, 2)) > 7) ? 0xFF0000 : (t instanceof IRecordWire ? 0x00FF00 : 0xFFFF00));
            GL11.glDepthMask(true);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_BLEND);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onRenderEvent(RenderWorldLastEvent event){
        Minecraft mc = Minecraft.getMinecraft();
        if(ItemWire.Companion.getConnection() != null){
            if(mc.player.getHeldItemMainhand() == null || !(mc.player.getHeldItemMainhand().getItem() instanceof ItemWire)){
                ItemWire.Companion.setConnection(null);
            }else{
                GL11.glPushMatrix();
                {
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    float dx = (float) (mc.player.prevPosX + (mc.player.posX - mc.player.prevPosX) * event.getPartialTicks());
                    float dy = (float) (mc.player.prevPosY + (mc.player.posY - mc.player.prevPosY) * event.getPartialTicks());
                    float dz = (float) (mc.player.prevPosZ + (mc.player.posZ - mc.player.prevPosZ) * event.getPartialTicks());
                    float x1 = -(dx - (ItemWire.Companion.getConnection().fromHome ? ItemWire.Companion.getConnection().x1 : ItemWire.Companion.getConnection().x2));
                    float y1 = -(dy - (ItemWire.Companion.getConnection().fromHome ? ItemWire.Companion.getConnection().y1 : ItemWire.Companion.getConnection().y2));
                    float z1 = -(dz - (ItemWire.Companion.getConnection().fromHome ? ItemWire.Companion.getConnection().z1 : ItemWire.Companion.getConnection().z2));
                    GL11.glTranslatef(x1 + .5F, y1 + .5F, z1 + .5F);
                    GL11.glLineWidth(2F);
                    GL11.glColor3f(0F, 0F, 0F);
                    GL11.glBegin(GL11.GL_LINE_STRIP);
                    {
                        GL11.glVertex3f(0F, 0F, 0F);
                        GL11.glVertex3f(0F, 3F, 0F);
                    }
                    GL11.glEnd();
                    if(ConfigHandler.devMode && ItemWire.Companion.getConnection().fromHome){
                        if(SoundHandler.soundPlaying.containsKey(ItemWire.Companion.getConnection().x1 + "," + ItemWire.Companion.getConnection().y1 + "," + ItemWire.Companion.getConnection().z1 + "," + mc.world.provider.getDimension())){
                            float radius = SoundHandler.soundPlaying.get(ItemWire.Companion.getConnection().x1 + "," + ItemWire.Companion.getConnection().y1 + "," + ItemWire.Companion.getConnection().z1 + "," + mc.world.provider.getDimension()).getCurrentSong().playRadius;
                            GL11.glDisable(GL11.GL_CULL_FACE);
                            GL11.glEnable(GL11.GL_BLEND);
                            GL11.glColor4f(.1F, .1F, 1F, .2F);
                            GL11.glBegin(GL11.GL_LINE_STRIP);
                            {
                                GL11.glVertex2f(0F, 0F);
                                GL11.glVertex2f(0F, radius + 10F);
                            }
                            GL11.glEnd();
                            double factor = Math.PI * 2 / 45;
                            for(double phi = 0; phi <= Math.PI / 1.05; phi += factor){
                                GL11.glBegin(GL11.GL_QUAD_STRIP);
                                {
                                    for(double theta = 0; theta <= Math.PI * 2 + factor; theta += factor){
                                        double x = radius * Math.sin(phi) * Math.cos(theta);
                                        double y = -radius * Math.cos(phi);
                                        double z = radius * Math.sin(phi) * Math.sin(theta);
                                        GL11.glVertex3d(x, y, z);
                                        x = radius * Math.sin(phi + factor) * Math.cos(theta);
                                        y = -radius * Math.cos(phi + factor);
                                        z = radius * Math.sin(phi + factor) * Math.sin(theta);
                                        GL11.glVertex3d(x, y, z);
                                    }
                                }
                                GL11.glEnd();
                            }
                            float volumeRadius = radius * (Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.MASTER) * Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.RECORDS));
                            GL11.glColor4f(1F, .1F, .1F, .2F);
                            for(double phi = 0; phi <= Math.PI / 1.05; phi += factor){
                                GL11.glBegin(GL11.GL_QUAD_STRIP);
                                {
                                    for(double theta = 0; theta <= Math.PI * 2 + factor; theta += factor){
                                        double x = volumeRadius * Math.sin(phi) * Math.cos(theta);
                                        double y = -volumeRadius * Math.cos(phi);
                                        double z = volumeRadius * Math.sin(phi) * Math.sin(theta);
                                        GL11.glVertex3d(x, y, z);
                                        x = volumeRadius * Math.sin(phi + factor) * Math.cos(theta);
                                        y = -volumeRadius * Math.cos(phi + factor);
                                        z = volumeRadius * Math.sin(phi + factor) * Math.sin(theta);
                                        GL11.glVertex3d(x, y, z);
                                    }
                                }
                                GL11.glEnd();
                            }
                            GL11.glDisable(GL11.GL_BLEND);
                            GL11.glEnable(GL11.GL_CULL_FACE);
                        }
                    }
                    GL11.glColor3f(1F, 1F, 1F);
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                }
                GL11.glPopMatrix();
            }
        }
    }

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
                strobeLinger -= (ConfigHandler.flashyMode < 3 ? 0.01F : 0.2F);
            }
            if(!tutorialText.equals("")){
                if(tutorialTime > System.currentTimeMillis()){
                    String[] str = BetterUtils.getWordWrappedString(70, tutorialText);
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
                fontRenderer.drawStringWithShadow(BetterUtils.getTranslatedString("overlay.downloading") + ": " + FileDownloader.nowDownloading, width / 2 - fontRenderer.getStringWidth(BetterUtils.getTranslatedString("overlay.downloading") + ": " + FileDownloader.nowDownloading) / 2, height - height / 4 + 15, 0xFFFF33);
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
                fontRenderer.drawStringWithShadow(BetterUtils.getTranslatedString("overlay.nowplaying") + ": " + SoundHandler.nowPlaying, width / 2 - fontRenderer.getStringWidth(BetterUtils.getTranslatedString("overlay.nowplaying") + ": " + SoundHandler.nowPlaying) / 2, height - height / 4, l1 + (k1 << 24 & -16777216));
            }
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event){
        if(event.phase == TickEvent.Phase.START){
            if(!SoundHandler.nowPlaying.equals("")) if(SoundHandler.nowPlayingEnd < System.currentTimeMillis()){
                SoundHandler.nowPlaying = "";
            }else SoundHandler.nowPlayingInt += 3;
            if(SoundHandler.soundPlaying.size() > 0){
                EntityPlayer player = Minecraft.getMinecraft().player;
                World world = Minecraft.getMinecraft().world;
                if(world == null || player == null){
                    SoundHandler.soundPlaying.clear();
                    SoundHandler.nowPlaying = "";
                    SoundHandler.nowPlayingEnd = 0;
                    SoundHandler.nowPlayingInt = 0;
                }else for(Entry<String, SoundManager> entry : SoundHandler.soundPlaying.entrySet()){
                    if(entry.getValue().getCurrentSong() == null || entry.getValue().getCurrentSong().volume == null) continue;
                    if(entry.getValue().getCurrentSong().dimension == 1234) entry.getValue().getCurrentSong().volume.setValue(-20F);
                    else if(entry.getValue().getCurrentSong().dimension != world.provider.getDimension()) entry.getValue().getCurrentSong().volume.setValue(-80F);
                    else{
                        TileEntity tileEntity = Minecraft.getMinecraft().world.getTileEntity(new BlockPos(entry.getValue().getCurrentSong().x, entry.getValue().getCurrentSong().y, entry.getValue().getCurrentSong().z));
                        if(tileEntity != null && tileEntity instanceof IRecordWireHome) entry.getValue().getCurrentSong().playRadius = ((IRecordWireHome) tileEntity).getSongRadius();
                        float dist = (float) Math.abs(Math.sqrt(Math.pow(player.posX - entry.getValue().getCurrentSong().x, 2) + Math.pow(player.posY - entry.getValue().getCurrentSong().y, 2) + Math.pow(player.posZ - entry.getValue().getCurrentSong().z, 2)));
                        IRecordWireHome wireHome = (IRecordWireHome) tileEntity;
                        if (wireHome != null) {
                            for (RecordConnection rc : wireHome.getConnections()) {
                                float d = (float) Math.abs(Math.sqrt(Math.pow(player.posX - rc.x2, 2) + Math.pow(player.posY - rc.y2, 2) + Math.pow(player.posZ - rc.z2, 2)));
                                if (d < dist) {
                                    dist = d;
                                }
                            }
                        }
                        if(dist > entry.getValue().getCurrentSong().playRadius + 10F) entry.getValue().getCurrentSong().volume.setValue(-80F);
                        else{
                            float volume = dist * (50F / entry.getValue().getCurrentSong().playRadius / (Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.MASTER) * Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.RECORDS)));
                            if(volume > 80F) entry.getValue().getCurrentSong().volume.setValue(-80F);
                            else entry.getValue().getCurrentSong().volume.setValue(0F - volume);
                        }
                    }
                }
            }
        } else { //TickEvent.Phase.END
            if(Minecraft.getMinecraft().player != null) {
                if (ConfigHandler.flashyMode == -1)
                    Minecraft.getMinecraft().player.openGui(BetterRecords.INSTANCE, 2, Minecraft.getMinecraft().world, 0, 0, 0);
            }
        }
    }
}
