package com.codingforcookies.betterrecords.util

import com.google.common.collect.Maps
import net.minecraft.client.Minecraft
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentTranslation
import net.minecraft.world.World
import org.apache.commons.lang3.text.WordUtils

object BetterUtils {

    private val translations = Maps.newHashMap<String, TextComponentTranslation>()
    private var lang = "en_US"

    fun markBlockDirty(world: World, pos: BlockPos) =
        world.getChunkFromBlockCoords(pos).setModified(true)

    fun getTranslatedString(str: String): String {
        var cct = translations[str]

        if (cct != null) {
            if (Minecraft.getMinecraft().gameSettings.language != lang) {
                lang = Minecraft.getMinecraft().gameSettings.language
                translations.clear()
                return TextComponentTranslation(str).formattedText
            }
        } else {
            cct = TextComponentTranslation(str)
            translations.put(str, cct)
        }
        return cct.formattedText
    }

    fun getWordWrappedString(maxWidth: Int, string: String) =
            WordUtils.wrap(string, maxWidth, "\n", false)
                    .replace("\\n", "\n")
                    .split("\n")

    fun wrapInt(x: Int, min: Int, max: Int) =
            when {
                x in min..max -> x
                x < min       -> max
                else          -> min
            }

    /**
     * Reads a resource from the jar, located at [path]
     *
     * We need to use Minecraft::class, because our own classes use the bootstrapped class loader
     */
    fun getResourceFromJar(path: String) =
            Minecraft::class.java.classLoader.getResource(path)
}
