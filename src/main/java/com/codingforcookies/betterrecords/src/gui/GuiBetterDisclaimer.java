package com.codingforcookies.betterrecords.src.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.opengl.GL11;

import com.codingforcookies.betterrecords.src.BetterRecords;
import com.codingforcookies.betterrecords.src.client.ClientProxy;

public class GuiBetterDisclaimer extends GuiScreen {
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);

		int x = width / 2 - 100;
		int y = height / 2 - 35;
		
		if(par1 > x + 5 && par1 < x + 45 && par2 > y + 50 && par2 < y + 65)
			ClientProxy.flashyMode = 0;
		else if(par1 > x + 50 && par1 < x + 90 && par2 > y + 50 && par2 < y + 65)
			ClientProxy.flashyMode = 1;
		else if(par1 > x + 95 && par1 < x + 150 && par2 > y + 50 && par2 < y + 65)
			ClientProxy.flashyMode = 2;
		else if(par1 > x + 155 && par1 < x + 195 && par2 > y + 50 && par2 < y + 65)
			ClientProxy.flashyMode = 3;
		
		if(ClientProxy.flashyMode != -1) {
			ClientProxy.saveConfig();
			Minecraft.getMinecraft().displayGuiScreen(null);
		}
	}
	
	public void drawScreen(int par1, int par2, float par3) {
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
		
		fontRendererObj.drawString("Better Records Disclaimer", (width - fontRendererObj.getStringWidth("Better Records Disclaimer")) / 2, y + 6, 0xFFFFFF);
		
		GL11.glPushMatrix();
		{
			GL11.glScalef(.5F, .5F, 0F);
			String[] wrapped = BetterRecords.getWordWrappedString(75, "WARNING: Parts of this mod make use of flashing lights and images. Consult a parent or guardian before continuing. I am not responsible for anything that may occur while using this mod.");
			for(int i = 0; i < wrapped.length; i++)
				fontRendererObj.drawString(wrapped[i], x * 2 + 6, y * 2 + 42 + i * 12, 0xFFFFFF);
		}
		GL11.glPopMatrix();
		
		fontRendererObj.drawString("None", x + 13, y + 54, 0x000000);
		fontRendererObj.drawString("Low", x + 62, y + 54, 0x000000);
		fontRendererObj.drawString("Normal", x + 107, y + 54, 0x000000);
		fontRendererObj.drawString("RAVE", x + 165, y + 54, 0x000000);
		
		if(par1 > x + 5 && par1 < x + 45 && par2 > y + 50 && par2 < y + 65) {
			List<String> infoText = new ArrayList<String>();
			infoText.add("Flashy Setting: None");
			infoText.add("\2477No flashing lights/images");
			drawHoveringText(infoText, par1, par2, fontRendererObj);
		}else if(par1 > x + 50 && par1 < x + 90 && par2 > y + 50 && par2 < y + 65) {
			List<String> infoText = new ArrayList<String>();
			infoText.add("Flashy Setting: Low");
			infoText.add("\2477Some flashing lights/images");
			drawHoveringText(infoText, par1, par2, fontRendererObj);
		}else if(par1 > x + 95 && par1 < x + 150 && par2 > y + 50 && par2 < y + 65) {
			List<String> infoText = new ArrayList<String>();
			infoText.add("Flashy Setting: Normal");
			infoText.add("\2477Flashing lights/images");
			drawHoveringText(infoText, par1, par2, fontRendererObj);
		}else if(par1 > x + 155 && par1 < x + 195 && par2 > y + 50 && par2 < y + 65) {
			List<String> infoText = new ArrayList<String>();
			infoText.add("Flashy Setting: RAVE");
			infoText.add("\2477For the truly daring.");
			infoText.add("\2477Waaaay too many flashy stuff");
			infoText.add("\2477which makes the game almost");
			infoText.add("\2477unplayable at times. But,");
			infoText.add("\2477it looks awesome.");
			drawHoveringText(infoText, par1, par2, fontRendererObj);
		}
	}
}