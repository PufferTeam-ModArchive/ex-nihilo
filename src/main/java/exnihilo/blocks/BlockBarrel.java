package exnihilo.blocks;

import java.lang.reflect.Constructor;
import java.util.List;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import exnihilo.ENItems;
import exnihilo.ExNihilo;
import exnihilo.blocks.tileentities.TileEntityBarrel;
import exnihilo.config.BarrelConfig;
import exnihilo.registries.BarrelRecipeRegistry;
import exnihilo.registries.CompostRegistry;
import exnihilo.registries.helpers.EntityWithItem;
import exnihilo.utils.ItemInfo;

public class BlockBarrel extends BlockContainer {

    @SideOnly(Side.CLIENT)
    public static IIcon iconCompost;

    public static IIcon iconClouds;

    @SideOnly(Side.CLIENT)
    private IIcon[] woodIcon;

    String[] woods;
    String mod;

    public BlockBarrel(String modName, String[] woodTypes, String key) {
        super(Material.wood);
        setCreativeTab(CreativeTabs.tabDecorations);
        setHardness(2.0F);
        setStepSound(soundTypeWood);
        setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 1.0F, 0.9F);
        setBlockName(ExNihilo.MODID + "." + key);
        GameRegistry.registerTileEntity(TileEntityBarrel.class, ExNihilo.MODID + "." + key);

