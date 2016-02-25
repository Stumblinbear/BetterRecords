package com.codingforcookies.betterrecords.common.block;

import com.codingforcookies.betterrecords.api.record.IRecord;
import com.codingforcookies.betterrecords.api.wire.IRecordWire;
import com.codingforcookies.betterrecords.api.wire.IRecordWireManipulator;
import com.codingforcookies.betterrecords.client.core.ClientProxy;
import com.codingforcookies.betterrecords.client.core.handler.BetterEventHandler;
import com.codingforcookies.betterrecords.common.BetterRecords;
import com.codingforcookies.betterrecords.common.block.tile.TileEntityRecordPlayer;
import com.codingforcookies.betterrecords.common.core.helper.ConnectionHelper;
import com.codingforcookies.betterrecords.common.lib.StaticInfo;
import com.codingforcookies.betterrecords.common.packets.PacketHandler;
import com.codingforcookies.betterrecords.common.util.BetterUtils;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockRecordPlayer extends BlockContainer {

    public BlockRecordPlayer(){
        super(Material.wood);
        setBlockBounds(.025F, 0F, .025F, .975F, .975F, .975F);
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state){
        super.onBlockAdded(world, pos, state);
        world.markBlockForUpdate(pos);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ){
        if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IRecordWireManipulator) return false;
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity == null || !(tileEntity instanceof TileEntityRecordPlayer)) return false;
        TileEntityRecordPlayer tileEntityRecordPlayer = (TileEntityRecordPlayer) tileEntity;
        if(player.isSneaking()){
            if(world.getBlockState(pos.add(0,1,0)).getBlock() == Blocks.air){
                if (!world.isRemote) {
                    tileEntityRecordPlayer.opening = !tileEntityRecordPlayer.opening;
                }
                world.markBlockForUpdate(pos);
                if(tileEntityRecordPlayer.opening) world.playSoundEffect(pos.getX(), (double) pos.getY() + 0.5D, pos.getZ(), "random.chestopen", 0.5F, world.rand.nextFloat() * 0.2F + 3F);
                else world.playSoundEffect(pos.getX(), (double) pos.getY() + 0.5D, pos.getZ(), "random.chestclosed", 0.5F, world.rand.nextFloat() * 0.2F + 3F);
            }
        }else if(tileEntityRecordPlayer.opening){
            if(tileEntityRecordPlayer.record != null){
                if(!world.isRemote) dropItem(world, pos);
                tileEntityRecordPlayer.setRecord(null);
                world.markBlockForUpdate(pos);
            }else if(player.getHeldItem() != null && (player.getHeldItem().getItem() == Items.diamond || (player.getHeldItem().getItem() instanceof IRecord && ((IRecord) player.getHeldItem().getItem()).isRecordValid(player.getHeldItem())))){
                if(player.getHeldItem().getItem() == Items.diamond){
                    ItemStack itemStack = new ItemStack(BetterRecords.itemURLRecord);
                    itemStack.setTagCompound(new NBTTagCompound());
                    itemStack.getTagCompound().setString("name", "easteregg.ogg");
                    itemStack.getTagCompound().setString("url", "http://files.enjin.com/788858/SBear'sMods/Songs/easteregg.ogg");
                    itemStack.getTagCompound().setString("local", "Darude - Sandstorm");
                    itemStack.getTagCompound().setInteger("color", 0x53EAD7);
                    tileEntityRecordPlayer.setRecord(itemStack);
                    world.markBlockForUpdate(pos);
                    player.getHeldItem().stackSize--;
                }else{
                    tileEntityRecordPlayer.setRecord(player.getHeldItem());
                    world.markBlockForUpdate(pos);
                    player.getHeldItem().stackSize--;
                    if(!world.isRemote) ((IRecord) player.getHeldItem().getItem()).onRecordInserted(tileEntityRecordPlayer, player.getHeldItem());
                }
            }
        }
        return true;
    }

    @Override
    public BlockState createBlockState() {
        return new BlockState(this, StaticInfo.CARDINAL_DIRECTIONS);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(StaticInfo.CARDINAL_DIRECTIONS).getHorizontalIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(StaticInfo.CARDINAL_DIRECTIONS, EnumFacing.getHorizontal(meta));
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(StaticInfo.CARDINAL_DIRECTIONS, placer.getHorizontalFacing().getOpposite()));
        if(world.isRemote && !ClientProxy.tutorials.get("recordplayer")){
            BetterEventHandler.tutorialText = BetterUtils.getTranslatedString("tutorial.recordplayer");
            BetterEventHandler.tutorialTime = System.currentTimeMillis() + 10000;
            ClientProxy.tutorials.put("recordplayer", true);
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
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        dropItem(world, pos);
        super.breakBlock(world, pos, state);
    }


    private void dropItem(World world, BlockPos pos){
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity == null || !(tileEntity instanceof TileEntityRecordPlayer)) return;
        TileEntityRecordPlayer tileEntityRecordPlayer = (TileEntityRecordPlayer) tileEntity;
        ItemStack item = tileEntityRecordPlayer.record;
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
            world.spawnEntityInWorld(entityItem);
            item.stackSize = 0;
            tileEntityRecordPlayer.record = null;
            PacketHandler.sendSoundStopToAllFromServer(tileEntityRecordPlayer.getPos().getX(), tileEntityRecordPlayer.getPos().getY(), tileEntityRecordPlayer.getPos().getZ(), world.provider.getDimensionId());
        }
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
    public boolean hasComparatorInputOverride(){
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, BlockPos pos){
        return 5;
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2){
        return new TileEntityRecordPlayer();
    }
}
