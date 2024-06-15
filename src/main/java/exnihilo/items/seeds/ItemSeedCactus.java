package exnihilo.items.seeds;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;

public class ItemSeedCactus extends ItemSeedBase {

    public ItemSeedCactus() {
        super(Blocks.cactus, Blocks.dirt);
    }

    @Override
    public Block getPlant(IBlockAccess world, int x, int y, int z) {
        return Blocks.cactus;
    }

    @Override
    public int getPlantMetadata(IBlockAccess world, int x, int y, int z) {
        return 0;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
        return EnumPlantType.Desert;
    }

    @Override
    public String getUnlocalizedName() {
        return "exnihilo.seed_cactus";
    }

    @Override
    public String getUnlocalizedName(ItemStack item) {
        return "exnihilo.seed_cactus";
    }

    @Override
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon("exnihilo:ItemSeedCactus");
    }
}
