package com.codingforcookies.betterrecords.handler

import net.minecraftforge.common.config.Configuration

import java.io.File

object ConfigHandler {

    private var config: Configuration? = null

    // Server
    var maxSpeakerRadius: Int = 0

    // Client
    var downloadSongs: Boolean = false
    var devMode: Boolean = false
    var flashyMode = -1

    var playWhileDownload: Boolean = false
    var downloadMax: Int = 0
    var streamBuffer = 256
    var streamRadio = true

    var defaultLibraryURL = ""

    var tutorials = hashMapOf(
            "recordplayer" to false,
            "speaker" to false,
            "radio" to false,
            "strobelight" to false,
            "laser" to false,
            "lasercluster" to false
    )

    fun loadConfig(configurationFile: File) {
        config = Configuration(configurationFile).apply {
            load()

            // Server Config
            maxSpeakerRadius = getInt("maxSpeakerRadius", "server", -1, -1, 1000, "Maxium speaker radius")

            // Client Config
            downloadSongs = getBoolean("downloadSongs", "client", true, "Should download songs from the internet?")
            devMode = getBoolean("devMode", "client", false, "Enable developer mode")
            downloadMax = getInt("downloadMax", "client", 10, 1, 1000, "Max file size to download")
            playWhileDownload = getBoolean("playWhileDownload", "client", false, "Play songs while downloading?")
            streamBuffer = getInt("streamBuffer", "client", 1024, 256, 2048, "")
            streamRadio = getBoolean("streamRadio", "client", true, "Should radio be streamed?")
            flashyMode = getInt("flashyMode", "client", -1, -1, 3, "Intensity of lights")
            defaultLibraryURL = getString("defaultLibrary", "client",
                    "https://raw.githubusercontent.com/stumblinbear/Versions/master/betterrecords/defaultlibrary.json", "")

            for (entry in tutorials.entries) {
                entry.setValue(getBoolean(entry.key, "client.tutorials", false, ""))
            }

            if (hasChanged()) {
                save()
            }
        }
    }

    fun updateConfig() = config?.let {
        it.get("server", "maxSpeakerRadius", true).set(maxSpeakerRadius)

        it.get("client", "downloadSongs", true).set(downloadSongs)
        it.get("client", "devMode", false).set(devMode)
        it.get("client", "downloadMax", 10).set(downloadMax)
        it.get("client", "playWhileDownload", false).set(playWhileDownload)
        it.get("client", "streamBuffer", 1024).set(streamBuffer)
        it.get("client", "streamRadio", true).set(streamRadio)
        it.get("client", "flashyMode", -1).set(flashyMode)
        it.get("client", "defaultLibrary",
                "https://raw.githubusercontent.com/stumblinbear/Versions/master/betterrecords/defaultlibrary.json").set(defaultLibraryURL)

        for ((key, value) in tutorials) {
            it.get("client.tutorials", key, false).set(value)
        }

        if (it.hasChanged()) {
            it.save()
        }
    }
}
