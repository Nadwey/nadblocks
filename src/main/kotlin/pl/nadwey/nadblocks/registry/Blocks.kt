package pl.nadwey.nadblocks.registry

import org.bukkit.Material
import org.bukkit.block.BlockFace
import pl.nadwey.nadblocks.NadBlocks
import pl.nadwey.nadblocks.block.Table
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
import xyz.xenondevs.nova.world.block.state.property.DefaultBlockStateProperties
import xyz.xenondevs.nova.world.block.state.property.DefaultScopedBlockStateProperties
import xyz.xenondevs.nova.world.block.state.property.DefaultScopedBlockStateProperties.FACING_CARTESIAN
import xyz.xenondevs.nova.world.block.state.property.DefaultScopedBlockStateProperties.FACING_HORIZONTAL
import xyz.xenondevs.nova.world.block.state.property.DefaultScopedBlockStateProperties.FACING_ROTATION
import xyz.xenondevs.nova.world.block.state.property.DefaultScopedBlockStateProperties.FACING_VERTICAL

@Init(stage = InitStage.PRE_PACK)
object Blocks : BlockRegistry by NadBlocks.registry {
    private val STONE =
        Breakable(2.0, VanillaToolCategories.PICKAXE, VanillaToolTiers.WOOD, true, Material.BLACK_CONCRETE)
    private val WOOD = Breakable(2.0, VanillaToolCategories.AXE, VanillaToolTiers.WOOD, false, Material.OAK_WOOD)
    private val FRAGILE_WOOD = Breakable(1.2, VanillaToolCategories.AXE, VanillaToolTiers.WOOD, false, Material.OAK_WOOD)
    private val METAL =
        Breakable(5.0, VanillaToolCategories.PICKAXE, VanillaToolTiers.STONE, true, Material.IRON_BLOCK)

    val HAZARD_BLOCK =
        solidNonInteractiveBlock("hazard_block") { behaviors(BlockDrops, STONE, BlockSounds(SoundGroup.STONE)) }

    val CARROT_CRATE =
        translucentNonInteractiveBlock("carrot_crate") { behaviors(BlockDrops, WOOD, BlockSounds(SoundGroup.BAMBOO_WOOD)) }

    val POTATO_CRATE =
        translucentNonInteractiveBlock("potato_crate") { behaviors(BlockDrops, WOOD, BlockSounds(SoundGroup.BAMBOO_WOOD)) }

    val TABLE =
        translucentNonInteractiveBlock("table") { behaviors(Table, FRAGILE_WOOD, BlockSounds(SoundGroup.WOOD)) }

    val DRAIN_GRATE =
        nonInteractiveBlock("drain_grate") {
            behaviors(BlockDrops, METAL, BlockSounds(SoundGroup.HEAVY_CORE))
            stateProperties(FACING_HORIZONTAL)
            models {
                selectModel { defaultModel.rotated() }
                stateBacked(BackingStateCategory.LEAVES)
            }
        }

    private fun translucentNonInteractiveBlock(name: String, block: NovaBlockBuilder.() -> Unit): NovaBlock =
        nonInteractiveBlock(name) {
            block()
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
