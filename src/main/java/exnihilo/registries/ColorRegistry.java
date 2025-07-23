package exnihilo.registries;

import java.util.Hashtable;

import exnihilo.config.ColorConfig;
import exnihilo.registries.helpers.Color;

public class ColorRegistry {

    public static final Hashtable<String, Color> entries = new Hashtable<>();

    public static void register(String name, Color color) {
        entries.put(name, color);
    }

    public static Color color(String name) {
        if (entries.containsKey(name)) return entries.get(name);
        return entries.get("white");
    }

    public static final String CATEGORY_COLORS = "Colors";

    public static void load() {
        register("white", new Color(1.0F, 1.0F, 1.0F, 1.0F));
        register("black", new Color(0.0F, 0.0F, 0.0F, 1.0F));
        register("dirt", new Color(ColorConfig.dirt));
        register("gravel", new Color(ColorConfig.gravel));
        register("sand", new Color(ColorConfig.sand));
        register("dust", new Color(ColorConfig.dust));
        register("soul_sand", new Color(ColorConfig.soul_sand));
        register("oak", new Color(ColorConfig.oak));
        register("birch", new Color(ColorConfig.birch));
        register("spruce", new Color(ColorConfig.spruce));
        register("jungle", new Color(ColorConfig.jungle));
        register("dark_oak", new Color(ColorConfig.dark_oak));
        register("acacia", new Color(ColorConfig.acacia));
        register("acacia_leaves", new Color(ColorConfig.acacia_leaves));
        register("pork_raw", new Color(ColorConfig.pork_raw));
        register("pork_cooked", new Color(ColorConfig.pork_cooked));
        register("beef_raw", new Color(ColorConfig.beef_raw));
        register("beef_cooked", new Color(ColorConfig.beef_cooked));
        register("chicken_raw", new Color(ColorConfig.chicken_raw));
        register("chicken_cooked", new Color(ColorConfig.chicken_cooked));
        register("fish_raw", new Color(ColorConfig.fish_raw));
        register("fish_cooked", new Color(ColorConfig.fish_cooked));
        register("salmon_raw", new Color(ColorConfig.salmon_raw));
        register("salmon_cooked", new Color(ColorConfig.salmon_cooked));
        register("clownfish", new Color(ColorConfig.fish_cooked));
        register("pufferfish", new Color(ColorConfig.fish_cooked));
        register("silkworm_raw", new Color(ColorConfig.silkworm_raw));
        register("silkworm_cooked", new Color(ColorConfig.silkworm_cooked));
        register("apple", new Color(ColorConfig.apple));
        register("melon", new Color(ColorConfig.melon));
        register("pumpkin", new Color(ColorConfig.pumpkin));
        register("carrot", new Color(ColorConfig.carrot));
        register("potato", new Color(ColorConfig.potato));
        register("potato_baked", new Color(ColorConfig.potato_baked));
        register("potato_poison", new Color(ColorConfig.potato_poison));
        register("rotten_flesh", new Color(ColorConfig.rotten_flesh));
        register("spider_eye", new Color(ColorConfig.spider_eye));
        register("wheat", new Color(ColorConfig.wheat));
        register("bread", new Color(ColorConfig.bread));
        register("pumpkin_pie", new Color(ColorConfig.pumpkin_pie));
        register("mushroom_brown", new Color(ColorConfig.mushroom_brown));
        register("mushroom_red", new Color(ColorConfig.mushroom_red));
        register("cactus", new Color(ColorConfig.cactus));
        register("vine", new Color(ColorConfig.vine));
        register("tall_grass", new Color(ColorConfig.tall_grass));
        register("waterlily", new Color(ColorConfig.waterlily));
        register("egg", new Color(ColorConfig.egg));
        register("netherwart", new Color(ColorConfig.netherwart));
        register("sugar_cane", new Color(ColorConfig.sugar_cane));
        register("water_slime_offset", new Color("33ff22"));
        register("witchwater", new Color("990066"));
        register("water_witchy_offset", new Color("bb1535"));
        register("dandelion", new Color(ColorConfig.dandelion));
        register("rose", new Color(ColorConfig.rose));
        register("poppy", new Color(ColorConfig.poppy));
        register("blue_orchid", new Color(ColorConfig.blue_orchid));
        register("allium", new Color(ColorConfig.allium));
        register("azure_bluet", new Color(ColorConfig.azure_bluet));
        register("red_tulip", new Color(ColorConfig.red_tulip));
        register("orange_tulip", new Color(ColorConfig.orange_tulip));
        register("white_tulip", new Color(ColorConfig.white_tulip));
        register("pink_tulip", new Color(ColorConfig.pink_tulip));
        register("oxeye_daisy", new Color(ColorConfig.oxeye_daisy));
        register("sunflower", new Color(ColorConfig.sunflower));
        register("lilac", new Color(ColorConfig.lilac));
        register("peony", new Color(ColorConfig.peony));
    }
}
