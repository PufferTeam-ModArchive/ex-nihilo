package exnihilo.registries;

import java.util.Hashtable;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;

import exnihilo.ENItems;
import exnihilo.registries.helpers.Color;
import exnihilo.registries.helpers.Compostable;
import exnihilo.utils.ItemInfo;

public class CompostRegistry {

    public static final Hashtable<ItemInfo, Compostable> entries = new Hashtable<>();

    public static void register(Item item, int meta, float value, Color color) {
        ItemInfo iteminfo = new ItemInfo(item, meta);
        Compostable entry = new Compostable(value, color);
        entries.put(iteminfo, entry);
    }

    public static boolean containsItem(Item item, int meta) {
        return entries.containsKey(new ItemInfo(item, meta));
    }

    public static Compostable getItem(Item item, int meta) {
        return entries.get(new ItemInfo(item, meta));
    }

    public static void unregister(Item item, int meta) {
        entries.remove(new ItemInfo(item, meta));
    }

    public static void unregister(Item item) {
        for (int i = 0; i < 16; i++) unregister(item, i);
    }

    public static void modify(Item item, int meta, float newValue) {
        ItemInfo iteminfo = new ItemInfo(item, meta);
        if (entries.containsKey(iteminfo)) {
            Compostable old = entries.get(iteminfo);
            Compostable entry = new Compostable(newValue, old.color);
            entries.put(iteminfo, entry);
        }
    }

