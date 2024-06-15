package exnihilo.config;

import com.gtnewhorizon.gtnhlib.config.Config;

import exnihilo.ExNihilo;

@Config(modid = ExNihilo.MODID, category = "sieve")
public class SieveConfig {

    @Config.Comment("Enable Default Crafting Recipe for Sieves")
    @Config.DefaultBoolean(true)
    @Config.RequiresMcRestart
    public static boolean enableDefaultSieveCraftingRecipe;

    @Config.Comment("Enable Default Mesh Crafting Recipes")
    @Config.DefaultBoolean(true)
    @Config.RequiresMcRestart
    public static boolean enableDefaultMeshCraftingRecipe;

    @Config.Comment("Enable Default Reward Registriong for Sieves")
    @Config.DefaultBoolean(true)
    @Config.RequiresMcRestart
    public static boolean enableDefaultSieveRewards;

    @Config.Comment("Allow Sieve Automation. Only Applies to things that simulate a player clicking on the block")
    @Config.DefaultBoolean(false)
    @Config.RequiresMcRestart
    public static boolean allowSieveAutomation;

    @Config.Comment("Additions for Sieving")
    @Config.DefaultStringList({})
    @Config.RequiresMcRestart
    public static String[] sieveAdditions;

    @Config.Comment("Oredict Additions for Sieving")
    @Config.DefaultStringList({})
    @Config.RequiresMcRestart
    public static String[] sieveAdditionsOredict;

    @Config.Comment("Sieve Frustration Multiplier")
    @Config.DefaultInt(0)
    @Config.RequiresMcRestart
    public static int sievePainMultiplier;
}
