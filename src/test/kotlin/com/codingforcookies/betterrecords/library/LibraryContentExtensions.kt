package com.codingforcookies.betterrecords.library

import com.codingforcookies.betterrecords.api.library.LibraryEntryMusic
import com.codingforcookies.betterrecords.api.library.LibraryEntryRadio
import com.codingforcookies.betterrecords.api.library.verify
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

fun LibraryContent.verify(expectedName: String, musicSize: Int, radioSize: Int) {
    assertNotNull(name)
    assertEquals(expectedName, name)

    assertEquals(musicSize, music.size)
    music.forEach(LibraryEntryMusic::verify)

    assertEquals(radioSize, radio.size)
    radio.forEach(LibraryEntryRadio::verify)
}