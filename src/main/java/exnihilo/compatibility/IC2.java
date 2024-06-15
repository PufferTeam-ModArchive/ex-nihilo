package exnihilo.compatibility;

import java.util.ArrayList;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import exnihilo.ENBlocks;
import exnihilo.ExNihilo;
import exnihilo.config.GeneralConfig;
import exnihilo.config.SieveConfig;
import exnihilo.items.seeds.ItemSeedRubber;
import exnihilo.registries.CompostRegistry;
import exnihilo.registries.SieveRegistry;
import exnihilo.registries.helpers.Color;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.RecipeOutput;
import ic2.api.recipe.Recipes;

public class IC2 {

    public static boolean isLoaded() {
        return Loader.isModLoaded("IC2");
    }

    public static void loadCompatibility() {
        ArrayList<ItemStack> ores = OreDictionary.getOres("dustSulfur");
        if (ores.size() > 0) {
            if (SieveConfig.enableDefaultSieveRewards) {
                ItemStack sulfur = ores.toArray(new ItemStack[0])[0];
                SieveRegistry.register(ENBlocks.Dust, 0, sulfur.getItem(), sulfur.getItemDamage(), 32);
            }
            ExNihilo.log.info("Sulfur was successfully integrated");
        } else {
            ExNihilo.log.error("SULFUR WAS NOT INTEGRATED");
        }
        Block rubberSapling = GameRegistry.findBlock("IC2", "blockRubSapling");
        if (rubberSapling != null) {
            ItemSeedRubber.AddSapling(rubberSapling);
            ExNihilo.log.info("Rubber was successfully integrated");
        } else {
            ExNihilo.log.error("RUBBER WAS NOT INTEGRATED");
        }
        Item crushedOres = GameRegistry.findItem("IC2", "itemCrushedOre");
        if (crushedOres != null) {
            if (SieveConfig.enableDefaultSieveRewards) {
                SieveRegistry.register(Blocks.sand, 0, crushedOres, 4, 48);
            }
            ExNihilo.log.info("Crushed Ores were successfully integrated");
        } else {
            ExNihilo.log.error("CRUSHED ORES WERE NOT INTEGRATED");
        }
        Item plantBall = GameRegistry.findItem("IC2", "itemFuelPlantBall");
        if (plantBall != null) {
            CompostRegistry.register(plantBall, 0, 0.5F, new Color("269900"));
            ExNihilo.log.info("Plantball was successfully integrated");
        } else {
            ExNihilo.log.error("PLANTBALL WAS NOT INTEGRATED");
        }
        Item plantBallCompressed = GameRegistry.findItem("IC2", "itemFuelPlantCmpr");
        if (plantBallCompressed != null) {
            CompostRegistry.register(plantBallCompressed, 0, 1.0F, new Color("269900"));
            ExNihilo.log.info("Compressed Plants were successfully integrated");
        } else {
            ExNihilo.log.error("COMPRESSED PLANTS WERE NOT INTEGRATED");
        }
        if (GeneralConfig.overwriteIC2Macerator) {
            Map<IRecipeInput, RecipeOutput> recipes = Recipes.macerator.getRecipes();
            IRecipeInput cobbleRecipe = null;
            IRecipeInput gravelRecipe = null;
            for (IRecipeInput i : recipes.keySet()) {
                if (i.matches(new ItemStack(Blocks.cobblestone))) cobbleRecipe = i;
                if (i.matches(new ItemStack(Blocks.gravel))) gravelRecipe = i;
            }
            if (cobbleRecipe != null) {
                recipes.remove(cobbleRecipe);
                ExNihilo.log.info("Macerator: removed default cobble->sand Macerator recipe");
            } else {
                ExNihilo.log.error("DEFAULT COBBLE TO SAND MACERATOR RECIPE WASN'T REMOVED");
            }
            if (gravelRecipe != null) {
                recipes.remove(gravelRecipe);
                ExNihilo.log.info("Macerator: removed default gravel->flint Macerator recipe");
            } else {
                ExNihilo.log.error("DEFAULT GRAVEL TO FLINT MACERATOR RECIPE WASN'T REMOVED");
            }
            Recipes.macerator.addRecipe(
                    new RecipeInputItemStack(new ItemStack(Blocks.cobblestone)),
                    null,
                    new ItemStack(Blocks.gravel));
            ExNihilo.log.info("Macerator: added recipe for cobble->gravel");
            Recipes.macerator.addRecipe(
                    new RecipeInputItemStack(new ItemStack(Blocks.gravel)),
                    null,
                    new ItemStack(Blocks.sand));
            ExNihilo.log.info("Macerator: added recipe for gravel->sand");
        }
        Recipes.macerator
                .addRecipe(new RecipeInputItemStack(new ItemStack(Blocks.sand)), null, new ItemStack(ENBlocks.Dust));
        ExNihilo.log.info("Macerator: added recipe sand->dust");
        ExNihilo.log.info("--- IC2 Integration Complete!");
    }
}
