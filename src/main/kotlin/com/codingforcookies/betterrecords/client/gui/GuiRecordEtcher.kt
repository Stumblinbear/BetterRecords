package com.codingforcookies.betterrecords.client.gui

import com.codingforcookies.betterrecords.ID
import com.codingforcookies.betterrecords.block.tile.TileRecordEtcher
import com.codingforcookies.betterrecords.client.ClientProxy
import com.codingforcookies.betterrecords.client.gui.parts.GuiButtonLibrary
import com.codingforcookies.betterrecords.handler.ConfigHandler
import com.codingforcookies.betterrecords.library.Libraries
import com.codingforcookies.betterrecords.network.PacketHandler
import com.codingforcookies.betterrecords.network.PacketURLWrite
import com.codingforcookies.betterrecords.util.BetterUtils
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiButtonImage
import net.minecraft.client.gui.GuiTextField
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.resources.I18n
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.util.ResourceLocation
import org.apache.commons.io.FilenameUtils
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class GuiRecordEtcher(inventoryPlayer: InventoryPlayer, val tileEntity: TileRecordEtcher) : GuiContainer(ContainerRecordEtcher(inventoryPlayer, tileEntity)) {

    val GUI = ResourceLocation(ID, "textures/gui/recordetcher.png")
    val BUTTONS = ResourceLocation(ID, "textures/gui/buttons.png")

    lateinit var nameField: GuiTextField
    lateinit var urlField: GuiTextField
    private var color = 0xFFFFFF

    /**
     * The current status of the GUI. This is updated in [updateStatus]
     */
    private var status = Status.NO_RECORD

    var selectedLibrary = Libraries.libraries.first()
    val selectedLibraryIndex get() = Libraries.libraries.indexOf(selectedLibrary)
    val maxLibraryIndex get() = Libraries.libraries.lastIndex

    var pageIndex = 0
    val maxPageIndex get() = Math.ceil(selectedLibrary.songs.size / 9.0).toInt() - 1

    init {
        xSize = 292
    }

    override fun initGui() {
        super.initGui()

        nameField = GuiTextField(1, fontRenderer, 44, 20, 124, 10)
        urlField = GuiTextField(2, fontRenderer, 44, 35, 124, 10)
        urlField.maxStringLength = 256

        // Add main buttons
        buttonList.addAll(listOf(
                // GUI Button image params: id, x, y, width, height, xtexstart, ytexstart, ydifftext
                // Library Left Button
                GuiButtonImage(0, guiLeft + 175, guiTop + 20, 20, 9, 0, 0, 0, BUTTONS),
                // Library Right Button
                GuiButtonImage(1, guiLeft + 265, guiTop + 20, 20, 9, 20, 0, 0, BUTTONS),
                // Page Left Button
                GuiButtonImage(2, guiLeft + 175, guiTop + 150, 20, 9, 0, 0, 0, BUTTONS),
                // Page Right Button
                GuiButtonImage(3, guiLeft + 265, guiTop + 150, 20, 9, 20, 0, 0, BUTTONS),
                // Etch Button
                GuiButton(4, guiLeft + 44, guiTop + 50, 31, 20, "Etch")
        ))

        // Buttons 10-18 are our List buttons
        (0 until 9).forEach { i ->
            buttonList.add(GuiButtonLibrary(10 + i, guiLeft + 176, guiTop + 31 + 13 * i, 108, 13, "", 0xFFFFFF))
        }

        updateListButtons()
    }

    /**
     * Update the info attached to buttons 10-18, which are our list buttons
     */
    private fun updateListButtons() {
        val songCount = selectedLibrary.songs.drop(pageIndex * 9).count()
        val amountToHide = if (songCount >= 9) 0 else 9 - songCount
        val amountToShow = 9 - amountToHide

        buttonList
                .takeLast(amountToHide)
                .forEach {
                    it.visible = false
                }

        buttonList
                .filterIsInstance<GuiButtonLibrary>()
                .take(amountToShow)
                .forEachIndexed { i, it ->
                    val entry = selectedLibrary.songs[i + pageIndex * 9]
                    it.displayString = entry.name
                    it.color = entry.color
                    it.visible = true
                }
    }

    /**
     * Clear any user entries to the GUI, minus the state of the library view.
     */
    private fun resetGUI() {
        nameField.text = ""
        urlField.text = ""
        color = 0xFFFFFF
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
            0 -> { // Library Left
                selectedLibrary = Libraries.libraries[BetterUtils.wrapInt(selectedLibraryIndex - 1, 0, maxLibraryIndex)]
                pageIndex = 0
                updateListButtons()
            }
            1 -> { // Library Right
                selectedLibrary = Libraries.libraries[BetterUtils.wrapInt(selectedLibraryIndex + 1, 0, maxLibraryIndex)]
                pageIndex = 0
                updateListButtons()
            }
            2 -> { // Page Left
                pageIndex = BetterUtils.wrapInt(pageIndex - 1, 0, maxPageIndex)
                updateListButtons()
            }
            3 -> { // Page Right
                pageIndex = BetterUtils.wrapInt(pageIndex + 1, 0, maxPageIndex)
                updateListButtons()
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

                    resetGUI()
                }
            }
            in 10..18 -> { // One of the Library buttons

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

            val libraryPageString = "${Libraries.libraries.indexOf(selectedLibrary) + 1}/${Libraries.libraries.lastIndex + 1}"
            drawString(libraryPageString, 212 + getStringWidth(libraryPageString) / 2, 20, 4210752)

            val pageString = "${pageIndex + 1}/${maxPageIndex + 1}"
            drawString(pageString, 212 + getStringWidth(pageString) / 2, 151, 4210752)

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

    // Fields required for [updateStatus] to do its thing.
    private var checkedURL = false
    private var checkURLTime = 0L

    /**
     * Update the current status of the GUI.
     *
     * Currently, this is called form [drawGuiContainerForegroundLayer]
     */
    private fun updateStatus() {
        if (tileEntity.record.isEmpty) {
            status = Status.NO_RECORD
        } else if (tileEntity.record.hasTagCompound() && tileEntity.record.tagCompound!!.hasKey("url")) {
            status = Status.NOT_BLANK_RECORD
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
        drawModalRectWithCustomSizedTexture(guiLeft, guiTop, 0F, 0F, xSize, ySize, 292F, 292F)
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawDefaultBackground()
        super.drawScreen(mouseX, mouseY, partialTicks)
        renderHoveredToolTip(mouseX, mouseY)
    }

    /**
     * Enum representing every possible status of our GUI,
     * as well as translation keys.
     *
     * Kind of ugly, but is better than what we had before.
     */
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
