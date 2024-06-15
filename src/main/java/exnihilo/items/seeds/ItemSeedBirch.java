package exnihilo.items.seeds;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;

public class ItemSeedBirch extends ItemSeedBase {

    public ItemSeedBirch() {
        super(Blocks.sapling, Blocks.dirt);
    }

    @Override
    public Block getPlant(IBlockAccess world, int x, int y, int z) {
        return Blocks.sapling;
    }

    @Override
    public int getPlantMetadata(IBlockAccess world, int x, int y, int z) {
        return 2;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
        return EnumPlantType.Plains;
    }

    @Override
    public String getUnlocalizedName() {
        return "exnihilo.seed_birch";
    }

    @Override
    public String getUnlocalizedName(ItemStack item) {
        return "exnihilo.seed_birch";
    }

    @Override
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon("exnihilo:ItemSeedBirch");
    }
}
