package com.codingforcookies.betterrecords.library

import com.codingforcookies.betterrecords.api.library.LibraryEntryMusic
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

object LibraryFileSpec : Spek({

    describe("A Music Library File") {
        val resourceURL = this.javaClass.getResource("/library/musicLibrary1.json")
        val tempFile = createTempFile().apply {
            writeText(resourceURL.readText())
            deleteOnExit()
        }


        on("instantiation from a url") {
            val musicFile = LibraryFile.fromURL(resourceURL)

            it("should have all the songs") {
                assertEquals(3, musicFile.entries.songs.size)

                val names = musicFile.entries.songs.map { it.name }
                assertTrue("Song 1" in names)
                assertTrue("Song 2" in names)
                assertTrue("Song 3" in names)
            }

            it("should not be local") {
                assertFalse(musicFile.isLocal)
            }
        }

        on("instantiation from a file") {
            val musicFile = LibraryFile.fromFile(tempFile)

            it("should have all the songs") {
                assertEquals(3, musicFile.entries.songs.size)

                val names = musicFile.entries.songs.map { it.name }
                assertTrue("Song 1" in names)
                assertTrue("Song 2" in names)
                assertTrue("Song 3" in names)
            }

            it("should be local") {
                assertTrue(musicFile.isLocal)
            }

            it("Should be able to be updated") {
                musicFile.entries.songs.add(LibraryEntryMusic("Song 4", "url"))
                musicFile.save()

                println(tempFile.readText())
                val newFile = LibraryFile.fromFile(tempFile)
                assertEquals(4, newFile.entries.songs.size)
            }
        }
    }
})
