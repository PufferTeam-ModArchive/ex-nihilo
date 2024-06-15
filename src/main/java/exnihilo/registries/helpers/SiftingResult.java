package exnihilo.registries.helpers;

import java.beans.ConstructorProperties;

import exnihilo.utils.ItemInfo;

public class SiftingResult {

    public final ItemInfo drop;

    public final int rarity;

    @ConstructorProperties({ "drop", "rarity", "meshLevel" })
    public SiftingResult(ItemInfo drop, int rarity) {
        this.drop = drop;
        this.rarity = rarity;
    }
}
