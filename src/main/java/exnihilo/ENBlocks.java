package exnihilo;

import net.minecraft.block.Block;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import exnihilo.blocks.BlockBarrel;
import exnihilo.blocks.BlockBarrelStone;
import exnihilo.blocks.BlockBeeTrap;
import exnihilo.blocks.BlockBeeTrapTreated;
import exnihilo.blocks.BlockCrucible;
import exnihilo.blocks.BlockCrucibleUnfired;
import exnihilo.blocks.BlockDust;
import exnihilo.blocks.BlockEnderGravel;
import exnihilo.blocks.BlockLeavesInfested;
import exnihilo.blocks.BlockNetherGravel;
import exnihilo.blocks.BlockSieve;
import exnihilo.blocks.itemBlocks.ItemBlockBarrel;
import exnihilo.blocks.itemBlocks.ItemBlockBarrelStone;
import exnihilo.blocks.itemBlocks.ItemBlockCrucible;
import exnihilo.blocks.itemBlocks.ItemBlockCrucibleUnfired;
import exnihilo.blocks.itemBlocks.ItemBlockLeavesInfested;
import exnihilo.blocks.itemBlocks.ItemBlockSieve;

public class ENBlocks {

    public static Block Barrel;

    public static Block BarrelBOP;

    public static Block BarrelStone;

    public static Block Crucible;

    public static Block CrucibleUnfired;

    public static Block Dust;

    public static Block LeavesInfested;

    public static Block Sieve;

    public static Block SieveBOP;

    public static Block BeeTrap;

    public static Block BeeTrapTreated;

    public static Block NetherGravel;

    public static Block EnderGravel;

    public static final String[] woodTypes = new String[] { "oak", "spruce", "birch", "jungle", "acacia", "dark_oak" };
    public static final String[] bopWoodTypes = new String[] { "sacredoak", "cherry", "dark", "fir", "ethereal",
            "magic", "mangrove", "palm", "redwood", "willow", "pine", "hellbark", "jacaranda", "mahogany" };

    public static String getCapitalized(String string) {
        String[] stringArray = string.split("");
        stringArray[0] = stringArray[0].toUpperCase();
        string = String.join("", stringArray);

        return string;
    }

    public static void registerBlocks() {
        Barrel = new BlockBarrel("vanilla", woodTypes, "barrel");
        GameRegistry.registerBlock(Barrel, ItemBlockBarrel.class, "barrel");

        if (Loader.isModLoaded("BiomesOPlenty")) {
            BarrelBOP = new BlockBarrel("bop", bopWoodTypes, "bop_barrel");
            GameRegistry.registerBlock(BarrelBOP, ItemBlockBarrel.class, "bop_barrel");
        }

        BarrelStone = new BlockBarrelStone();
        GameRegistry.registerBlock(BarrelStone, ItemBlockBarrelStone.class, "barrel_stone");
        Crucible = new BlockCrucible();
        GameRegistry.registerBlock(Crucible, ItemBlockCrucible.class, "crucible");
        CrucibleUnfired = new BlockCrucibleUnfired();
        GameRegistry.registerBlock(CrucibleUnfired, ItemBlockCrucibleUnfired.class, "crucible_unfired");
        Dust = new BlockDust();
        GameRegistry.registerBlock(Dust, "dust");
        LeavesInfested = new BlockLeavesInfested();
        GameRegistry.registerBlock(LeavesInfested, ItemBlockLeavesInfested.class, "infested_leaves");
        Sieve = new BlockSieve("vanilla", woodTypes, "sifting_table");
        GameRegistry.registerBlock(Sieve, ItemBlockSieve.class, "sifting_table");
        if (Loader.isModLoaded("BiomesOPlenty")) {
            SieveBOP = new BlockSieve("bop", bopWoodTypes, "bop_sifting_table");
            GameRegistry.registerBlock(SieveBOP, ItemBlockSieve.class, "bop_sifting_table");
        }
        BeeTrap = new BlockBeeTrap();
        GameRegistry.registerBlock(BeeTrap, "bee_trap");
        BeeTrapTreated = new BlockBeeTrapTreated();
        GameRegistry.registerBlock(BeeTrapTreated, "bee_trap_treated");
        NetherGravel = new BlockNetherGravel();
        GameRegistry.registerBlock(NetherGravel, NetherGravel.getUnlocalizedName());
        EnderGravel = new BlockEnderGravel();
        GameRegistry.registerBlock(EnderGravel, EnderGravel.getUnlocalizedName());
    }
}
