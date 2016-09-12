package com.codingforcookies.betterrecords.common;

import com.codingforcookies.betterrecords.common.core.CommonProxy;
import com.codingforcookies.betterrecords.common.core.handler.GuiHandler;
import com.codingforcookies.betterrecords.common.item.ModItems;
import com.codingforcookies.betterrecords.common.packets.ChannelHandler;
import com.codingforcookies.betterrecords.common.packets.PacketHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import javax.annotation.Nonnull;


@Mod(modid = BetterRecords.ID, version = "@VERSION@", useMetadata = true, name = "Better Records",
acceptableRemoteVersions = "@CHANGE_VERSION@", acceptedMinecraftVersions = "@MC_VERSION@", acceptableSaveVersions = "@CHANGE_VERSION@")
public class BetterRecords {

    public static final String ID = "betterrecords";
    public static final String VERSION = "@VERSION@";

    @Instance(value = ID)
    public static BetterRecords instance;

    @SidedProxy(clientSide = "com.codingforcookies.betterrecords.client.core.ClientProxy", serverSide = "com.codingforcookies.betterrecords.common.core.CommonProxy")
    public static CommonProxy proxy;

    public static final CreativeTabs recordsTab = new CreativeTabs("betterrecords") {
        @Nonnull
        @Override
        public Item getTabIconItem() {
            return ModItems.itemURLRecord;
        }
    };

    @EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(final FMLInitializationEvent event) {
        PacketHandler.channels = NetworkRegistry.INSTANCE.newChannel("BetterRecords", new ChannelHandler());
        NetworkRegistry.INSTANCE.registerGuiHandler(BetterRecords.instance, new GuiHandler());
        proxy.init(event);
    }

    @EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
