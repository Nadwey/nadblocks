package pl.nadwey.nadblocks.block

import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.nova.data.context.Context
import xyz.xenondevs.nova.data.context.intention.DefaultContextIntentions.BlockBreak
import xyz.xenondevs.nova.data.context.param.DefaultContextParamTypes
import xyz.xenondevs.nova.world.BlockPos
import xyz.xenondevs.nova.world.block.behavior.BlockBehavior
import xyz.xenondevs.nova.world.block.state.NovaBlockState

object Table : BlockBehavior {
    override fun getDrops(pos: BlockPos, state: NovaBlockState, ctx: Context<BlockBreak>): List<ItemStack> {
        if (ctx[DefaultContextParamTypes.SOURCE_PLAYER]?.gameMode == GameMode.CREATIVE)
            return emptyList()
        
        if (ctx[DefaultContextParamTypes.TOOL_ITEM_STACK] != null && ctx[DefaultContextParamTypes.TOOL_ITEM_STACK]?.getEnchantmentLevel(Enchantment.SILK_TOUCH) != 0)
            return listOf(state.block.item!!.createItemStack())

        return listOf(ItemStack(Material.OAK_SLAB, 1), ItemStack(Material.STICK, 2))
    }
}