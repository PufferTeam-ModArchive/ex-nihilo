package exnihilo.utils;

import java.util.LinkedHashSet;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import exnihilo.ENBlocks;
import exnihilo.ENItems;
import exnihilo.config.CrookConfig;

public class CrookUtils {

    private static final LinkedHashSet<ItemInfo> blacklist = new LinkedHashSet<>();

    public static void load() {
        for (String input : CrookConfig.crookBlacklist) {
            String[] current = input.split(":");
            if (current.length == 3 && Block.blockRegistry.getObject(current[0] + ":" + current[1]) != null) {
                Block block = (Block) Block.blockRegistry.getObject(current[0] + ":" + current[1]);
                blacklist.add(new ItemInfo(block, Integer.parseInt(current[2])));
            }
        }
    }

    public static boolean doCrooking(ItemStack item, int X, int Y, int Z, EntityPlayer player) {
        World world = player.worldObj;
        Block block = world.getBlock(X, Y, Z);
        int meta = world.getBlockMetadata(X, Y, Z);
        boolean validTarget = false;
        if (block.isLeaves(world, X, Y, Z) && !blacklist.contains(new ItemInfo(block, meta))) {
            if (!world.isRemote) {
                block.dropBlockAsItem(world, X, Y, Z, meta, 0);
                if (CrookConfig.enableDefaultCrookRewards && world.rand.nextInt(100) == 0) world.spawnEntityInWorld(
                        new EntityItem(world, X + 0.5D, Y + 0.5D, Z + 0.5D, new ItemStack(ENItems.Silkworm, 1, 0)));
            }
            validTarget = true;
        }
        if (block == ENBlocks.LeavesInfested) {
            if (!world.isRemote)
                if (CrookConfig.enableDefaultCrookRewards && world.rand.nextInt(15) == 0) world.spawnEntityInWorld(
                        new EntityItem(world, X + 0.5D, Y + 0.5D, Z + 0.5D, new ItemStack(ENItems.Silkworm, 1, 0)));
            validTarget = true;
        }
        return validTarget;
    }
}
