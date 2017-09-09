package com.codingforcookies.betterrecords.util

fun blendRGB(c1: Int, c2: Int): Int {
    val r1 = (c1 and 0xff0000) shr 16
    val g1 = (c1 and 0xff00) shr 8
    val b1 = c1 and 0xff

    val r2 = (c2 and 0xff0000) shr 16
    val g2 = (c2 and 0xff00) shr 8
    val b2 = c2 and 0xff

    val r = r1 + r2
    val g = g1 + g2
    val b = b1 + b2

    return (r shl 16) or (g shl 8) or b
}