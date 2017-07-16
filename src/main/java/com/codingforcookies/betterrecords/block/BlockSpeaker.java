package com.codingforcookies.betterrecords.block;

import com.codingforcookies.betterrecords.api.wire.IRecordWire;
import com.codingforcookies.betterrecords.client.core.handler.BetterEventHandler;
import com.codingforcookies.betterrecords.block.tile.TileSpeaker;
import com.codingforcookies.betterrecords.common.core.handler.ConfigHandler;
import com.codingforcookies.betterrecords.common.core.helper.ConnectionHelper;
import com.codingforcookies.betterrecords.common.util.BetterUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSpeaker extends BetterBlock {

    public static String[] speakers = new String[]{"sm", "md", "lg"};
    public int meta = 0;

    public BlockSpeaker(String name, int meta){
        super(Material.WOOD, name);
        this.meta = meta;

        switch (meta) {
            case 0: setHardness(2F); setResistance(7.5F); break;
            case 1: setHardness(3F); setResistance(8F);   break;
            case 2: setHardness(4F); setResistance(9.5F); break;
        }
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state){
        super.onBlockAdded(world, pos, state);
        world.notifyBlockUpdate(pos, state, state, 3);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess block, BlockPos pos) {
        switch (meta){
            case 0:
                return new AxisAlignedBB(0.26F, 0.05F, 0.25F, 0.75F, 0.65F, 0.74F);
            case 1:
                return new AxisAlignedBB(0.2F, 0.0F, 0.2F, 0.8F, 0.88F, 0.8F);
            case 2:
                return new AxisAlignedBB(0.12F, 0.0F, 0.12F, 0.88F, 1.51F, 0.88F);
            default:
                return FULL_BLOCK_AABB;
        }
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity == null || !(tileEntity instanceof TileSpeaker)) return;
        ((TileSpeaker) tileEntity).rotation = placer.rotationYaw;
        ((TileSpeaker) tileEntity).type = meta;
        if(world.isRemote && !ConfigHandler.tutorials.get("speaker")) {
            BetterEventHandler.tutorialText = BetterUtils.getTranslatedString("tutorial.speaker");
            BetterEventHandler.tutorialTime = System.currentTimeMillis() + 10000;
            ConfigHandler.tutorials.put("speaker", true);
        }
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest){
        if(world.isRemote) return super.removedByPlayer(state, world, pos, player, willHarvest);
        TileEntity te = world.getTileEntity(pos);
        if(te != null && te instanceof IRecordWire) ConnectionHelper.clearConnections(world, (IRecordWire) te);
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2){
        return new TileSpeaker();
    }
}
