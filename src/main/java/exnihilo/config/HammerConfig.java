package exnihilo.config;

import com.gtnewhorizon.gtnhlib.config.Config;

import exnihilo.ExNihilo;

@Config(modid = ExNihilo.MODID, category = "hammers")
public class HammerConfig {

    @Config.Comment("Enable Default Hammer Crafting Recipes")
    @Config.DefaultBoolean(true)
    @Config.RequiresMcRestart
    public static boolean enableDefaultHammerCraftingRecipes;

    @Config.Comment("Enable Default Hammer Rewards")
    @Config.DefaultBoolean(true)
    @Config.RequiresMcRestart
    public static boolean enableDefaultHammerRewards;

    @Config.Comment("Enable Ice Shards")
    @Config.DefaultBoolean(true)
    @Config.RequiresMcRestart
    public static boolean enableIceShards;
}
