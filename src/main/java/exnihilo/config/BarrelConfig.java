package exnihilo.config;

import com.gtnewhorizon.gtnhlib.config.Config;

import exnihilo.ExNihilo;

@Config(modid = ExNihilo.MODID, category = "barrel")
public class BarrelConfig {

    @Config.Comment("Enable Default Barrel Crafting Recipes")
    @Config.DefaultBoolean(true)
    @Config.RequiresMcRestart
    public static boolean enableDefaultBarrelCraftingRecipes;

    @Config.Comment("Allow Barrels to Fill When Rained On")
    @Config.DefaultBoolean(true)
    @Config.RequiresMcRestart
    public static boolean enableBarrelRainFill;

    @Config.Comment("Enable Barrel Recipe (Dirt)")
    @Config.DefaultBoolean(true)
    @Config.RequiresMcRestart
    public static boolean enableBarrelRecipeDirt;

    @Config.Comment("Enable Barrel Recipe (Clay)")
    @Config.DefaultBoolean(true)
    @Config.RequiresMcRestart
    public static boolean enableBarrelRecipeClay;

    @Config.Comment("Enable Barrel Recipe (Netherrack)")
    @Config.DefaultBoolean(true)
    @Config.RequiresMcRestart
    public static boolean enableBarrelRecipeNetherrack;

    @Config.Comment("Enable Barrel Recipe (End Stone)")
    @Config.DefaultBoolean(true)
    @Config.RequiresMcRestart
    public static boolean enableBarrelRecipeEndStone;

    @Config.Comment("Enable Barrel Recipe (Slime)")
    @Config.DefaultBoolean(true)
    @Config.RequiresMcRestart
    public static boolean enableBarrelRecipeSlime;

    @Config.Comment("Enable Barrel Recipe (Soulsand)")
    @Config.DefaultBoolean(true)
    @Config.RequiresMcRestart
    public static boolean enableBarrelRecipeSoulsand;

    @Config.Comment("Enable Barrel Recipe (Blaze Rods)")
    @Config.DefaultBoolean(true)
    @Config.RequiresMcRestart
    public static boolean enableBarrelRecipeBlazeRod;

    @Config.Comment("Enable Barrel Recipe (Ender Pearls)")
    @Config.DefaultBoolean(true)
    @Config.RequiresMcRestart
    public static boolean enableBarrelRecipeEnderPearl;

    @Config.Comment("Enable Barrel Recipe (Dark Oak)")
    @Config.DefaultBoolean(true)
    @Config.RequiresMcRestart
    public static boolean enableBarrelRecipeDarkOak;

    @Config.Comment("Enable Barrel Recipe (Double Flowers)")
    @Config.DefaultBoolean(true)
    @Config.RequiresMcRestart
    public static boolean enableBarrelRecipeDoubleFlowers;

    @Config.Comment("Additions for Composting")
    @Config.DefaultStringList({})
    @Config.RequiresMcRestart
    public static String[] compostAdditions;

    @Config.Comment("Oredict Additions for Composting")
    @Config.DefaultStringList({})
    @Config.RequiresMcRestart
    public static String[] compostAdditionsOredict;

}
