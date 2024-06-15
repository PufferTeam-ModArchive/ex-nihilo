package exnihilo.items.crooks;

import net.minecraft.item.Item;

public enum CrookType {

    WOOD("wood", 64, Item.ToolMaterial.WOOD),
    STONE("stone", 128, Item.ToolMaterial.STONE),
    IRON("iron", 256, Item.ToolMaterial.IRON),
    GOLD("gold", 32, Item.ToolMaterial.GOLD),
    DIAMOND("diamond", 2048, Item.ToolMaterial.EMERALD),
    BONE("bone", 256, Item.ToolMaterial.IRON);

    private final String name;
    private final int durability;
    private final Item.ToolMaterial material;

    CrookType(String name, int durability, Item.ToolMaterial material) {
        this.name = "crook_" + name;
        this.durability = durability;
        this.material = material;
    }

    public String getName() {
        return this.name;
    }

    public int getDurability() {
        return this.durability;
    }

    public Item.ToolMaterial getMaterial() {
        return this.material;
    }
}
