package com.codingforcookies.betterrecords.api.library

import com.codingforcookies.betterrecords.library.Libraries

fun urlExistsInAnyLibrary(url: String) =
        Libraries.libraries
                .flatMap { it.songs }
                .any { it.url == url }