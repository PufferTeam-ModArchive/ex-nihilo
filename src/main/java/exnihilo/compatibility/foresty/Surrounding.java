package exnihilo.compatibility.foresty;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import forestry.api.apiculture.FlowerManager;

public class Surrounding {

    private static final EnumSet<FlowerType> addedFlowerTypes = EnumSet.of(
            FlowerType.Normal,
            FlowerType.Nether,
            FlowerType.End,
            FlowerType.Jungle,
            FlowerType.Mushroom,
            FlowerType.Cactus,
            FlowerType.Gourd);

    public final Map<String, Integer> blocks = new HashMap<>();

    public final Map<String, Integer> flowers = new HashMap<>();

    public int leafCount;

    public String blockAbove;

    public void addBlock(World world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);
        if (block != null && block.isLeaves(world, x, y, z)) this.leafCount++;
        String key = block + ":" + meta;
        if (this.blocks.containsKey(key)) {
            int count = this.blocks.get(key);
            this.blocks.put(key, count + 1);
        } else {
            this.blocks.put(key, 1);
        }
        tryAddFlower(world, x, y, z);
    }

    public void setBlockAbove(Block block, int meta) {
        this.blockAbove = block + ":" + meta;
    }

    public void tryAddFlower(World world, int x, int y, int z) {
        for (FlowerType flowerType : addedFlowerTypes) {
            if (FlowerManager.flowerRegistry.isAcceptedFlower(flowerType.getForestryKey(), world, x, y, z))
                addFlower(flowerType);
        }
        if (world.getBlock(x, y, z) == Blocks.waterlily) addFlower(FlowerType.Water);
    }

    private void addFlower(FlowerType type) {
        String key = type.name();
        if (this.flowers.containsKey(key)) {
            int count = this.flowers.get(key);
            this.flowers.put(key, count + 1);
        } else {
            this.flowers.put(key, 1);
        }
    }

    public int getFlowerCount(FlowerType type) {
        String key = type.name();
        if (type == FlowerType.None) {
            return 0;
        }
        if (this.flowers.containsKey(key)) return this.flowers.get(key);
        return 0;
    }
}
