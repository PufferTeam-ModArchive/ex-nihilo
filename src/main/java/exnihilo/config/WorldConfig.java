package exnihilo.config;

import com.gtnewhorizon.gtnhlib.config.Config;

import exnihilo.ExNihilo;

@Config(modid = ExNihilo.MODID, category = "world")
public class WorldConfig {

    @Config.Comment("Enable Void Overworld Hijacking")
    @Config.DefaultBoolean(false)
    @Config.RequiresMcRestart
    public static boolean hijackOverworld;

    @Config.Comment("Enable Void Nether Hijacking")
    @Config.DefaultBoolean(false)
    @Config.RequiresMcRestart
    public static boolean hijackNether;

    @Config.Comment("Allow Nether Fortresses")
    @Config.DefaultBoolean(false)
    @Config.RequiresMcRestart
    public static boolean allowNetherFortresses;

    @Config.Comment("Enable Void End Hijacking")
    @Config.DefaultBoolean(false)
    @Config.RequiresMcRestart
    public static boolean hijackEnd;
}
