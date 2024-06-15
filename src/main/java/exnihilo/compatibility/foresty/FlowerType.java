package exnihilo.compatibility.foresty;

public enum FlowerType {

    None("flowersNone"),
    Normal("flowersVanilla"),
    Nether("flowersNether"),
    End("flowersEnd"),
    Jungle("flowersJungle"),
    Mushroom("flowersMushrooms"),
    Gourd("flowersGourd"),
    Cactus("flowersCacti"),
    Wheat("flowersWheat"),
    Water("flowersWater");

    private final String forestryKey;

    FlowerType(String forestryKey) {
        this.forestryKey = forestryKey;
    }

    public String getForestryKey() {
        return this.forestryKey;
    }
}
