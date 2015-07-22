package com.codingforcookies.betterrecords.src;

import net.minecraft.world.World;

public class BetterUtils {
	public static void markBlockDirty(World par1World, int x, int y, int z) {
		if(par1World.blockExists(x, y, z))
			par1World.getChunkFromBlockCoords(x, z).setChunkModified();
	}
}