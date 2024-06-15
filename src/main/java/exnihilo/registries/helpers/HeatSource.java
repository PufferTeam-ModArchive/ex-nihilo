package exnihilo.registries.helpers;

import java.util.Objects;

import net.minecraft.block.Block;

public class HeatSource {

    public final Block block;

    public final int meta;

    public float value;

    public int hashCode() {
        int result = 1;
        Object $block = this.block;
        result = result * 59 + (($block == null) ? 0 : $block.hashCode());
        result = result * 59 + this.meta;
        return result * 59 + Float.floatToIntBits(this.value);
    }

    protected boolean canEqual(Object other) {
        return other instanceof HeatSource;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof HeatSource other)) return false;
        if (!other.canEqual(this)) return false;
        Object this$block = this.block, other$block = other.block;
        return (Objects.equals(this$block, other$block))
                && (this.meta == other.meta && (Float.compare(this.value, other.value) == 0));
    }

    public HeatSource(Block block, int meta, float value) {
        this.block = block;
        this.meta = meta;
        this.value = value;
    }
}
