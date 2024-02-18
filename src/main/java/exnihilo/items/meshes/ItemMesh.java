package exnihilo.items.meshes;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ItemMesh extends Item {

    private final MeshType type;

  public ItemMesh(@Nonnull MeshType type) {
      this.type = type;
      setCreativeTab(CreativeTabs.tabMisc);
  }

  @Override
  public String getUnlocalizedName() {
    return "exnihilo." + type.getName();
  }

  @Override
  public String getUnlocalizedName(ItemStack item) {
    return "exnihilo." + type.getName();
  }

  public MeshType getType() {
      return this.type;
  }

  @Override
  public void registerIcons(IIconRegister register) {
    this.itemIcon = register.registerIcon("exnihilo:" + type.getName());
  }
}
