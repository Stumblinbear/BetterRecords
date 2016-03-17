package com.codingforcookies.betterrecords.common;

import com.codingforcookies.betterrecords.client.BetterCreativeTab;
import com.codingforcookies.betterrecords.common.core.CommonProxy;
import com.codingforcookies.betterrecords.common.core.handler.GuiHandler;
import com.codingforcookies.betterrecords.common.packets.ChannelHandler;
import com.codingforcookies.betterrecords.common.packets.PacketHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.commons.lang3.text.WordUtils;


@Mod(modid = BetterRecords.ID, version = "@VERSION@", useMetadata = true, name = "Better Records",
acceptableRemoteVersions = "@CHANGE_VERSION@", acceptedMinecraftVersions = "@MC_VERSION@", acceptableSaveVersions = "@CHANGE_VERSION@")
public class BetterRecords {

    public static final String ID = "betterrecords";
    public static final String VERSION = "@VERSION@";

    @Instance(value = ID)
    public static BetterRecords instance;

    @SidedProxy(clientSide = "com.codingforcookies.betterrecords.client.core.ClientProxy", serverSide = "com.codingforcookies.betterrecords.common.core.CommonProxy")
    public static CommonProxy proxy;

    public static final BetterCreativeTab recordsTab = new BetterCreativeTab();

    @EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        proxy.preInit();
    }

    @EventHandler
    public void init(final FMLInitializationEvent event) {
        PacketHandler.channels = NetworkRegistry.INSTANCE.newChannel("BetterRecords", new ChannelHandler());
        NetworkRegistry.INSTANCE.registerGuiHandler(BetterRecords.instance, new GuiHandler());
        proxy.init();
    }

    @EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
        proxy.postInit();
    }
}
