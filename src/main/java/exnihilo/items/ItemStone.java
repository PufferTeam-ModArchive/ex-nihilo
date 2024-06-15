package exnihilo.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import exnihilo.entities.EntityStone;

public class ItemStone extends Item {

    public ItemStone() {
        setCreativeTab(CreativeTabs.tabMaterials);
    }

    @Override
    public String getUnlocalizedName() {
        return "exnihilo.stone";
    }

    @Override
    public String getUnlocalizedName(ItemStack item) {
        return "exnihilo.stone";
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
        if (!player.capabilities.isCreativeMode) item.stackSize--;
        world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        if (!world.isRemote) world.spawnEntityInWorld(new EntityStone(world, player));
        return item;
    }

    @Override
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon("exnihilo:IconStone");
    }
}
