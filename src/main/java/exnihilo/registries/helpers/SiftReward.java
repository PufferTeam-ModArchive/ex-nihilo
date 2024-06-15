package exnihilo.registries.helpers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import exnihilo.config.SieveConfig;

public class SiftReward {

    public final Block source;

    public final int sourceMeta;

    public final boolean ignoreMeta;

    public final Item item;

    public final int meta;

    public final int rarity;

    public SiftReward(Block source, int sourceMeta, Item item, int meta, int rarity) {
        this.source = source;
        this.sourceMeta = sourceMeta;
        this.ignoreMeta = false;
        this.item = item;
        this.meta = meta;
        this.rarity = calculateRarity(rarity);
    }

    public SiftReward(Block source, Item item, int meta, int rarity) {
        this.source = source;
        this.sourceMeta = 0;
        this.ignoreMeta = true;
        this.item = item;
        this.meta = meta;
        this.rarity = calculateRarity(rarity);
    }

    private static int calculateRarity(int base) {
        int multiplier = SieveConfig.sievePainMultiplier + 1;
        int rarity = base * multiplier + (int) (multiplier / 2.0F);
        return rarity;
    }
}
