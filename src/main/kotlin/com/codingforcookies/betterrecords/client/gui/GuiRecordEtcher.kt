package com.codingforcookies.betterrecords.client.gui

import com.codingforcookies.betterrecords.ID
import com.codingforcookies.betterrecords.api.song.LibrarySong
import com.codingforcookies.betterrecords.block.tile.TileRecordEtcher
import com.codingforcookies.betterrecords.client.ClientProxy
import com.codingforcookies.betterrecords.extensions.glMatrix
import com.codingforcookies.betterrecords.extensions.glVertices
import com.codingforcookies.betterrecords.handler.ConfigHandler
import com.codingforcookies.betterrecords.network.PacketHandler
import com.codingforcookies.betterrecords.network.PacketURLWrite
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiTextField
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.client.resources.I18n
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.util.ResourceLocation
import org.apache.commons.io.FilenameUtils
import org.lwjgl.opengl.GL11
import java.awt.Color
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import kotlin.concurrent.thread

class GuiRecordEtcher(inventoryPlayer: InventoryPlayer, val tileEntity: TileRecordEtcher) : GuiContainer(ContainerRecordEtcher(inventoryPlayer, tileEntity)) {

    val GUI = ResourceLocation(ID, "textures/gui/recordetcher.png")

    lateinit var nameField: GuiTextField
    lateinit var urlField: GuiTextField

    var page = 0
    var maxPage = 0

    var rootObj = JsonObject()

    var checkedURL = false
    var checkURLTime = 0L

    var error = ""

    var selectedLib = -1

    var etchSize = 0

    init {
        xSize = 256
    }

    override fun initGui() {
        super.initGui()

        nameField = GuiTextField(1, fontRenderer, 44, 20, 124, 10)
        urlField = GuiTextField(2, fontRenderer, 44, 35, 124, 10)
        urlField.maxStringLength = 256

        if (ClientProxy.defaultLibrary.size == 0 || ClientProxy.lastCheckType == 0 || ClientProxy.lastCheckType != if (Minecraft.getMinecraft().world.isRemote) 1 else 2) {
            ClientProxy.lastCheckType = if (Minecraft.getMinecraft().world.isRemote) 1 else 2

            println("Loading default Library")
            thread(start = true) {
                val content = URL(ConfigHandler.defaultLibraryURL).readText()
                JsonParser().parse(content).asJsonObject.entrySet()
                        .filter { it.value is JsonObject }
                        .forEach(this::addEntryToLibrary)
            }

            println("Loading available encodings...")
            thread(start = true) {
                val content = URL("https://raw.githubusercontent.com/stumblinbear/Versions/master/betterrecords/encodings.txt").readText()
                ClientProxy.encodings.clear()

                for (line in content.lines()) {
                    ClientProxy.encodings.add(line)
                }
            }

            if (ClientProxy.lastCheckType == 1) {
                println("Loading local library...")
                loadLocalLibrary()
            }
        }
        maxPage = Math.ceil(ClientProxy.defaultLibrary.size.toDouble() / 14).toInt()
    }

    private fun loadLocalLibrary() {
        if (!ClientProxy.localLibrary.exists()) {
            ClientProxy.localLibrary.createNewFile()

            ClientProxy.localLibrary.writer().use {
                it.write("{}")
            }
        }

        ClientProxy.localLibrary.inputStream().reader().use {
            rootObj = JsonParser().parse(it).asJsonObject
            rootObj.entrySet()
                    .filter { it.value is JsonObject }
                    .forEach(this::addEntryToLibrary)
        }
    }

