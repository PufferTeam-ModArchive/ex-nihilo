package exnihilo.items.meshes;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public abstract class ItemMesh extends Item {

    protected IIcon sieveRenderIcon;

    public ItemMesh() {
        setCreativeTab(CreativeTabs.tabMisc);
    }

    @Override
    public String getUnlocalizedName() {
        return "exnihilo." + this.getName();
    }

    @Override
    public String getUnlocalizedName(ItemStack item) {
        return "exnihilo." + this.getName();
    }

    @Override
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon("exnihilo:" + this.getName());
    }

    public void registerSieveRenderIcon(IIconRegister register) {
        this.sieveRenderIcon = register.registerIcon("exnihilo:" + this.getName());
    }

    public abstract String getName();
}
