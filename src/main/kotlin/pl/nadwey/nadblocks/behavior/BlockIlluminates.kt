package pl.nadwey.nadblocks.behavior

import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.LightBlock
import net.minecraft.world.level.block.state.BlockState
import xyz.xenondevs.nova.data.context.Context
import xyz.xenondevs.nova.data.context.intention.DefaultContextIntentions
import xyz.xenondevs.nova.util.serverLevel
import xyz.xenondevs.nova.world.BlockPos
import xyz.xenondevs.nova.world.block.behavior.BlockBehavior
import xyz.xenondevs.nova.world.block.state.NovaBlockState
import net.minecraft.core.BlockPos as MojangPos

// https://www.spigotmc.org/threads/1-19-3-1-20-3-nms-make-block-emit-light.594267/
class BlockIlluminates(private val level: Int) : BlockBehavior {
    companion object {
        val LIGHT_BLOCK_FACES: Array<Direction> = arrayOf<Direction>(
            Direction.DOWN,
            Direction.EAST,
            Direction.NORTH,
            Direction.SOUTH,
            Direction.UP,
            Direction.WEST
        )
    }

    override fun handleNeighborChanged(pos: BlockPos, state: NovaBlockState, neighborPos: BlockPos) {
        updateLight(pos)
    }

    override fun handlePlace(pos: BlockPos, state: NovaBlockState, ctx: Context<DefaultContextIntentions.BlockPlace>) {
        updateLight(pos)
    }

    override fun handleBreak(pos: BlockPos, state: NovaBlockState, ctx: Context<DefaultContextIntentions.BlockBreak>) {
        val serverLevel: ServerLevel = pos.world.serverLevel

        for (face in LIGHT_BLOCK_FACES) {
            val entry: MojangPos = pos.nmsPos.relative(face)
            val blockData: BlockState = serverLevel.getBlockState(entry)
            if (blockData.isAir || blockData.block === Blocks.LIGHT) {
                serverLevel.destroyBlock(entry, false)
            }
        }
    }

    private fun updateLight(pos: BlockPos) {
        val serverLevel: ServerLevel = pos.world.serverLevel

        var newState: BlockState = Blocks.LIGHT.defaultBlockState()
        newState = newState.setValue(LightBlock.LEVEL, level)

        for (face in LIGHT_BLOCK_FACES) {
            val entry: MojangPos = pos.nmsPos.relative(face)
            val blockData: BlockState = serverLevel.getBlockState(entry)
            if (blockData.isAir || blockData.block === Blocks.LIGHT) {
                serverLevel.setBlock(entry, newState, 2, 0)
            }
        }
    }

}