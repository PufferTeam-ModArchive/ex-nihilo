package exnihilo.registries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import exnihilo.config.HammerConfig;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.ShapedOreRecipe;

import cpw.mods.fml.common.registry.GameRegistry;
import exnihilo.ENBlocks;
import exnihilo.ENItems;
import exnihilo.registries.helpers.Smashable;
import exnihilo.utils.ItemInfo;

public class HammerRegistry {

    public static HashMap<ItemInfo, ArrayList<Smashable>> getRewards() {
        return rewards;
    }

    private static final HashMap<ItemInfo, ArrayList<Smashable>> rewards = new HashMap<>();

    public static boolean allowIceShards = true;

    public static final String categorySmashableOptions = "smashables";

    public static void register(Block source, int sourceMeta, Item output, int outputMeta, float chance,
            float luckMultiplier) {
        ArrayList<Smashable> current;
        Smashable entry = new Smashable(source, sourceMeta, output, outputMeta, chance, luckMultiplier);
        ItemInfo ii = new ItemInfo(source, sourceMeta);
        if (rewards.containsKey(ii)) {
            current = rewards.get(ii);
        } else {
            current = new ArrayList<>();
        }
        current.add(entry);
        rewards.put(ii, current);
    }

    public static void remove(Block source, int sourceMeta, Item output, int outputMeta) {
        ItemInfo input = new ItemInfo(source, sourceMeta);
        ItemInfo removal = new ItemInfo(output, outputMeta);
        ArrayList<Smashable> list = rewards.get(input);
        Iterator<Smashable> it = list.iterator();
        while (it.hasNext()) {
            Smashable s = it.next();
            ItemInfo current = new ItemInfo(s.item, s.meta);
            if (current.equals(removal)) it.remove();
        }
        rewards.put(input, list);
    }

    public static void editChance(Block source, int sourceMeta, Item output, int outputMeta, float newChance) {
        ItemInfo input = new ItemInfo(source, sourceMeta);
        ItemInfo removal = new ItemInfo(output, outputMeta);
        ArrayList<Smashable> list = rewards.get(input);
        Iterator<Smashable> it = list.iterator();
        ArrayList<Smashable> replacements = new ArrayList<>();
        while (it.hasNext()) {
            Smashable s = it.next();
            ItemInfo current = new ItemInfo(s.item, s.meta);
            if (current.equals(removal)) {
                s.chance = newChance;
                replacements.add(s);
                it.remove();
            }
        }
        list.addAll(replacements);
        rewards.put(input, list);
    }

    public static ArrayList<Smashable> getRewards(Block block, int meta) {
        return rewards.get(new ItemInfo(block, meta));
    }

    public static ArrayList<Smashable> getRewards(ItemInfo ii) {
        return rewards.get(ii);
    }

    public static void load() {
        if (HammerConfig.enableIceShards) {
            register(Blocks.ice, 0, ENItems.IceShard, 0, 1.0F, 0.0F);
            register(Blocks.ice, 0, ENItems.IceShard, 0, 0.75F, 0.1F);
            register(Blocks.ice, 0, ENItems.IceShard, 0, 0.75F, 0.1F);
            register(Blocks.ice, 0, ENItems.IceShard, 0, 0.5F, 0.1F);
            register(Blocks.ice, 0, ENItems.IceShard, 0, 0.25F, 0.1F);
            register(Blocks.ice, 0, ENItems.IceShard, 0, 0.05F, 0.1F);
            GameRegistry.addRecipe(
                    new ShapedOreRecipe(
                            new ItemStack(Blocks.packed_ice),
                            new Object[] { "a a", " b ", "a a", 'a', new ItemStack(ENItems.IceShard), 'b',
                                    new ItemStack(Blocks.sand) }));
            GameRegistry.addRecipe(
                    new ShapedOreRecipe(
                            new ItemStack(Blocks.ice),
                            new Object[] { "aa", "aa", 'a', new ItemStack(ENItems.IceShard) }));
        }
    }

