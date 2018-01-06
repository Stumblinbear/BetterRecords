package com.codingforcookies.betterrecords.library

import com.codingforcookies.betterrecords.ModConfig
import net.minecraft.client.Minecraft
import java.io.File
import java.net.URL

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
        /*  Create the library folder if it doesn't exist */
        LOCAL_LIBRARY_DIR.mkdirs()

        // Create the remoteLibraries file if it doesn't exist.
        val remoteLibrariesFile = File(LOCAL_LIBRARY_DIR.parent, "remoteLibraries.txt")
        with (remoteLibrariesFile) {
            if (!exists()) {
                writeText(this::class.java.getResource("/assets/betterrecords/libraries/remoteLibraries.txt").readText())
            }
        }

        // Create an empty library for their etchings if it doesn't exist. We need at least one library.
        with (File(LOCAL_LIBRARY_DIR, "myEtchings.json")) {
            if (!exists()) {
                writeText(this::class.java.getResource("/assets/betterrecords/libraries/empty_library.json").readText())
            }
        }

        // Load the mod's built in libraries
        if (ModConfig.client.loadDefaultLibraries) {
            listOf("/assets/betterrecords/libraries/kevin_macleod.json")
                    .map { RemoteLibrary(this::class.java.getResource(it)) }
                    .forEach { libraries.add(it) }
        }

        // Load all of the local library files
        LOCAL_LIBRARY_DIR
                .listFiles()
                .map { LocalLibrary(it) }
                .forEach { libraries.add(it) }

        // Load remote libraries
        remoteLibrariesFile
                .readLines()
                .map(String::trim)
                .filter { !it.startsWith("#") }
                .map { RemoteLibrary(URL(it)) }
                .forEach { libraries.add(it) }
    }
}
