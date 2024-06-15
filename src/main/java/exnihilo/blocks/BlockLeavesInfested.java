package exnihilo.blocks;

import java.util.Random;

import exnihilo.ExNihilo;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import exnihilo.blocks.tileentities.TileEntityLeavesInfested;
import exnihilo.config.CrookConfig;
import exnihilo.data.BlockData;
import exnihilo.registries.ColorRegistry;

public class BlockLeavesInfested extends BlockLeaves implements ITileEntityProvider {

    int[] adjacentTreeBlocks;

    public BlockLeavesInfested() {
        this.isBlockContainer = true;
        setHardness(0.4F);
        setLightOpacity(1);
        setStepSound(soundTypeGrass);
        setBlockName(ExNihilo.MODID + "." + BlockData.LEAVES_INFESTED_KEY);
        GameRegistry.registerTileEntity(TileEntityLeavesInfested.class, getUnlocalizedName());
    }

    @Override
    public void beginLeavesDecay(World world, int x, int y, int z) {
        TileEntityLeavesInfested te = (TileEntityLeavesInfested) world.getTileEntity(x, y, z);
        if (te != null) te.dying = true;
    }

    @Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
        if (!par1World.isRemote) {
            TileEntityLeavesInfested te = (TileEntityLeavesInfested) par1World.getTileEntity(par2, par3, par4);
            if (te != null && !te.permanent && te.dying) {
                byte b0 = 4;
                int i1 = b0 + 1;
                byte b1 = 32;
                int j1 = b1 * b1;
                int k1 = b1 / 2;
                if (this.adjacentTreeBlocks == null) this.adjacentTreeBlocks = new int[b1 * b1 * b1];
                if (par1World.checkChunksExist(par2 - i1, par3 - i1, par4 - i1, par2 + i1, par3 + i1, par4 + i1)) {
                    int i;
                    for (i = -b0; i <= b0; i++) {
                        for (int i2 = -b0; i2 <= b0; i2++) {
                            for (int j2 = -b0; j2 <= b0; j2++) {
                                Block block = par1World.getBlock(par2 + i, par3 + i2, par4 + j2);
                                if (block != null
                                        && block.canSustainLeaves(par1World, par2 + i, par3 + i2, par4 + j2)) {
                                    this.adjacentTreeBlocks[(i + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = 0;
                                } else if (block != null && block.isLeaves(par1World, par2 + i, par3 + i2, par4 + j2)) {
                                    this.adjacentTreeBlocks[(i + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = -2;
                                } else {
                                    this.adjacentTreeBlocks[(i + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = -1;
                                }
                            }
                        }
                    }
                    for (i = 1; i <= 4; i++) {
                        for (int i2 = -b0; i2 <= b0; i2++) {
                            for (int j2 = -b0; j2 <= b0; j2++) {
                                for (int k2 = -b0; k2 <= b0; k2++) {
                                    if (this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1] == i - 1) {
                                        if (this.adjacentTreeBlocks[(i2 + k1 - 1) * j1 + (j2 + k1) * b1 + k2 + k1]
                                                == -2)
                                            this.adjacentTreeBlocks[(i2 + k1 - 1) * j1 + (j2 + k1) * b1 + k2 + k1] = i;
                                        if (this.adjacentTreeBlocks[(i2 + k1 + 1) * j1 + (j2 + k1) * b1 + k2 + k1]
                                                == -2)
                                            this.adjacentTreeBlocks[(i2 + k1 + 1) * j1 + (j2 + k1) * b1 + k2 + k1] = i;
                                        if (this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1 - 1) * b1 + k2 + k1]
                                                == -2)
                                            this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1 - 1) * b1 + k2 + k1] = i;
                                        if (this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1 + 1) * b1 + k2 + k1]
                                                == -2)
                                            this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1 + 1) * b1 + k2 + k1] = i;
                                        if (this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 - 1]
                                                == -2)
                                            this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 - 1] = i;
                                        if (this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 + 1]
                                                == -2)
                                            this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 + 1] = i;
                                    }
                                }
                            }
                        }
                    }
                }
                int l1 = this.adjacentTreeBlocks[k1 * j1 + k1 * b1 + k1];
                if (l1 >= 0) {
                    te.dying = false;
                } else {
                    par1World.setBlockToAir(par2, par3, par4);
                }
            }
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        this.blockIcon = Blocks.leaves.getIcon(0, 0);
    }

    @Override
    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int par5, float par6, int par7) {}

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
        TileEntityLeavesInfested leaves = (TileEntityLeavesInfested) world.getTileEntity(x, y, z);
        if (leaves != null) return leaves.color.toInt();
        return ColorRegistry.color("white").toInt();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int par1, int par2) {
        return this.blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int par1) {
        return ColorRegistry.color("white").toInt();
    }

    @Override
    public int damageDropped(int par1) {
        return 0;
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
    public void onBlockAdded(World par1World, int par2, int par3, int par4) {
        super.onBlockAdded(par1World, par2, par3, par4);
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z) {
        if (!world.isRemote) {
            TileEntityLeavesInfested leaves = (TileEntityLeavesInfested) world.getTileEntity(x, y, z);
            if (leaves != null) {
                if (world.rand.nextFloat() < leaves.getProgress() * (float) CrookConfig.infestedLeafStringChance)
                    dropBlockAsItem(world, x, y, z, new ItemStack(Items.string, 1, 0));
                if (world.rand.nextFloat()
                        < leaves.getProgress() * (float) (CrookConfig.infestedLeafStringChance / 4.0D))
                    dropBlockAsItem(world, x, y, z, new ItemStack(Items.string, 1, 0));
            }
        }
        return world.setBlockToAir(x, y, z);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block par5, int par6) {
        super.breakBlock(world, x, y, z, par5, par6);
        world.removeTileEntity(x, y, z);
    }

    @Override
    public boolean onBlockEventReceived(World par1World, int par2, int par3, int par4, int par5, int par6) {
        super.onBlockEventReceived(par1World, par2, par3, par4, par5, par6);
        TileEntity tileentity = par1World.getTileEntity(par2, par3, par4);
        return tileentity != null && tileentity.receiveClientEvent(par5, par6);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityLeavesInfested();
    }

    @Override
    public String[] func_150125_e() {
        return new String[] { "infested" };
    }
}
