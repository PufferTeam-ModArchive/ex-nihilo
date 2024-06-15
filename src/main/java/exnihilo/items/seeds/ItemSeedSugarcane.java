package exnihilo.items.seeds;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;

public class ItemSeedSugarcane extends ItemSeedBase {

    public ItemSeedSugarcane() {
        super(Blocks.reeds, Blocks.dirt);
    }

    @Override
    public Block getPlant(IBlockAccess world, int x, int y, int z) {
        return Blocks.reeds;
    }

    @Override
    public int getPlantMetadata(IBlockAccess world, int x, int y, int z) {
        return 0;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
        return EnumPlantType.Beach;
    }

    @Override
    public String getUnlocalizedName() {
        return "exnihilo.seed_sugar_cane";
    }

    @Override
    public String getUnlocalizedName(ItemStack item) {
        return "exnihilo.seed_sugar_cane";
    }

    @Override
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon("exnihilo:ItemSeedSugarcane");
    }
}
