package exnihilo.compatibility;

import java.util.ArrayList;

import exnihilo.config.OreConfig;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;

import exnihilo.registries.HammerRegistry;
import exnihilo.registries.OreRegistry;
import exnihilo.registries.helpers.Color;

public class OreList {

    public static void processOreDict() {
        String[] oreString = { "iron", "gold", "copper", "tin", "silver", "lead", "nickel", "platinum", "aluminum",
                "osmium" };
        for (String name : oreString) {
            String name2 = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
            ArrayList<ItemStack> ores = OreDictionary.getOres("ore" + name2);
            if (ores.size() > 0) for (ItemStack i : ores) {
                System.out.println(
                        "Registering " + Item.itemRegistry.getNameForObject(i.getItem()) + ":" + i.getItemDamage());
                if (Block.getBlockFromItem(i.getItem()) != Blocks.air) {
                    HammerRegistry.registerOre(
                            Block.getBlockFromItem(i.getItem()),
                            i.getItemDamage(),
                            OreRegistry.brokenTable.get(name),
                            0);
                    continue;
                }
                System.out.println(
                        Item.itemRegistry.getNameForObject(i.getItem()) + ":" + i.getItemDamage() + " is null!");
            }
        }
    }

    public static void registerOres() {
        boolean ignoreOreDict = false;
        OreRegistry.createOverworldOre("iron", new Color("F2AB7C"), Type.Iron.getChance(), Items.iron_ingot);
        OreRegistry.createNetherOre("iron", new Color("F2AB7C"), Type.NetherIron.getChance(), Items.iron_ingot);
        OreRegistry.createOverworldOre("gold", new Color("FFD000"), Type.Gold.getChance(), Items.gold_ingot);
        OreRegistry.createNetherOre("gold", new Color("FFD000"), Type.NetherGold.getChance(), Items.gold_ingot);
        if (OreDictionary.getOres("oreCopper").size() > 0 || ignoreOreDict || OreConfig.dropCopper) {
            OreRegistry.createOverworldOre("copper", new Color("FF4D00"), Type.Copper.getChance());
            OreRegistry.createNetherOre("copper", new Color("FF4D00"), Type.NetherCopper.getChance());
        }
        if (OreDictionary.getOres("oreTin").size() > 0 || ignoreOreDict || OreConfig.dropTin) {
            OreRegistry.createOverworldOre("tin", new Color("ABC9B6"), Type.Tin.getChance());
            OreRegistry.createEnderOre("tin", new Color("ABC9B6"), Type.EnderTin.getChance());
        }
        if (OreDictionary.getOres("oreSilver").size() > 0 || ignoreOreDict || OreConfig.dropSilver) {
            OreRegistry.createOverworldOre("silver", new Color("8CC9FF"), Type.Silver.getChance());
            OreRegistry.createEnderOre("silver", new Color("8CC9FF"), Type.EnderSilver.getChance());
        }
        if (OreDictionary.getOres("oreLead").size() > 0 || ignoreOreDict || OreConfig.dropLead) {
            OreRegistry.createOverworldOre("lead", new Color("2D2563"), Type.Lead.getChance());
            OreRegistry.createEnderOre("lead", new Color("2D2563"), Type.EnderLead.getChance());
        }
        if (OreDictionary.getOres("oreNickel").size() > 0 || ignoreOreDict || OreConfig.dropNickel) {
            OreRegistry.createOverworldOre("nickel", new Color("BAB877"), Type.Nickel.getChance());
            OreRegistry.createNetherOre("nickel", new Color("BAB877"), Type.NetherNickel.getChance());
        }
        if (OreDictionary.getOres("orePlatinum").size() > 0 || ignoreOreDict || OreConfig.dropPlatinum) {
            OreRegistry.createOverworldOre("platinum", new Color("38CDFF"), Type.Platinum.getChance());
            OreRegistry.createEnderOre("platinum", new Color("38CDFF"), Type.EnderPlatinum.getChance());
        }
        if (OreDictionary.getOres("oreAluminum").size() > 0 || OreDictionary.getOres("oreAluminium").size() > 0
                || ignoreOreDict
                || OreConfig.dropAluminum) {
            OreRegistry.createOverworldOre("aluminum", new Color("FFC7C7"), Type.Aluminum.getChance());
            Item ingot = OreRegistry.getIngot("aluminum");
            if (ingot != null) OreRegistry.registerOreDict("aluminium", ingot);
        }
        if (OreDictionary.getOres("oreOsmium").size() > 0 || ignoreOreDict || OreConfig.dropOsmium)
            OreRegistry.createOverworldOre("osmium", new Color("608FC4"), Type.Osmium.getChance());
    }

    public enum Type {

        Iron(OreConfig.ironChance, "F2AB7C"),
        NetherIron(OreConfig.ironChanceNether, "F2AB7C"),
        Gold(OreConfig.goldChance, "FFD000"),
        NetherGold(OreConfig.goldChanceNether, "FFD000"),
        Tin(OreConfig.tinChance, "ABC9B6"),
        EnderTin(OreConfig.tinChanceEnd, "ABC9B6"),
        Copper(OreConfig.copperChance, "FF4D00"),
        NetherCopper(OreConfig.copperChanceNether, "FF4D00"),
        Nickel(OreConfig.nickelChance, "BAB877"),
        NetherNickel(OreConfig.nickelChanceNether, "BAB877"),
        Platinum(OreConfig.platinumChance, "38CDFF"),
        EnderPlatinum(OreConfig.platinumChanceEnd, "38CDFF"),
        Silver(OreConfig.silverChance, "8CC9FF"),
        EnderSilver(OreConfig.silverChanceEnd, "8CC9FF"),
        Lead(OreConfig.leadChance, "2D2563"),
        EnderLead(OreConfig.leadChanceEnd, "2D2563"),
        Aluminum(OreConfig.aluminumChance, "FFC7C7"),
        Osmium(OreConfig.osmiumChance, "608FC4");

        private int chance;

        private final String color;

        Type(int chance, String color) {
            this.chance = chance;
            this.color = color;
        }

        private int getChance() {
            return this.chance;
        }

        public void setChance(int newChance) {
            this.chance = newChance;
        }

        public String getColor() {
            return this.color;
        }
    }

    public enum OreDimension {
        OVERWORLD,
        NETHER,
        ENDER
    }
}
