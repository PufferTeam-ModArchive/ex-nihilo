package exnihilo.items.crooks;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.google.common.collect.Sets;

import exnihilo.config.CrookConfig;
import exnihilo.proxies.Proxy;
import exnihilo.utils.CrookUtils;

public class ItemCrook extends ItemTool {

    public static final double pullingForce = 1.5D;

    public static final double pushingForce = 1.5D;

    public static final Set<Object> blocksEffectiveAgainst = Sets.newHashSet(new Block[0]);

    private final CrookType type;

    public ItemCrook(CrookType type) {

        super(0.0F, type.getMaterial(), blocksEffectiveAgainst);
        this.type = type;
        setMaxDamage(type.getDurability());
    }

    public boolean canItemHarvestBlock(Block block) {
        return block.isLeaves(Proxy.getProxy().getWorldObj(), 0, 0, 0);
    }

    public float getStrVsBlock(ItemStack item, Block block) {
        if (block.isLeaves(Proxy.getProxy().getWorldObj(), 0, 0, 0)) return this.efficiencyOnProperMaterial + 1.0F;
        return 1.0F;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack item, int X, int Y, int Z, EntityPlayer player) {
        CrookUtils.doCrooking(item, X, Y, Z, player);
        return false;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack item, EntityPlayer player, Entity entity) {
        if (!player.worldObj.isRemote) {
            double distance = Math
                    .sqrt(Math.pow(player.posX - entity.posX, 2.0D) + Math.pow(player.posZ - entity.posZ, 2.0D));
            double scalarX = (player.posX - entity.posX) / distance;
            double scalarZ = (player.posZ - entity.posZ) / distance;
            double velX = 0.0D - scalarX * 1.5D;
            double velZ = 0.0D - scalarZ * 1.5D;
            double velY = 0.0D;
            entity.addVelocity(velX, velY, velZ);
        }
        item.damageItem(1, player);
        return true;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack item, EntityPlayer player, EntityLivingBase entity) {
        double distance = Math
                .sqrt(Math.pow(player.posX - entity.posX, 2.0D) + Math.pow(player.posZ - entity.posZ, 2.0D));
        double scalarX = (player.posX - entity.posX) / distance;
        double scalarZ = (player.posZ - entity.posZ) / distance;
        double velX = scalarX * 1.5D;
        double velZ = scalarZ * 1.5D;
        double velY = 0.0D;
        entity.addVelocity(velX, velY, velZ);
        item.damageItem(1, player);
        return true;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
            float hitX, float hitY, float hitZ) {
        if (!world.isRemote && world.getBlock(x, y, z) == Blocks.dirt
                && world.getBlock(x, y + 1, z) == Blocks.water
                && ForgeDirection.getOrientation(side) == ForgeDirection.UP
                && CrookConfig.lilypadChance > 0) {
            if (world.rand.nextInt(CrookConfig.lilypadChance) == 0) {
                EntityItem item = new EntityItem(world, x, (y + 1), z, new ItemStack(Blocks.waterlily));
                world.spawnEntityInWorld(item);
            }
            stack.damageItem(1, player);
            return true;
        }
        return false;
    }

    @Override
    public String getUnlocalizedName() {
        return "exnihilo." + type.getName();
    }

    @Override
    public String getUnlocalizedName(ItemStack item) {
        return "exnihilo." + type.getName();
    }

    @Override
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon("exnihilo:" + type.getName());
    }
}
