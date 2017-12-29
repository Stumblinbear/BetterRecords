package com.codingforcookies.betterrecords.api.library

/**
 * Superclass for Library Entries.
 */
sealed class LibraryEntry

/**
 * @property name The name of the song
 * @property url The url of the file
 */
data class LibraryEntryMusic(
        val name: String,
        val url: String
) : LibraryEntry()

/**
 * @property name The name of the radio station
 * @property url The url of the radio stream
 */
data class LibraryEntryRadio(
        val name: String,
        val url: String
) : LibraryEntry()
