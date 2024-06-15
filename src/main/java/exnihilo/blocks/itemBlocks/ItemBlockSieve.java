package exnihilo.blocks.itemBlocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import exnihilo.data.BlockData;

public class ItemBlockSieve extends ItemBlock {

    public ItemBlockSieve(Block block) {
        super(block);
        setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        return "exnihilo." + BlockData.SIEVE_UNLOCALIZED_NAMES[itemstack.getItemDamage()];
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }
}
