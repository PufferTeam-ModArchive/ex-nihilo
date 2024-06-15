package exnihilo.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSpores extends Item {

    public ItemSpores() {
        setCreativeTab(CreativeTabs.tabMaterials);
    }

    @Override
    public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int par7,
            float par8, float par9, float par10) {
        if (world.getBlock(x, y, z) == Blocks.dirt) {
            world.setBlock(x, y, z, Blocks.mycelium, 0, 3);
            item.stackSize--;
            if (item.stackSize <= 0) player.destroyCurrentEquippedItem();
            return true;
        }
        return false;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack item, EntityPlayer player, EntityLivingBase entity) {
        if (!entity.worldObj.isRemote && entity instanceof net.minecraft.entity.passive.EntityCow) {
            entity.setDead();
            EntityMooshroom mooshroom = new EntityMooshroom(entity.worldObj);
            mooshroom.setLocationAndAngles(
                    entity.posX,
                    entity.posY,
                    entity.posZ,
                    entity.rotationYaw,
                    entity.rotationPitch);
            mooshroom.setHealth(entity.getHealth());
            mooshroom.renderYawOffset = entity.renderYawOffset;
            entity.worldObj.spawnEntityInWorld(mooshroom);
            entity.worldObj.spawnParticle(
                    "largeexplode",
                    entity.posX,
                    entity.posY + (entity.height / 2.0F),
                    entity.posZ,
                    0.0D,
                    0.0D,
                    0.0D);
            item.stackSize--;
            if (item.stackSize <= 0) player.destroyCurrentEquippedItem();
            return true;
        }
        return false;
    }

    @Override
    public String getUnlocalizedName() {
        return "exnihilo.spores";
    }

    @Override
    public String getUnlocalizedName(ItemStack item) {
        return "exnihilo.spores";
    }

    @Override
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon("exnihilo:IconSpores");
    }
}
