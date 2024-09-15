package pl.nadwey.nadblocks.registry

import org.bukkit.Material
import pl.nadwey.nadblocks.NadBlocks
import pl.nadwey.nadblocks.behavior.BlockIlluminates
import pl.nadwey.nadblocks.block.Table
import xyz.xenondevs.nova.addon.registry.BlockRegistry
import xyz.xenondevs.nova.initialize.Init
import xyz.xenondevs.nova.initialize.InitStage
import xyz.xenondevs.nova.resources.layout.block.BackingStateCategory
import xyz.xenondevs.nova.world.block.NovaBlock
import xyz.xenondevs.nova.world.block.NovaBlockBuilder
import xyz.xenondevs.nova.world.block.behavior.BlockDrops
import xyz.xenondevs.nova.world.block.behavior.BlockSounds
import xyz.xenondevs.nova.world.block.behavior.Breakable
import xyz.xenondevs.nova.world.block.sound.SoundGroup
import xyz.xenondevs.nova.world.block.state.property.DefaultScopedBlockStateProperties.FACING_CARTESIAN
import xyz.xenondevs.nova.world.block.state.property.DefaultScopedBlockStateProperties.FACING_HORIZONTAL
import xyz.xenondevs.nova.world.item.tool.VanillaToolCategories
import xyz.xenondevs.nova.world.item.tool.VanillaToolTiers

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

    val APPLE_CRATE =
        translucentNonInteractiveBlock("apple_crate") { behaviors(BlockDrops, WOOD, BlockSounds(SoundGroup.BAMBOO_WOOD)) }

    val TABLE =
        translucentNonInteractiveBlock("table") { behaviors(Table, FRAGILE_WOOD, BlockSounds(SoundGroup.CHISELED_BOOKSHELF)) }

    val DRAIN_GRATE =
        nonInteractiveBlock("drain_grate") {
            behaviors(BlockDrops, METAL, BlockSounds(SoundGroup.HEAVY_CORE))
            stateProperties(FACING_HORIZONTAL)
            models {
                selectModel { defaultModel.rotated() }
                stateBacked(BackingStateCategory.LEAVES)
            }
        }

    val LAMP =
        nonInteractiveBlock("lamp") {
            behaviors(
                BlockDrops,
                BlockIlluminates(15),
                METAL,
                BlockSounds(SoundGroup.METAL)
            )
            stateProperties(FACING_CARTESIAN)
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
