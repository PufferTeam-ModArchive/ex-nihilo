package exnihilo.blocks;

import java.util.ArrayList;
import java.util.List;

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
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import exnihilo.ENItems;
import exnihilo.ExNihilo;
import exnihilo.api.items.IMesh;
import exnihilo.blocks.tileentities.TileEntitySieve;
import exnihilo.config.SieveConfig;
import exnihilo.registries.SieveRegistry;
import exnihilo.registries.helpers.SiftingResult;
import exnihilo.utils.BlockInfo;
import exnihilo.utils.ItemInfo;

public class BlockSieve extends BlockContainer {

    public static final int SIEVE_RADIUS = 1;

    @SideOnly(Side.CLIENT)
    private IIcon[] woodIcon;

    String[] woods;
    String mod;

    public BlockSieve(String modName, String[] woodTypes, String key) {
        super(Material.wood);
        setCreativeTab(CreativeTabs.tabDecorations);
        setHardness(2.0F);
        setStepSound(soundTypeWood);
        setBlockName(ExNihilo.MODID + "." + key);
        GameRegistry.registerTileEntity(TileEntitySieve.class, ExNihilo.MODID + "." + key);

        woods = woodTypes;
        mod = modName;
    }

    public BlockSieve(Material material) {
        super(material);
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

        ENItems.MeshSilk.registerSieveRenderIcon(register);
        ENItems.MeshFlint.registerSieveRenderIcon(register);
        ENItems.MeshIron.registerSieveRenderIcon(register);
        ENItems.MeshDiamond.registerSieveRenderIcon(register);
        this.blockIcon = Blocks.planks.getIcon(0, 0);
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tabs, List subItems) {
        for (int i = 0; i < woods.length; i++) subItems.add(new ItemStack(item, 1, i));
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

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        return woodIcon[meta];
    }

    public String[] getWoods() {
        return woods;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
            float hitY, float hitZ) {
        if (world.isRemote) return true;

        if (!isHuman(player) && !SieveConfig.allowSieveAutomation) return false;

        TileEntitySieve sieve = (TileEntitySieve) world.getTileEntity(x, y, z);

        if (sieve == null) return true;

        ItemStack held = player.getCurrentEquippedItem();

        if (held == null && sieve.getCurrentMesh() != null
                && player.isSneaking()
                && sieve.getCurrentStack() == BlockInfo.EMPTY) {
            EntityItem entityItem = new EntityItem(
                    world,
                    sieve.xCoord + 0.5D,
                    sieve.yCoord + 1.5D,
                    sieve.zCoord + 0.5D,
                    new ItemStack(sieve.getCurrentMesh().getItem(), 1, 0));
            sieve.setCurrentMesh(null);
            world.spawnEntityInWorld(entityItem);
            return true;
        }

        if (sieve.getCurrentStack() == BlockInfo.EMPTY && held != null) {
            // Handle inserting mesh
            if (sieve.getCurrentMesh() == null && held.getItem() instanceof IMesh) {
                sieve.setCurrentMesh((IMesh) held.getItem());
                removeCurrentItem(player);
                return true;
            }
            if (sieve.getCurrentMesh() == null) return true;
            ArrayList<SiftingResult> result = SieveRegistry
                    .getSiftingOutput(new ItemInfo(held), sieve.getCurrentMesh());
            if (result != null) {
                outerloop: for (int dx = -SIEVE_RADIUS; dx <= SIEVE_RADIUS; dx++) {
                    for (int dz = -SIEVE_RADIUS; dz <= SIEVE_RADIUS; dz++) {
                        if (held == null) break outerloop; // ran out of items
                        final TileEntity otherTE = world.getTileEntity(x + dx, y, z + dz);
                        if (!(otherTE instanceof TileEntitySieve otherSieve)) continue; // Not a sieve

                        if (otherSieve.getCurrentStack() == BlockInfo.EMPTY
                                && otherSieve.getCurrentMesh() == sieve.getCurrentMesh()) {
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
                if ((te instanceof TileEntitySieve teSieve) && teSieve.getCurrentStack() != BlockInfo.EMPTY
                        && teSieve.getCurrentMesh() == sieve.getCurrentMesh()) {
                    teSieve.ProcessContents();
                }
            }
        }

        return true;
    }

    @Override
    public void breakBlock(World worldIn, int x, int y, int z, Block blockBroken, int meta) {
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof TileEntitySieve tileEntitySieve) {
            if (tileEntitySieve.getCurrentMesh() != null && !worldIn.isRemote) {
                final EntityItem entityitem = new EntityItem(
                        worldIn,
                        x + 0.5D,
                        y + 1.5D,
                        z + 0.5D,
                        new ItemStack(tileEntitySieve.getCurrentMesh().getItem(), 1, 0));
                entityitem.motionX = worldIn.rand.nextGaussian() * 0.02F;
                entityitem.motionY = 0.1D;
                entityitem.motionZ = worldIn.rand.nextGaussian() * 0.02F;
                worldIn.spawnEntityInWorld(entityitem);
            }
        }
        super.breakBlock(worldIn, x, y, z, blockBroken, meta);
        worldIn.removeTileEntity(x, y, z);
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
