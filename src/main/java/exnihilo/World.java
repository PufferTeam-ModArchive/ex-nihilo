package exnihilo;

import exnihilo.config.WorldConfig;
import net.minecraftforge.common.DimensionManager;

import exnihilo.world.WorldProviderDefaultVoid;
import exnihilo.world.WorldProviderEndVoid;
import exnihilo.world.WorldProviderHellVoid;

public class World {

    public static void registerWorldProviders() {
        if (WorldConfig.hijackNether) {
            DimensionManager.unregisterProviderType(-1);
            DimensionManager.registerProviderType(-1, WorldProviderHellVoid.class, true);
        }
        if (WorldConfig.hijackOverworld) {
            DimensionManager.unregisterProviderType(0);
            DimensionManager.registerProviderType(0, WorldProviderDefaultVoid.class, true);
        }
        if (WorldConfig.hijackEnd) {
            DimensionManager.unregisterProviderType(1);
            DimensionManager.registerProviderType(1, WorldProviderEndVoid.class, true);
        }
    }
}
