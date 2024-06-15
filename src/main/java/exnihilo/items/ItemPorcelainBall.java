package exnihilo.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemPorcelainBall extends Item {

    public ItemPorcelainBall() {
        setCreativeTab(CreativeTabs.tabMaterials);
    }

    @Override
    public String getUnlocalizedName() {
        return "exnihilo.porcelain";
    }

    @Override
    public String getUnlocalizedName(ItemStack item) {
        return "exnihilo.porcelain";
    }

    @Override
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon("exnihilo:ItemPorcelainBall");
    }
}
