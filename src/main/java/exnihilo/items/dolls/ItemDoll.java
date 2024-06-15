package exnihilo.items.dolls;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemDoll extends Item {

    public ItemDoll() {
        setCreativeTab(CreativeTabs.tabMisc);
    }

    @Override
    public String getUnlocalizedName() {
        return "exnihilo.doll";
    }

    @Override
    public String getUnlocalizedName(ItemStack item) {
        return "exnihilo.doll";
    }

    @Override
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon("exnihilo:ItemDoll");
    }
}
