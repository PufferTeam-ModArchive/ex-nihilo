package exnihilo.events;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.event.world.BlockEvent;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import exnihilo.items.hammers.IHammer;
import exnihilo.registries.HammerRegistry;
import exnihilo.registries.helpers.Smashable;

public class HandlerHammer {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void hammer(BlockEvent.HarvestDropsEvent event) {
        if (event.world.isRemote) return;
        if (event.harvester == null) return;
        if (event.isSilkTouching) return;
        Block block = event.block;
        int meta = event.blockMetadata;
        ArrayList<Smashable> rewards = HammerRegistry.getRewards(block, meta);
        ItemStack held = event.harvester.getHeldItem();
        if (isHammer(held) && canHarvest(block, meta, held) && rewards != null && rewards.size() > 0) {
            event.drops.clear();
            event.dropChance = 1.0F;
            int fortune = EnchantmentHelper.getFortuneModifier(event.harvester);
            for (Smashable reward : rewards) {
                if (event.world.rand.nextFloat() <= reward.chance + reward.luckMultiplier * fortune)
                    event.drops.add(new ItemStack(reward.item, 1, reward.meta));
            }
        }
    }

    public boolean isHammer(ItemStack stack) {
        if (stack == null || stack.getItem() == null) return false;
        if (stack.getItem() instanceof IHammer) return ((IHammer) stack.getItem()).isHammer(stack);
        return stack.hasTagCompound() && stack.stackTagCompound.getBoolean("Hammered");
    }

    public boolean canHarvest(Block block, int meta, ItemStack stack) {
        int harvestLevel = 0;
        if (stack.getItem() instanceof ItemTool)
            harvestLevel = ((ItemTool) stack.getItem()).func_150913_i().getHarvestLevel();
        return (block.getHarvestLevel(meta) <= harvestLevel);
    }
}
