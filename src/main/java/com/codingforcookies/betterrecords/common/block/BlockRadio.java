package com.codingforcookies.betterrecords.common.block;

import com.codingforcookies.betterrecords.api.wire.IRecordWire;
import com.codingforcookies.betterrecords.api.wire.IRecordWireManipulator;
import com.codingforcookies.betterrecords.client.core.ClientProxy;
import com.codingforcookies.betterrecords.client.core.handler.BetterEventHandler;
import com.codingforcookies.betterrecords.common.block.tile.TileEntityRadio;
import com.codingforcookies.betterrecords.common.core.helper.ConnectionHelper;
import com.codingforcookies.betterrecords.common.item.ModItems;
import com.codingforcookies.betterrecords.common.lib.StaticInfo;
import com.codingforcookies.betterrecords.common.packets.PacketHandler;
import com.codingforcookies.betterrecords.common.util.BetterUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockRadio extends BetterBlock {

    public BlockRadio(String name){
        super(Material.wood, name);
        setBlockBounds(0.13F, 0F, 0.2F, 0.87F, 0.98F, 0.8F);
        setHardness(2F);
        setResistance(6.3F);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess block, BlockPos pos) {
        switch (block.getTileEntity(pos).getBlockMetadata()){
            case 0:
            case 2:
                setBlockBounds(0.13F, 0F, 0.2F, 0.87F, 0.98F, 0.8F);
                break;
            case 1:
            case 3:
                setBlockBounds(0.2F, 0F, 0.13F, 0.8F, 0.98F, 0.87F);
                break;
        }
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
        if(tileEntity == null || !(tileEntity instanceof TileEntityRadio)) return false;
        TileEntityRadio tileEntityRadio = (TileEntityRadio) tileEntity;
        if(player.isSneaking()) {
            tileEntityRadio.opening = !tileEntityRadio.opening;
            world.markBlockForUpdate(pos);
            if(tileEntityRadio.opening) world.playSoundEffect(pos.getX(), (double) pos.getY() + 0.5D, pos.getZ(), "random.chestopen", 0.2F, world.rand.nextFloat() * 0.2F + 3F);
            else world.playSoundEffect(pos.getX(), (double) pos.getY() + 0.5D, pos.getZ(), "random.chestclosed", 0.2F, world.rand.nextFloat() * 0.2F + 3F);
        }else if(tileEntityRadio.opening) {
            if(tileEntityRadio.crystal != null) {
                if(!world.isRemote) dropItem(world, pos);
                tileEntityRadio.setCrystal(null);
                world.markBlockForUpdate(pos);
            }else if(player.getHeldItem() != null && (player.getHeldItem().getItem() == ModItems.itemFreqCrystal && player.getHeldItem().getTagCompound() != null && player.getHeldItem().getTagCompound().hasKey("url"))) {
                tileEntityRadio.setCrystal(player.getHeldItem());
                world.markBlockForUpdate(pos);
                player.getHeldItem().stackSize--;
                if(!world.isRemote) PacketHandler.sendRadioPlayToAllFromServer(tileEntityRadio.getPos().getX(), tileEntityRadio.getPos().getY(), tileEntityRadio.getPos().getZ(), world.provider.getDimensionId(), tileEntityRadio.getSongRadius(), tileEntityRadio.crystal.getTagCompound().getString("name"), tileEntityRadio.crystal.getTagCompound().getString("url"));
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
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack itemStack){
        world.setBlockState(pos, state.withProperty(StaticInfo.CARDINAL_DIRECTIONS, placer.getHorizontalFacing().getOpposite()));
        if(world.isRemote && !ClientProxy.tutorials.get("radio")) {
            BetterEventHandler.tutorialText = BetterUtils.getTranslatedString("tutorial.radio");
            BetterEventHandler.tutorialTime = System.currentTimeMillis() + 10000;
            ClientProxy.tutorials.put("radio", true);
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
        if(tileEntity == null || !(tileEntity instanceof TileEntityRadio)) return;
        TileEntityRadio tileEntityRadio = (TileEntityRadio) tileEntity;
        ItemStack item = tileEntityRadio.crystal;
        if(item != null) {
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
            tileEntityRadio.crystal = null;
            PacketHandler.sendSoundStopToAllFromServer(tileEntityRadio.getPos().getX(), tileEntityRadio.getPos().getY(), tileEntityRadio.getPos().getZ(), world.provider.getDimensionId());
        }
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2){
        return new TileEntityRadio();
    }
}
