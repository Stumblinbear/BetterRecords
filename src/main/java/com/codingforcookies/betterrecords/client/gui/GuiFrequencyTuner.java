package com.codingforcookies.betterrecords.client.gui;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FilenameUtils;
import org.lwjgl.opengl.GL11;

import com.codingforcookies.betterrecords.common.util.BetterUtils;
import com.codingforcookies.betterrecords.common.lib.StaticInfo;
import com.codingforcookies.betterrecords.client.sound.IcyURLConnection;
import com.codingforcookies.betterrecords.common.block.tile.TileEntityFrequencyTuner;
import com.codingforcookies.betterrecords.common.packets.PacketHandler;

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

public class GuiFrequencyTuner extends GuiContainer{

	TileEntityFrequencyTuner tileEntity;
	GuiTextField nameField;
	GuiTextField urlField;
	String error = "";
	long checkURLTime = 0;
	boolean checkedURL = false;

	public GuiFrequencyTuner(InventoryPlayer inventoryPlayer, TileEntityFrequencyTuner tileEntity){
		super(new ContainerFrequencyTuner(inventoryPlayer, tileEntity));
		this.tileEntity = tileEntity;
	}

	public void initGui(){
		super.initGui();
		nameField = new GuiTextField(1, this.fontRendererObj, 44, 20, 124, 10);
		urlField = new GuiTextField(2, this.fontRendererObj, 44, 35, 124, 10);
		urlField.setMaxStringLength(128);
	}

	protected void keyTyped(char par1, int par2) throws IOException {
		checkedURL = false;
		checkURLTime = System.currentTimeMillis() + 2000;
		if(nameField.isFocused()) nameField.textboxKeyTyped(par1, par2);
		else if(urlField.isFocused()) urlField.textboxKeyTyped(par1, par2);
		else super.keyTyped(par1, par2);
	}

	protected void mouseClicked(int par1, int par2, int par3) throws IOException {
		super.mouseClicked(par1, par2, par3);
		int x = par1 - (width - xSize) / 2;
		int y = par2 - (height - ySize) / 2;
		nameField.mouseClicked(x, y, par3);
		urlField.mouseClicked(x, y, par3);
		if(error == BetterUtils.getTranslatedString("gui.frequencytuner.ready") && x >= 44 && x <= 75 && y >= 51 && y <= 66){
			String superName = FilenameUtils.getName(urlField.getText());
			superName = superName.split("#")[0];
			superName = superName.split("\\?")[0];
			PacketHandler.sendURLWriteFromClient(tileEntity.getPos().getX(), tileEntity.getPos().getY(), tileEntity.getPos().getZ(), superName, urlField.getText(), nameField.getText(), 0);
		}
	}

	protected void drawGuiContainerForegroundLayer(int par1, int par2){
		fontRendererObj.drawString(BetterUtils.getTranslatedString("gui.frequencytuner"), 8, 6, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
		fontRendererObj.drawString(BetterUtils.getTranslatedString("gui.name") + ": ", 10, 21, 4210752);
		fontRendererObj.drawString(BetterUtils.getTranslatedString("gui.url") + ": ", 10, 36, 4210752);
		int mx = par1 - (width - xSize) / 2;
		int my = par2 - (height - ySize) / 2;
		fontRendererObj.drawStringWithShadow(BetterUtils.getTranslatedString("gui.frequencytuner.tune"), 48, 53, (error == BetterUtils.getTranslatedString("gui.frequencytuner.ready") ? (mx >= 44 && mx <= 75 && my >= 51 && my <= 66 ? 0xFFFF55 : 0xFFFFFF) : 0x555555));
		fontRendererObj.drawString(error, 172 - fontRendererObj.getStringWidth(error), 65, (error == BetterUtils.getTranslatedString("gui.frequencytuner.ready") ? 0x229922 : 0x992222));
		nameField.drawTextBox();
		urlField.drawTextBox();
		if(tileEntity.crystal == null) error = BetterUtils.getTranslatedString("gui.frequencytuner.error1");
		else if(tileEntity.crystal.hasTagCompound() && tileEntity.crystal.getTagCompound().hasKey("url")) error = BetterUtils.getTranslatedString("gui.frequencytuner.error2");
		else if(nameField.getText().length() == 0) error = BetterUtils.getTranslatedString("gui.error1");
		else if(nameField.getText().length() < 3) error = BetterUtils.getTranslatedString("gui.error2");
		else if(urlField.getText().length() == 0) error = BetterUtils.getTranslatedString("gui.error3");
		else if(!checkedURL){
			error = BetterUtils.getTranslatedString("gui.validating");
			if(checkURLTime < System.currentTimeMillis()){
				checkURLTime = 0;
				try{
					IcyURLConnection connection = new IcyURLConnection(new URL(urlField.getText().replace(" ", "%20")));
					if(connection instanceof IcyURLConnection){
						connection.setInstanceFollowRedirects(true);
						((HttpURLConnection) connection).setRequestMethod("HEAD");
						connection.connect();
						if(((HttpURLConnection) connection).getResponseCode() != 200) error = BetterUtils.getTranslatedString("gui.error4");
						else error = BetterUtils.getTranslatedString("gui.frequencytuner.ready");
					}else error = BetterUtils.getTranslatedString("gui.frequencytuner.error3");
				}catch(MalformedURLException e){
					error = BetterUtils.getTranslatedString("gui.error5");
				}catch(IOException e){
					error = BetterUtils.getTranslatedString("gui.error6");
				}
				checkedURL = true;
			}
		}
	}

	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3){
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		mc.renderEngine.bindTexture(StaticInfo.GUIFrequencyTuner);
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		drawTexturedModalRect(x + 43, y + 51, 0, (error == BetterUtils.getTranslatedString("gui.frequencytuner.ready") ? 166 : 178), 33, 12);
	}
}