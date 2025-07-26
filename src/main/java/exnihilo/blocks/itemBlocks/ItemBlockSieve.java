package exnihilo.blocks.itemBlocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import exnihilo.blocks.BlockSieve;

public class ItemBlockSieve extends ItemBlock {

    String[] woods;

    public ItemBlockSieve(Block block) {
        super(block);
        setHasSubtypes(true);

        woods = ((BlockSieve) block).getWoods();
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        return "exnihilo.sieve." + woods[itemstack.getItemDamage()];
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }
}
