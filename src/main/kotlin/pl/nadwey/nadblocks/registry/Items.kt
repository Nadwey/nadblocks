package pl.nadwey.nadblocks.registry

import pl.nadwey.nadblocks.NadBlocks
import xyz.xenondevs.nova.addon.registry.ItemRegistry
import xyz.xenondevs.nova.initialize.Init
import xyz.xenondevs.nova.initialize.InitStage

@Init(stage = InitStage.PRE_PACK)
object Items : ItemRegistry by NadBlocks.registry {
    val HAZARD_BLOCK = registerItem(Blocks.HAZARD_BLOCK)
    val CARROT_CRATE = registerItem(Blocks.CARROT_CRATE)
    val POTATO_CRATE = registerItem(Blocks.POTATO_CRATE)
    val TABLE = registerItem(Blocks.TABLE)
    val DRAIN_GRATE = registerItem(Blocks.DRAIN_GRATE)
}