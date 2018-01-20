package com.codingforcookies.betterrecords.client.handler

import com.codingforcookies.betterrecords.BetterRecords
import com.codingforcookies.betterrecords.client.old.sound.FileDownloader
import com.codingforcookies.betterrecords.util.ClasspathInjector
import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.Loader
import java.io.File
import java.io.IOException

/**
 * Handler to download and inject any needed external libraries into the classpath.
 */
object ExternalLibraryHandler {

    /** The directory library jars are to be stored in */
    private val libraryLocation = File(Minecraft.getMinecraft().mcDataDir, "betterecords/libs")

    /** The base URL for all of the jars, where they will be downloaded from */
    private const val libHost = "https://raw.githubusercontent.com/stumblinbear/Versions/master/betterrecords/libs/"

    /** A list of all the jars that are to be downloaded and injected */
    private val libraries = arrayOf("vorbisspi-1.0.3-1.jar", "tritonus-share-0.3.7-2.jar", "mp3spi1.9.5.jar", "mp3plugin.jar")

    /**
     * Download all of the files in [libraries] from [libHost] to [libraryLocation] and inject into classpath
     */
    fun init() {
        libraryLocation.mkdirs()

        // Make sure all libraries are downloaded
        libraries
                .forEach(this::downloadIfNotExist)

        // Inject every library into the classpath
        libraries
                .map { File(libraryLocation, it) }
                .forEach(this::injectLibrary)
    }

    /**
     * Downloads the file [name] if it has not yet been downloaded
     */
    private fun downloadIfNotExist(name: String) {
        if (!File(libraryLocation, name).exists()) {
            FileDownloader.downloadFile(name, libHost + name, name, libraryLocation)
        }
    }

    /**
     * Inject the jar [file] into the classpath.
     */
    private fun injectLibrary(file: File) {
        BetterRecords.logger.info("Injecting library: ${file.name}")

        try {
            Loader.instance().modClassLoader.addFile(file)
        } catch (e: IOException) {
            BetterRecords.logger.warn("Failed to load library, trying another method: ${file.name}")
            ClasspathInjector.addFile(file)
        }
    }
}
