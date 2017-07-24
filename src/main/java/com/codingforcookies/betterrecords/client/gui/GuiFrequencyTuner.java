package com.codingforcookies.betterrecords.client.gui;

import com.codingforcookies.betterrecords.ConstantsKt;
import com.codingforcookies.betterrecords.client.sound.IcyURLConnection;
import com.codingforcookies.betterrecords.block.tile.TileFrequencyTuner;
import com.codingforcookies.betterrecords.common.packets.PacketHandler;
import com.codingforcookies.betterrecords.common.util.BetterUtils;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import org.apache.commons.io.FilenameUtils;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class GuiFrequencyTuner extends GuiContainer {

    private static final ResourceLocation GUI = new ResourceLocation(ConstantsKt.ID, "textures/gui/frequencytuner.png");

    TileFrequencyTuner tileEntity;
    GuiTextField nameField;
    GuiTextField urlField;
    String error = "";
    long checkURLTime = 0;
    boolean checkedURL = false;

    public GuiFrequencyTuner(InventoryPlayer inventoryPlayer, TileFrequencyTuner tileEntity){
        super(new ContainerFrequencyTuner(inventoryPlayer, tileEntity));
        this.tileEntity = tileEntity;
    }

    @Override
    public void initGui(){
        super.initGui();
        nameField = new GuiTextField(1, this.fontRendererObj, 44, 20, 124, 10);
        urlField = new GuiTextField(2, this.fontRendererObj, 44, 35, 124, 10);
        urlField.setMaxStringLength(128);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        checkedURL = false;
        checkURLTime = System.currentTimeMillis() + 2000;
        if(nameField.isFocused()) nameField.textboxKeyTyped(typedChar, keyCode);
        else if(urlField.isFocused()) urlField.textboxKeyTyped(typedChar, keyCode);
        else super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        int x = mouseX - (width - xSize) / 2;
        int y = mouseY - (height - ySize) / 2;
        nameField.mouseClicked(x, y, mouseButton);
        urlField.mouseClicked(x, y, mouseButton);
        if(error.equals(BetterUtils.getTranslatedString("gui.frequencytuner.ready")) && x >= 44 && x <= 75 && y >= 51 && y <= 66){
            String superName = FilenameUtils.getName(urlField.getText());
            superName = superName.split("#")[0];
            superName = superName.split("\\?")[0];
            PacketHandler.sendURLWriteFromClient(tileEntity.getPos().getX(), tileEntity.getPos().getY(), tileEntity.getPos().getZ(), superName, urlField.getText(), nameField.getText(), 0);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
        fontRendererObj.drawString(BetterUtils.getTranslatedString("gui.frequencytuner"), 8, 6, 4210752);
        fontRendererObj.drawString(I18n.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
        fontRendererObj.drawString(BetterUtils.getTranslatedString("gui.name") + ": ", 10, 21, 4210752);
        fontRendererObj.drawString(BetterUtils.getTranslatedString("gui.url") + ": ", 10, 36, 4210752);
        int mx = mouseX - (width - xSize) / 2;
        int my = mouseY - (height - ySize) / 2;
        fontRendererObj.drawStringWithShadow(BetterUtils.getTranslatedString("gui.frequencytuner.tune"), 48, 53, (error.equals(BetterUtils.getTranslatedString("gui.frequencytuner.ready")) ? (mx >= 44 && mx <= 75 && my >= 51 && my <= 66 ? 0xFFFF55 : 0xFFFFFF) : 0x555555));
        fontRendererObj.drawString(error, 172 - fontRendererObj.getStringWidth(error), 65, (error.equals(BetterUtils.getTranslatedString("gui.frequencytuner.ready")) ? 0x229922 : 0x992222));
        nameField.drawTextBox();
        urlField.drawTextBox();
        if(tileEntity.getCrystal() == null) error = BetterUtils.getTranslatedString("gui.frequencytuner.error1");
        else if(tileEntity.getCrystal().hasTagCompound() && tileEntity.getCrystal().getTagCompound().hasKey("url")) error = BetterUtils.getTranslatedString("gui.frequencytuner.error2");
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
                        connection.setRequestMethod("HEAD");
                        connection.connect();
                        if(connection.getResponseCode() != 200) error = BetterUtils.getTranslatedString("gui.error4");
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

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        mc.renderEngine.bindTexture(GUI);
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        drawTexturedModalRect(x + 43, y + 51, 0, (error.equals(BetterUtils.getTranslatedString("gui.frequencytuner.ready")) ? 166 : 178), 33, 12);
    }
}
