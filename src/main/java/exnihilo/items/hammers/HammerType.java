package exnihilo.items.hammers;

import net.minecraft.item.Item;

public enum HammerType {

    WOOD("wood", 64, Item.ToolMaterial.WOOD),
    STONE("stone", 128, Item.ToolMaterial.STONE),
    IRON("iron", 256, Item.ToolMaterial.IRON),
    GOLD("gold", 32, Item.ToolMaterial.GOLD),
    DIAMOND("diamond", 2048, Item.ToolMaterial.EMERALD);

    private final String name;
    private final int durability;
    private final Item.ToolMaterial material;

    HammerType(String name, int durability, Item.ToolMaterial material) {
        this.name = "hammer_"+name;
        this.durability = durability;
        this.material = material;
    }

    public String getName() {
        return this.name;
    }

    public int getDurability() { return this.durability; }

    public Item.ToolMaterial getMaterial() { return this.material; }

}
