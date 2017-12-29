package com.codingforcookies.betterrecords.library

import net.minecraft.client.Minecraft
import java.io.File

/**
 * Collection of all Libraries containing the songs / radio stations
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
object Libraries {

    /** The directory where local libraries are stored */
    private val LOCAL_LIBRARY_DIR = File(Minecraft.getMinecraft().mcDataDir, "betterrecords/library")

    /** All of the library files that have been loaded */
    val libraries = mutableListOf<Library>()

    /**
     * TEMPORARY shunt to adapt this to match old library system, easing transition
     */
    val songs
            get() = libraries.flatMap { it.songs }

    /**
     * Entry point for loading the library
     * Load both local and remote files
     */
    fun init() {
        if (LOCAL_LIBRARY_DIR.mkdirs()) {
            // TODO: Create a local library for etchings
        }

        LOCAL_LIBRARY_DIR
                .listFiles()
                .map { LocalLibrary(it) }
                .forEach { libraries.add(it) }

        // TODO: Load remote libraries
    }
}
