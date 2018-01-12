package com.codingforcookies.betterrecords.library

import com.codingforcookies.betterrecords.api.library.Song
import com.codingforcookies.betterrecords.library.LibraryContent.Companion.fromJson
import com.google.gson.GsonBuilder

/**
 * A class used to read the contents of a json string.
 *
 * Normally, this class is only instantiated by Gson in [fromJson]
 *
 */
class LibraryContent(
        /** The name of this library */
        val name: String,
        /** A list of the songs contained in this library */
        val songs: MutableList<Song>
) {

    companion object {
        private var gson = GsonBuilder().setPrettyPrinting().create()

        /**
         * Construct a [LibraryContent] object from a json string
         */
        fun fromJson(json: String): LibraryContent {
            return gson.fromJson(json, LibraryContent::class.java)
        }
    }

    /**
     * Convert this object into a json string
     */
    fun toJson(): String {
        return gson.toJson(this)
    }
}
