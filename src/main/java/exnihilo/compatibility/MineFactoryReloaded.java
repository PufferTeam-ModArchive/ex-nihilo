package exnihilo.compatibility;

import net.minecraft.block.Block;

import cpw.mods.fml.common.registry.GameRegistry;
import exnihilo.ExNihilo;
import exnihilo.items.seeds.ItemSeedRubber;

public class MineFactoryReloaded {

    public static void loadCompatibility() {
        Block rubberSapling = GameRegistry.findBlock("MineFactoryReloaded", "rubberwood.sapling");
        if (rubberSapling != null) {
            ItemSeedRubber.AddSapling(rubberSapling);
            ExNihilo.log.info("Rubber Tree saplings were successfuly integrated");
        }
        ExNihilo.log.info("--- MineFactory Reloaded Integration Complete!");
    }
}
