package exnihilo.registries;

import java.util.HashMap;
import java.util.Hashtable;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import exnihilo.registries.helpers.HeatSource;
import exnihilo.utils.ItemInfo;

public class HeatRegistry {

    @Deprecated
    public static final Hashtable<String, HeatSource> entries = new Hashtable<>();

    private static final HashMap<ItemInfo, Float> heatmap = new HashMap<>();

    public static void register(Block block, int meta, float value) {
        heatmap.put(new ItemInfo(block, meta), value);
    }

    public static void register(Block block, float value) {
        for (int x = 0; x <= 15; x++) register(block, x, value);
    }

    public static boolean containsItem(Block block, int meta) {
        return heatmap.containsKey(new ItemInfo(block, meta));
    }

    @Deprecated
    public static HeatSource getItem(Block block, int meta) {
        return entries.get(block + ":" + meta);
    }

    public static float getSpeed(Block block, int meta) {
        if (heatmap.get(new ItemInfo(block, meta)) == null) return 0.0F;
        return heatmap.get(new ItemInfo(block, meta));
    }

    public static void registerVanillaHeatSources() {
        register(Blocks.torch, 0.1F);
        register(Blocks.lava, 0.2F);
        register(Blocks.flowing_lava, 0.1F);
        register(Blocks.lit_furnace, 0.15F);
        register(Blocks.fire, 0.3F);
    }

    public static void unregister(Block block, int meta) {
        entries.remove(block + ":" + meta);
        heatmap.remove(new ItemInfo(block, meta));
    }

    public static void unregister(Block block) {
        for (int x = 0; x <= 15; x++) unregister(block, x);
    }

    public static void modify(Block block, int meta, float newHeatValue) {
        if (entries.containsKey(block + ":" + meta)) {
            HeatSource src = entries.get(block + ":" + meta);
            src.value = newHeatValue;
            entries.put(block + ":" + meta, src);
        }
        ItemInfo info = new ItemInfo(block, meta);
        if (heatmap.containsKey(info)) heatmap.put(info, newHeatValue);
    }
}
