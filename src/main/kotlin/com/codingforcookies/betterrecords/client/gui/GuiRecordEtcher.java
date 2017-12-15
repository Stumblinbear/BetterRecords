package com.codingforcookies.betterrecords.client.gui;

import com.codingforcookies.betterrecords.ConstantsKt;
import com.codingforcookies.betterrecords.api.song.LibrarySong;
import com.codingforcookies.betterrecords.block.tile.TileRecordEtcher;
import com.codingforcookies.betterrecords.client.ClientProxy;
import com.codingforcookies.betterrecords.handler.ConfigHandler;
import com.codingforcookies.betterrecords.common.packets.PacketHandler;
import com.codingforcookies.betterrecords.util.BetterUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import org.apache.commons.io.FilenameUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class GuiRecordEtcher extends GuiContainer {

    private static final ResourceLocation GUI = new ResourceLocation(ConstantsKt.ID, "textures/gui/recordetcher.png");

    TileRecordEtcher tileEntity;
    GuiTextField nameField;
    GuiTextField urlField;
    String error = "";
    long checkURLTime = 0;
    boolean checkedURL = false;
    int etchSize = 0;
    int selectedLib = -1;
    int page = 0;
    int maxpage = 0;
    private JsonElement root = null;
    private JsonObject rootObj = null;

    public GuiRecordEtcher(InventoryPlayer inventoryPlayer, TileRecordEtcher tileEntity) {
        super(new ContainerRecordEtcher(inventoryPlayer, tileEntity));
        this.tileEntity = tileEntity;
        xSize = 256;
    }

    @Override
    public void initGui() {
        super.initGui();
        nameField = new GuiTextField(1, this.fontRenderer, 44, 20, 124, 10);
        urlField = new GuiTextField(2, this.fontRenderer, 44, 35, 124, 10);
        urlField.setMaxStringLength(256);
        if(ClientProxy.Companion.getDefaultLibrary().size() == 0 || (ClientProxy.Companion.getLastCheckType() == 0 || ClientProxy.Companion.getLastCheckType() != (Minecraft.getMinecraft().world.isRemote ? 1 : 2))){
            System.out.println("Loading default library...");
            ClientProxy.Companion.setLastCheckType(Minecraft.getMinecraft().world.isRemote ? 1 : 2);
            new Thread(new Runnable(){

                public void run(){
                    try{
                        HttpURLConnection request = (HttpURLConnection) new URL(ConfigHandler.INSTANCE.getDefaultLibraryURL()).openConnection();
                        request.connect();
                        if(request.getResponseCode() == 200){
                            JsonParser jp = new JsonParser();
                            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
                            JsonObject rootobj = root.getAsJsonObject();
                            for(Entry<String, JsonElement> entry : rootobj.entrySet()){
                                if(entry.getValue().isJsonObject()){
                                    JsonObject obj = entry.getValue().getAsJsonObject();
                                    ClientProxy.Companion.getDefaultLibrary().add(new LibrarySong(entry.getKey(), obj.get("author").getAsString(), obj.get("name").getAsString(), obj.get("url").getAsString(), Integer.parseInt(obj.get("color").getAsString().replaceFirst("#", ""), 16)));
                                }
                            }
                        }
                        request.disconnect();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
            System.out.println("Loading available encodings...");
            new Thread(new Runnable(){

                public void run(){
                    try{
                        HttpURLConnection request = (HttpURLConnection) new URL("https://raw.githubusercontent.com/stumblinbear/Versions/master/betterrecords/encodings.txt").openConnection();
                        request.connect();
                        if(request.getResponseCode() == 200){
                            ClientProxy.Companion.setEncodings(new ArrayList<>());
                            BufferedReader br = new BufferedReader(new InputStreamReader((InputStream) request.getContent()));
                            String line;
                            while((line = br.readLine()) != null)
                                ClientProxy.Companion.getEncodings().add(line);
                            br.close();
                        }
                        request.disconnect();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
            if(ClientProxy.Companion.getLastCheckType() == 1){
                System.out.println("Loading local library...");
                loadLocalLibrary();
            }
        }
        maxpage = (int) Math.ceil(ClientProxy.Companion.getDefaultLibrary().size() / 14);
    }

    private void loadLocalLibrary() {
        try{
            if(!ClientProxy.Companion.getLocalLibrary().exists()){
                ClientProxy.Companion.getLocalLibrary().createNewFile();
                BufferedWriter writer = null;
                try{
                    writer = new BufferedWriter(new FileWriter(ClientProxy.Companion.getLocalLibrary()));
                    writer.write("{}");
                }finally{
                    try{
                        writer.close();
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }
            JsonParser jp = new JsonParser();
            root = jp.parse(new InputStreamReader(new FileInputStream(ClientProxy.Companion.getLocalLibrary())));
            rootObj = root.getAsJsonObject();
            for(Entry<String, JsonElement> entry : rootObj.entrySet()){
                if(entry.getValue().isJsonObject()){
                    JsonObject obj = entry.getValue().getAsJsonObject();
                    ClientProxy.Companion.getDefaultLibrary().add(0, new LibrarySong(entry.getKey(), obj.get("author").getAsString(), obj.get("name").getAsString(), obj.get("url").getAsString(), Integer.parseInt(obj.get("color").getAsString().replaceFirst("#", ""), 16)));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
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
        if(error.equals(BetterUtils.INSTANCE.getTranslatedString("gui.recordetcher.ready")) && x >= 44 && x <= 75 && y >= 51 && y <= 66){
            if(selectedLib != -1){
                LibrarySong sel = ClientProxy.Companion.getDefaultLibrary().get(selectedLib);
                try{
                    PacketHandler.sendURLWriteFromClient(tileEntity.getPos().getX(), tileEntity.getPos().getY(), tileEntity.getPos().getZ(), sel.name, sel.url, sel.local, new URL(sel.url).openConnection().getContentLength() / 1024 / 1024, sel.color, sel.author);
                }catch(MalformedURLException e){
                    e.printStackTrace();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }else{
                String superName = FilenameUtils.getName(urlField.getText());
                superName = superName.split("#")[0];
                superName = superName.split("\\?")[0];
                String superLocal = nameField.getText().trim();
                if(ClientProxy.Companion.getLastCheckType() == 1){
                    boolean exists = false;
                    for(LibrarySong sng : ClientProxy.Companion.getDefaultLibrary()){
                        if(sng.local.equals(superLocal)){
                            exists = true;
                            break;
                        }
                    }
                    if(!exists){
                        JsonObject elmnt = new JsonObject();
                        elmnt.addProperty("author", Minecraft.getMinecraft().player.getName());
                        elmnt.addProperty("name", superName);
                        elmnt.addProperty("url", urlField.getText());
                        elmnt.addProperty("color", "#FFFFFF");
                        if(rootObj == null) loadLocalLibrary();
                        if(rootObj != null){
                            rootObj.add(superLocal, elmnt);
                            ClientProxy.Companion.getDefaultLibrary().add(0, new LibrarySong(superLocal, Minecraft.getMinecraft().player.getName(), superName, urlField.getText(), Integer.parseInt("FFFFFF", 16)));
                            if(!ClientProxy.Companion.getLocalLibrary().exists()){
                                if(ClientProxy.Companion.getLocalLibrary().getParentFile().mkdirs()) try{
                                    ClientProxy.Companion.getLocalLibrary().createNewFile();
                                }catch(IOException e){
                                    e.printStackTrace();
                                }
                            }
                            BufferedWriter writer = null;
                            try{
                                writer = new BufferedWriter(new FileWriter(ClientProxy.Companion.getLocalLibrary()));
                                writer.write(rootObj.toString());
                            }catch(IOException e){
                                e.printStackTrace();
                            }finally{
                                try{
                                    writer.close();
                                }catch(IOException e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
                PacketHandler.sendURLWriteFromClient(tileEntity.getPos().getX(), tileEntity.getPos().getY(), tileEntity.getPos().getZ(), superName, urlField.getText(), superLocal, etchSize);
            }
        }
        if(x >= 175 && x <= 195 && y >= 150 && y <= 159){
            if(page > 0) page--;
        }else if(x >= 229 && x <= 249 && y >= 150 && y <= 159){
            if(page < maxpage) page++;
        }
        for(int i = 0; i < 14; i++){
            int offsetI = page * 14 + i;
            if(offsetI > ClientProxy.Companion.getDefaultLibrary().size() - 1) break;
            if(x >= 178 && x <= 245 && y >= 9 + i * 10 && y <= 17 + i * 10){
                if(selectedLib == offsetI){
                    selectedLib = -1;
                    nameField.setEnabled(true);
                    urlField.setEnabled(true);
                }else{
                    selectedLib = offsetI;
                    nameField.setEnabled(false);
                    urlField.setEnabled(false);
                }
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString(BetterUtils.INSTANCE.getTranslatedString("gui.recordetcher"), 8, 6, 4210752);
        fontRenderer.drawString(I18n.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
        fontRenderer.drawString(BetterUtils.INSTANCE.getTranslatedString("gui.name") + ": ", 10, 21, 4210752);
        fontRenderer.drawString(BetterUtils.INSTANCE.getTranslatedString("gui.url") + ": ", 10, 36, 4210752);
        int mx = mouseX - (width - xSize) / 2;
        int my = mouseY - (height - ySize) / 2;
        fontRenderer.drawStringWithShadow(BetterUtils.INSTANCE.getTranslatedString("gui.recordetcher.etch"), 50, 53, (error.equals(BetterUtils.INSTANCE.getTranslatedString("gui.recordetcher.ready")) ? (mx >= 44 && mx <= 75 && my >= 51 && my <= 66 ? 0xFFFF55 : 0xFFFFFF) : 0x555555));
        fontRenderer.drawString(error, 172 - fontRenderer.getStringWidth(error), 65, (error.equals(BetterUtils.INSTANCE.getTranslatedString("gui.recordetcher.ready")) ? 0x229922 : 0x992222));
        nameField.drawTextBox();
        urlField.drawTextBox();
        if(tileEntity.getRecord().isEmpty()) error = BetterUtils.INSTANCE.getTranslatedString("gui.recordetcher.error1");
        else if(tileEntity.getRecord().hasTagCompound() && tileEntity.getRecord().getTagCompound().hasKey("url")) error = BetterUtils.INSTANCE.getTranslatedString("gui.recordetcher.error2");
        else if(selectedLib != -1) error = BetterUtils.INSTANCE.getTranslatedString("gui.recordetcher.ready");
        else if(nameField.getText().length() == 0) error = BetterUtils.INSTANCE.getTranslatedString("gui.error1");
        else if(nameField.getText().length() < 3) error = BetterUtils.INSTANCE.getTranslatedString("gui.error2");
        else if(urlField.getText().length() == 0) error = BetterUtils.INSTANCE.getTranslatedString("gui.error3");
        else if(!checkedURL){
            error = BetterUtils.INSTANCE.getTranslatedString("gui.validating");
            if(checkURLTime < System.currentTimeMillis()){
                checkURLTime = 0;
                URL url;
                try{
                    url = new URL(urlField.getText().replace(" ", "%20"));
                    URLConnection connection = url.openConnection();
                    if(connection instanceof HttpURLConnection){
                        ((HttpURLConnection) connection).setRequestMethod("HEAD");
                        connection.connect();
                        if(((HttpURLConnection) connection).getResponseCode() == 200){
                            if(connection.getContentLength() / 1024 / 1024 > (ConfigHandler.INSTANCE.getDownloadMax() != 100 ? ConfigHandler.INSTANCE.getDownloadMax() : 102400)){
                                error = BetterUtils.INSTANCE.getTranslatedString("gui.recordetcher.error3").replace("<size>", "" + ConfigHandler.INSTANCE.getDownloadMax());
                            }
                        }else error = BetterUtils.INSTANCE.getTranslatedString("gui.error4");
                    }else{
                        if(Minecraft.getMinecraft().world.isRemote){
                            connection.connect();
                            if(connection.getContentLength() == 0) error = BetterUtils.INSTANCE.getTranslatedString("gui.recordetcher.error4");
                        }else error = BetterUtils.INSTANCE.getTranslatedString("gui.recordetcher.error5");
                    }
                    if(!error.equals("")){
                        etchSize = connection.getContentLength() / 1024 / 1024;
                        String contentType = connection.getContentType();
                        if(ClientProxy.Companion.getEncodings().contains(contentType)) error = BetterUtils.INSTANCE.getTranslatedString("gui.recordetcher.ready");
                        else error = BetterUtils.INSTANCE.getTranslatedString("gui.recordetcher.error1").replace("<type>", contentType);
                    }
                }catch(MalformedURLException e) {
                    error = BetterUtils.INSTANCE.getTranslatedString("gui.error5");
                }catch (StringIndexOutOfBoundsException e) {
                    error = BetterUtils.INSTANCE.getTranslatedString("gui.error5");
                }catch(IOException e){
                    error = BetterUtils.INSTANCE.getTranslatedString("gui.error6");
                }
                checkedURL = true;
            }
        }
        fontRenderer.drawString((page + 1) + "/" + (maxpage + 1), 195 + fontRenderer.getStringWidth((page + 1) + "/" + (maxpage + 1)) / 2, 151, 4210752);
        for(int i = 0; i < 14; i++){
            int offsetI = page * 14 + i;
            if(offsetI > ClientProxy.Companion.getDefaultLibrary().size() - 1) break;
            if(mx >= 178 && mx <= 245 && my >= 9 + i * 10 && my <= 17 + i * 10){
                GL11.glPushMatrix();
                {
                    List<String> txt = new ArrayList<String>();
                    txt.add(ClientProxy.Companion.getDefaultLibrary().get(offsetI).local);
                    txt.add("\2477" + BetterUtils.INSTANCE.getTranslatedString("item.record.by") + ": " + ClientProxy.Companion.getDefaultLibrary().get(offsetI).author);
                    drawHoveringText(txt, mx, my, fontRenderer);
                }
                GL11.glPopMatrix();
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        mc.renderEngine.bindTexture(GUI);
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        drawTexturedModalRect(x + 44, y + 51, 0, (error.equals(BetterUtils.INSTANCE.getTranslatedString("gui.recordetcher.ready")) ? 166 : 178), 33, 12);
        int mx = mouseX - x;
        int my = mouseY - y;
        if(ClientProxy.Companion.getDefaultLibrary().isEmpty()) return;
        for(int i = 0; i < 14; i++){
            int offsetI = page * 14 + i;
            if(offsetI > ClientProxy.Companion.getDefaultLibrary().size() - 1) break;
            GL11.glPushMatrix();
            {
                GL11.glTranslatef(x, y, 0F);
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                RenderHelper.disableStandardItemLighting();
                if(selectedLib == offsetI){
                    GL11.glBegin(GL11.GL_QUADS);
                    {
                        GL11.glColor3f(.7F, .7F, .7F);
                        GL11.glVertex2f(248, 8 + i * 10);
                        GL11.glVertex2f(176, 8 + i * 10);
                        GL11.glVertex2f(176, 18 + i * 10);
                        GL11.glVertex2f(248, 18 + i * 10);
                    }
                    GL11.glEnd();
                }
                GL11.glBegin(GL11.GL_QUADS);
                {
                    Color color = new Color(ClientProxy.Companion.getDefaultLibrary().get(offsetI).color);
                    GL11.glColor3f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F);
                    GL11.glVertex2f(185, 9 + i * 10);
                    GL11.glVertex2f(178, 9 + i * 10);
                    GL11.glVertex2f(178, 17 + i * 10);
                    GL11.glVertex2f(185, 17 + i * 10);
                }
                GL11.glEnd();
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                fontRenderer.drawString(ClientProxy.Companion.getDefaultLibrary().get(offsetI).local, 188, 9 + i * 10, mx >= 178 && mx <= 245 && my >= 9 + i * 10 && my <= 17 + i * 10 ? 0xFFFF00 : 4210752);
            }
            GL11.glPopMatrix();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}
