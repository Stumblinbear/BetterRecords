package com.codingforcookies.betterrecords.src.gui;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.apache.commons.io.FilenameUtils;
import org.lwjgl.opengl.GL11;

import com.codingforcookies.betterrecords.src.StaticInfo;
import com.codingforcookies.betterrecords.src.client.sound.IcyURLConnection;
import com.codingforcookies.betterrecords.src.items.TileEntityFrequencyTuner;
import com.codingforcookies.betterrecords.src.packets.PacketHandler;

public class GuiFrequencyTuner extends GuiContainer {
	TileEntityFrequencyTuner tileEntity;
	GuiTextField nameField;
	GuiTextField urlField;
	String error = "";
	
	long checkURLTime = 0;
	boolean checkedURL = false;
	
	public GuiFrequencyTuner(InventoryPlayer inventoryPlayer, TileEntityFrequencyTuner tileEntity) {
		super(new ContainerFrequencyTuner(inventoryPlayer, tileEntity));
		this.tileEntity = tileEntity;
	}
	
	public void initGui() {
		super.initGui();
		
		nameField = new GuiTextField(this.fontRendererObj, 44, 20, 124, 10);
		urlField = new GuiTextField(this.fontRendererObj, 44, 35, 124, 10);
		urlField.setMaxStringLength(128);
	}
	
	protected void keyTyped(char par1, int par2) {
		checkedURL = false;
		checkURLTime = System.currentTimeMillis() + 2000;
		
		if(nameField.isFocused())
			nameField.textboxKeyTyped(par1, par2);
		else if(urlField.isFocused())
			urlField.textboxKeyTyped(par1, par2);
		else
			super.keyTyped(par1, par2);
	}
	
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		int x = par1 - (width - xSize) / 2;
		int y = par2 - (height - ySize) / 2;
		
		nameField.mouseClicked(x, y, par3);
		urlField.mouseClicked(x, y, par3);
		
		if(error == "Tuner ready!" && x >= 44 && x <= 75 && y >= 51 && y <= 66) {
			String superName = FilenameUtils.getName(urlField.getText());
			superName = superName.split("#")[0];
			superName = superName.split("\\?")[0];
			PacketHandler.sendURLWriteFromClient(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, superName, urlField.getText(), nameField.getText(), 0);
		}
	}

	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		fontRendererObj.drawString("Crystal Tune", 8, 6, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
		
		fontRendererObj.drawString("Name: ", 10, 21, 4210752);
		fontRendererObj.drawString("URL: ", 10, 36, 4210752);
		
		int mx = par1 - (width - xSize) / 2;
		int my = par2 - (height - ySize) / 2;
		fontRendererObj.drawStringWithShadow("Tune", 48, 53, (error == "Tuner ready!" ? (mx >= 44 && mx <= 75 && my >= 51 && my <= 66 ? 0xFFFF55 : 0xFFFFFF) : 0x555555));

		GL11.glPushMatrix();
		{
			GL11.glScalef(.5F, .5F, 1F);
			fontRendererObj.drawString(error, 332 - fontRendererObj.getStringWidth(error), 109, (error == "Tuner ready!" ? 0x229922 : 0x992222));
		}
		GL11.glPopMatrix();
		
		nameField.drawTextBox();
		urlField.drawTextBox();
		
		if(tileEntity.crystal == null)
			error = "No crystal to tune";
		else if(tileEntity.crystal.hasTagCompound() && tileEntity.crystal.stackTagCompound.hasKey("url"))
			error = "Crystal already tuned";
		else if(nameField.getText().length() == 0)
			error = "Please insert a name";
		else if(nameField.getText().length() < 3)
			error = "Name is not long enough";
		else if(urlField.getText().length() == 0)
			error = "Please insert a URL";
		else if(!checkedURL) {
			error = "Validating...";
			
			if(checkURLTime < System.currentTimeMillis()) {
				checkURLTime = 0;
				
				try {
					IcyURLConnection connection = new IcyURLConnection(new URL(urlField.getText().replace(" ", "%20")));
					
					if(connection instanceof IcyURLConnection) {
						connection.setInstanceFollowRedirects(true);
						((HttpURLConnection)connection).setRequestMethod("HEAD");
						connection.connect();
						if(((HttpURLConnection)connection).getResponseCode() != 200)
							error = "URL unavailable (Does it exist?)";
						else
							error = "Tuner ready!";
					}else
						error = "Invalid Icy Stream URL!";
				} catch (MalformedURLException e) {
					error = "Invalid URL";
				} catch (IOException e) {
					error = "IO Exception";
				}
				checkedURL = true;
			}
		}
	}
	
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		mc.renderEngine.bindTexture(StaticInfo.GUIFrequencyTuner);
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		drawTexturedModalRect(x + 43, y + 51, 0, (error == "Tuner ready!" ? 166 : 178), 33, 12);
	}
}