package exnihilo.registries.helpers;

import net.minecraft.block.Block;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;

public class Meltable {

    public final Block block;

    public final int meta;

    public final float solidVolume;

    public Fluid fluid;

    public float fluidVolume;

    public final Block appearance;

    public final int appearanceMeta;

    public Meltable(Block block, int meta, float solidAmount, Fluid fluid, float fluidAmount, Block appearance,
            int appearanceMeta) {
        this.block = block;
        this.meta = meta;
        this.solidVolume = solidAmount;
        this.fluid = fluid;
        this.fluidVolume = fluidAmount;
        this.appearance = appearance;
        this.appearanceMeta = appearanceMeta;
    }

    @Deprecated
    public Meltable(Block block, int meta, float solidAmount, Fluid fluid, float fluidAmount, Block appearance) {
        this(block, meta, solidAmount, fluid, fluidAmount, appearance, 0);
    }

    public IIcon getIcon() {
        return this.appearance.getIcon(0, this.appearanceMeta);
    }
}