    private fun addEntryToLibrary(entry: Map.Entry<String, JsonElement>) {
        val obj = entry.value.asJsonObject
        ClientProxy.defaultLibrary += LibrarySong(entry.key, obj["author"].asString, obj["name"].asString, obj["url"].asString, obj["color"].asString.replace("#", "").toInt(16))
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        checkedURL = false
        checkURLTime = System.currentTimeMillis() + 2000

        when {
            nameField.isFocused -> nameField.textboxKeyTyped(typedChar, keyCode)
            urlField.isFocused -> urlField.textboxKeyTyped(typedChar, keyCode)
            else -> super.keyTyped(typedChar, keyCode)
        }
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        super.mouseClicked(mouseX, mouseY, mouseButton)

        val x = mouseX - (width - xSize) / 2
        val y = mouseY - (height - ySize) / 2

        nameField.mouseClicked(x, y, mouseButton)
        urlField.mouseClicked(x, y, mouseButton)

        if (error == I18n.format("gui.recordetcher.ready") && x in 44..75 && y in 51..66) {
            if (selectedLib != -1) {
                with(ClientProxy.defaultLibrary[selectedLib]) {
                    PacketHandler.sendToServer(PacketURLWrite(
                            tileEntity.pos,
                            URL(url).openConnection().contentLength / 1024 / 1024,
                            name,
                            url,
                            local,
                            color,
                            author
                    ))
                }
            } else {
                val superName = FilenameUtils.getName(urlField.text)
                        .split("#", "?")[0]
                val superLocal = nameField.text.trim()

                if (ClientProxy.lastCheckType == 1) {
                    if (!ClientProxy.defaultLibrary.any { it.local == superLocal }) {
                        val elmnt = JsonObject().apply {
                            addProperty("author", Minecraft.getMinecraft().player.name)
                            addProperty("name", superName)
                            addProperty("url", urlField.text)
                            addProperty("color", "#FFFFFF")
                        }

                        if (rootObj.size() == 0) {
                            rootObj.add(superLocal, elmnt)
                            ClientProxy.defaultLibrary.add(0, LibrarySong(superLocal, Minecraft.getMinecraft().player.name, superName, urlField.text, "FFFFFF".toInt(16)))
                            if (!ClientProxy.localLibrary.exists()) {
                                with(ClientProxy.localLibrary) {
                                    parentFile.mkdirs()
                                    createNewFile()
                                }
                            }

                            ClientProxy.localLibrary.writer().use {
                                it.write(rootObj.toString())
                            }
                        }
                    }
                }
                PacketHandler.sendToServer(PacketURLWrite(
                        tileEntity.pos,
                        etchSize,
                        superName,
                        urlField.text,
                        superLocal
                ))
            }
        }

        if (x in 175..195 && y in 150..159) {
            if (page > 0) {
                page--
            }
        } else if (x in 229..249 && y in 150..159) {
            if (page < maxPage) {
                page++
            }
        }

        for (i in 0 until 14) {
            val offsetI = page * 14 + i
            if (offsetI > ClientProxy.defaultLibrary.size - 1) break
            if (x in 178..245 && y in (9 + i * 10)..(17 + i * 10)) {
                if (selectedLib == offsetI) {
                    selectedLib = -1
                    nameField.setEnabled(true)
                    urlField.setEnabled(true)
                } else {
                    selectedLib = offsetI
                    nameField.setEnabled(false)
                    urlField.setEnabled(false)
                }
            }
        }
    }

    override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
        with(fontRenderer) {
            drawString(I18n.format("gui.recordetcher"), 8, 6, 4210752)
            drawString(I18n.format("container.inventory"), 8, ySize - 96 + 2, 4210752)
            drawString(I18n.format("gui.name") + ": ", 10, 21, 4210752)
            drawString(I18n.format("gui.url") + ": ", 10, 36, 4210752)
        }

        val x = mouseX - (width - xSize) / 2
        val y = mouseY - (height - ySize) / 2

        with(fontRenderer) {
            val etchColor = if (error == I18n.format("gui.recordetcher.ready")) {
                if (x in 44..75 && y in 51..66) {
                    0xFFFF55
                } else {
                    0xFFFFFF
                }
            } else {
                0x555555
            }

            val errorColor = if (error == I18n.format("gui.recordetcher.ready")) 0x229922 else 0x992222

            drawStringWithShadow(I18n.format("gui.recordetcher.etch"), 50F, 53F, etchColor)
            drawString(error, 172 - fontRenderer.getStringWidth(error), 65, errorColor)
        }

        nameField.drawTextBox()
        urlField.drawTextBox()

