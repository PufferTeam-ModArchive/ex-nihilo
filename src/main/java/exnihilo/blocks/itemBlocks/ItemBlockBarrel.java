package exnihilo.blocks.itemBlocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import exnihilo.data.BlockData;

public class ItemBlockBarrel extends ItemBlock {

    public ItemBlockBarrel(Block block) {
        super(block);
        setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        return "exnihilo." + BlockData.BARREL_UNLOCALIZED_NAMES[itemstack.getItemDamage()];
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }
}
