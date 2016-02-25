package com.codingforcookies.betterrecords.common.block;

import com.codingforcookies.betterrecords.common.util.BetterUtils;
import com.codingforcookies.betterrecords.common.core.helper.ConnectionHelper;
import com.codingforcookies.betterrecords.api.wire.IRecordWire;
import com.codingforcookies.betterrecords.client.core.handler.BetterEventHandler;
import com.codingforcookies.betterrecords.client.core.ClientProxy;

import com.codingforcookies.betterrecords.common.block.tile.TileEntityLazer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLazer extends BlockContainer{

    public BlockLazer(){
        super(Material.iron);
        setBlockBounds(0.25F, 0F, 0.25F, 0.75F, 0.75F, 0.74F);
    }

    @Override
    public int getLightValue(IBlockAccess world, BlockPos pos){
        TileEntity te = world.getTileEntity(pos);
        if(te == null || !(te instanceof IRecordWire)) return 0;
        BetterUtils.markBlockDirty(te.getWorld(), te.getPos().getX(), te.getPos().getY(), te.getPos().getZ());
        return(((IRecordWire) te).getConnections().size() > 0 ? 5 : 0);
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state){
        super.onBlockAdded(world, pos, state);
        world.markBlockForUpdate(pos);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entityLiving, ItemStack itemStack){
        TileEntity te = world.getTileEntity(pos);
        if(te != null && te instanceof TileEntityLazer){
            ((TileEntityLazer) te).pitch = entityLiving.rotationPitch;
            ((TileEntityLazer) te).yaw = entityLiving.rotationYaw;
        }
        if(world.isRemote && !ClientProxy.tutorials.get("lazer")){
            BetterEventHandler.tutorialText = BetterUtils.getTranslatedString("tutorial.lazer");
            BetterEventHandler.tutorialTime = System.currentTimeMillis() + 10000;
            ClientProxy.tutorials.put("lazer", true);
        }
    }

    @Override
    public boolean removedByPlayer(World world, BlockPos pos, EntityPlayer player, boolean willHarvest){
        if(world.isRemote) return super.removedByPlayer(world, pos, player, willHarvest);
        TileEntity te = world.getTileEntity(pos);
        if(te != null && te instanceof IRecordWire) ConnectionHelper.clearConnections(world, (IRecordWire) te);
        return super.removedByPlayer(world, pos, player, willHarvest);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ){
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity == null || !(tileEntity instanceof TileEntityLazer)) return false;
        TileEntityLazer tileEntityLazer = (TileEntityLazer) tileEntity;
        float length = tileEntityLazer.length;
        if(player.isSneaking()){
            if(tileEntityLazer.length > 0){
                tileEntityLazer.length--;
            }
        }else{
            if(tileEntityLazer.length < 25){
                tileEntityLazer.length++;
            }
        }
        if(tileEntityLazer.length != length && !world.isRemote){
            player.addChatMessage(new ChatComponentTranslation("msg.lazerlength." + (tileEntityLazer.length > length ? "increase" : "decrease")).appendText(" " + tileEntityLazer.length));
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderType(){
        return 2;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isOpaqueCube(){
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2){
        return new TileEntityLazer();
    }
}