        woods = woodTypes;
        mod = modName;
    }

    public BlockBarrel(Material material) {
        super(material);
    }

    @SideOnly(Side.CLIENT)
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void getSubBlocks(Item item, CreativeTabs tabs, List subItems) {
        for (int i = 0; i < woods.length; i++) subItems.add(new ItemStack(item, 1, i));
    }

    @Override
    public int damageDropped(int metadata) {
        return metadata;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityBarrel();
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
        int meta = world.getBlockMetadata(x, y, z);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7,
            float par8, float par9) {
        if (player == null) return false;
        TileEntityBarrel barrel = (TileEntityBarrel) world.getTileEntity(x, y, z);
        if ((barrel.getMode()).canExtract == TileEntityBarrel.ExtractMode.Always
                || (world.difficultySetting.getDifficultyId() == 0
                        && (barrel.getMode()).canExtract == TileEntityBarrel.ExtractMode.PeacefulOnly)) {
            barrel.giveAppropriateItem();
        } else if (player.getCurrentEquippedItem() != null) {
            ItemStack item = player.getCurrentEquippedItem();
            if (item != null) {
                if (BarrelConfig.enableBarrelRecipeDirt) if (barrel.getMode() == TileEntityBarrel.BarrelMode.EMPTY
                        || (barrel.getMode() == TileEntityBarrel.BarrelMode.COMPOST && !barrel.isFull()))
                    if (CompostRegistry.containsItem(item.getItem(), item.getItemDamage())) {
                        barrel.addCompostItem(CompostRegistry.getItem(item.getItem(), item.getItemDamage()));
                        if (!player.capabilities.isCreativeMode) {
                            item.stackSize--;
                            if (item.stackSize == 0) item = null;
                        }
                    }
                if (barrel.getMode() == TileEntityBarrel.BarrelMode.EMPTY
                        || barrel.getMode() == TileEntityBarrel.BarrelMode.FLUID) {
                    FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(item);
                    if (fluid != null) {
                        int capacity = barrel.fill(ForgeDirection.UP, fluid, false);
                        if (capacity > 0) {
                            barrel.fill(ForgeDirection.UP, fluid, true);
                            if (!player.capabilities.isCreativeMode)
                                if (item.getItem() == Items.potionitem && item.getItemDamage() == 0) {
                                    player.inventory.setInventorySlotContents(
                                            player.inventory.currentItem,
                                            new ItemStack(Items.glass_bottle, 1, 0));
                                } else {
                                    player.inventory
                                            .setInventorySlotContents(player.inventory.currentItem, getContainer(item));
                                }
                        }
                    } else if (FluidContainerRegistry.isContainer(item)) {
                        FluidStack available = barrel.drain(ForgeDirection.DOWN, 2147483647, false);
                        if (available != null) {
                            ItemStack filled = FluidContainerRegistry.fillFluidContainer(available, item);
                            FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(filled);
                            if (liquid != null) {
                                if (item.stackSize > 1) {
                                    if (!player.inventory.addItemStackToInventory(filled)) return false;
                                    item.stackSize--;
                                } else {
                                    player.inventory.setInventorySlotContents(player.inventory.currentItem, filled);
                                }
                                barrel.drain(ForgeDirection.DOWN, liquid.amount, true);
                                return true;
                            }
                        }
                    }
                }
                if (item != null) {
                    ItemInfo output = BarrelRecipeRegistry.getOutput(barrel.fluid, item);
                    if (output != null && barrel.isFull() && barrel.getMode() == TileEntityBarrel.BarrelMode.FLUID) {
                        barrel.outputStack = new ItemStack(output.getItem(), 1, output.getMeta());
                        barrel.setMode(TileEntityBarrel.BarrelMode.RECIPE);
                        useItem(player);
                    }
                    EntityWithItem mob = BarrelRecipeRegistry.getMobOutput(barrel.fluid, item);
                    if (mob != null && barrel.isFull() && barrel.getMode() == TileEntityBarrel.BarrelMode.FLUID) {
                        try {
                            Constructor<EntityLivingBase> constructor = mob.getEntity().getConstructor(World.class);
                            barrel.entity = constructor.newInstance(barrel.getWorldObj());
                        } catch (Exception ignored) {}
                        barrel.entityParticleName = mob.getParticle();
                        barrel.peacefulDrop = mob.getDrops();
                        barrel.setMode(TileEntityBarrel.BarrelMode.MOB);
                        useItem(player);
                    }
                    if (barrel.getMode() == TileEntityBarrel.BarrelMode.FLUID && barrel.isFull())
                        if (barrel.fluid.getFluid().equals(FluidRegistry.WATER)) {
                            if (BarrelConfig.enableBarrelRecipeSlime && item.getItem() == Items.milk_bucket) {
                                barrel.setMode(TileEntityBarrel.BarrelMode.MILKED);
                                useItem(player);
                            }
                            if (BarrelConfig.enableBarrelRecipeSoulsand
                                    && (item.getItem() == Items.mushroom_stew || item.getItem() == ENItems.Spores)) {
                                barrel.setMode(TileEntityBarrel.BarrelMode.SPORED);
                                useItem(player);
                            }
                        }
                }
            }
        }
        return true;
    }

    public void useItem(EntityPlayer player) {
        if (!player.capabilities.isCreativeMode) {
            ItemStack item = player.inventory.mainInventory[player.inventory.currentItem];
            if (item.getItem() == Items.milk_bucket) {
                player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack(Items.bucket, 1);
            } else if (item.getItem() == Items.mushroom_stew) {
                player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack(Items.bowl, 1);
            } else {
                item.stackSize--;
                if (item.stackSize == 0) item = null;
            }
        }
        player.swingItem();
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        woodIcon = new IIcon[woods.length];

        for (int i = 0; i < woods.length; i++) {
            String currentWood = woods[i];
            if (currentWood.equals("hellbark")) {
                currentWood = "hell_bark";
            }
            if (currentWood.equals("dark_oak")) {
                currentWood = "big_oak";
            }
            if (mod.equals("vanilla")) {
                woodIcon[i] = register.registerIcon("minecraft:planks_" + currentWood);
            } else if (mod.equals("bop")) {
                woodIcon[i] = register.registerIcon("biomesoplenty:plank_" + currentWood);
            } else if (mod.equals("thaumcraft")) {
                woodIcon[i] = register.registerIcon("thaumcraft:planks_" + currentWood);
            } else if (mod.equals("witchery")) {
                woodIcon[i] = register.registerIcon("witchery:planks_" + currentWood);
            }
        }

        iconCompost = register.registerIcon(ExNihilo.MODID + ":" + "IconBarrelCompost");
        iconClouds = register.registerIcon(ExNihilo.MODID + ":" + "IconBarrelInternalClouds");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        return woodIcon[meta];
    }

    public String[] getWoods() {
        return woods;
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean hasTileEntity(int meta) {
        return true;
    }

    private ItemStack getContainer(ItemStack item) {
        if (item.stackSize == 1) {
            if (item.getItem().hasContainerItem(item)) return item.getItem().getContainerItem(item);
            return null;
        }
        item.splitStack(1);
        return item;
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        TileEntityBarrel te = (TileEntityBarrel) world.getTileEntity(x, y, z);
        if (te != null) return te.getLightLevel();
        return 0;
    }
}
