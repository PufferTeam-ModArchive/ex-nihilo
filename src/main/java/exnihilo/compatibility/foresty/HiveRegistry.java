package exnihilo.compatibility.foresty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.world.biome.BiomeGenBase;

import exnihilo.ExNihilo;

public class HiveRegistry {

    public static final Map<String, Hive> hives = new HashMap<>();

    public static final Random rand = new Random();

    public static void registerHive(Hive hive) {
        hives.put(hive.block + ":" + hive.meta, hive);
    }

    public static Hive getHive(BiomeGenBase biome, Surrounding local, boolean canSeeSky, int height) {
        List<Hive> found = new ArrayList<>();
        for (Map.Entry pairs : hives.entrySet()) {
            Hive hive = (Hive) pairs.getValue();
            if (hive != null) if (hive.areAllRequirementsMet(biome, local, canSeeSky, height)) found.add(hive);
        }
        if (!found.isEmpty()) {
            int index = rand.nextInt(found.size());
            return found.get(index);
        }
        return null;
    }

    public static void registerHives() {
        ExNihilo.log.info("Beginning Hive Registry...");
        if (HiveList.generateForestryHives()) {
            registerHive(HiveList.forest);
            registerHive(HiveList.meadow);
            registerHive(HiveList.desert);
            registerHive(HiveList.jungle);
            registerHive(HiveList.end);
            registerHive(HiveList.snow);
            registerHive(HiveList.swamp);
        }
        if (HiveList.generateExtraBeesHives()) {
            registerHive(HiveList.water);
            registerHive(HiveList.rock);
            registerHive(HiveList.nether);
        }
        if (HiveList.generateMagicBeesHives()) {
            registerHive(HiveList.curious);
            registerHive(HiveList.resonating);
            registerHive(HiveList.unusual);
            registerHive(HiveList.deep);
            registerHive(HiveList.infernal);
            registerHive(HiveList.oblivion);
        }
        ExNihilo.log.info("Hive Registry Completed!");
    }
}
