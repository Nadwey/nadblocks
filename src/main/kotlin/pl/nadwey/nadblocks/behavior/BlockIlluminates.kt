package pl.nadwey.nadblocks.behavior

import net.minecraft.core.Direction
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.LightBlock
import xyz.xenondevs.nova.context.Context
import xyz.xenondevs.nova.context.intention.DefaultContextIntentions
import xyz.xenondevs.nova.util.serverLevel
import xyz.xenondevs.nova.world.BlockPos
import xyz.xenondevs.nova.world.block.behavior.BlockBehavior
import xyz.xenondevs.nova.world.block.state.NovaBlockState

// https://www.spigotmc.org/threads/1-19-3-1-20-3-nms-make-block-emit-light.594267/
class BlockIlluminates(
    private val level: Int, private val blockFaces: Array<Direction> = arrayOf(
        Direction.DOWN,
        Direction.EAST,
        Direction.NORTH,
        Direction.SOUTH,
        Direction.UP,
        Direction.WEST
    )
) : BlockBehavior {

    override fun handleNeighborChanged(pos: BlockPos, state: NovaBlockState, neighborPos: BlockPos) {
        updateLight(pos)
    }

    override fun handlePlace(pos: BlockPos, state: NovaBlockState, ctx: Context<DefaultContextIntentions.BlockPlace>) {
        updateLight(pos)
    }

    override fun handleBreak(pos: BlockPos, state: NovaBlockState, ctx: Context<DefaultContextIntentions.BlockBreak>) {
        val serverLevel = pos.world.serverLevel

        blockFaces.forEach { face ->
            val entry = pos.nmsPos.relative(face)
            val blockData = serverLevel.getBlockState(entry)
            if (blockData.isAir || blockData.block == Blocks.LIGHT) {
                serverLevel.destroyBlock(entry, false)
            }
        }
    }

    private fun updateLight(pos: BlockPos) {
        val serverLevel = pos.world.serverLevel
        val newState = Blocks.LIGHT.defaultBlockState().setValue(LightBlock.LEVEL, level)

        blockFaces.forEach { face ->
            val entry = pos.nmsPos.relative(face)
            val blockData = serverLevel.getBlockState(entry)
            if (blockData.isAir || blockData.block == Blocks.LIGHT) {
                serverLevel.setBlock(entry, newState, 2, 0)
            }
        }
    }
}
