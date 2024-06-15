package exnihilo.compatibility;

import net.minecraft.block.Block;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import exnihilo.ExNihilo;
import tconstruct.library.crafting.Smeltery;

public class TinkersConstruct {

    private static final int INGOT_AMOUNT = 144;

    public static void loadCompatibility() {
        ExNihilo.log.info("--- Tinkers Construct Integration Complete!");
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
        if (name.equalsIgnoreCase("aluminum") || name.equalsIgnoreCase("aluminium"))
            RegisterOre(OreList.Type.Aluminum, block);
    }

    public static void RegisterOre(OreList.Type ore, Block block) {
        int meltingPoint = getMeltingPoint(ore);
        FluidStack moltenMetal = getMoltenMetal(ore);
        if (block != null && meltingPoint != 0 && moltenMetal != null)
            Smeltery.addMelting(block, 0, meltingPoint, moltenMetal);
    }

    private static int getMeltingPoint(OreList.Type ore) {
        return switch (ore) {
            case Iron -> 600;
            case Gold, Aluminum, Platinum, Nickel, Lead, Silver, Tin -> 400;
            case Copper -> 550;
            default -> 0;
        };
    }

    private static FluidStack getMoltenMetal(OreList.Type ore) {
        Fluid metal = findMoltenMetal(ore);
        if (metal != null) return new FluidStack(metal, 288);
        return null;
    }

    private static Fluid findMoltenMetal(OreList.Type ore) {
        return switch (ore) {
            case Iron -> FluidRegistry.getFluid("iron.molten");
            case Gold -> FluidRegistry.getFluid("gold.molten");
            case Tin -> FluidRegistry.getFluid("tin.molten");
            case Copper -> FluidRegistry.getFluid("copper.molten");
            case Silver -> FluidRegistry.getFluid("silver.molten");
            case Lead -> FluidRegistry.getFluid("lead.molten");
            case Nickel -> FluidRegistry.getFluid("nickel.molten");
            case Platinum -> FluidRegistry.getFluid("platinum.molten");
            case Aluminum -> FluidRegistry.getFluid("aluminum.molten");
            default -> null;
        };
    }
}
