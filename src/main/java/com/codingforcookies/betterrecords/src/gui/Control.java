package com.codingforcookies.betterrecords.src.gui;

import net.minecraft.client.gui.FontRenderer;

import org.lwjgl.opengl.GL11;

public class Control {
	public ControlType type;
	public String text;
	public int x, y, width, height;
	public ControlInformation info;
	
	public Control(ControlType type, String text, int x, int y, int width, int height, ControlInformation info) {
		this.type = type;
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.info = info;
	}
	
	public int mousepressed(int mousebtn, int mouseX, int mouseY) {
		switch(type) {
			case BUTTON:
				return (mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height ? 1 : 0);
			case INCREMENT:
				return (mouseY > y && mouseY < y + height ? (mouseX > x && mouseX < x + 6 ? 1 : (mouseX > x + width - 6 && mouseX < x + width ? 2 : 0)) : 0);
			default:
				return 0;
		}
	}
	
	public void render(FontRenderer fontRenderer, int mouseX, int mouseY) {
		GL11.glPushMatrix();
		{
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			int hover = 0;
			switch(type) {
				case BUTTON:
					hover = mousepressed(0, mouseX, mouseY);
					if(hover == 1)
						GL11.glColor4f(info.getColor()[0] + .25F, info.getColor()[1] + .25F, info.getColor()[2] + .25F, .5F);
					else
						GL11.glColor4f(info.getColor()[0], info.getColor()[1], info.getColor()[2], .5F);
					GL11.glBegin(GL11.GL_QUADS);
					{
						GL11.glVertex2f(x + width, y);
						GL11.glVertex2f(x, y);
						GL11.glVertex2f(x, y + height);
						GL11.glVertex2f(x + width, y + height);
					}
					GL11.glEnd();
					break;
				case INCREMENT:
					hover = mousepressed(0, mouseX, mouseY);
					if(hover == 1)
						GL11.glColor4f(info.getColor()[0] + .25F, info.getColor()[1] + .25F, info.getColor()[2] + .25F, .5F);
					else
						GL11.glColor4f(info.getColor()[0], info.getColor()[1], info.getColor()[2], .5F);
					GL11.glBegin(GL11.GL_QUADS);
					{
						GL11.glVertex2f(x + 6, y);
						GL11.glVertex2f(x, y);
						GL11.glVertex2f(x, y + height);
						GL11.glVertex2f(x + 6, y + height);
					}
					GL11.glEnd();
					if(hover == 2)
						GL11.glColor4f(info.getColor()[0] + .25F, info.getColor()[1] + .25F, info.getColor()[2] + .25F, .5F);
					else
						GL11.glColor4f(info.getColor()[0], info.getColor()[1], info.getColor()[2], .5F);
					GL11.glBegin(GL11.GL_QUADS);
					{
						GL11.glVertex2f(x + width, y);
						GL11.glVertex2f(x + width - 6, y);
						GL11.glVertex2f(x + width - 6, y + height);
						GL11.glVertex2f(x + width, y + height);
					}
					GL11.glEnd();
					break;
				default:
					break;
			}
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_TEXTURE_2D);

			switch(type) {
				case BUTTON:
					break;
				case INCREMENT:
					fontRenderer.drawString("<", x + 1, y + 2, 0xFFFFFF);
					fontRenderer.drawString(">", x + width - 5, y + 2, 0xFFFFFF);
					break;
				default:
					break;
			}
			
			fontRenderer.drawString(info.getText(), x + width / 2 - fontRenderer.getStringWidth(info.getText()) / 2, y + 2, 0xFFFFFF);
		}
		GL11.glPopMatrix();
	}

	public enum ControlType {
		BUTTON,
		INCREMENT
	}
}