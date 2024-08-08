package pl.nadwey.nadblocks.registry

import org.bukkit.Material
import pl.nadwey.nadblocks.NadBlocks
import xyz.xenondevs.nova.addon.registry.BlockRegistry
import xyz.xenondevs.nova.data.resources.layout.block.BackingStateCategory
import xyz.xenondevs.nova.initialize.Init
import xyz.xenondevs.nova.initialize.InitStage
import xyz.xenondevs.nova.item.tool.VanillaToolCategories
import xyz.xenondevs.nova.item.tool.VanillaToolTiers
import xyz.xenondevs.nova.world.block.NovaBlock
import xyz.xenondevs.nova.world.block.NovaBlockBuilder
import xyz.xenondevs.nova.world.block.behavior.BlockDrops
import xyz.xenondevs.nova.world.block.behavior.BlockSounds
import xyz.xenondevs.nova.world.block.behavior.Breakable
import xyz.xenondevs.nova.world.block.sound.SoundGroup

@Init(stage = InitStage.PRE_PACK)
object Blocks : BlockRegistry by NadBlocks.registry {
    private val STONE = Breakable(2.0, VanillaToolCategories.PICKAXE, VanillaToolTiers.WOOD, true, Material.BLACK_CONCRETE)
    private val WOOD = Breakable(2.0, VanillaToolCategories.AXE, VanillaToolTiers.WOOD, false, Material.OAK_WOOD)

    val HAZARD_BLOCK =
        solidNonInteractiveBlock("hazard_block") {
            behaviors(BlockDrops, STONE, BlockSounds(SoundGroup.STONE))
        }

    val CARROT_CRATE =
        nonInteractiveBlock("carrot_crate") {
            behaviors(BlockDrops, WOOD, BlockSounds(SoundGroup.BAMBOO_WOOD))
            models {
                stateBacked(BackingStateCategory.LEAVES)
            }
        }

    val POTATO_CRATE =
        nonInteractiveBlock("potato_crate") {
            behaviors(BlockDrops, WOOD, BlockSounds(SoundGroup.BAMBOO_WOOD))
            models {
                stateBacked(BackingStateCategory.LEAVES)
            }
        }

    private fun solidNonInteractiveBlock(name: String, block: NovaBlockBuilder.() -> Unit): NovaBlock =
        nonInteractiveBlock(name) {
            block()
            models {
                stateBacked(BackingStateCategory.MUSHROOM_BLOCK, BackingStateCategory.NOTE_BLOCK)
            }
        }

    private fun nonInteractiveBlock(name: String, block: NovaBlockBuilder.() -> Unit): NovaBlock =
        block(name) {
            block()
        }
}