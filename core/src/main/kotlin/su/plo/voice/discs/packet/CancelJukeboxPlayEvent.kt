package su.plo.voice.discs.packet

import com.github.retrooper.packetevents.event.PacketListener
import com.github.retrooper.packetevents.event.PacketSendEvent
import com.github.retrooper.packetevents.protocol.packettype.PacketType
import com.github.retrooper.packetevents.util.Vector3i
import org.bukkit.Bukkit
import org.bukkit.World
import su.plo.voice.discs.AddonKeys
import su.plo.voice.discs.event.JukeboxEventListener

class CancelJukeboxPlayEvent(
    private val keys: AddonKeys,
    private val jukeboxes: JukeboxEventListener,
): PacketListener {
    override fun onPacketSend(event: PacketSendEvent) = with(keys) {
        if (event.packetType != PacketType.Play.Server.EFFECT) return

        val packet = WrapperPlayServerEffect(event)

        // https://minecraft.wiki/w/Java_Edition_protocol#World_Event
        // 1010: Play record
        if (packet.event != 1010) return@with

        val player = Bukkit.getPlayer(event.user.uuid) ?: return@with

        val isCustomDisc = jukeboxes.isPlaying(packet.position.toBlock(player.world))

        if (isCustomDisc) event.isCancelled = true
    }

    private fun Vector3i.toBlock(world: World) =
        world.getBlockAt(x, y, z)
}