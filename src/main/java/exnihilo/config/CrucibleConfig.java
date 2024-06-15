package exnihilo.config;

import com.gtnewhorizon.gtnhlib.config.Config;

import exnihilo.ExNihilo;

@Config(modid = ExNihilo.MODID, category = "crucible")
public class CrucibleConfig {

    @Config.Comment("Enable Default Crucible Crafting Recipes")
    @Config.DefaultBoolean(true)
    @Config.RequiresMcRestart
    public static boolean enableDefaultCrucibleCraftingRecipes;

    @Config.Comment("Enable Default Crucible Rewards")
    @Config.DefaultBoolean(true)
    @Config.RequiresMcRestart
    public static boolean enableDefaultCrucibleRewards;

    @Config.Comment("Enable Default Heat Sources")
    @Config.DefaultBoolean(true)
    @Config.RequiresMcRestart
    public static boolean enableDefaultHeatSources;

}
