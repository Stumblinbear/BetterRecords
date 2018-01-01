package com.codingforcookies.betterrecords.client.gui

import com.codingforcookies.betterrecords.ID
import com.codingforcookies.betterrecords.block.tile.TileRecordEtcher
import com.codingforcookies.betterrecords.client.ClientProxy
import com.codingforcookies.betterrecords.handler.ConfigHandler
import com.codingforcookies.betterrecords.library.Libraries
import com.codingforcookies.betterrecords.network.PacketHandler
import com.codingforcookies.betterrecords.network.PacketURLWrite
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiButtonImage
import net.minecraft.client.gui.GuiTextField
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.resources.I18n
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.client.config.GuiButtonExt
import org.apache.commons.io.FilenameUtils
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class GuiRecordEtcher(inventoryPlayer: InventoryPlayer, val tileEntity: TileRecordEtcher) : GuiContainer(ContainerRecordEtcher(inventoryPlayer, tileEntity)) {

    val GUI = ResourceLocation(ID, "textures/gui/recordetcher.png")

    lateinit var nameField: GuiTextField
    lateinit var urlField: GuiTextField

    var selectedLibrary = Libraries.libraries.first()
    //var selectedSong: LibraryEntryMusic? = selectedLibrary.songs.first()

    private var status = Status.NO_RECORD

    var page = 0
    var maxPage = 0

    var selectedLib = -1

    init {
        xSize = 256
    }

    override fun initGui() {
        super.initGui()

        nameField = GuiTextField(1, fontRenderer, 44, 20, 124, 10)
        urlField = GuiTextField(2, fontRenderer, 44, 35, 124, 10)
        urlField.maxStringLength = 256

        // Add main buttons
        buttonList.addAll(listOf(
                // GUI Button image params: id, x, y, width, height, ytexstart, xtexstart, ydifftext
                // Library Left Button
                GuiButtonImage(0, guiLeft + 175, guiTop + 20, 20, 9, 0, 166, 0, GUI),
                // Library Right Button
                GuiButtonImage(1, guiLeft + 229, guiTop + 20, 20, 9, 20, 166, 0, GUI),
                // Page Left Button
                GuiButtonImage(2, guiLeft + 175, guiTop + 150, 20, 9, 0, 166, 0, GUI),
                // Page Right Button
                GuiButtonImage(3, guiLeft + 229, guiTop + 150, 20, 9, 20, 166, 0, GUI),
                // Etch Button
                GuiButton(4, guiLeft + 44, guiTop + 50, 31, 20, "Etch")
        ))

        // Buttons 10-18 are our List buttons
        (0 until 9).forEach { i ->
            buttonList.add(GuiButtonExt(10 + i, guiLeft + 176, guiTop + 31 + 13 * i, 72, 13, ""))
        }

        updateListButtons()
    }

    private fun updateListButtons() {
        val songCount = selectedLibrary.songs.count()

        buttonList
                .takeLast(9 - songCount)
                .forEach {
                    it.visible = false
                }

        buttonList
                .drop(5)
                .take(songCount)
                .forEachIndexed { i, it ->
                    it.displayString = selectedLibrary.songs[i].name
                    it.visible = true
                }
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
    }

    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            0 -> {
                println("LIBRARY LEFT CLICKED")
            }
            1 -> {
                println("LIBRARY RIGHT CLICKED")
            }
            2 -> {
                println("PAGE LEFT CLICKED")
            }
            3 -> {
                println("PAGE RIGHT CLICKED")
            }
            4 -> { // Etch Button
                if (status == Status.READY) {
                    PacketHandler.sendToServer(PacketURLWrite(
                            tileEntity.pos,
                            URL(urlField.text).openConnection().contentLength / 1024 / 1024,
                            FilenameUtils.getName(urlField.text).split("#", "?")[0], // TODO
                            urlField.text, // TODO
                            nameField.text.trim()
                    ))
                }
            }
        }
    }

    override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
        with(fontRenderer) {
            drawString(I18n.format("gui.recordetcher"), 8, 6, 4210752)
            //drawString(I18n.format("container.inventory"), 8, 72, 4210752)
            drawString(I18n.format("gui.name") + ": ", 10, 21, 4210752)
            drawString(I18n.format("gui.url") + ": ", 10, 36, 4210752)

            drawString(selectedLibrary.name, xSize - 5 - getStringWidth(selectedLibrary.name), 8, 4210752)

            val pageString = "${page + 1}/${maxPage + 1}"
            drawString(pageString, 195 + getStringWidth(pageString) / 2, 151, 4210752)

            val statusColor = when (status) {
                Status.READY -> 0x229922
                else -> 0x992222
            }
            drawString(status.message, 172 - getStringWidth(status.message), 72, statusColor)
        }

        nameField.drawTextBox()
        urlField.drawTextBox()

        updateStatus()
    }

    var checkedURL = false
    var checkURLTime = 0L

    // This eldritch behemoth lives on
    private fun updateStatus() {
        if (tileEntity.record.isEmpty) {
            status = Status.NO_RECORD
        } else if (tileEntity.record.hasTagCompound() && tileEntity.record.tagCompound!!.hasKey("url")) {
            status = Status.NOT_BLANK_RECORD
        } else if (selectedLib != -1) {
            status = Status.READY
        } else if (nameField.text.isEmpty()) {
            status = Status.NO_NAME
        } else if (nameField.text.length < 3) {
            status = Status.NAME_TOO_SHORT
        } else if (urlField.text.isEmpty()) {
            status = Status.NO_URL
        } else if (!checkedURL) {
            status = Status.VALIDATING
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
                                status = Status.FILE_TOO_BIG.formatParams(ConfigHandler.downloadMax)
                            }
                        } else {
                            status = Status.INVALID_URL
                        }
                    } else {
                        if (Minecraft.getMinecraft().world.isRemote) {
                            connection.connect()
                            if (connection.contentLength == 0) {
                                status = Status.INVALID_FILE
                            }
                        } else {
                            status = Status.MULTIPLAYER
                        }
                    }
                    if (status == Status.VALIDATING) {
                        val contentType = connection.contentType
                        status = if (ClientProxy.encodings.contains(contentType))
                            Status.READY
                        else
                            Status.INVALID_FILE_ENCODING.formatParams(contentType)
                    }
                } catch (e: MalformedURLException) {
                    status = Status.INVALID_URL
                } catch (e: StringIndexOutOfBoundsException) {
                    status = Status.INVALID_URL
                } catch (e: IOException) {
                    status = Status.IO_EXCEPTION
                } finally {
                    checkedURL = true
                }
            }
        }
    }

    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        mc.renderEngine.bindTexture(GUI)
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize)
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawDefaultBackground()
        super.drawScreen(mouseX, mouseY, partialTicks)
        renderHoveredToolTip(mouseX, mouseY)
    }

    private enum class Status(val translateKey: String) {

        VALIDATING("gui.validating"),
        NO_RECORD("gui.recordetcher.error1"),
        NOT_BLANK_RECORD("gui.recordetcher.error2"),
        NO_NAME("gui.error1"),
        NAME_TOO_SHORT("gui.error2"),
        NO_URL("gui.error3"),
        INVALID_URL("gui.error5"),
        FILE_TOO_BIG("gui.recordetcher.error3"),
        INVALID_FILE("gui.recordetcher.error4"),
        INVALID_FILE_ENCODING("gui.recordetcher.error6"),
        MULTIPLAYER("gui.recordetcher.error5"),
        IO_EXCEPTION("gui.error6"),
        READY("gui.recordetcher.ready");

        var arg: Any = ""

        fun formatParams(arg: Any) = this.apply {
            this.arg = ""
            this.arg = arg
        }

        val message: String get() = I18n.format(translateKey, arg)
    }
}
