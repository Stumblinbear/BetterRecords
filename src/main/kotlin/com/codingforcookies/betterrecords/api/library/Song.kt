package com.codingforcookies.betterrecords.api.library

data class Song(
        /** The name of the song */
        val name: String,
        /** The author, or player who etched */
        val author: String,
        /** The url of the file */
        val url: String,
        /** The color for the record */
        val color: String
) {
    val colorInt get() = color.toInt(16) // Radix 16 to convert hex
}
