package exnihilo.registries;

import java.util.Hashtable;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import exnihilo.registries.helpers.Meltable;

public class CrucibleRegistry {

    public static final Hashtable<String, Meltable> entries = new Hashtable<>();

    public static void register(Block block, int meta, float solidAmount, Fluid fluid, float fluidAmount,
            Block appearance, int appearanceMeta) {
        Meltable entry = new Meltable(block, meta, solidAmount, fluid, fluidAmount, appearance, appearanceMeta);
        entries.put(block + ":" + meta, entry);
    }

    public static void register(Block block, int meta, float solidAmount, Fluid fluid, float fluidAmount) {
        register(block, meta, solidAmount, fluid, fluidAmount, block, meta);
    }

    public static void register(Block block, int meta, float solidAmount, Fluid fluid, float fluidAmount,
            Block appearance) {
        Meltable entry = new Meltable(block, meta, solidAmount, fluid, fluidAmount, appearance, 0);
        entries.put(block + ":" + meta, entry);
    }

    public static void register(Block block, float solidAmount, Fluid fluid, float fluidAmount) {
        for (int i = 0; i < 16; i++) register(block, i, solidAmount, fluid, fluidAmount);
    }

    public static boolean containsItem(Block block, int meta) {
        return entries.containsKey(block + ":" + meta);
    }

    public static Meltable getItem(Block block, int meta) {
        return entries.get(block + ":" + meta);
    }

    public static boolean changeFluidFromBlock(Block block, int meta, Fluid newFluid, float newVolume) {
        if (containsItem(block, meta)) {
            Meltable melt = getItem(block, meta);
            melt.fluid = newFluid;
            melt.fluidVolume = newVolume;
            return true;
        }
        return false;
    }

    public static void registerMeltables() {
        register(Blocks.cobblestone, 0, 2000.0F, FluidRegistry.LAVA, 250.0F);
        register(Blocks.stone, 0, 2000.0F, FluidRegistry.LAVA, 250.0F);
        register(Blocks.gravel, 0, 2000.0F, FluidRegistry.LAVA, 250.0F);
        register(Blocks.netherrack, 0, 2000.0F, FluidRegistry.LAVA, 1000.0F);
        register(Blocks.snow, 0, 2000.0F, FluidRegistry.WATER, 500.0F);
        register(Blocks.ice, 0, 2000.0F, FluidRegistry.WATER, 1000.0F);
    }
}
