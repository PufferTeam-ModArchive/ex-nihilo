package exnihilo.registries;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import exnihilo.ENBlocks;
import exnihilo.blocks.ores.BlockOre;
import exnihilo.blocks.ores.BlockOreFactory;
import exnihilo.compatibility.ThermalExpansion;
import exnihilo.compatibility.TinkersConstruct;
import exnihilo.config.SieveConfig;
import exnihilo.items.ores.ItemOre;
import exnihilo.items.ores.ItemOreFactory;
import exnihilo.registries.helpers.Color;

public class OreRegistry {

    public static final List<String> ores = new ArrayList<>();

    public static final Hashtable<String, BlockOre> gravelTable = new Hashtable<>();

    public static final Hashtable<String, BlockOre> sandTable = new Hashtable<>();

    public static final Hashtable<String, BlockOre> dustTable = new Hashtable<>();

    public static final Hashtable<String, ItemOre> brokenTable = new Hashtable<>();

    public static final Hashtable<String, ItemOre> crushedTable = new Hashtable<>();

    public static final Hashtable<String, ItemOre> powderedTable = new Hashtable<>();

    public static final Hashtable<String, ItemOre> ingotTable = new Hashtable<>();

    private static void registerOreBlock(String name, BlockOre block, Hashtable<String, BlockOre> table) {
        if (!table.containsValue(block)) {
            table.put(name.toLowerCase(), block);
            GameRegistry.registerBlock(block, block.getName());
            if (Loader.isModLoaded("ThermalExpansion")) ThermalExpansion.TryRegisterOre(name, block);
            if (Loader.isModLoaded("TConstruct")) TinkersConstruct.TryRegisterOre(name, block);
        }
    }

    private static void registerOreItem(String name, ItemOre item, Hashtable<String, ItemOre> table) {
        String lname = name.toLowerCase();
        if (!table.containsValue(item)) {
            table.put(lname, item);
            GameRegistry.registerItem(item, item.getUnlocalizedName());
        }
    }

    public static void createOverworldOre(String name, Color color, int rarity) {
        createOverworldOre(name, color, rarity, null, false);
    }

    public static void createOverworldOre(String name, Color color, int rarity, Item ingot) {
        createOverworldOre(name, color, rarity, ingot, false);
    }

    public static void createOverworldOre(String name, Color color, int rarity, boolean skipFurnaceRecipes) {
        createOverworldOre(name, color, rarity, null, skipFurnaceRecipes);
    }

    public static void createOverworldOre(String name, Color color, int rarity, Item existingIngot,
            boolean skipFurnaceRecipes) {
        createOre(0, name, color, rarity, existingIngot, skipFurnaceRecipes);
    }

    public static void createNetherOre(String name, Color color, int rarity) {
        createNetherOre(name, color, rarity, null, false);
    }

    public static void createNetherOre(String name, Color color, int rarity, Item ingot) {
        createNetherOre(name, color, rarity, ingot, false);
    }

    public static void createNetherOre(String name, Color color, int rarity, boolean skipFurnaceRecipes) {
        createNetherOre(name, color, rarity, null, skipFurnaceRecipes);
    }

    public static void createNetherOre(String name, Color color, int rarity, Item existingIngot,
            boolean skipFurnaceRecipes) {
        createOre(-1, name, color, rarity, existingIngot, skipFurnaceRecipes);
    }

    public static void createEnderOre(String name, Color color, int rarity) {
        createEnderOre(name, color, rarity, null, false);
    }

    public static void createEnderOre(String name, Color color, int rarity, Item ingot) {
        createEnderOre(name, color, rarity, ingot, false);
    }

    public static void createEnderOre(String name, Color color, int rarity, boolean skipFurnaceRecipes) {
        createEnderOre(name, color, rarity, null, skipFurnaceRecipes);
    }

    public static void createEnderOre(String name, Color color, int rarity, Item existingIngot,
            boolean skipFurnaceRecipes) {
        createOre(1, name, color, rarity, existingIngot, skipFurnaceRecipes);
    }

