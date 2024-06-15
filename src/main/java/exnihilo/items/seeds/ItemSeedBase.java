package exnihilo.items.seeds;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemSeedBase extends Item implements IPlantable {

    private final Block blockType;

    private final Block soilBlock;

    public ItemSeedBase(Block par2, Block par3) {
        this.blockType = par2;
        this.soilBlock = par3;
        setCreativeTab(CreativeTabs.tabMaterials);
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4,
            int par5, int par6, int par7, float par8, float par9, float par10) {
        if (par7 != 1) return false;
        if (par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)
                && par2EntityPlayer.canPlayerEdit(par4, par5 + 1, par6, par7, par1ItemStack)) {
            Block soil = par3World.getBlock(par4, par5, par6);
            if (soil != null && soil.canSustainPlant(par3World, par4, par5, par6, ForgeDirection.UP, this)
                    && par3World.isAirBlock(par4, par5 + 1, par6)
                    && getPlant(par3World, par4, par5, par6) != null
                    && getPlant(par3World, par4, par5, par6).canBlockStay(par3World, par4, par5 + 1, par6)) {
                par3World.setBlock(
                        par4,
                        par5 + 1,
                        par6,
                        getPlant(par3World, par4, par5, par6),
                        getPlantMetadata(par3World, par4, par5, par6),
                        3);
                par1ItemStack.stackSize--;
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
        return (this.blockType == Blocks.nether_wart) ? EnumPlantType.Nether : EnumPlantType.Crop;
    }

    @Override
    public Block getPlant(IBlockAccess world, int x, int y, int z) {
        return this.blockType;
    }

    @Override
    public int getPlantMetadata(IBlockAccess world, int x, int y, int z) {
        return 0;
    }
}
