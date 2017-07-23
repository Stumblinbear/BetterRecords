package com.codingforcookies.betterrecords.block.tile

import com.codingforcookies.betterrecords.api.connection.RecordConnection
import com.codingforcookies.betterrecords.api.wire.IRecordWire
import com.codingforcookies.betterrecords.api.wire.IRecordWireHome
import com.codingforcookies.betterrecords.common.core.handler.ConfigHandler
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ITickable
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.ArrayList
import java.util.HashMap

abstract class SimpleRecordWireHome : BetterTile(), IRecordWireHome, ITickable {

    var formTreble = ArrayList<Float>()
    var formBass = ArrayList<Float>()

    @Synchronized override fun addTreble(form: Float) {
        formTreble.add(form)
    }

    @Synchronized override fun addBass(form: Float) {
        formBass.add(form)
    }

    override var connections = mutableListOf<RecordConnection>()

    var wireSystemInfo = HashMap<String, Int>()

    internal var playRadius = 0f

    override fun increaseAmount(wireComponent: IRecordWire) {
        if (wireSystemInfo.containsKey(wireComponent.getName())) {
            wireSystemInfo.put(wireComponent.getName(), wireSystemInfo[wireComponent.getName()]!! + 1)
        } else {
            wireSystemInfo.put(wireComponent.getName(), 1)
        }
        playRadius += wireComponent.songRadiusIncrease
    }

    override fun decreaseAmount(wireComponent: IRecordWire) {
        if (wireSystemInfo.containsKey(wireComponent.getName())) {
            wireSystemInfo.put(wireComponent.getName(), wireSystemInfo[wireComponent.getName()]!! - 1)
            if (wireSystemInfo[wireComponent.getName()]!! <= 0) {
                wireSystemInfo.remove(wireComponent.getName())
            }
            playRadius -= wireComponent.songRadiusIncrease
        }
    }

    override fun update() {
        if (world.isRemote) {
            while (formTreble.size > 2500)
                for (i in 0..24) {
                    formTreble.removeAt(0)
                    formBass.removeAt(0)
                }
        }
    }

    internal abstract val songRadiusIncrease: Float

    override val songRadius: Float
        get() {
            val radius = songRadiusIncrease + playRadius
            val maxRadius = ConfigHandler.maxSpeakerRadius
            return if (radius <= maxRadius || maxRadius == -1) radius else maxRadius.toFloat()
        }

    override val tileEntity: TileEntity
        get() = this

    @SideOnly(Side.SERVER)
    fun canUpdate(): Boolean {
        return false
    }
}