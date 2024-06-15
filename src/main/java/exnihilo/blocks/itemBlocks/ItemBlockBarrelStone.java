package exnihilo.blocks.itemBlocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockBarrelStone extends ItemBlock {

    public ItemBlockBarrelStone(Block block) {
        super(block);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        return "exnihilo.barrel.stone";
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }
}
