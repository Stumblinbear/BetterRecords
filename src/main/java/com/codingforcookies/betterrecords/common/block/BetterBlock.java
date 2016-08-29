package com.codingforcookies.betterrecords.common.block;

import com.codingforcookies.betterrecords.common.BetterRecords;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BetterBlock<T extends TileEntity> extends BlockContainer {

    public BetterBlock(Material material, String name) {
        super(material);
        setUnlocalizedName(name);
        this.setCreativeTab(BetterRecords.recordsTab);
    }

    @Override
    public Block setUnlocalizedName(String name) {
        GameRegistry.register(this.setRegistryName(name));
        GameRegistry.register(new ItemBlock(this).setRegistryName(name));
        return super.setUnlocalizedName("betterrecords:" + name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFullyOpaque(IBlockState state) {
        return false;
    }

    @Override
    public abstract T createNewTileEntity(World world, int meta);
}
