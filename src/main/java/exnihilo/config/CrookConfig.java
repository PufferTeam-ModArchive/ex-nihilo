package exnihilo.config;

import com.gtnewhorizon.gtnhlib.config.Config;

import exnihilo.ExNihilo;

@Config(modid = ExNihilo.MODID, category = "crook")
public class CrookConfig {

    @Config.Comment("Enable Default Crook Crafting Recipes")
    @Config.DefaultBoolean(true)
    @Config.RequiresMcRestart
    public static boolean enableDefaultCrookCraftingRecipes;

    @Config.Comment("Enable Default Crook Rewards")
    @Config.DefaultBoolean(true)
    @Config.RequiresMcRestart
    public static boolean enableDefaultCrookRewards;

    @Config.Comment("Lilypad Crook Chance")
    @Config.DefaultInt(100)
    @Config.RequiresMcRestart
    public static int lilypadChance;

    @Config.Comment("Infested Leaf String Chance Chance")
    @Config.DefaultFloat(0.4F)
    @Config.RequiresMcRestart
    public static float infestedLeafStringChance;

    @Config.Comment("Crook Blacklist")
    @Config.DefaultStringList({})
    @Config.RequiresMcRestart
    public static String[] crookBlacklist;
}
