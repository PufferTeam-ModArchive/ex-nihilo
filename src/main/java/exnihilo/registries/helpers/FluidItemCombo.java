package exnihilo.registries.helpers;

import java.beans.ConstructorProperties;
import java.util.Objects;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

import exnihilo.utils.ItemInfo;

public class FluidItemCombo {

    private Fluid inputFluid;

    private ItemInfo inputItem;

    public void setInputFluid(Fluid inputFluid) {
        this.inputFluid = inputFluid;
    }

    public void setInputItem(ItemInfo inputItem) {
        this.inputItem = inputItem;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof FluidItemCombo other)) return false;
        if (!other.canEqual(this)) return false;
        Object this$inputFluid = getInputFluid(), other$inputFluid = other.getInputFluid();
        if (!Objects.equals(this$inputFluid, other$inputFluid)) return false;
        Object this$inputItem = getInputItem(), other$inputItem = other.getInputItem();
        return Objects.equals(this$inputItem, other$inputItem);
    }

    protected boolean canEqual(Object other) {
        return other instanceof FluidItemCombo;
    }

    public int hashCode() {
        int result = 1;
        Object $inputFluid = getInputFluid();
        result = result * 59 + (($inputFluid == null) ? 0 : $inputFluid.hashCode());
        Object $inputItem = getInputItem();
        return result * 59 + (($inputItem == null) ? 0 : $inputItem.hashCode());
    }

    public String toString() {
        return "FluidItemCombo(inputFluid=" + getInputFluid() + ", inputItem=" + getInputItem() + ")";
    }

    @ConstructorProperties({ "inputFluid", "inputItem" })
    public FluidItemCombo(Fluid inputFluid, ItemInfo inputItem) {
        this.inputFluid = inputFluid;
        this.inputItem = inputItem;
    }

    public Fluid getInputFluid() {
        return this.inputFluid;
    }

    public ItemInfo getInputItem() {
        return this.inputItem;
    }

    public FluidItemCombo(Fluid inputFluid, ItemStack inputStack) {
        this.inputFluid = inputFluid;
        this.inputItem = new ItemInfo(inputStack);
    }
}
