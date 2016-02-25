package com.codingforcookies.betterrecords.common.util;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

public class BetterUtils{

    private static Map<String, ChatComponentTranslation> translations = Maps.newHashMap();
    private static String lang = "en_US";

    public static void markBlockDirty(World par1World, int x, int y, int z){
        //if(par1World.blockExists(x, y, z)) par1World.getChunkFromBlockCoords(x, z).setChunkModified();
        par1World.getChunkFromBlockCoords(new BlockPos(x,y,z)).setChunkModified();
    }

    public static String getTranslatedString(String str){
        ChatComponentTranslation cct = translations.get(str);
        if(cct != null) {
            if(!Minecraft.getMinecraft().gameSettings.language.equals(lang)) {
                lang = Minecraft.getMinecraft().gameSettings.language;
                translations.clear();
                return new ChatComponentTranslation(str).getFormattedText();
            }
            return cct.getFormattedText();
        }else{
            cct = new ChatComponentTranslation(str);
            translations.put(str, cct);
            return cct.getFormattedText();
        }
    }
}