package exnihilo.registries.helpers;

import java.util.Objects;

import net.minecraft.item.Item;

public class Compostable {

    public String unlocalizedName;

    public Item item;

    public int meta;

    public final float value;

    public final Color color;

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Compostable other)) return false;
        if (!other.canEqual(this)) return false;
        Object this$unlocalizedName = this.unlocalizedName, other$unlocalizedName = other.unlocalizedName;
        if (!Objects.equals(this$unlocalizedName, other$unlocalizedName)) return false;
        Object this$item = this.item, other$item = other.item;
        if (!Objects.equals(this$item, other$item)) return false;
        if (this.meta != other.meta) return false;
        if (Float.compare(this.value, other.value) != 0) return false;
        return Objects.equals(this.color, other.color);
    }

    protected boolean canEqual(Object other) {
        return other instanceof Compostable;
    }

    public int hashCode() {
        int result = 1;
        Object $unlocalizedName = this.unlocalizedName;
        result = result * 59 + (($unlocalizedName == null) ? 0 : $unlocalizedName.hashCode());
        Object $item = this.item;
        result = result * 59 + (($item == null) ? 0 : $item.hashCode());
        result = result * 59 + this.meta;
        result = result * 59 + Float.floatToIntBits(this.value);
        Object $color = this.color;
        return result * 59 + (($color == null) ? 0 : $color.hashCode());
    }

    @Deprecated
    public Compostable(Item item, int meta, float value, Color color) {
        this.item = item;
        this.meta = meta;
        this.value = value;
        this.color = color;
    }

    public Compostable(float value, Color color) {
        this.value = value;
        this.color = color;
    }
}