    private static void createOre(int type, String name, Color color, int rarity, Item existingIngot,
            boolean skipFurnaceRecipes) {
        if (ores.contains(type + "_" + name.toLowerCase())) return;
        ores.add(type + "_" + name.toLowerCase());
        BlockOre gravel = null;
        boolean gravelAlreadyExisted = false;
        switch (type) {
            // nether
            case -1 -> {
                gravel = gravelTable.get("nether_" + name);
                if (gravel == null) {
                    gravel = BlockOreFactory.MakeNetherGravel(name, color);
                    registerOreBlock("nether_" + name, gravel, gravelTable);
                    break;
                }
                gravelAlreadyExisted = true;
            }
            // normal
            case 0 -> {
                gravel = gravelTable.get(name);
                if (gravel == null) {
                    gravel = BlockOreFactory.MakeOverworldGravel(name, color);
                    registerOreBlock(name, gravel, gravelTable);
                    break;
                }
                gravelAlreadyExisted = true;
            }
            // end
            case 1 -> {
                gravel = gravelTable.get("ender_" + name);
                if (gravel == null) {
                    gravel = BlockOreFactory.MakeEnderGravel(name, color);
                    registerOreBlock("ender_" + name, gravel, gravelTable);
                    break;
                }
                gravelAlreadyExisted = true;
            }
        }
        BlockOre sand = sandTable.get(name);
        if (sand == null) {
            sand = BlockOreFactory.MakeSand(name, color);
            registerOreBlock(name, sand, sandTable);
        }
        BlockOre dust = dustTable.get(name);
        if (dust == null) {
            dust = BlockOreFactory.MakeDust(name, color);
            registerOreBlock(name, dust, dustTable);
        }

        ItemOre broken = null;
        switch (type) {
            case -1 -> {
                broken = brokenTable.get("nether_" + name);
                if (broken == null) {
                    broken = ItemOreFactory.MakeNetherBrokenOre(name, color);
                    registerOreItem("nether_" + name, broken, brokenTable);
                }
            }
            case 0 -> {
                broken = brokenTable.get(name);
                if (broken == null) {
                    broken = ItemOreFactory.MakeOverworldBrokenOre(name, color);
                    registerOreItem(name, broken, brokenTable);
                }
            }
            case 1 -> {
                broken = brokenTable.get("ender_" + name);
                if (broken == null) {
                    broken = ItemOreFactory.MakeEnderBrokenOre(name, color);
                    registerOreItem("ender_" + name, broken, brokenTable);
                }
            }
        }
        ItemOre crushed = crushedTable.get(name);
        if (crushed == null) {
            crushed = ItemOreFactory.MakeCrushedOre(name, color);
            registerOreItem(name, crushed, crushedTable);
        }
        ItemOre powdered = powderedTable.get(name);
        if (powdered == null) {
            powdered = ItemOreFactory.MakePulverizedOre(name, color);
            registerOreItem(name, powdered, powderedTable);
        }

        // Use an existing ingot if it's specified.
        Item ingot;
        if (existingIngot != null) {
            ingot = existingIngot;
        } else {
            ingot = ingotTable.get(name);
            if (ingot == null) {
                ItemOre newIngot = ItemOreFactory.MakeIngot(name, color);
                registerOreItem(name, newIngot, ingotTable);
                ingot = newIngot;
                registerOreDict(name, ingot);
            }
        }
        // Register hammer recipes.
        if (!gravelAlreadyExisted) registerHammerRecipe(gravel, crushed);
        if (!HammerRegistry.registered(sand, 0)) registerHammerRecipe(sand, powdered);
        registerCraftingRecipe(broken, gravel);
        registerCraftingRecipe(crushed, sand);
        registerCraftingRecipe(powdered, dust);
        if (!skipFurnaceRecipes) {
            registerFurnaceRecipe(gravel, ingot);
            registerFurnaceRecipe(sand, ingot);
            registerFurnaceRecipe(dust, ingot);
        }
        if (SieveConfig.enableDefaultSieveRewards) {
            switch (type) {
                case -1 -> registerSieveRecipe(ENBlocks.NetherGravel, broken, rarity);
                case 0 -> {
                    registerSieveRecipe(Blocks.gravel, broken, rarity);
                    registerSieveRecipe(Blocks.sand, crushed, rarity);
                    registerSieveRecipe(ENBlocks.Dust, powdered, rarity);
                }
                case 1 -> registerSieveRecipe(ENBlocks.EnderGravel, broken, rarity);
            }
        }
    }

    public static void registerOreDict(String name, Item ingot) {
        OreDictionary.registerOre("ingot" + formatOreName(name), ingot);
    }

    private static void registerHammerRecipe(Block block, ItemOre reward) {
        HammerRegistry.registerOre(block, 0, reward, 0);
    }

    private static void registerHammerRecipe(Block block, int meta, ItemOre reward) {
        HammerRegistry.registerOre(block, meta, reward, 0);
    }

    private static void registerCraftingRecipe(ItemOre ingredient, BlockOre result) {
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(result, 1, 0),
                        "xx",
                        "xx",

                        'x',
                        ingredient));
    }

    private static void registerFurnaceRecipe(BlockOre ore, Item ingot) {
        FurnaceRecipes.smelting().func_151393_a(ore, new ItemStack(ingot, 1, 0), 0.1F);
    }

    private static void registerSieveRecipe(Block block, ItemOre item, int rarity) {
        SieveRegistry.register(block, item, 0, rarity);
    }

    private static String formatOreName(String input) {
        String lcase = input.toLowerCase();
        return lcase.substring(0, 1).toUpperCase() + lcase.substring(1);
    }

    public static Block getGravel(String name) {
        return gravelTable.get(name.toLowerCase());
    }

    public static Block getSand(String name) {
        return sandTable.get(name.toLowerCase());
    }

    public static Block getDust(String name) {
        return dustTable.get(name.toLowerCase());
    }

    public static Item getBroken(String name) {
        return brokenTable.get(name.toLowerCase());
    }

    public static Item getCrushed(String name) {
        return crushedTable.get(name.toLowerCase());
    }

    public static Item getPowdered(String name) {
        return powderedTable.get(name.toLowerCase());
    }

    public static Item getIngot(String name) {
        return ingotTable.get(name.toLowerCase());
    }
}
