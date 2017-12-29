package com.codingforcookies.betterrecords.library

import com.codingforcookies.betterrecords.api.library.LibraryEntryMusic
import com.codingforcookies.betterrecords.api.library.LibraryEntryRadio
import net.minecraft.client.Minecraft
import java.io.File
import java.net.URL

/**
 * Library containing all of the songs / radio stations
 * that are available on the client or server
 *
 * Sources include:
 *  - Local library files
 *  - Remote library files
 *
 *  Local library files are loaded from  $minecraft/betterrecords/library/music
 *                                  and  $minecraft/betterrecords/library/radio
 *
 *  Remote library files are loaded from $minecraft/betterrecords/library/music/remote
 *                                   and $minecraft/betterrecords/library/radio/remote
 *
 * If we are running on the server, entries in this library will be sent to the client.
 * If we are running on the client, entries from the server will be added to this library.
 */
object Library {

    /** The directory where local libraries are stored */
    private val LOCAL_LIBRARY_DIR = File(Minecraft.getMinecraft().mcDataDir, "betterrecords/library")
    /** The directory where local music libraries are stored */
    private val MUSIC_LIBRARY_DIR = File(LOCAL_LIBRARY_DIR, "music")
    /** The directory where local radio libraries are stored */
    private val RADIO_LIBRARY_DIR = File(LOCAL_LIBRARY_DIR, "radio")

    // TODO: Load remote URLS from config
    private val REMOTE_MUSIC_URLS = listOf("URL1", "URL2")
    private val REMOTE_RADIO_URLS = listOf("URL1", "URL2")

    /**
     * All of the music library entries that have been loaded
     */
    val musicFiles = mutableListOf<LibraryEntryMusic>()

    /**
     * All of the radio library entries that have been loaded
     */
    val radioFiles = mutableListOf<LibraryEntryRadio>()

    /**
     * Entry point for loading the library
     * Load both local and remote files
     */
    fun init() {
        MUSIC_LIBRARY_DIR
                .listFiles()
                .map { LibraryFileMusic(it) }
                .forEach { musicFiles.addAll(it.entries) }

        RADIO_LIBRARY_DIR
                .listFiles()
                .map { LibraryFileRadio(it) }
                .forEach { radioFiles.addAll(it.entries) }

        REMOTE_MUSIC_URLS
                .map { URL(it) }
                .map { LibraryFileMusic(it) }
                .forEach { musicFiles.addAll(it.entries) }

        REMOTE_RADIO_URLS
                .map { URL(it) }
                .map { LibraryFileRadio(it) }
                .forEach { radioFiles.addAll(it.entries) }
    }
}
