package exnihilo.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class ItemSilkwormCooked extends ItemFood {

    public ItemSilkwormCooked() {
        super(2, 10.0F, false);
        setCreativeTab(CreativeTabs.tabFood);
    }

    @Override
    public String getUnlocalizedName() {
        return "exnihilo.silkworm_cooked";
    }

    @Override
    public String getUnlocalizedName(ItemStack item) {
        return "exnihilo.silkworm_cooked";
    }

    @Override
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon("exnihilo:SilkwormCooked");
    }
}
