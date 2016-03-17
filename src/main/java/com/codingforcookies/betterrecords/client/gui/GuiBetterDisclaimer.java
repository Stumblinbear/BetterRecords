package com.codingforcookies.betterrecords.client.gui;

import com.codingforcookies.betterrecords.client.core.ClientProxy;
import com.codingforcookies.betterrecords.common.BetterRecords;
import com.codingforcookies.betterrecords.common.util.BetterUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiBetterDisclaimer extends GuiScreen {

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        int x = width / 2 - 100;
        int y = height / 2 - 35;
        if(mouseX > x + 5 && mouseX < x + 45 && mouseY > y + 50 && mouseY < y + 65) ClientProxy.flashyMode = 0;
        else if(mouseX > x + 50 && mouseX < x + 90 && mouseY > y + 50 && mouseY < y + 65) ClientProxy.flashyMode = 1;
        else if(mouseX > x + 95 && mouseX < x + 150 && mouseY > y + 50 && mouseY < y + 65) ClientProxy.flashyMode = 2;
        else if(mouseX > x + 155 && mouseX < x + 195 && mouseY > y + 50 && mouseY < y + 65) ClientProxy.flashyMode = 3;
        if(ClientProxy.flashyMode != -1) {
            ClientProxy.saveConfig();
            Minecraft.getMinecraft().displayGuiScreen(null);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        int x = width / 2 - 100;
        int y = height / 2 - 35;
        GL11.glPushMatrix();
        {
            GL11.glTranslatef(x, y, 0F);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBegin(GL11.GL_QUADS);
            {
                GL11.glColor4f(0F, 0F, 0F, .5F);
                GL11.glVertex2f(200, 0);
                GL11.glVertex2f(0, 0);
                GL11.glVertex2f(0, 70);
                GL11.glVertex2f(200, 70);
            }
            GL11.glEnd();
            GL11.glBegin(GL11.GL_QUADS);
            {
                GL11.glColor4f(1F, 1F, 1F, .75F);
                GL11.glVertex2f(45, 50);
                GL11.glVertex2f(5, 50);
                GL11.glVertex2f(5, 65);
                GL11.glVertex2f(45, 65);
            }
            GL11.glEnd();
            GL11.glBegin(GL11.GL_QUADS);
            {
                GL11.glColor4f(1F, 1F, 1F, .75F);
                GL11.glVertex2f(90, 50);
                GL11.glVertex2f(50, 50);
                GL11.glVertex2f(50, 65);
                GL11.glVertex2f(90, 65);
            }
            GL11.glEnd();
            GL11.glBegin(GL11.GL_QUADS);
            {
                GL11.glColor4f(1F, 1F, 1F, .75F);
                GL11.glVertex2f(150, 50);
                GL11.glVertex2f(95, 50);
                GL11.glVertex2f(95, 65);
                GL11.glVertex2f(150, 65);
            }
            GL11.glEnd();
            GL11.glBegin(GL11.GL_QUADS);
            {
                GL11.glColor4f(1F, 1F, 1F, .75F);
                GL11.glVertex2f(195, 50);
                GL11.glVertex2f(155, 50);
                GL11.glVertex2f(155, 65);
                GL11.glVertex2f(195, 65);
            }
            GL11.glEnd();
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }
        GL11.glPopMatrix();
        fontRendererObj.drawString(BetterUtils.getTranslatedString("gui.disclaimer.title"), (width - fontRendererObj.getStringWidth(BetterUtils.getTranslatedString("gui.disclaime.title"))) / 2, y + 6, 0xFFFFFF);
        GL11.glPushMatrix();
        {
            GL11.glScalef(.5F, .5F, 0F);
            String[] wrapped = BetterUtils.getWordWrappedString(75, BetterUtils.getTranslatedString("gui.disclaimer.warning"));
            for(int i = 0; i < wrapped.length; i++)
                fontRendererObj.drawString(wrapped[i], x * 2 + 6, y * 2 + 42 + i * 12, 0xFFFFFF);
        }
        GL11.glPopMatrix();
        fontRendererObj.drawString(BetterUtils.getTranslatedString("gui.config.flashymode.none"), x + 13, y + 54, 0x000000);
        fontRendererObj.drawString(BetterUtils.getTranslatedString("gui.config.flashymode.low"), x + 62, y + 54, 0x000000);
        fontRendererObj.drawString(BetterUtils.getTranslatedString("gui.config.flashymode.norm"), x + 107, y + 54, 0x000000);
        fontRendererObj.drawString(BetterUtils.getTranslatedString("gui.config.flashymode.rave"), x + 165, y + 54, 0x000000);
        if(mouseX > x + 5 && mouseX < x + 45 && mouseY > y + 50 && mouseY < y + 65) {
            List<String> infoText = new ArrayList<String>();
            infoText.add(BetterUtils.getTranslatedString("gui.disclaimer.flashymode.none"));
            infoText.add("\2477" + BetterUtils.getTranslatedString("gui.disclaimer.flashymode.none.line1"));
            drawHoveringText(infoText, mouseX, mouseY, fontRendererObj);
        }else if(mouseX > x + 50 && mouseX < x + 90 && mouseY > y + 50 && mouseY < y + 65) {
            List<String> infoText = new ArrayList<String>();
            infoText.add(BetterUtils.getTranslatedString("gui.disclaimer.flashymode.low"));
            infoText.add("\2477" + BetterUtils.getTranslatedString("gui.disclaimer.flashymode.low.line1"));
            drawHoveringText(infoText, mouseX, mouseY, fontRendererObj);
        }else if(mouseX > x + 95 && mouseX < x + 150 && mouseY > y + 50 && mouseY < y + 65) {
            List<String> infoText = new ArrayList<String>();
            infoText.add(BetterUtils.getTranslatedString("gui.disclaimer.flashymode.norm"));
            infoText.add("\2477" + BetterUtils.getTranslatedString("gui.disclaimer.flashymode.norm.line1"));
            drawHoveringText(infoText, mouseX, mouseY, fontRendererObj);
        }else if(mouseX > x + 155 && mouseX < x + 195 && mouseY > y + 50 && mouseY < y + 65) {
            List<String> infoText = new ArrayList<String>();
            infoText.add(BetterUtils.getTranslatedString("gui.disclaimer.flashymode.rave"));
            infoText.add("\2477" + BetterUtils.getTranslatedString("gui.disclaimer.flashymode.rave.line1"));
            infoText.add("\2477" + BetterUtils.getTranslatedString("gui.disclaimer.flashymode.rave.line2"));
            infoText.add("\2477" + BetterUtils.getTranslatedString("gui.disclaimer.flashymode.rave.line3"));
            infoText.add("\2477" + BetterUtils.getTranslatedString("gui.disclaimer.flashymode.rave.line4"));
            infoText.add("\2477" + BetterUtils.getTranslatedString("gui.disclaimer.flashymode.rave.line5"));
            drawHoveringText(infoText, mouseX, mouseY, fontRendererObj);
        }
    }
}
