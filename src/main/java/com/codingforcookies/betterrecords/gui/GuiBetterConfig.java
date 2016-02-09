package com.codingforcookies.betterrecords.gui;

import java.util.ArrayList;
import java.util.List;

import com.codingforcookies.betterrecords.BetterRecords;
import com.codingforcookies.betterrecords.BetterUtils;
import com.codingforcookies.betterrecords.client.ClientProxy;
import com.codingforcookies.betterrecords.client.sound.SoundHandler;
import com.codingforcookies.betterrecords.gui.Control.ControlType;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GuiBetterConfig extends GuiScreen{

	public ControlHandler controlHandler;

	public GuiBetterConfig(){}

	public void initGui(){
		controlHandler = new ControlHandler(width / 2 - 80, height / 2 - 75, 166, 155);
		controlHandler.controls.add(new Control(ControlType.BUTTON, BetterUtils.getTranslatedString("gui.config.downloadsongs"), 125, 20, 36, 11, new ControlInformation(){

			public void onActivated(int mousebtn, int ctrlNum){
				SoundHandler.downloadSongs = !SoundHandler.downloadSongs;
				ClientProxy.saveConfig();
			}

			public void onHover(int mouseX, int mouseY){}

			public String getText(){
				return SoundHandler.downloadSongs ? BetterUtils.getTranslatedString("gui.config.on") : BetterUtils.getTranslatedString("gui.config.off");
			}

			public float[] getColor(){
				return new float[]{!SoundHandler.downloadSongs ? 1F : 0F, SoundHandler.downloadSongs ? 1F : 0F, 0F};
			}
		}));
		controlHandler.controls.add(new Control(ControlType.BUTTON, BetterUtils.getTranslatedString("gui.config.streamradio"), 125, 40, 36, 11, new ControlInformation(){

			public void onActivated(int mousebtn, int ctrlNum){
				SoundHandler.streamRadio = !SoundHandler.streamRadio;
				ClientProxy.saveConfig();
			}

			public void onHover(int mouseX, int mouseY){}

			public String getText(){
				return SoundHandler.streamRadio ? BetterUtils.getTranslatedString("gui.config.on") : BetterUtils.getTranslatedString("gui.config.off");
			}

			public float[] getColor(){
				return new float[]{!SoundHandler.streamRadio ? 1F : 0F, SoundHandler.streamRadio ? 1F : 0F, 0F};
			}
		}));
		controlHandler.controls.add(new Control(ControlType.INCREMENT, BetterUtils.getTranslatedString("gui.config.maxdownload"), 125, 60, 36, 11, new ControlInformation(){

			public void onActivated(int mousebtn, int ctrlNum){
				if(ctrlNum == 1) {
					if(ClientProxy.downloadMax - 1 >= 0) ClientProxy.downloadMax--;
				}else if(ctrlNum == 2) {
					if(ClientProxy.downloadMax < 100) ClientProxy.downloadMax++;
				}
				ClientProxy.saveConfig();
			}

			public void onHover(int mouseX, int mouseY){}

			public String getText(){
				return "" + (ClientProxy.downloadMax == 100 ? BetterUtils.getTranslatedString("gui.config.inf") : ClientProxy.downloadMax);
			}

			public float[] getColor(){
				return new float[]{0F, 0F, 0F};
			}
		}));
		controlHandler.controls.add(new Control(ControlType.BUTTON, BetterUtils.getTranslatedString("gui.config.playondownload"), 125, 80, 36, 11, new ControlInformation(){

			public void onActivated(int mousebtn, int ctrlNum){
				ClientProxy.playWhileDownload = !ClientProxy.playWhileDownload;
				ClientProxy.saveConfig();
			}

			public void onHover(int mouseX, int mouseY){
				List<String> infoText = new ArrayList<String>();
				infoText.add(BetterUtils.getTranslatedString("gui.config.playondownload"));
				infoText.add("\2477" + BetterUtils.getTranslatedString("gui.config.playondownload.line1"));
				infoText.add("");
				infoText.add("\247c\247l" + BetterUtils.getTranslatedString("gui.config.playondownload.line1"));
				infoText.add("\247c  " + BetterUtils.getTranslatedString("gui.config.playondownload.line1"));
				infoText.add("\247c  " + BetterUtils.getTranslatedString("gui.config.playondownload.line1"));
				drawHoveringText(infoText, mouseX, mouseY, fontRendererObj);
			}

			public String getText(){
				return ClientProxy.playWhileDownload ? BetterUtils.getTranslatedString("gui.config.on") : BetterUtils.getTranslatedString("gui.config.off");
			}

			public float[] getColor(){
				return new float[]{!ClientProxy.playWhileDownload ? 1F : 0F, ClientProxy.playWhileDownload ? 1F : 0F, 0F};
			}
		}));
		controlHandler.controls.add(new Control(ControlType.INCREMENT, BetterUtils.getTranslatedString("gui.config.streambuffer"), 125, 100, 36, 11, new ControlInformation(){

			public void onActivated(int mousebtn, int ctrlNum){
				if(ctrlNum == 1) {
					if(SoundHandler.streamBuffer != 8) SoundHandler.streamBuffer = SoundHandler.streamBuffer >> 1;
				}else if(ctrlNum == 2) {
					if(SoundHandler.streamBuffer != 4096) SoundHandler.streamBuffer = SoundHandler.streamBuffer << 1;
				}
				ClientProxy.saveConfig();
			}

			public void onHover(int mouseX, int mouseY){
				List<String> infoText = new ArrayList<String>();
				infoText.add(BetterUtils.getTranslatedString("gui.config.streambuffer"));
				infoText.add("\2477" + BetterUtils.getTranslatedString("gui.config.streambuffer.line1"));
				infoText.add("");
				infoText.add("\2477" + BetterUtils.getTranslatedString("gui.config.streambuffer.line2"));
				infoText.add("\2477" + BetterUtils.getTranslatedString("gui.config.streambuffer.line3"));
				infoText.add("\2477" + BetterUtils.getTranslatedString("gui.config.streambuffer.line4"));
				infoText.add("\2477" + BetterUtils.getTranslatedString("gui.config.streambuffer.line5"));
				infoText.add("\2477" + BetterUtils.getTranslatedString("gui.config.streambuffer.line6"));
				infoText.add("\2477" + BetterUtils.getTranslatedString("gui.config.streambuffer.line7"));
				infoText.add("\2477" + BetterUtils.getTranslatedString("gui.config.streambuffer.line8"));
				infoText.add("\2477" + BetterUtils.getTranslatedString("gui.config.streambuffer.line9"));
				infoText.add("\2477" + BetterUtils.getTranslatedString("gui.config.streambuffer.line10"));
				drawHoveringText(infoText, mouseX, mouseY, fontRendererObj);
			}

			public String getText(){
				return "" + SoundHandler.streamBuffer;
			}

			public float[] getColor(){
				return new float[]{0F, 0F, 0F};
			}
		}));
		controlHandler.controls.add(new Control(ControlType.BUTTON, BetterUtils.getTranslatedString("gui.config.flashymode"), 125, 140, 36, 11, new ControlInformation(){

			public void onActivated(int mousebtn, int ctrlNum){
				Minecraft.getMinecraft().thePlayer.openGui(BetterRecords.instance, 2, Minecraft.getMinecraft().theWorld, 0, 0, 0);
			}

			public void onHover(int mouseX, int mouseY){}

			public String getText(){
				return(ClientProxy.flashyMode == 0 ? BetterUtils.getTranslatedString("gui.config.flashymode.none") : (ClientProxy.flashyMode == 1 ? BetterUtils.getTranslatedString("gui.config.flashymode.low") : (ClientProxy.flashyMode == 2 ? BetterUtils.getTranslatedString("gui.config.flashymode.norm") : BetterUtils.getTranslatedString("gui.config.flashymode.rave"))));
			}

			public float[] getColor(){
				return new float[]{(ClientProxy.flashyMode == 3 ? 1F : ClientProxy.flashyMode == 1 ? .75F : 0F), (ClientProxy.flashyMode == 2 ? 1F : ClientProxy.flashyMode == 1 ? .25F : 0F), 0F};
			}
		}));
	}

	protected void mouseClicked(int par1, int par2, int par3){
		super.mouseClicked(par1, par2, par3);
		controlHandler.mousepressed(par3, par1, par2);
	}

	public void drawScreen(int par1, int par2, float par3){
		controlHandler.render(fontRendererObj, par1, par2);
		fontRendererObj.drawString(BetterUtils.getTranslatedString("gui.config.title"), (width - fontRendererObj.getStringWidth(BetterUtils.getTranslatedString("gui.configname"))) / 2, height / 2 - 75 + 6, 0xFFFFFF);
	}
}