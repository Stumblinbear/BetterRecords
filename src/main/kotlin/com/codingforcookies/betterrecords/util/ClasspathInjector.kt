package com.codingforcookies.betterrecords.util

import java.io.File
import java.io.IOException
import java.net.URL
import java.net.URLClassLoader

object ClasspathInjector {

    private val parameters = arrayOf<Class<*>>(URL::class.java)

    @Throws(IOException::class)
    fun addFile(f: File) {
        addURL(f.toURI().toURL())
    }

    @Throws(IOException::class)
    fun addURL(url: URL) {
        val sysloader = ClassLoader.getSystemClassLoader() as URLClassLoader
        val sysclass = URLClassLoader::class.java

        try {
            val method = sysclass.getDeclaredMethod("addURL", *parameters)
            method.isAccessible = true
            method.invoke(sysloader, url)
        } catch (t: Throwable) {
            t.printStackTrace()
            throw IOException("Error, could not inject file to system classloader")
        }
    }
}
