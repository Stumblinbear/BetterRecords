package com.codingforcookies.betterrecords.extensions

import org.lwjgl.opengl.GL11

fun glMatrix(block: () -> Unit) {
    GL11.glPushMatrix()
    block()
    GL11.glPopMatrix()
}
