package exnihilo.blocks.itemBlocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockCrucibleUnfired extends ItemBlock {

    public ItemBlockCrucibleUnfired(Block block) {
        super(block);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        return "exnihilo.crucible_unfired";
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }
}
