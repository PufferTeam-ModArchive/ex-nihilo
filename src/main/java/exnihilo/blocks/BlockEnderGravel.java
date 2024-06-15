package exnihilo.blocks;

import java.util.List;

import net.minecraft.block.BlockGravel;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import exnihilo.ExNihilo;

public class BlockEnderGravel extends BlockGravel {

    public BlockEnderGravel() {
        setHardness(0.6F);
        setStepSound(soundTypeGravel);
    }

    @Override
    public String getUnlocalizedName() {
        return ExNihilo.MODID + ".gravel_ender";
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        this.blockIcon = register.registerIcon(ExNihilo.MODID + ":IconEnderGravel");
    }

    @SideOnly(Side.CLIENT)
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void getSubBlocks(Item item, CreativeTabs tabs, List items) {
        items.add(new ItemStack(item, 1, 0));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int id, int meta) {
        return this.blockIcon;
    }
}