        // TODO: Sort out this eldritch behemoth
        if (tileEntity.record.isEmpty) {
            error = I18n.format("gui.recordetcher.error1")
        } else if (tileEntity.record.hasTagCompound() && tileEntity.record.tagCompound!!.hasKey("url")) {
            error = I18n.format("gui.recordetcher.error2")
        } else if (selectedLib != -1) {
            error = I18n.format("gui.recordetcher.ready")
        } else if (nameField.text.isEmpty()) {
            error = I18n.format("gui.error1")
        } else if (nameField.text.length < 3) {
            error = I18n.format("gui.error2")
        } else if (urlField.text.isEmpty()) {
            error = I18n.format("gui.error3")
        } else if (!checkedURL) {
            error = I18n.format("gui.validating")
            if (checkURLTime < System.currentTimeMillis()) {
                checkURLTime = 0
                val url: URL
                try {
                    url = URL(urlField.text.replace(" ", "%20"))
                    val connection = url.openConnection()
                    if (connection is HttpURLConnection) {
                        connection.requestMethod = "HEAD"
                        connection.connect()
                        if (connection.responseCode == 200) {
                            if (connection.getContentLength() / 1024 / 1024 > (if (ConfigHandler.downloadMax != 100) ConfigHandler.downloadMax else 102400)) {
                                error = I18n.format("gui.recordetcher.error3").replace("<size>", "" + ConfigHandler.downloadMax)
                            }
                        } else
                            error = I18n.format("gui.error4")
                    } else {
                        if (Minecraft.getMinecraft().world.isRemote) {
                            connection.connect()
                            if (connection.contentLength == 0) error = I18n.format("gui.recordetcher.error4")
                        } else
                            error = I18n.format("gui.recordetcher.error5")
                    }
                    if (error != "") {
                        etchSize = connection.contentLength / 1024 / 1024
                        val contentType = connection.contentType
                        error = if (ClientProxy.encodings.contains(contentType))
                            I18n.format("gui.recordetcher.ready")
                        else
                            I18n.format("gui.recordetcher.error1").replace("<type>", contentType)
                    }
                } catch (e: MalformedURLException) {
                    error = I18n.format("gui.error5")
                } catch (e: StringIndexOutOfBoundsException) {
                    error = I18n.format("gui.error5")
                } catch (e: IOException) {
                    error = I18n.format("gui.error6")
                } finally {
                    checkedURL = true
                }
            }
        }

        val pageString = "${page + 1}/${maxPage + 1}"
        fontRenderer.drawString(pageString, 195 + fontRenderer.getStringWidth(pageString) / 2, 151, 4210752)

        for (i in 0 until 14) {
            val offsetI = page * 14 + i

            if (offsetI > ClientProxy.defaultLibrary.size -1) {
                break
            }

            if (x in 178..245 && y in (9 + i * 10)..(17 * i + 10)) {
                glMatrix {
                    val txt = listOf(
                            ClientProxy.defaultLibrary[offsetI].local,
                            "\u00a77${I18n.format("item.record.by")}: ${ClientProxy.defaultLibrary[offsetI].author}")
                    drawHoveringText(txt, x, y, fontRenderer)
                }
            }
        }
    }

    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        GlStateManager.color(1F, 1F, 1F, 1F)

        val x = (width - xSize) / 2
        val y = (height - ySize) / 2

        mc.renderEngine.bindTexture(GUI)

        drawTexturedModalRect(x, y, 0, 0, xSize, ySize)
        drawTexturedModalRect(x + 44, y + 51, 0, if (error == I18n.format("gui.recordetcher.ready")) 166 else 178, 33, 12)

        val mx = mouseX - x
        val my = mouseY - y

        if (ClientProxy.defaultLibrary.isEmpty()) {
            return
        }

        for (i in 0 until 14) {
            val offsetI = page * 14 + i

            if (offsetI > ClientProxy.defaultLibrary.size - 1) {
                break
            }

            glMatrix {
                GlStateManager.translate(x.toDouble(), y.toDouble(), 0.toDouble())

                GlStateManager.disableTexture2D()
                RenderHelper.disableStandardItemLighting()

                if (selectedLib == offsetI) {
                    glVertices(GL11.GL_QUADS) {
                        GlStateManager.color(.7F, .7F, .7F)
                        // No GlStateManager methods for this?
                        GL11.glVertex2i(284, 8 + i * 10)
                        GL11.glVertex2i(176, 8 + i * 10)
                        GL11.glVertex2i(176, 18 + i * 10)
                        GL11.glVertex2i(284, 18 + i * 10)
                    }
                }

                glVertices(GL11.GL_QUADS) {
                    with (Color(ClientProxy.defaultLibrary[offsetI].color) ) {
                        GlStateManager.color(red / 255F, green / 255F, blue / 255F)
                    }

                    GL11.glVertex2i(185, 9 + i * 10)
                    GL11.glVertex2i(178, 9 + i * 10)
                    GL11.glVertex2i(178, 17 + i * 10)
                    GL11.glVertex2i(185, 17 + i * 10)
                }

                GlStateManager.enableTexture2D()

                val color = if (mx in 178..245 && my in (9 + i * 10)..(17 + i * 10)) {
                    0xFFFF00
                } else {
                    4210725
                }
                fontRenderer.drawString(ClientProxy.defaultLibrary[offsetI].local, 188, 9 + i * 10, color)
            }
        }
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawDefaultBackground()
        super.drawScreen(mouseX, mouseY, partialTicks)
        renderHoveredToolTip(mouseX, mouseY)
    }
}
