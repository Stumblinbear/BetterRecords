package com.codingforcookies.betterrecords.library

import com.codingforcookies.betterrecords.api.library.LibraryEntryMusic
import com.codingforcookies.betterrecords.api.library.LibraryEntryRadio
import com.google.gson.GsonBuilder
import java.io.File
import java.net.URL



/**
 * Class representing a Library file
 */
open class LibraryFile(
        val entries: EntrySection
) {

    /**
     * If the LibraryFile was made from a local file, this will be the file (for later updating)
     */
    @Transient
    var localFile: File? = null

    /**
     * Convenience value
     * Checks whether this file is a local, based on the presence of [localFile]
     */
    val isLocal
        get() = localFile != null

    class EntrySection(
            val songs: MutableList<LibraryEntryMusic>,
            val radioStations: MutableList<LibraryEntryRadio>
    )

    /**
     * Writes the contents of the file if it is local
     */
    fun save() {
        localFile?.writeText(gson.toJson(this))
    }

    companion object {

        var gson = GsonBuilder().setPrettyPrinting().create()

        /**
         * Create a [LibraryFile] from the given string
         */
        fun fromString(content: String): LibraryFile =
                gson.fromJson(content, LibraryFile::class.java)

        /**
         * Create a [LibraryFile] from the given file.
         *
         * Sets [localFile], so that it can be updated later
         */
        fun fromFile(file: File): LibraryFile =
                fromString(file.readText()).apply {
                    localFile = file
                }

        /**
         * Create a [LibraryFile] from the given url
         */
        fun fromURL(url: URL) =
                fromString(url.readText())
    }
}
