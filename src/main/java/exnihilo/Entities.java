package exnihilo;

import cpw.mods.fml.common.registry.EntityRegistry;
import exnihilo.entities.EntityStone;

public class Entities {

    private static final int STONE_ID = 0;

    public static void registerEntities() {
        EntityRegistry.registerModEntity(EntityStone.class, "Broken Stone", 0, ExNihilo.instance, 64, 10, true);
    }
}
