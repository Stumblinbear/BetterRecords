package com.codingforcookies.betterrecords.common.util;

import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Map;

public class BetterUtils{

    private static Map<String, ChatComponentTranslation> translations = Maps.newHashMap();
    private static String lang = "en_US";

    public static void markBlockDirty(World world, BlockPos pos){
        world.getChunkFromBlockCoords(pos).setChunkModified();
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

    public static String[] getWordWrappedString(final int maxWidth, final String string) {
        return WordUtils.wrap(string, maxWidth, "\n", false).replace("\\n", "\n").split("\n");
    }
}
