package exnihilo.compatibility.foresty;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

public class Hive {

    public final BiomeGenBase requiredBiome = null;

    public final Float minTemperature = null;

    public final Float maxTemperature = null;

    public final Float minRainfall = null;

    public final Float maxRainfall = null;

    public final Float minYLevel = null;

    public Float maxYLevel = null;

    public int defaultSpawnBonus = 0;

    public FlowerType flowers = FlowerType.None;

    public String requiredSubstrate = null;

    private static final int REQUIRED_SUBSTRATE_COUNT = 15;

    public Boolean requiredCanSeeSky = null;

    public Boolean requiresTree = null;

    public String requiresBlockAbove = null;

    public final List<BiomeDictionary.Type> biomeTypes = new ArrayList<>();

    public final Block block;

    public final int meta;

    public Hive(Block block, int meta) {
        this.block = block;
        this.meta = meta;
    }

    public boolean areAllRequirementsMet(BiomeGenBase biome, Surrounding local, boolean canSeeSky, int height) {
        if ((this.requiredBiome != null && biome.biomeID != this.requiredBiome.biomeID)
                || (this.minTemperature != null && biome.temperature < this.minTemperature)
                || (this.maxTemperature != null && biome.temperature > this.maxTemperature)
                || (this.minRainfall != null && biome.rainfall < this.minRainfall)
                || (this.maxRainfall != null && biome.rainfall > this.maxRainfall)
                || (this.minYLevel != null && height < this.minYLevel)
                || (this.maxYLevel != null && height > this.maxYLevel)
                || (this.requiredCanSeeSky != null && canSeeSky != this.requiredCanSeeSky))
            return false;
        if (this.requiresBlockAbove != null && !this.requiresBlockAbove.equals(local.blockAbove)) return false;
        if (this.requiresTree != null && this.requiresTree && local.leafCount < 20) return false;
        if (this.requiredSubstrate != null) {
            int substrateCount = 0;
            if (local.blocks.containsKey(this.requiredSubstrate))
                substrateCount = local.blocks.get(this.requiredSubstrate);
            if (substrateCount < REQUIRED_SUBSTRATE_COUNT) return false;
        }
        if (!this.biomeTypes.isEmpty()) {
            BiomeDictionary.Type[] types = BiomeDictionary.getTypesForBiome(biome);
            for (BiomeDictionary.Type biomeType : this.biomeTypes) {
                boolean found = false;
                BiomeDictionary.Type currentType = biomeType;
                for (BiomeDictionary.Type type : types) {
                    if (type == currentType) {
                        found = true;
                        break;
                    }
                }
                if (!found) return false;
            }
        }
        if (this.flowers != FlowerType.None) {
            int flowerCount = local.getFlowerCount(this.flowers);
            return flowerCount != 0;
        }
        return true;
    }

    public int getSpawnChanceModifier(Surrounding local) {
        int flowerCount = local.getFlowerCount(this.flowers);
        return this.defaultSpawnBonus + flowerCount;
    }
}
