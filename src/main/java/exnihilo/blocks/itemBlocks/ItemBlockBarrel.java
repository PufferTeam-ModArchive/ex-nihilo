package exnihilo.blocks.itemBlocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import exnihilo.blocks.BlockBarrel;

public class ItemBlockBarrel extends ItemBlock {

    String[] woods;

    public ItemBlockBarrel(Block block) {
        super(block);
        setHasSubtypes(true);

        woods = ((BlockBarrel) block).getWoods();
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        return "exnihilo.barrel." + woods[itemstack.getItemDamage()];
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }
}