    public static void load() {
        register(Item.getItemFromBlock(Blocks.sapling), 0, 0.125F, ColorRegistry.color("oak"));
        register(Item.getItemFromBlock(Blocks.sapling), 1, 0.125F, ColorRegistry.color("spruce"));
        register(Item.getItemFromBlock(Blocks.sapling), 2, 0.125F, ColorRegistry.color("birch"));
        register(Item.getItemFromBlock(Blocks.sapling), 3, 0.125F, ColorRegistry.color("jungle"));
        register(Item.getItemFromBlock(Blocks.sapling), 4, 0.125F, ColorRegistry.color("acacia"));
        register(Item.getItemFromBlock(Blocks.sapling), 5, 0.125F, ColorRegistry.color("dark_oak"));
        register(Item.getItemFromBlock(Blocks.leaves), 0, 0.125F, ColorRegistry.color("oak"));
        register(Item.getItemFromBlock(Blocks.leaves), 1, 0.125F, ColorRegistry.color("spruce"));
        register(Item.getItemFromBlock(Blocks.leaves), 2, 0.125F, ColorRegistry.color("birch"));
        register(Item.getItemFromBlock(Blocks.leaves), 3, 0.125F, ColorRegistry.color("jungle"));
        register(Item.getItemFromBlock(Blocks.leaves2), 0, 0.125F, ColorRegistry.color("acacia_leaves"));
        register(Item.getItemFromBlock(Blocks.leaves2), 1, 0.125F, ColorRegistry.color("dark_oak"));
        register(Items.rotten_flesh, 0, 0.1F, ColorRegistry.color("rotten_flesh"));
        register(Items.spider_eye, 0, 0.08F, ColorRegistry.color("spider_eye"));
        register(Items.wheat, 0, 0.08F, ColorRegistry.color("wheat"));
        register(Items.bread, 0, 0.16F, ColorRegistry.color("bread"));
        register(Item.getItemFromBlock(Blocks.yellow_flower), 0, 0.1F, ColorRegistry.color("dandelion"));
        register(Item.getItemFromBlock(Blocks.red_flower), 0, 0.1F, ColorRegistry.color("poppy"));
        register(Item.getItemFromBlock(Blocks.red_flower), 1, 0.1F, ColorRegistry.color("blue_orchid"));
        register(Item.getItemFromBlock(Blocks.red_flower), 2, 0.1F, ColorRegistry.color("allium"));
        register(Item.getItemFromBlock(Blocks.red_flower), 3, 0.1F, ColorRegistry.color("azure_bluet"));
        register(Item.getItemFromBlock(Blocks.red_flower), 4, 0.1F, ColorRegistry.color("red_tulip"));
        register(Item.getItemFromBlock(Blocks.red_flower), 5, 0.1F, ColorRegistry.color("orange_tulip"));
        register(Item.getItemFromBlock(Blocks.red_flower), 6, 0.1F, ColorRegistry.color("white_tulip"));
        register(Item.getItemFromBlock(Blocks.red_flower), 7, 0.1F, ColorRegistry.color("pink_tulip"));
        register(Item.getItemFromBlock(Blocks.red_flower), 8, 0.1F, ColorRegistry.color("oxeye_daisy"));
        register(Item.getItemFromBlock(Blocks.double_plant), 0, 0.1F, ColorRegistry.color("sunflower"));
        register(Item.getItemFromBlock(Blocks.double_plant), 1, 0.1F, ColorRegistry.color("lilac"));
        register(Item.getItemFromBlock(Blocks.double_plant), 4, 0.1F, ColorRegistry.color("rose"));
        register(Item.getItemFromBlock(Blocks.double_plant), 5, 0.1F, ColorRegistry.color("peony"));
        register(Item.getItemFromBlock(Blocks.brown_mushroom), 0, 0.1F, ColorRegistry.color("mushroom_brown"));
        register(Item.getItemFromBlock(Blocks.red_mushroom), 0, 0.1F, ColorRegistry.color("mushroom_red"));
        register(Items.pumpkin_pie, 0, 0.16F, ColorRegistry.color("pumpkin_pie"));
        register(Items.porkchop, 0, 0.2F, ColorRegistry.color("pork_raw"));
        register(Items.cooked_porkchop, 0, 0.2F, ColorRegistry.color("pork_cooked"));
        register(Items.beef, 0, 0.2F, ColorRegistry.color("beef_raw"));
        register(Items.cooked_beef, 0, 0.2F, ColorRegistry.color("beef_cooked"));
        register(Items.chicken, 0, 0.2F, ColorRegistry.color("chicken_raw"));
        register(Items.cooked_chicken, 0, 0.2F, ColorRegistry.color("chicken_cooked"));
        register(Items.fish, 0, 0.15F, ColorRegistry.color("fish_raw"));
        register(Items.cooked_fished, 0, 0.15F, ColorRegistry.color("fish_cooked"));
        register(Items.fish, 1, 0.15F, ColorRegistry.color("salmon_raw"));
        register(Items.cooked_fished, 1, 0.15F, ColorRegistry.color("salmon_cooked"));
        register(Items.fish, 2, 0.15F, ColorRegistry.color("clownfish"));
        register(Items.fish, 3, 0.15F, ColorRegistry.color("pufferfish"));
        register(ENItems.Silkworm, 0, 0.04F, ColorRegistry.color("silkworm_raw"));
        register(ENItems.SilkwormCooked, 0, 0.04F, ColorRegistry.color("silkworm_cooked"));
        register(Items.apple, 0, 0.1F, ColorRegistry.color("apple"));
        register(Items.melon, 0, 0.04F, ColorRegistry.color("melon"));
        register(Item.getItemFromBlock(Blocks.melon_block), 0, 0.16666667F, ColorRegistry.color("melon"));
        register(Item.getItemFromBlock(Blocks.pumpkin), 0, 0.16666667F, ColorRegistry.color("pumpkin"));
        register(Item.getItemFromBlock(Blocks.lit_pumpkin), 0, 0.16666667F, ColorRegistry.color("pumpkin"));
        register(Item.getItemFromBlock(Blocks.cactus), 0, 0.1F, ColorRegistry.color("cactus"));
        register(Items.carrot, 0, 0.08F, ColorRegistry.color("carrot"));
        register(Items.potato, 0, 0.08F, ColorRegistry.color("potato"));
        register(Items.baked_potato, 0, 0.08F, ColorRegistry.color("potato_baked"));
        register(Items.poisonous_potato, 0, 0.08F, ColorRegistry.color("potato_poison"));
        register(Item.getItemFromBlock(Blocks.waterlily), 0, 0.1F, ColorRegistry.color("waterlily"));
        register(Item.getItemFromBlock(Blocks.vine), 0, 0.1F, ColorRegistry.color("vine"));
        register(Item.getItemFromBlock(Blocks.tallgrass), 1, 0.08F, ColorRegistry.color("tall_grass"));
        register(Items.egg, 0, 0.08F, ColorRegistry.color("egg"));
        register(Items.nether_wart, 0, 0.1F, ColorRegistry.color("netherwart"));
        register(Items.reeds, 0, 0.08F, ColorRegistry.color("sugar_cane"));
        register(Items.string, 0, 0.04F, ColorRegistry.color("white"));
    }

    public static void registerOreDictAdditions(String[] names) {
        for (String input : names) {
            String[] current = input.split(":");
            for (ItemStack stack : OreDictionary.getOres(current[0]))
                register(stack.getItem(), stack.getItemDamage(), Float.parseFloat(current[1]), new Color(current[2]));
        }
    }

    public static void registerNonDictAdditions(String[] names) {
        for (String input : names) {
            String[] current = input.split(":");
            if (current.length == 5 && Item.itemRegistry.getObject(current[0] + ":" + current[1]) != null) {
                Item item = (Item) Item.itemRegistry.getObject(current[0] + ":" + current[1]);
                register(item, Integer.parseInt(current[2]), Float.parseFloat(current[3]), new Color(current[4]));
            }
        }
    }
}
