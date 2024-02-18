package exnihilo;

import cpw.mods.fml.common.registry.GameRegistry;
import exnihilo.data.ModData;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class Recipes {

    public static void registerCraftingRecipes() {
        if (ModData.ALLOW_BARRELS) {
            for (int i = 0; i < 6; i++) {
                GameRegistry.addRecipe(new ItemStack(ENBlocks.Barrel, 1, i), "x x", "x x", "xyx",

                    'x', new ItemStack(Blocks.planks, 1, i), 'y', new ItemStack(Blocks.wooden_slab, 1, i));
            }
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ENBlocks.BarrelStone, 1, 0), "x x", "x x", "xyx",

                'x', new ItemStack(Blocks.stone, 1, 0), 'y', new ItemStack(Blocks.stone_slab, 1, 0)));
        }
        if (ModData.ALLOW_HAMMERS) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ENItems.HammerWood, 1, 0), " x ", " yx", "y  ",

                'x', "plankWood", 'y', "stickWood"));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ENItems.HammerStone, 1, 0), " x ", " yx", "y  ",

                'x', Blocks.cobblestone, 'y', "stickWood"));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ENItems.HammerIron, 1, 0), " x ", " yx", "y  ",

                'x', Items.iron_ingot, 'y', "stickWood"));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ENItems.HammerGold, 1, 0), " x ", " yx", "y  ",

                'x', Items.gold_ingot, 'y', "stickWood"));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ENItems.HammerDiamond, 1, 0), " x ", " yx", "y  ",

                'x', Items.diamond, 'y', "stickWood"));
        }
        if (ModData.ALLOW_CROOKS) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ENItems.CrookWood, 1, 0), "xx ", " x ", " x ",

                'x', "stickWood"));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ENItems.CrookStone, 1, 0), "xx ", " x ", " x ", 'x', new ItemStack(Blocks.stone, 1, 0)));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ENItems.CrookIron, 1, 0), "xx ", " x ", " x ", 'x', Items.iron_ingot));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ENItems.CrookGold, 1, 0), "xx ", " x ", " x ", 'x', Items.gold_ingot));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ENItems.CrookDiamond, 1, 0), "xx ", " x ", " x ", 'x', Items.diamond));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ENItems.CrookBone, 1, 0), "xx ", " x ", " x ",

                'x', Items.bone));
        }
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ENItems.MeshSilk, 1, 0), "xxx", "xxx", "xxx",

            'x', Items.string));
        if (!ModData.LEGACY_SIEVE) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ENItems.MeshFlint, 1, 0), "xxx", "xyx", "xxx",
                'x', Items.flint, 'y', ENItems.MeshSilk));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ENItems.MeshIron, 1, 0), "xxx", "xyx", "xxx",
                'x', Items.iron_ingot, 'y', ENItems.MeshFlint));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ENItems.MeshDiamond, 1, 0), "xxx", "xyx", "xxx",
                'x', Items.diamond, 'y', ENItems.MeshIron));
        }
        if (ModData.ALLOW_SIEVES) for (int i = 0; i < 6; i++) {
            if (!ModData.LEGACY_SIEVE)
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ENBlocks.Sieve, 1, i), "x x", "xzx", "y y",

                    'x', new ItemStack(Blocks.planks, 1, i), 'y', "stickWood", 'z', new ItemStack(Blocks.wooden_slab, 1, i)));
            else
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ENBlocks.Sieve, 1, i), "xzx", "xzx", "y y",
                    'x', new ItemStack(Blocks.planks, 1, i), 'y', "stickWood", 'z', new ItemStack(ENItems.MeshSilk, 1, i)));
        }
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ENItems.Porcelain, 1, 0),
            new ItemStack(Items.clay_ball, 1, 0),
            new ItemStack(Items.dye, 1, 15)));
        if (ModData.ALLOW_CRUCIBLES)
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ENBlocks.CrucibleUnfired, 1, 0),
                "x x",
                "x x",
                "xxx",

                'x',
                new ItemStack(ENItems.Porcelain, 1, 0)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.cobblestone, 1, 0), "xx", "xx",

            'x', ENItems.Stones));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ENItems.Doll, 1, 0), "xyx", " x ", "x x",

            'x', ENItems.Porcelain, 'y', Items.diamond));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ENItems.Doll, 1, 0), "xyx", " x ", "x x",

            'x', ENItems.Porcelain, 'y', Items.emerald));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ENItems.DollAngry, 1, 0),
            "xyx",
            "zwz",
            "xvx",

            'v',
            Items.redstone,
            'w',
            ENItems.Doll,
            'x',
            Items.blaze_powder,
            'y',
            Items.nether_wart,
            'z',
            Items.glowstone_dust));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ENItems.DollAngry, 1, 0),
            "xyx",
            "zwz",
            "xvx",

            'v',
            Items.nether_wart,
            'w',
            ENItems.Doll,
            'x',
            Items.blaze_powder,
            'y',
            Items.redstone,
            'z',
            Items.glowstone_dust));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ENItems.DollCreepy, 1, 0),
            "xyx",
            "zwz",
            "xvx",

            'v',
            Items.redstone,
            'w',
            ENItems.Doll,
            'x',
            new ItemStack(Items.dye, 1, 0),
            'y',
            Items.nether_wart,
            'z',
            new ItemStack(Items.dye, 1, 4)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ENItems.DollCreepy, 1, 0),
            "xyx",
            "zwz",
            "xvx",

            'v',
            Items.nether_wart,
            'w',
            ENItems.Doll,
            'x',
            new ItemStack(Items.dye, 1, 0),
            'y',
            Items.redstone,
            'z',
            new ItemStack(Items.dye, 1, 4)));
    }

    public static void registerFurnaceRecipes() {
        FurnaceRecipes.smelting().func_151396_a(ENItems.Silkworm, new ItemStack(ENItems.SilkwormCooked, 1, 0), 0.1F);
        if (ModData.ALLOW_CRUCIBLES) FurnaceRecipes.smelting()
            .func_151393_a(ENBlocks.CrucibleUnfired, new ItemStack(ENBlocks.Crucible, 1, 0), 0.1F);
    }
}
