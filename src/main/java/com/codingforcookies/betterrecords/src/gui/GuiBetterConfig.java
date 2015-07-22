package com.codingforcookies.betterrecords.src.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import com.codingforcookies.betterrecords.src.BetterRecords;
import com.codingforcookies.betterrecords.src.client.ClientProxy;
import com.codingforcookies.betterrecords.src.client.sound.SoundHandler;
import com.codingforcookies.betterrecords.src.gui.Control.ControlType;

public class GuiBetterConfig extends GuiScreen {
	public ControlHandler controlHandler;
	
	public GuiBetterConfig() {
		
	}
	
	public void initGui() {
		controlHandler = new ControlHandler(width / 2 - 80, height / 2 - 75, 166, 155);
		
		controlHandler.controls.add(new Control(ControlType.BUTTON, "Download Songs", 125, 20, 36, 11, new ControlInformation() {
			public void onActivated(int mousebtn, int ctrlNum) { SoundHandler.downloadSongs = !SoundHandler.downloadSongs; ClientProxy.saveConfig(); }
			public void onHover(int mouseX, int mouseY) { }
			public String getText() { return SoundHandler.downloadSongs ? "ON" : "OFF"; }
			public float[] getColor() {
				return new float[]{ !SoundHandler.downloadSongs ? 1F : 0F, SoundHandler.downloadSongs ? 1F : 0F, 0F };
			}
		}));

		controlHandler.controls.add(new Control(ControlType.BUTTON, "Stream Radio", 125, 40, 36, 11, new ControlInformation() {
			public void onActivated(int mousebtn, int ctrlNum) { SoundHandler.streamRadio = !SoundHandler.streamRadio; ClientProxy.saveConfig(); }
			public void onHover(int mouseX, int mouseY) { }
			public String getText() { return SoundHandler.streamRadio ? "ON" : "OFF"; }
			public float[] getColor() {
				return new float[]{ !SoundHandler.streamRadio ? 1F : 0F, SoundHandler.streamRadio ? 1F : 0F, 0F };
			}
		}));
		
		controlHandler.controls.add(new Control(ControlType.INCREMENT, "Max Download(MB)", 125, 60, 36, 11, new ControlInformation() {
			public void onActivated(int mousebtn, int ctrlNum) { if(ctrlNum == 1) { if(ClientProxy.downloadMax - 1 >= 0) ClientProxy.downloadMax--; } else if(ctrlNum == 2) { if(ClientProxy.downloadMax < 100) ClientProxy.downloadMax++; } ClientProxy.saveConfig(); }
			public void onHover(int mouseX, int mouseY) { }
			public String getText() { return "" + (ClientProxy.downloadMax == 100 ? "Inf" : ClientProxy.downloadMax); }
			public float[] getColor() {
				return new float[]{ 0F, 0F, 0F };
			}
		}));
		
		controlHandler.controls.add(new Control(ControlType.BUTTON, "Play on Download", 125, 80, 36, 11, new ControlInformation() {
			public void onActivated(int mousebtn, int ctrlNum) { ClientProxy.playWhileDownload = !ClientProxy.playWhileDownload; ClientProxy.saveConfig(); }
			public void onHover(int mouseX, int mouseY) {
				List<String> infoText = new ArrayList<String>();
				infoText.add("Play on Download");
				infoText.add("\2477Play song while downloading!");
				infoText.add("");
				infoText.add("\247c\247lWARNING!!!");
				infoText.add("\247c  This requires a relatively");
				infoText.add("\247c  fast download speed.");
				drawHoveringText(infoText, mouseX, mouseY, fontRendererObj);
			}
			public String getText() { return ClientProxy.playWhileDownload ? "ON" : "OFF"; }
			public float[] getColor() {
				return new float[]{ !ClientProxy.playWhileDownload ? 1F : 0F, ClientProxy.playWhileDownload ? 1F : 0F, 0F };
			}
		}));
		
		controlHandler.controls.add(new Control(ControlType.INCREMENT, "Stream Buffer", 125, 100, 36, 11, new ControlInformation() {
			public void onActivated(int mousebtn, int ctrlNum) { if(ctrlNum == 1) { if(SoundHandler.streamBuffer != 8) SoundHandler.streamBuffer = SoundHandler.streamBuffer >> 1; } else if(ctrlNum == 2) { if(SoundHandler.streamBuffer != 4096) SoundHandler.streamBuffer = SoundHandler.streamBuffer << 1; } ClientProxy.saveConfig(); }
			public void onHover(int mouseX, int mouseY) {
				List<String> infoText = new ArrayList<String>();
				infoText.add("Stream Buffer");
				infoText.add("\2477Size of the stream buffer");
				infoText.add("");
				infoText.add("\2477This increases the accuracy");
				infoText.add("\2477of amplitude based objects,");
				infoText.add("\2477(i.e. Strobe Lights & Lazers)");
				infoText.add("\2477speed of pausing songs,");
				infoText.add("\2477and time taken before a song");
				infoText.add("\2477is stopped once the record");
				infoText.add("\2477player is broken.");
				infoText.add("\2477But, lower values require");
				infoText.add("\2477better processors.");
				drawHoveringText(infoText, mouseX, mouseY, fontRendererObj);
			}
			public String getText() { return "" + SoundHandler.streamBuffer; }
			public float[] getColor() {
				return new float[]{ 0F, 0F, 0F };
			}
		}));

		controlHandler.controls.add(new Control(ControlType.BUTTON, "Flashy Mode", 125, 140, 36, 11, new ControlInformation() {
			public void onActivated(int mousebtn, int ctrlNum) { Minecraft.getMinecraft().thePlayer.openGui(BetterRecords.instance, 2, Minecraft.getMinecraft().theWorld, 0, 0, 0); }
			public void onHover(int mouseX, int mouseY) { }
			public String getText() { return (ClientProxy.flashyMode == 0 ? "None" : (ClientProxy.flashyMode == 1 ? "Low" : (ClientProxy.flashyMode == 2 ? "Norm" : "RAVE"))); }
			public float[] getColor() {
				return new float[]{ (ClientProxy.flashyMode == 3 ? 1F : ClientProxy.flashyMode == 1 ? .75F : 0F), (ClientProxy.flashyMode == 2 ? 1F : ClientProxy.flashyMode == 1 ? .25F : 0F), 0F };
			}
		}));
	}
	
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		
		controlHandler.mousepressed(par3, par1, par2);
	}
	
	public void drawScreen(int par1, int par2, float par3) {
		controlHandler.render(fontRendererObj, par1, par2);
		
		fontRendererObj.drawString("Better Records Config", (width - fontRendererObj.getStringWidth("Better Records Config")) / 2, height / 2 - 75 + 6, 0xFFFFFF);
	}
}