package su.plo.voice.discs.utils.extend

import org.bukkit.Chunk
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.Jukebox

fun Block.isJukebox() = this.type == Material.JUKEBOX

fun Block.isBeaconBaseBlock() = when (this.type) {
    Material.IRON_BLOCK,
    Material.GOLD_BLOCK,
    Material.DIAMOND_BLOCK,
    Material.NETHERITE_BLOCK,
    Material.EMERALD_BLOCK -> true
    else -> false
}

fun Block.asJukebox() = this.takeIf { it.isJukebox() }?.let { it.state as? Jukebox }

fun Block.inChunk(chunk: Chunk) =
    chunk.x == chunkX() && chunk.z == chunkZ()

fun Block.chunkX() = x shr 4

fun Block.chunkZ() = z shr 4