    public static boolean registeredInefficiently(Block block, int meta) {
        for (Smashable reward : rewards.get(new ItemInfo(block, meta))) {
            if (reward.source.getUnlocalizedName().equals(block.getUnlocalizedName()) && reward.meta == meta)
                return true;
        }
        return false;
    }

    public static boolean registered(ItemStack item) {
        return rewards.containsKey(new ItemInfo(item));
    }

    public static boolean registered(Block block, int meta) {
        return registered(new ItemStack(block, 1, meta));
    }

    public static void registerSmashables() {
        register(Blocks.stone, 0, ENItems.Stones, 0, 1.0F, 0.0F);
        register(Blocks.stone, 0, ENItems.Stones, 0, 0.75F, 0.1F);
        register(Blocks.stone, 0, ENItems.Stones, 0, 0.75F, 0.1F);
        register(Blocks.stone, 0, ENItems.Stones, 0, 0.5F, 0.1F);
        register(Blocks.stone, 0, ENItems.Stones, 0, 0.25F, 0.1F);
        register(Blocks.stone, 0, ENItems.Stones, 0, 0.05F, 0.1F);
        register(Blocks.cobblestone, 0, Item.getItemFromBlock(Blocks.gravel), 0, 1.0F, 0.0F);
        register(Blocks.gravel, 0, Item.getItemFromBlock(Blocks.sand), 0, 1.0F, 0.0F);
        register(Blocks.sand, 0, Item.getItemFromBlock(ENBlocks.Dust), 0, 1.0F, 0.0F);
        register(Blocks.sandstone, 0, Item.getItemFromBlock(Blocks.sand), 0, 1.0F, 0.0F);
        register(Blocks.sandstone, 1, Item.getItemFromBlock(Blocks.sand), 0, 1.0F, 0.0F);
        register(Blocks.sandstone, 2, Item.getItemFromBlock(Blocks.sand), 0, 1.0F, 0.0F);
        register(Blocks.stonebrick, 0, Item.getItemFromBlock(Blocks.stonebrick), 2, 1.0F, 0.0F);
        register(Blocks.stonebrick, 2, ENItems.Stones, 0, 1.0F, 0.0F);
        register(Blocks.stonebrick, 2, ENItems.Stones, 0, 0.75F, 0.1F);
        register(Blocks.stonebrick, 2, ENItems.Stones, 0, 0.75F, 0.1F);
        register(Blocks.stonebrick, 2, ENItems.Stones, 0, 0.5F, 0.1F);
        register(Blocks.stonebrick, 2, ENItems.Stones, 0, 0.25F, 0.1F);
        register(Blocks.stonebrick, 2, ENItems.Stones, 0, 0.05F, 0.1F);
        register(Blocks.end_stone, 0, Item.getItemFromBlock(ENBlocks.EnderGravel), 0, 1.0F, 0.0F);
        register(Blocks.netherrack, 0, Item.getItemFromBlock(ENBlocks.NetherGravel), 0, 1.0F, 0.0F);
    }

    public static void registerOre(Block ore, int oreMeta, Item reward, int rewardMeta) {
        if (ore != null && reward != null) {
            register(ore, oreMeta, reward, rewardMeta, 1.0F, 0.0F);
            register(ore, oreMeta, reward, rewardMeta, 1.0F, 0.0F);
            register(ore, oreMeta, reward, rewardMeta, 1.0F, 0.0F);
            register(ore, oreMeta, reward, rewardMeta, 1.0F, 0.0F);
            register(ore, oreMeta, reward, rewardMeta, 0.5F, 0.1F);
            register(ore, oreMeta, reward, rewardMeta, 0.05F, 0.1F);
            register(ore, oreMeta, reward, rewardMeta, 0.0F, 0.05F);
        }
    }

    public static ArrayList<ItemInfo> getSources(ItemStack reward) {
        ArrayList<ItemInfo> res = new ArrayList<>();
        for (ItemInfo entry : rewards.keySet()) {
            for (Smashable smash : rewards.get(entry)) {
                if ((new ItemInfo(smash.item, smash.meta)).equals(new ItemInfo(reward))) res.add(entry);
            }
        }
        return res;
    }
}
