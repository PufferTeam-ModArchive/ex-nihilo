package exnihilo.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import exnihilo.ExNihilo;
import exnihilo.blocks.tileentities.TileEntityBeeTrap;
import exnihilo.data.BlockData;

public class BlockBeeTrapTreated extends BlockContainer {

    public static IIcon topIcon;

    public static IIcon sideIcon;

    public BlockBeeTrapTreated() {
        super(Material.ground);
        setHardness(1.0F);
        setStepSound(soundTypeGrass);
        setCreativeTab(CreativeTabs.tabBlock);
        GameRegistry.registerTileEntity(TileEntityBeeTrap.class, getUnlocalizedName());
    }

    @Override
    public String getUnlocalizedName() {
        return ExNihilo.MODID + "." + BlockData.BEE_TRAP_TREATED_UNLOCALIZED_NAME;
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        topIcon = register.registerIcon(ExNihilo.MODID + ":IconBeeTrapTopTreated");
        sideIcon = register.registerIcon(ExNihilo.MODID + ":IconBeeTrapSideTreated");
        this.blockIcon = sideIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (side == 0 || side == 1) return topIcon;
        return sideIcon;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityBeeTrap();
    }
}
