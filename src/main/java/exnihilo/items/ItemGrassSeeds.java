package exnihilo.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGrassSeeds extends Item {

    public ItemGrassSeeds() {
        setCreativeTab(CreativeTabs.tabMaterials);
    }

    @Override
    public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int par7,
            float par8, float par9, float par10) {
        if (world.getBlock(x, y, z) == Blocks.dirt) {
            world.setBlock(x, y, z, Blocks.grass, 0, 3);
            item.stackSize--;
            if (item.stackSize <= 0) player.destroyCurrentEquippedItem();
            return true;
        }
        return false;
    }

    @Override
    public String getUnlocalizedName() {
        return "exnihilo.seed_grass";
    }

    @Override
    public String getUnlocalizedName(ItemStack item) {
        return "exnihilo.seed_grass";
    }

    @Override
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon("exnihilo:IconGrassSeed");
    }
}
