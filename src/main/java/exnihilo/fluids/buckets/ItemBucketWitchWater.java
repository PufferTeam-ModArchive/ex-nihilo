package exnihilo.fluids.buckets;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBucketWitchWater extends ItemBucket {

    public ItemBucketWitchWater(Block block) {
        super(block);
        setCreativeTab(CreativeTabs.tabMisc);
        setContainerItem(Items.bucket);
    }

    @Override
    public String getUnlocalizedName() {
        return "exnihilo.bucket_witchwater";
    }

    @Override
    public String getUnlocalizedName(ItemStack item) {
        return "exnihilo.bucket_witchwater";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon("exnihilo:ItemBucketWitchWater");
    }
}
