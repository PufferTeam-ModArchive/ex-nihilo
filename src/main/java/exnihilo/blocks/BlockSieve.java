package exnihilo.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import exnihilo.blocks.tileentities.TileEntitySieve;
import exnihilo.data.BlockData;
import exnihilo.data.ModData;
import exnihilo.items.meshes.ItemMesh;
import exnihilo.items.meshes.MeshType;
import exnihilo.registries.SieveRegistry;

import java.util.ArrayList;
import java.util.List;

import exnihilo.registries.helpers.SiftingResult;
import exnihilo.utils.BlockInfo;
import exnihilo.utils.ItemInfo;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

public class BlockSieve extends BlockContainer {
    public static final int SIEVE_RADIUS = 1;

    public BlockSieve() {
        super(Material.wood);
        setCreativeTab(CreativeTabs.tabDecorations);
        setHardness(2.0F);
        setBlockName(ModData.ID + "." + BlockData.SIEVE_KEY);
        GameRegistry.registerTileEntity(TileEntitySieve.class, ModData.ID + "." + BlockData.SIEVE_KEY);
    }

    public BlockSieve(Material material) {
        super(material);
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        for (MeshType mesh : MeshType.getValues()) {
            mesh.registerMeshRenderIcon(register);
        }
        this.blockIcon = Blocks.planks.getIcon(0, 0);
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tabs, List subItems) {
        for (int i = 0; i < 6; i++)
            subItems.add(new ItemStack(item, 1, i));
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
    public int damageDropped(int metadata) {
        return metadata;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntitySieve();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (world.isRemote)
            return true;

        if (!isHuman(player) && !ModData.ALLOW_SIEVE_AUTOMATION)
            return false;

        TileEntitySieve sieve = (TileEntitySieve) world.getTileEntity(x, y, z);

        if (sieve == null)
            return true;

        ItemStack held = player.getCurrentEquippedItem();

        if (!ModData.LEGACY_SIEVE) {
            if (held == null && sieve.getMeshType() != MeshType.NONE && player.isSneaking() && sieve.getCurrentStack() == BlockInfo.EMPTY) {
                EntityItem entityItem = new EntityItem(world, sieve.xCoord + 0.5D, sieve.yCoord + 1.5D, sieve.zCoord + 0.5D, new ItemStack(MeshType.getItemForType(sieve.getMeshType()), 1, 0));
                sieve.setMeshType(MeshType.NONE);
                world.spawnEntityInWorld(entityItem);
                return true;
            }
        }

        if (sieve.getCurrentStack() == BlockInfo.EMPTY && held != null) {
            //Handle inserting mesh
            if (sieve.getMeshType() == MeshType.NONE && held.getItem() instanceof ItemMesh) {
                sieve.setMeshType(((ItemMesh) held.getItem()).getType());
                removeCurrentItem(player);
                return true;
            }
            if (sieve.getMeshType() == MeshType.NONE)
                return true;
            ArrayList<SiftingResult> result = SieveRegistry.getSiftingOutput(new ItemInfo(held), sieve.getMeshType());
            if (result != null) {
                    outerloop:
                    for (int dx = -SIEVE_RADIUS; dx <= SIEVE_RADIUS; dx++) {
                        for (int dz = -SIEVE_RADIUS; dz <= SIEVE_RADIUS; dz++) {
                            if (held == null)
                                break outerloop; // ran out of items
                            final TileEntity otherTE = world.getTileEntity(x + dx, y, z + dz);
                            if (!(otherTE instanceof TileEntitySieve otherSieve))
                                continue; // Not a sieve

                            if (otherSieve.getCurrentStack() == BlockInfo.EMPTY && otherSieve.getMeshType() == sieve.getMeshType()) {
                                otherSieve.addSievable(Block.getBlockFromItem(held.getItem()), held.getItemDamage());
                                held = removeCurrentItem(player);
                            }
                        }
                    }
            }
            return true;
        }

        for (int dx = -SIEVE_RADIUS; dx <= SIEVE_RADIUS; dx++) {
            for (int dz = -SIEVE_RADIUS; dz <= SIEVE_RADIUS; dz++) {
                final TileEntity te = world.getTileEntity(x + dx, y, z + dz);
                if ((te instanceof TileEntitySieve teSieve) && teSieve.getCurrentStack() != BlockInfo.EMPTY && teSieve.getMeshType() == sieve.getMeshType()) {
                    teSieve.ProcessContents();
                }
            }
        }

        return true;
    }

    private boolean isHuman(EntityPlayer player) {
        return player instanceof net.minecraft.entity.player.EntityPlayerMP && !(player instanceof FakePlayer);
    }

    private ItemStack removeCurrentItem(EntityPlayer player) {
        ItemStack item = player.getCurrentEquippedItem();
        if (!player.capabilities.isCreativeMode) {
            item.stackSize--;
            if (item.stackSize == 0) item = null;
        }
        return item;
    }
}
