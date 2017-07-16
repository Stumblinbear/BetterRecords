package com.codingforcookies.betterrecords.common.item;

import com.codingforcookies.betterrecords.BetterRecords;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BetterItem extends Item {

    public BetterItem(String name) {
        setCreativeTab(BetterRecords.INSTANCE.getCreativeTab());
        setUnlocalizedName(name);
    }

    @Override
    public Item setUnlocalizedName(String name) {
        GameRegistry.register(this.setRegistryName(name));
        return super.setUnlocalizedName("betterrecords:" + name);
    }
}
