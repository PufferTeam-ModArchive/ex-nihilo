package exnihilo.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import cpw.mods.fml.common.registry.GameRegistry;
import exnihilo.ExNihilo;
import exnihilo.blocks.tileentities.TileEntityCrucible;
import exnihilo.data.BlockData;

public class BlockCrucible extends BlockContainer {

    public BlockCrucible() {
        super(Material.rock);
        setCreativeTab(CreativeTabs.tabDecorations);
        setHardness(2.0F);
        setBlockName(ExNihilo.MODID + "." + BlockData.CRUCIBLE_KEY);
        GameRegistry.registerTileEntity(TileEntityCrucible.class, getUnlocalizedName());
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        this.blockIcon = Blocks.stone.getIcon(0, 0);
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
    public boolean hasTileEntity() {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityCrucible();
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        TileEntityCrucible te = (TileEntityCrucible) world.getTileEntity(x, y, z);
        return te.getLightLevel();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7,
            float par8, float par9) {
        if (player == null) return false;
        TileEntityCrucible crucible = (TileEntityCrucible) world.getTileEntity(x, y, z);
        if (player.getCurrentEquippedItem() != null) {
            ItemStack item = player.getCurrentEquippedItem();
            if (item != null) if (crucible.addItem(item) && !player.capabilities.isCreativeMode) {
                item.stackSize--;
                if (item.stackSize == 0) item = null;
            }
            FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(item);
            if (fluid != null) {
                int capacity = crucible.fill(ForgeDirection.UP, fluid, false);
                if (capacity > 0) {
                    crucible.fill(ForgeDirection.UP, fluid, true);
                    if (!player.capabilities.isCreativeMode)
                        if (item.getItem() == Items.potionitem && item.getItemDamage() == 0) {
                            player.inventory.setInventorySlotContents(
                                    player.inventory.currentItem,
                                    new ItemStack(Items.glass_bottle, 1, 0));
                        } else {
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, getContainer(item));
                        }
                }
            } else if (FluidContainerRegistry.isContainer(item)) {
                FluidStack available = crucible.drain(ForgeDirection.DOWN, 2147483647, false);
                if (available != null) {
                    ItemStack filled = FluidContainerRegistry.fillFluidContainer(available, item);
                    if (filled != null) {
                        FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(filled);
                        if (liquid != null) {
                            if (item.stackSize > 1) {
                                boolean added = player.inventory.addItemStackToInventory(filled);
                                if (!added) return false;
                                item.stackSize--;
                            } else {
                                player.inventory.setInventorySlotContents(player.inventory.currentItem, filled);
                            }
                            crucible.drain(ForgeDirection.DOWN, liquid.amount, true);
                            return true;
                        }
                    }
                }
            }
        }
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
}
