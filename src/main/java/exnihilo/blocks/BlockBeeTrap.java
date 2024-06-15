package exnihilo.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import exnihilo.ExNihilo;
import exnihilo.data.BlockData;

public class BlockBeeTrap extends Block {

    public static IIcon topIcon;

    public static IIcon sideIcon;

    public BlockBeeTrap() {
        super(Material.ground);
        setHardness(0.8F);
        setStepSound(soundTypeGrass);
        setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public String getUnlocalizedName() {
        return ExNihilo.MODID + "." + BlockData.BEE_TRAP_UNLOCALIZED_NAME;
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        topIcon = register.registerIcon(ExNihilo.MODID + ":IconBeeTrapTopRaw");
        sideIcon = register.registerIcon(ExNihilo.MODID + ":IconBeeTrapSideRaw");
        this.blockIcon = sideIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (side == 0 || side == 1) return topIcon;
        return sideIcon;
    }
}
