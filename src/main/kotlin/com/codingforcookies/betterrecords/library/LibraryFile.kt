package com.codingforcookies.betterrecords.library

import com.codingforcookies.betterrecords.api.library.LibraryEntry
import com.codingforcookies.betterrecords.api.library.LibraryEntryMusic
import com.codingforcookies.betterrecords.api.library.LibraryEntryRadio
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.File
import java.net.URL

/**
 * Class representing a File containing a number of [LibraryEntry] of type [T].
 *
 * This can either be a local [File] or remote [URL].
 *
 * @param T The type of [LibraryEntry] this file contains
 */
sealed class LibraryFile<out T> where T: LibraryEntry {

    /**
     * A list of library entries contained in this file
     */
    abstract val entries: Collection<T>
}

class LibraryFileMusic private constructor(content: String) : LibraryFile<LibraryEntryMusic>() {

    override val entries =
            JsonParser()
                    .parse(content).asJsonObject
                    .entrySet()
                    .map { it.value }
                    .filterIsInstance<JsonObject>()
                    .map { Gson().fromJson(it, LibraryEntryMusic::class.java) }

    constructor(file: File) : this(file.readText())
    constructor(url: URL) : this(url.readText())
}

class LibraryFileRadio private constructor(content: String) : LibraryFile<LibraryEntryRadio>() {

    override val entries =
            JsonParser()
                    .parse(content).asJsonObject
                    .entrySet()
                    .map { it.value }
                    .filterIsInstance<JsonObject>()
                    .map { Gson().fromJson(it, LibraryEntryRadio::class.java) }

    constructor(file: File) : this(file.readText())
    constructor(url: URL) : this(url.readText())
}
