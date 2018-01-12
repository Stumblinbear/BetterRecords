package com.codingforcookies.betterrecords.util

import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.Mod
import org.apache.commons.lang3.text.WordUtils

object BetterUtils {

    fun markBlockDirty(world: World, pos: BlockPos) =
        world.getChunkFromBlockCoords(pos).setModified(true)

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
     * We need to use Mod::class, because our own classes use the bootstrapped class loader
     */
    fun getResourceFromJar(path: String) =
            Mod::class.java.classLoader.getResource(path)
}
