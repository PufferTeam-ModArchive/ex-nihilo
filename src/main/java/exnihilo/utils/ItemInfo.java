package exnihilo.utils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Objects;

public class ItemInfo {

    public static final ItemInfo EMPTY = new ItemInfo((Item) null, 0);
  private Item item;

  private int meta;

  public void setItem(Item item) {
    this.item = item;
  }

  public void setMeta(int meta) {
    this.meta = meta;
  }

  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof ItemInfo other))
      return false;
  if (!other.canEqual(this))
      return false;
    Object this$item = getItem(), other$item = other.getItem();
    return (Objects.equals(this$item, other$item)) && (getMeta() == other.getMeta());
  }

  protected boolean canEqual(Object other) {
    return other instanceof ItemInfo;
  }

  public int hashCode() {
    int result = 1;
    Object $item = getItem();
    result = result * 59 + (($item == null) ? 0 : $item.hashCode());
    return result * 59 + getMeta();
  }

  public String toString() {
    return "ItemInfo(item=" + getItem() + ", meta=" + getMeta() + ")";
  }

  public Item getItem() {
    return this.item;
  }

  public int getMeta() {
    return this.meta;
  }

  public ItemInfo(ItemStack itemStack) {
    if (itemStack != null && itemStack.getItem() != null) {
      this.item = itemStack.getItem();
      this.meta = itemStack.getItemDamage();
    }
  }

  public ItemInfo(Block block, int meta) {
      this.item = Item.getItemFromBlock(block);
      this.meta = meta;
  }

  public ItemInfo(Item item, int meta) {
      this.item = item;
      this.meta = meta;
  }

  public ItemStack getStack() {
    return new ItemStack(this.item, 1, this.meta);
  }
}
