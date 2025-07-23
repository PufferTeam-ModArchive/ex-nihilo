package exnihilo.config;

import com.gtnewhorizon.gtnhlib.config.Config;

import exnihilo.ExNihilo;

@Config(modid = ExNihilo.MODID, category = "ores")
public class OreConfig {

    public static boolean dropCopper = false;
    public static boolean dropTin = false;
    public static boolean dropLead = false;
    public static boolean dropSilver = false;
    public static boolean dropNickel = false;
    public static boolean dropPlatinum = false;
    public static boolean dropAluminum = false;
    public static boolean dropOsmium = false;

    public static int ironChance = 5;
    public static int ironChanceNether = 6;
    public static int goldChance = 32;
    public static int goldChanceNether = 6;
    public static int tinChance = 18;
    public static int tinChanceEnd = 10;
    public static int copperChance = 18;
    public static int copperChanceNether = 10;
    public static int nickelChance = 32;
    public static int nickelChanceNether = 10;
    public static int platinumChance = 128;
    public static int platinumChanceEnd = 20;
    public static int silverChance = 45;
    public static int silverChanceEnd = 6;
    public static int leadChance = 32;
    public static int leadChanceEnd = 6;
    public static int aluminumChance = 8;
    public static int osmiumChance = 10;

}
