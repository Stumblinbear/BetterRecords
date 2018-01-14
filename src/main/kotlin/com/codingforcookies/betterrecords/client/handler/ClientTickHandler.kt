package com.codingforcookies.betterrecords.client.handler

import com.codingforcookies.betterrecords.BetterRecords
import com.codingforcookies.betterrecords.ID
import com.codingforcookies.betterrecords.ModConfig
import com.codingforcookies.betterrecords.api.wire.IRecordWireHome
import com.codingforcookies.betterrecords.client.sound.SoundHandler
import net.minecraft.client.Minecraft
import net.minecraft.util.SoundCategory
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import net.minecraftforge.fml.relauncher.Side

@Mod.EventBusSubscriber(modid = ID, value = [Side.CLIENT])
object ClientTickHandler {

    @JvmStatic
    @SubscribeEvent
    fun showDisclaimer(event: TickEvent.ClientTickEvent) {
        if (event.phase == TickEvent.Phase.END) {
            Minecraft.getMinecraft().player?.let {
                if (ModConfig.client.flashMode < 0) {
                    it.openGui(BetterRecords, 2, Minecraft.getMinecraft().world, 0, 0, 0)
                }
            }
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun incrementNowPlayingInt(event: TickEvent.ClientTickEvent) {
        if (event.phase == TickEvent.Phase.START) {
            if (SoundHandler.nowPlaying != "" && SoundHandler.nowPlayingEnd < System.currentTimeMillis()) {
                SoundHandler.nowPlaying = ""
            } else {
                SoundHandler.nowPlayingInt += 3
            }
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun updateVolumeBasedOnDistance(event: TickEvent.ClientTickEvent) {
        if (event.phase == TickEvent.Phase.START) {
            if (SoundHandler.soundPlaying.size > 0) {
                val player = Minecraft.getMinecraft().player
                val world  = Minecraft.getMinecraft().world

                if (world == null || player == null) {
                    SoundHandler.soundPlaying.clear()
                    SoundHandler.nowPlaying = ""
                    SoundHandler.nowPlayingEnd = 0
                    SoundHandler.nowPlayingInt = 0
                    return
                }

                for (value in SoundHandler.soundPlaying.values) {
                    if (value.currentSong == null || value.currentSong.volume == null) {
                        continue
                    }

                    if (value.currentSong.dimension == 1234) {
                        value.currentSong.volume.value = -20F
                        return
                    }

                    if (value.currentSong.dimension != world.provider.dimension) {
                        value.currentSong.volume.value = -80F
                        return
                    }

                    val te = world.getTileEntity(BlockPos(value.currentSong.x, value.currentSong.y, value.currentSong.z))
                    if (te != null && te is IRecordWireHome) {
                        value.currentSong.playRadius = te.songRadius

                        var dist = Math.abs(Math.sqrt(Math.pow(player.posX - value.currentSong.x, 2.toDouble()) +
                                                      Math.pow(player.posY - value.currentSong.y, 2.toDouble()) +
                                                      Math.pow(player.posZ - value.currentSong.z, 2.toDouble())))

                        for (rc in te.connections) {
                            val d = Math.abs(Math.sqrt(Math.pow(player.posX - rc.x2, 2.toDouble()) +
                                                       Math.pow(player.posY - rc.y2, 2.toDouble()) +
                                                       Math.pow(player.posZ - rc.z2, 2.toDouble())))
                            if (d < dist) {
                                dist = d
                            }
                        }

                        if (dist > value.currentSong.playRadius + 10F) {
                            value.currentSong.volume.value = -80F
                        } else {
                            val volume = dist * (50F / value.currentSong.playRadius /
                                    (Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.MASTER) *
                                     Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.RECORDS)))

                            if (volume > 80F) {
                                value.currentSong.volume.value = -80F
                            } else {
                                value.currentSong.volume.value = 0F - volume.toFloat()
                            }
                        }
                    }
                }
            }
        }
    }
}
