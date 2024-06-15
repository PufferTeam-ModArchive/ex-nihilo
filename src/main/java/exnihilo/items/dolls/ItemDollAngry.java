package exnihilo.items.dolls;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemDollAngry extends Item {

    public ItemDollAngry() {
        setCreativeTab(CreativeTabs.tabMisc);
    }

    @Override
    public String getUnlocalizedName() {
        return "exnihilo.doll_angry";
    }

    @Override
    public String getUnlocalizedName(ItemStack item) {
        return "exnihilo.doll_angry";
    }

    @Override
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon("exnihilo:ItemDollBlaze");
    }
}
