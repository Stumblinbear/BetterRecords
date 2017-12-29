package com.codingforcookies.betterrecords.library

import net.minecraft.client.Minecraft
import java.io.File

/**
 * Library containing all of the songs / radio stations
 * that are available on the client or server
 *
 * Sources include:
 *  - Local library files
 *  - Remote library files
 *
 *  Local library files are loaded from  $minecraft/betterrecords/library/
 *
 * If we are running on the server, entries in this library will be sent to the client.
 * If we are running on the client, entries from the server will be added to this library.
 */
object Library {

    /** The directory where local libraries are stored */
    private val LOCAL_LIBRARY_DIR = File(Minecraft.getMinecraft().mcDataDir, "betterrecords/library")

    // TODO: Load remote URLS from config
    private val REMOTE_LIBRARY_URLS = listOf("URL1", "URL2")

    /**
     * All of the library files that have been loaded
     */
    val libraries = mutableListOf<LibraryFile>()

    /**
     * Entry point for loading the library
     * Load both local and remote files
     */
    fun init() {
        LOCAL_LIBRARY_DIR
                .listFiles()
                .map { LibraryFile.fromFile(it) }
                .forEach { libraries.add(it) }
    }
}
