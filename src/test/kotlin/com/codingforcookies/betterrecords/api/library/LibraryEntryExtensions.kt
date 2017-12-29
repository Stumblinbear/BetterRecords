package com.codingforcookies.betterrecords.api.library

import kotlin.test.assertNotNull

fun LibraryEntryMusic.verify() {
    assertNotNull(name)
    assertNotNull(author)
    assertNotNull(url)
    assertNotNull(color)
}

fun LibraryEntryRadio.verify() {
    assertNotNull(name)
    assertNotNull(url)
}
