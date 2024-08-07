package pl.nadwey.betterhubaddon.registry

import pl.nadwey.betterhubaddon.BetterHubAddon
import xyz.xenondevs.nova.addon.registry.ItemRegistry
import xyz.xenondevs.nova.initialize.Init
import xyz.xenondevs.nova.initialize.InitStage

@Init(stage = InitStage.PRE_PACK)
object Items : ItemRegistry by BetterHubAddon.registry {
    val HAZARD_BLOCK = registerItem(Blocks.HAZARD_BLOCK)
}