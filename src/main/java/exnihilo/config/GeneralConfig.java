package exnihilo.config;

import net.minecraftforge.common.config.Configuration;

import com.gtnewhorizon.gtnhlib.config.Config;

import exnihilo.ExNihilo;

@Config(modid = ExNihilo.MODID, category = Configuration.CATEGORY_GENERAL)
public class GeneralConfig {

    @Config.Comment("Overwrite Default IC2 Macerator Recipes (if present)")
    @Config.DefaultBoolean(true)
    @Config.RequiresMcRestart
    public static boolean overwriteIC2Macerator;

    @Config.Comment("Overwrite Default Thermal Expansion Pulverizer Recipes (if present)")
    @Config.DefaultBoolean(true)
    @Config.RequiresMcRestart
    public static boolean overwriteThermalPulverizerRecipes;
}
