package com.codingforcookies.betterrecords.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.codingforcookies.betterrecords.items.TileEntityFrequencyTuner;
import com.codingforcookies.betterrecords.items.TileEntityRecordEtcher;

import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
    	TileEntity tileEntity;
    	switch(id) {
			case 0:
				tileEntity = world.getTileEntity(x, y, z);
				if(tileEntity instanceof TileEntityRecordEtcher)
						return new ContainerRecordEtcher(player.inventory, (TileEntityRecordEtcher)tileEntity);
				break;
			case 1:
				tileEntity = world.getTileEntity(x, y, z);
				if(tileEntity instanceof TileEntityFrequencyTuner)
					return new ContainerFrequencyTuner(player.inventory, (TileEntityFrequencyTuner)tileEntity);
				break;
    	}
		return null;
    }
    
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
    	TileEntity tileEntity;
    	switch(id) {
    		case 0:
	    		tileEntity = world.getTileEntity(x, y, z);
	            if(tileEntity instanceof TileEntityRecordEtcher)
	            	return new GuiRecordEtcher(player.inventory, (TileEntityRecordEtcher)tileEntity);
	            break;
    		case 1:
	    		tileEntity = world.getTileEntity(x, y, z);
	            if(tileEntity instanceof TileEntityFrequencyTuner)
	            	return new GuiFrequencyTuner(player.inventory, (TileEntityFrequencyTuner)tileEntity);
	            break;
	    	case 2:
	    		return new GuiBetterDisclaimer();
	    	case 3:
	    		return new GuiBetterConfig();
    	}
   		return null;
    }
}