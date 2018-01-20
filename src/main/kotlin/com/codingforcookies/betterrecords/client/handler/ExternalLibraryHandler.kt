package com.codingforcookies.betterrecords.client.handler

import com.codingforcookies.betterrecords.client.old.sound.FileDownloader
import com.codingforcookies.betterrecords.util.ClasspathInjector
import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.Loader
import java.io.File
import java.io.IOException

object ExternalLibraryHandler {

    private val libraryLocation = File(Minecraft.getMinecraft().mcDataDir, "betterecords/libs")
    private const val libHost = "https://raw.githubusercontent.com/stumblinbear/Versions/master/betterrecords/libs/"

    private val libraries = arrayOf("vorbisspi-1.0.3-1.jar", "tritonus-share-0.3.7-2.jar", "mp3spi1.9.5.jar", "mp3plugin.jar")

    fun init() {
        libraryLocation.mkdirs()

        libraries
                .forEach(this::downloadIfNotExist)

        libraries
                .map { File(libraryLocation, it) }
                .forEach(this::injectLibrary)
    }

    private fun downloadIfNotExist(name: String) {
        if (!File(libraryLocation, name).exists()) {
            FileDownloader.downloadFile(name, libHost + name, name, libraryLocation)
        }
    }

    private fun injectLibrary(file: File) {
        println("Injecting library: ${file.name}")

        try {
            Loader.instance().modClassLoader.addFile(file)
        } catch (e: IOException) {
            println("Failed to load library, trying another method: ${file.name}")
            ClasspathInjector.addFile(file)
        }
    }
}
