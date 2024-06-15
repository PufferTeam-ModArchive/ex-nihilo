package exnihilo.compatibility;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.FluidRegistry;

import appeng.api.AEApi;
import appeng.api.definitions.Materials;
import exnihilo.ENBlocks;
import exnihilo.ExNihilo;
import exnihilo.config.SieveConfig;
import exnihilo.registries.BarrelRecipeRegistry;
import exnihilo.registries.SieveRegistry;

public class AE2 {

    public static Item certusDust = (AEApi.instance().materials()).materialCertusQuartzDust.item();

    public static Item skyStoneDust = (AEApi.instance().materials()).materialSkyDust.item();

    public static Block skyStone = (AEApi.instance().blocks()).blockSkyStone.block();

    public static void loadCompatibility() {
        Materials materials = AEApi.instance().materials();
        appeng.api.definitions.Blocks blocks = AEApi.instance().blocks();
        if (SieveConfig.enableDefaultSieveRewards) {
            SieveRegistry.register(
                    Blocks.sand,
                    0,
                    materials.materialCertusQuartzCrystal.item(),
                    materials.materialCertusQuartzCrystal.stack(1).getItemDamage(),
                    6);
            SieveRegistry.register(
                    Blocks.sand,
                    0,
                    materials.materialCertusQuartzCrystal.item(),
                    materials.materialCertusQuartzCrystalCharged.stack(1).getItemDamage(),
                    128);
            SieveRegistry.register(
                    ENBlocks.Dust,
                    0,
                    materials.materialCertusQuartzDust.item(),
                    materials.materialCertusQuartzDust.stack(1).getItemDamage(),
                    6);
            SieveRegistry.register(
                    ENBlocks.Dust,
                    0,
                    materials.materialSkyDust.item(),
                    materials.materialSkyDust.stack(1).getItemDamage(),
                    8);
        }
        ExNihilo.log.info("Certus Quartz was successfully integrated");
        ExNihilo.log.info("Skystone was successfully integrated");
        BarrelRecipeRegistry.addFluidItemRecipe(
                FluidRegistry.LAVA,
                materials.materialSkyDust.stack(1),
                blocks.blockSkyStone.stack(1));
        ExNihilo.log.info("--- AE2 Integration Complete!");
    }
}
