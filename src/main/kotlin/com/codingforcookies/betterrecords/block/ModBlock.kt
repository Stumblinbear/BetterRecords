package com.codingforcookies.betterrecords.block

import com.codingforcookies.betterrecords.BetterRecords
import com.codingforcookies.betterrecords.ID
import com.codingforcookies.betterrecords.api.BetterRecordsAPI
import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.client.renderer.block.statemap.StateMap
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumBlockRenderType
import net.minecraft.world.World
import net.minecraftforge.client.ForgeHooksClient
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.common.registry.GameRegistry
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

/**
 * Abstract representation of a BetterRecords block.
 *
 * Since every block in BetterRecords is a tile entity, most of the logic for registering the tile entity and TESR is
 * here. However, this class is designed such that it can still be used for a block that is not a TESR without issue.
 */
abstract class ModBlock(material: Material, val name: String) : BlockContainer(material) {

    /**
     * Register the block, as well as the tile entity if it is specified
     * @see getTileEntityClass
     */
    init {
        setRegistryName(name)
        unlocalizedName = registryName.toString()

        setCreativeTab(BetterRecords.creativeTab)
        println("CREATE")

        getTileEntityClass()?.let {
            GameRegistry.registerTileEntity(it.java, "$ID:$name")
        }
    }

    /**
     * Register the TESR for a block.
     * Note that the state mapper is deferred to a template method.
     * @see setStateMapper
     */
    fun registerTESR() {
        val item = Item.getItemFromBlock(this)
        setStateMapper()
        ForgeHooksClient.registerTESRItemStack(item, 0, getTileEntityClass()?.java)
        ModelLoader.setCustomModelResourceLocation(item, 0, ModelResourceLocation("$ID:itemblock/$name", "inventory"))
    }

    /**
     * By default, the state mapper for our block will be cardinal directions.
     * If a different state mapper is desired, this can be overridden by the parent class
     */
    protected open fun setStateMapper() {
        ModelLoader.setCustomStateMapper(this, StateMap.Builder().ignore(BetterRecordsAPI.CARDINAL_DIRECTIONS).build())
    }

    /**
     * If the parent class has a tile entity, overriding this method will cause the tile entity to be registered with it
     */
    open fun getTileEntityClass(): KClass<out TileEntity>? = null

    override fun hasTileEntity() = getTileEntityClass() != null
    override fun createNewTileEntity(worldIn: World?, meta: Int) = getTileEntityClass()?.primaryConstructor?.call()

    override fun getRenderType(state: IBlockState?) = EnumBlockRenderType.ENTITYBLOCK_ANIMATED
    override fun isFullCube(state: IBlockState?) = false
    override fun isOpaqueCube(state: IBlockState?) = false
}
