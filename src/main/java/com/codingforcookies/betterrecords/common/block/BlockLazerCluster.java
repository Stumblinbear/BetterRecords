package com.codingforcookies.betterrecords.common.block;

import com.codingforcookies.betterrecords.api.wire.IRecordWire;
import com.codingforcookies.betterrecords.client.core.ClientProxy;
import com.codingforcookies.betterrecords.client.core.handler.BetterEventHandler;
import com.codingforcookies.betterrecords.common.block.tile.TileEntityLazerCluster;
import com.codingforcookies.betterrecords.common.core.helper.ConnectionHelper;
import com.codingforcookies.betterrecords.common.util.BetterUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLazerCluster extends BetterBlock {

    public BlockLazerCluster(String name){
        super(Material.IRON, name);
        setHardness(4.8F);
        setResistance(4.8F);
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess access, BlockPos pos) {
        TileEntity te = access.getTileEntity(pos);
        if(te == null || !(te instanceof IRecordWire)) return 0;
        BetterUtils.markBlockDirty(te.getWorld(), te.getPos());
        return(((IRecordWire) te).getConnections().size() > 0 ? 5 : 0);
    }

    @Override
    public void onBlockAdded(World world, net.minecraft.util.math.BlockPos pos, IBlockState state){
        super.onBlockAdded(world, pos, state);
        world.notifyBlockUpdate(pos, state, state, 3);
    }

    @Override
    public void onBlockPlacedBy(World world, net.minecraft.util.math.BlockPos pos, IBlockState state, EntityLivingBase entityLiving, ItemStack itemStack){
        if(world.isRemote && !ClientProxy.tutorials.get("lazercluster")) {
            BetterEventHandler.tutorialText = BetterUtils.getTranslatedString("tutorial.lazercluster");
            BetterEventHandler.tutorialTime = System.currentTimeMillis() + 10000;
            ClientProxy.tutorials.put("lazercluster", true);
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
        return new TileEntityLazerCluster();
    }
}
