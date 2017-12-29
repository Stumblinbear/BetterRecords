package com.codingforcookies.betterrecords.library

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

class LibraryContentSpec: Spek({

    given("A Simple Library Json") {
        val json = this::class.java.getResource("/library/exampleLibrary.json").readText()

        on("Creating Content from the string") {
            val content = LibraryContent.fromJson(json)

            it("Should have all of the correct info") {
                content.verify("An Example Library", 3, 2)
            }
        }
    }
})
