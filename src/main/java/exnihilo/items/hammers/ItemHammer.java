package exnihilo.items.hammers;

import com.google.common.collect.Sets;
import exnihilo.registries.HammerRegistry;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

public class ItemHammer extends ItemTool implements IHammer {
  public static final Set<Object> blocksEffectiveAgainst = Sets.newHashSet(new Block[0]);

  private final HammerType type;

  public ItemHammer(HammerType type) {
      super(3.0F, type.getMaterial(), blocksEffectiveAgainst);
      this.type = type;
  }

  public boolean canItemHarvestBlock(Block block) {
    return HammerRegistry.registered(new ItemStack(block));
  }

  @Override
  public boolean canHarvestBlock(Block block, ItemStack stack) {
    return HammerRegistry.registered(new ItemStack(block));
  }

  @Override
  public int getHarvestLevel(ItemStack stack, String toolClass) {
    return this.toolMaterial.getHarvestLevel();
  }

  @Override
  public float getDigSpeed(ItemStack item, Block block, int meta) {
    if (HammerRegistry.registered(new ItemStack(block, 1, meta)) && block.getHarvestLevel(meta) <= this.toolMaterial.getHarvestLevel())
      return this.efficiencyOnProperMaterial * 0.75F;
    return 0.8F;
  }

  @Override
  public boolean isHammer(ItemStack stack) {
    return true;
  }

  @Override
    public String getUnlocalizedName() { return "exnihilo." + type.getName(); }

    @Override
    public String getUnlocalizedName(ItemStack item) { return "exnihilo." + type.getName(); }

    @Override
    public void registerIcons(IIconRegister register) { this.itemIcon = register.registerIcon("exnihilo:" + type.getName()); }
}
