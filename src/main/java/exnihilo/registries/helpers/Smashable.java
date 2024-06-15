package exnihilo.registries.helpers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class Smashable {

    public final Block source;

    public final int sourceMeta;

    public final Item item;

    public final int meta;

    public float chance;

    public final float luckMultiplier;

    public Smashable(Block source, int sourceMeta, Item item, int meta, float chance, float luckMultiplier) {
        this.source = source;
        this.sourceMeta = sourceMeta;
        this.item = item;
        this.meta = meta;
        this.chance = chance;
        this.luckMultiplier = luckMultiplier;
    }
}
