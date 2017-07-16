package com.codingforcookies.betterrecords.block;

import com.codingforcookies.betterrecords.api.BetterRecordsAPI;
import com.codingforcookies.betterrecords.api.record.IRecord;
import com.codingforcookies.betterrecords.api.wire.IRecordWire;
import com.codingforcookies.betterrecords.api.wire.IRecordWireManipulator;
import com.codingforcookies.betterrecords.client.core.handler.BetterEventHandler;
import com.codingforcookies.betterrecords.block.tile.TileRecordPlayer;
import com.codingforcookies.betterrecords.common.core.handler.ConfigHandler;
import com.codingforcookies.betterrecords.common.core.helper.ConnectionHelper;
import com.codingforcookies.betterrecords.item.ModItems;
import com.codingforcookies.betterrecords.common.packets.PacketHandler;
import com.codingforcookies.betterrecords.common.util.BetterUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockRecordPlayer extends BetterBlock {

    public BlockRecordPlayer(String name){
        super(Material.WOOD, name);
        setHardness(1F);
        setResistance(5F);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess block, BlockPos pos) {
        return new AxisAlignedBB(.025F, 0F, .025F, .975F, .975F, .975F);
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state){
        super.onBlockAdded(world, pos, state);
        world.notifyBlockUpdate(pos, state, state, 3);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(heldItem != null && heldItem.getItem() instanceof IRecordWireManipulator) return false;
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity == null || !(tileEntity instanceof TileRecordPlayer)) return false;
        TileRecordPlayer tileRecordPlayer = (TileRecordPlayer) tileEntity;
        if(player.isSneaking()){
            if(world.getBlockState(pos.add(0,1,0)).getBlock() == Blocks.AIR){
                if (!world.isRemote) {
                    tileRecordPlayer.opening = !tileRecordPlayer.opening;
                }
                world.notifyBlockUpdate(pos, state, state, 3);
                if(tileRecordPlayer.opening) world.playSound(pos.getX(), (double) pos.getY() + 0.5D, pos.getZ(), SoundEvent.REGISTRY.getObject(new ResourceLocation("block.chest.open")), SoundCategory.NEUTRAL, 0.2F, world.rand.nextFloat() * 0.2F + 3F, false);
                else world.playSound(pos.getX(), (double) pos.getY() + 0.5D, pos.getZ(), SoundEvent.REGISTRY.getObject(new ResourceLocation("block.chest.open")), SoundCategory.NEUTRAL, 0.2F, world.rand.nextFloat() * 0.2F + 3F, false);
            }
        }else if(tileRecordPlayer.opening){
            if(tileRecordPlayer.record != null){
                if(!world.isRemote) dropItem(world, pos);
                tileRecordPlayer.setRecord(null);
                world.notifyBlockUpdate(pos, state, state, 3);
            }else if(heldItem != null && (heldItem.getItem() == Items.DIAMOND || (heldItem.getItem() instanceof IRecord && ((IRecord) heldItem.getItem()).isRecordValid(heldItem)))){
                if(heldItem.getItem() == Items.DIAMOND){
                    ItemStack itemStack = new ItemStack(ModItems.itemURLRecord);
                    itemStack.setTagCompound(new NBTTagCompound());
                    itemStack.getTagCompound().setString("name", "easteregg.ogg");
                    itemStack.getTagCompound().setString("url", "http://files.enjin.com/788858/SBear'sMods/Songs/easteregg.ogg");
                    itemStack.getTagCompound().setString("local", "Darude - Sandstorm");
                    itemStack.getTagCompound().setInteger("color", 0x53EAD7);
                    tileRecordPlayer.setRecord(itemStack);
                    world.notifyBlockUpdate(pos, state, state, 3);
                    heldItem.stackSize--;
                }else{
                    tileRecordPlayer.setRecord(heldItem);
                    world.notifyBlockUpdate(pos, state, state, 3);
                    if (!world.isRemote) ((IRecord) heldItem.getItem()).onRecordInserted(tileRecordPlayer, heldItem);
                    heldItem.stackSize--;
                }
            }
        }
        return true;
    }

    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BetterRecordsAPI.CARDINAL_DIRECTIONS);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(BetterRecordsAPI.CARDINAL_DIRECTIONS).getHorizontalIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(BetterRecordsAPI.CARDINAL_DIRECTIONS, EnumFacing.getHorizontal(meta));
    }

    @Override
    public void onBlockPlacedBy(World world, net.minecraft.util.math.BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(BetterRecordsAPI.CARDINAL_DIRECTIONS, placer.getHorizontalFacing().getOpposite()));
        if(world.isRemote && !ConfigHandler.tutorials.get("recordplayer")){
            BetterEventHandler.tutorialText = BetterUtils.getTranslatedString("tutorial.recordplayer");
            BetterEventHandler.tutorialTime = System.currentTimeMillis() + 10000;
            ConfigHandler.tutorials.put("recordplayer", true);
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
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        dropItem(world, pos);
        super.breakBlock(world, pos, state);
    }


    private void dropItem(World world, BlockPos pos){
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity == null || !(tileEntity instanceof TileRecordPlayer)) return;
        TileRecordPlayer tileRecordPlayer = (TileRecordPlayer) tileEntity;
        ItemStack item = tileRecordPlayer.record;
        if(item != null){
            Random rand = new Random();
            float rx = rand.nextFloat() * 0.8F + 0.1F;
            float ry = rand.nextFloat() * 0.8F + 0.1F;
            float rz = rand.nextFloat() * 0.8F + 0.1F;
            EntityItem entityItem = new EntityItem(world, pos.getX() + rx, pos.getY() + ry, pos.getZ() + rz, new ItemStack(item.getItem(), item.stackSize, item.getItemDamage()));
            if(item.hasTagCompound()) entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
            entityItem.motionX = rand.nextGaussian() * 0.05F;
            entityItem.motionY = rand.nextGaussian() * 0.05F + 0.2F;
            entityItem.motionZ = rand.nextGaussian() * 0.05F;
            world.spawnEntity(entityItem);
            item.stackSize = 0;
            tileRecordPlayer.record = null;
            PacketHandler.sendSoundStopToAllFromServer(tileRecordPlayer.getPos().getX(), tileRecordPlayer.getPos().getY(), tileRecordPlayer.getPos().getZ(), world.provider.getDimension());
        }
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2){
        return new TileRecordPlayer();
    }
}
