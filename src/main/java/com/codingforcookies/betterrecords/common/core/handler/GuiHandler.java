package com.codingforcookies.betterrecords.common.core.handler;

import com.codingforcookies.betterrecords.client.gui.*;
import com.codingforcookies.betterrecords.common.block.tile.TileEntityFrequencyTuner;
import com.codingforcookies.betterrecords.common.block.tile.TileEntityRecordEtcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity;
        switch(id) {
            case 0:
                tileEntity = world.getTileEntity(new net.minecraft.util.math.BlockPos(x, y, z));
                if(tileEntity instanceof TileEntityRecordEtcher)
                        return new ContainerRecordEtcher(player.inventory, (TileEntityRecordEtcher)tileEntity);
                break;
            case 1:
                tileEntity = world.getTileEntity(new net.minecraft.util.math.BlockPos(x, y, z));
                if(tileEntity instanceof TileEntityFrequencyTuner)
                    return new ContainerFrequencyTuner(player.inventory, (TileEntityFrequencyTuner)tileEntity);
                break;
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity;
        switch(id) {
            case 0:
                tileEntity = world.getTileEntity(new BlockPos(x, y, z));
                if(tileEntity instanceof TileEntityRecordEtcher)
                    return new GuiRecordEtcher(player.inventory, (TileEntityRecordEtcher)tileEntity);
                break;
            case 1:
                tileEntity = world.getTileEntity(new net.minecraft.util.math.BlockPos(x, y, z));
                if(tileEntity instanceof TileEntityFrequencyTuner)
                    return new GuiFrequencyTuner(player.inventory, (TileEntityFrequencyTuner)tileEntity);
                break;
            case 2:
                return new GuiBetterDisclaimer();
        }
           return null;
    }
}
