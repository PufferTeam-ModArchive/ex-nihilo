package exnihilo.fluids;

import net.minecraftforge.fluids.Fluid;

import exnihilo.registries.ColorRegistry;

public class FluidWitchWater extends Fluid {

    public FluidWitchWater(String fluidName) {
        super(fluidName);
    }

    @Override
    public int getColor() {
        return ColorRegistry.color("witchwater").toInt();
    }
}
