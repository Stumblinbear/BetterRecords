package com.codingforcookies.betterrecords.library

import java.io.File
import java.net.URL

/**
 * Abstract class representing a library File
 */
sealed class Library {

    /**
     * The [LibraryContent], to be set after reading from whatever source
     */
    abstract protected val libraryContent: LibraryContent

    /**
     * The list of songs in this library
     */
    val songs
        get() = libraryContent.music

    /**
     * The list of radio stations in this library
     */
    val radioStations
        get() = libraryContent.radio
}

/**
 * Class representing a library that is stored as a local file
 */
class LocalLibrary(val file: File) : Library() {

    override val libraryContent = LibraryContent.fromJson(file.readText())

    /**
     * Save the library back to the file, in order to save changes
     */
    fun save() {
        file.writeText(libraryContent.toJson())
    }
}

/**
 * Class representing a library that is a remote url
 */
class RemoteLibrary(url: URL) : Library() {

    override val libraryContent = LibraryContent.fromJson(url.readText())

}
