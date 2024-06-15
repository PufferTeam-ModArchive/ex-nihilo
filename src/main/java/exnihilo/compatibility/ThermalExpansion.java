package exnihilo.compatibility;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

import cofh.api.modhelpers.ThermalExpansionHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import exnihilo.ENBlocks;
import exnihilo.ExNihilo;
import exnihilo.config.CrucibleConfig;
import exnihilo.config.GeneralConfig;
import exnihilo.registries.HeatRegistry;

public class ThermalExpansion {

    public static void loadCompatibility() {
        if (GeneralConfig.overwriteThermalPulverizerRecipes) {
            overwritePulverizerRecipe(
                    3200,
                    new ItemStack(Blocks.cobblestone),
                    new ItemStack(Blocks.gravel),
                    new ItemStack(Blocks.sand),
                    10,
                    true);
            overwritePulverizerRecipe(
                    3200,
                    new ItemStack(Blocks.gravel),
                    new ItemStack(Blocks.sand),
                    new ItemStack(ENBlocks.Dust),
                    10,
                    true);
            overwritePulverizerRecipe(
                    3200,
                    new ItemStack(Blocks.netherrack),
                    new ItemStack(ENBlocks.NetherGravel),
                    OreDictionary.getOres("dustSulfur").get(0),
                    15,
                    true);
            ExNihilo.log.info("Pulverizer: overwrote the cobble->sand recipe with cobble->gravel");
            ExNihilo.log.info("Pulverizer: added recipe for gravel->sand");
        }
        ThermalExpansionHelper.addPulverizerRecipe(3200, new ItemStack(Blocks.sand), new ItemStack(ENBlocks.Dust));
        ExNihilo.log.info("Pulverizer: added recipe for sand->dust");
        ThermalExpansionHelper
                .addPulverizerRecipe(3200, new ItemStack(Blocks.end_stone), new ItemStack(ENBlocks.EnderGravel));
        Block pyrotheum = FluidRegistry.getFluid("pyrotheum").getBlock();
        if (pyrotheum != null) {
            if (CrucibleConfig.enableDefaultHeatSources) {
                HeatRegistry.register(pyrotheum, 0.5F);
                HeatRegistry.register(pyrotheum, 0, 0.7F);
                ExNihilo.log.info("Added blazing pyrotheum as a crucible heat source");
            }
        }
        ExNihilo.log.info("--- Thermal Expansion Integration Complete!");
    }

    public static void overwritePulverizerRecipe(int energy, ItemStack input, ItemStack primaryOutput,
            ItemStack secondaryOutput, int secondaryChance, boolean overwrite) {
        ThermalExpansionHelper.removePulverizerRecipe(input);
        ThermalExpansionHelper.addPulverizerRecipe(energy, input, primaryOutput, secondaryOutput, secondaryChance);
    }

    public static void TryRegisterOre(String name, Block block) {
        name = name.replace("ender_", "");
        name = name.replace("nether_", "");
        if (name.equalsIgnoreCase("iron")) RegisterOre(OreList.Type.Iron, block);
        if (name.equalsIgnoreCase("gold")) RegisterOre(OreList.Type.Gold, block);
        if (name.equalsIgnoreCase("copper")) RegisterOre(OreList.Type.Copper, block);
        if (name.equalsIgnoreCase("tin")) RegisterOre(OreList.Type.Tin, block);
        if (name.equalsIgnoreCase("nickel")) RegisterOre(OreList.Type.Nickel, block);
        if (name.equalsIgnoreCase("platinum")) RegisterOre(OreList.Type.Platinum, block);
        if (name.equalsIgnoreCase("silver")) RegisterOre(OreList.Type.Silver, block);
        if (name.equalsIgnoreCase("lead")) RegisterOre(OreList.Type.Lead, block);
    }

    public static void RegisterOre(OreList.Type ore, Block block) {
        ItemStack primary, secondary, iblock = new ItemStack(block);
        switch (ore) {
            case Iron -> {
                primary = GameRegistry.findItemStack("ThermalFoundation", "dustIron", 2);
                secondary = GameRegistry.findItemStack("ThermalFoundation", "dustNickel", 1);
                ThermalExpansionHelper.addPulverizerRecipe(3200, iblock, primary, secondary, 10);
            }
            case Gold -> {
                primary = GameRegistry.findItemStack("ThermalFoundation", "dustGold", 2);
                secondary = null;
                ThermalExpansionHelper.addPulverizerRecipe(3200, iblock, primary, secondary, 10);
            }
            case Tin -> {
                primary = GameRegistry.findItemStack("ThermalFoundation", "dustTin", 2);
                secondary = GameRegistry.findItemStack("ThermalFoundation", "dustIron", 1);
                ThermalExpansionHelper.addPulverizerRecipe(3200, iblock, primary, secondary, 10);
            }
            case Copper -> {
                primary = GameRegistry.findItemStack("ThermalFoundation", "dustCopper", 2);
                secondary = GameRegistry.findItemStack("ThermalFoundation", "dustGold", 1);
                ThermalExpansionHelper.addPulverizerRecipe(3200, iblock, primary, secondary, 10);
            }
            case Silver -> {
                primary = GameRegistry.findItemStack("ThermalFoundation", "dustSilver", 2);
                secondary = GameRegistry.findItemStack("ThermalFoundation", "dustLead", 1);
                ThermalExpansionHelper.addPulverizerRecipe(3200, iblock, primary, secondary, 10);
            }
            case Lead -> {
                primary = GameRegistry.findItemStack("ThermalFoundation", "dustLead", 2);
                secondary = GameRegistry.findItemStack("ThermalFoundation", "dustSilver", 1);
                ThermalExpansionHelper.addPulverizerRecipe(3200, iblock, primary, secondary, 10);
            }
            case Nickel -> {
                primary = GameRegistry.findItemStack("ThermalFoundation", "dustNickel", 2);
                secondary = GameRegistry.findItemStack("ThermalFoundation", "dustPlatinum", 1);
                ThermalExpansionHelper.addPulverizerRecipe(3200, iblock, primary, secondary, 10);
            }
            case Platinum -> {
                primary = GameRegistry.findItemStack("ThermalFoundation", "dustPlatinum", 2);
                secondary = null;
                ThermalExpansionHelper.addPulverizerRecipe(3200, iblock, primary, secondary, 10);
            }
        }
    }
}
