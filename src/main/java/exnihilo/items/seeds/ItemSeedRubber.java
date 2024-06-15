package exnihilo.items.seeds;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

import exnihilo.ENItems;
import exnihilo.registries.SieveRegistry;

public class ItemSeedRubber extends ItemSeedBase {

    private static boolean registered = false;

    private static final ArrayList<Block> saplings = new ArrayList<>();

    public ItemSeedRubber() {
        super(Blocks.sapling, Blocks.dirt);
    }

    public static void AddSapling(Block block) {
        if (block != null) {
            saplings.add(block);
            if (!registered) {
                SieveRegistry.register(Blocks.dirt, 0, ENItems.SeedsRubber, 0, 45);
                registered = true;
            }
        }
    }

    @Override
    public Block getPlant(IBlockAccess world, int x, int y, int z) {
        World worldRand = (World) world;
        if (saplings.size() > 0) {
            int rand = worldRand.rand.nextInt(saplings.size());
            return saplings.get(rand);
        }
        return Blocks.sapling;
    }

    @Override
    public int getPlantMetadata(IBlockAccess world, int x, int y, int z) {
        return 0;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
        return EnumPlantType.Plains;
    }

    @Override
    public String getUnlocalizedName() {
        return "exnihilo.seed_rubber";
    }

    @Override
    public String getUnlocalizedName(ItemStack item) {
        return "exnihilo.seed_rubber";
    }

    @Override
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon("exnihilo:ItemSeedRubber");
    }
}
