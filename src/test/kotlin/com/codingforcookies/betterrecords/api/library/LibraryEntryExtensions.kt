package com.codingforcookies.betterrecords.api.library

import kotlin.test.assertNotNull

fun Song.verify() {
    assertNotNull(name)
    assertNotNull(author)
    assertNotNull(url)
    assertNotNull(color)
}
