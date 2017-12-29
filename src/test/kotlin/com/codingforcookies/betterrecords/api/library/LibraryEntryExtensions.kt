package com.codingforcookies.betterrecords.api.library

import kotlin.test.assertNotNull

fun LibraryEntryMusic.verify() {
    assertNotNull(name)
    assertNotNull(url)
}

fun LibraryEntryRadio.verify() {
    assertNotNull(name)
    assertNotNull(url)
}
