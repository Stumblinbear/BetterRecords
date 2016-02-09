package com.codingforcookies.betterrecords.gui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.FontRenderer;

public class ControlHandler {
	public ArrayList<Control> controls;
	public int offsetX, offsetY, width, height;
	
	public ControlHandler(int offsetX, int offsetY, int width, int height) {
		controls = new ArrayList<Control>();
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.width = width;
		this.height = height;
	}
	
	public void mousepressed(int mousebtn, int mouseX, int mouseY) {
		for(Control ctrl : controls) {
			int i = ctrl.mousepressed(mousebtn, mouseX - offsetX, mouseY - offsetY);
			if(i != 0)
				ctrl.info.onActivated(mousebtn, i);
		}
	}
	
	public void render(FontRenderer fontRenderer, int mouseX, int mouseY) {
		mouseX -= offsetX;
		mouseY -= offsetY;
		GL11.glPushMatrix();
		{
			GL11.glTranslatef(offsetX, offsetY, 0F);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			
			GL11.glBegin(GL11.GL_QUADS);
			{
				GL11.glColor4f(0F, 0F, 0F, .5F);
				GL11.glVertex2f(width, 0);
				GL11.glVertex2f(-5, 0);
				GL11.glVertex2f(-5, height);
				GL11.glVertex2f(width, height);
			}
			GL11.glEnd();
			
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			
			for(Control ctrl : controls) {
				fontRenderer.drawString(ctrl.text + ":", 0, ctrl.y, 0xFFFFFF);
				ctrl.render(fontRenderer, mouseX, mouseY);
			}
			
			for(Control ctrl : controls)
				if(mouseX > 0 && mouseX < ctrl.x && mouseY > ctrl.y && mouseY < ctrl.y + ctrl.height)
					ctrl.info.onHover(mouseX, mouseY);
			
			GL11.glTranslatef(-offsetX, -offsetY, 0F);
		}
		GL11.glPopMatrix();
	}
}