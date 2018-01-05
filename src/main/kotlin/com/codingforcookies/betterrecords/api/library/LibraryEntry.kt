package com.codingforcookies.betterrecords.api.library

/**
 * Superclass for Library Entries.
 */
sealed class LibraryEntry

/**
 * Class representing a music library entry
 */
data class LibraryEntryMusic(
        /** The name of the song */
        val name: String,
        /** The author, or player who etched */
        val author: String,
        /** The url of the file */
        val url: String,
        /** The color for the record */
        val color: String
) : LibraryEntry() {

        val colorInt get() = color.toInt(16) // Radix 16 to convert hex
}

/**
 * Class representing a radio library entry
 */
data class LibraryEntryRadio(
        /** The name of the radio station */
        val name: String,
        /** The url of the radio stream */
        val url: String
) : LibraryEntry()
