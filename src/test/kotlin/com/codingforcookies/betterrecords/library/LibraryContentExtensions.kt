package com.codingforcookies.betterrecords.library

import com.codingforcookies.betterrecords.api.library.Song
import com.codingforcookies.betterrecords.api.library.verify
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

fun LibraryContent.verify(expectedName: String, songSize: Int) {
    assertNotNull(name)
    assertEquals(expectedName, name)

    assertEquals(songSize, songs.size)
    songs.forEach(Song::verify)
}
