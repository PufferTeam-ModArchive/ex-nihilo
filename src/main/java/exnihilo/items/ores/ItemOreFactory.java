package exnihilo.items.ores;

import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import exnihilo.images.Resource;
import exnihilo.images.TextureFactory;
import exnihilo.proxies.Proxy;
import exnihilo.registries.helpers.Color;

public class ItemOreFactory {

    public static ItemOre MakeOverworldBrokenOre(String name, Color color) {
        String texture_name = "ItemBroken" + formatName(name);
        String item_name = name.toLowerCase() + "_broken";
        ItemOre broken = new ItemOre(item_name);
        ResourceLocation baseTexture = Resource.getItemTextureLocation("exnihilo", "ItemBrokenBase");
        ResourceLocation templateTexture = Resource.getItemTextureLocation("exnihilo", "ItemBrokenTemplate");
        if (!Proxy.runningOnServer()) attachTexture(broken, texture_name, baseTexture, templateTexture, color);
        return broken;
    }

    public static ItemOre MakeNetherBrokenOre(String name, Color color) {
        String texture_name = "ItemBrokenNether" + formatName(name);
        String item_name = "nether_" + name.toLowerCase() + "_broken";
        ItemOre broken = new ItemOre(item_name);
        ResourceLocation baseTexture = Resource.getItemTextureLocation("exnihilo", "ItemBrokenBaseNether");
        ResourceLocation templateTexture = Resource.getItemTextureLocation("exnihilo", "ItemBrokenTemplate");
        if (!Proxy.runningOnServer()) attachTexture(broken, texture_name, baseTexture, templateTexture, color);
        return broken;
    }

    public static ItemOre MakeEnderBrokenOre(String name, Color color) {
        String texture_name = "ItemBrokenEnder" + formatName(name);
        String item_name = "ender_" + name.toLowerCase() + "_broken";
        ItemOre broken = new ItemOre(item_name);
        ResourceLocation baseTexture = Resource.getItemTextureLocation("exnihilo", "ItemBrokenBaseEnder");
        ResourceLocation templateTexture = Resource.getItemTextureLocation("exnihilo", "ItemBrokenTemplate");
        if (!Proxy.runningOnServer()) attachTexture(broken, texture_name, baseTexture, templateTexture, color);
        return broken;
    }

    public static ItemOre MakeCrushedOre(String name, Color color) {
        String texture_name = "ItemCrushed" + formatName(name);
        String item_name = name.toLowerCase() + "_crushed";
        ItemOre crushed = new ItemOre(item_name);
        ResourceLocation baseTexture = Resource.getItemTextureLocation("exnihilo", "ItemCrushedBase");
        ResourceLocation templateTexture = Resource.getItemTextureLocation("exnihilo", "ItemCrushedTemplate");
        if (!Proxy.runningOnServer()) attachTexture(crushed, texture_name, baseTexture, templateTexture, color);
        return crushed;
    }

    public static ItemOre MakePulverizedOre(String name, Color color) {
        String texture_name = "ItemPowdered" + formatName(name);
        String item_name = name.toLowerCase() + "_powdered";
        ItemOre pulverized = new ItemOre(item_name);
        ResourceLocation baseTexture = Resource.getItemTextureLocation("exnihilo", "ItemPowderedBase");
        ResourceLocation templateTexture = Resource.getItemTextureLocation("exnihilo", "ItemPowderedTemplate");
        if (!Proxy.runningOnServer()) attachTexture(pulverized, texture_name, baseTexture, templateTexture, color);
        return pulverized;
    }

    public static ItemOre MakeIngot(String name, Color color) {
        String texture_name = "ItemIngot" + formatName(name);
        String item_name = name.toLowerCase() + "_ingot";
        ItemOre ingot = new ItemOre(item_name);
        ResourceLocation baseTexture = Resource.getItemTextureLocation("exnihilo", "ItemIngotBase");
        ResourceLocation templateTexture = Resource.getItemTextureLocation("exnihilo", "ItemIngotTemplate");
        if (!Proxy.runningOnServer()) attachTexture(ingot, texture_name, baseTexture, templateTexture, color);
        return ingot;
    }

    private static String formatName(String input) {
        String lcase = input.toLowerCase();
        String output = lcase.substring(0, 1).toUpperCase() + lcase.substring(1);
        return output;
    }

    @SideOnly(Side.CLIENT)
    private static void attachTexture(ItemOre item, String name, ResourceLocation base, ResourceLocation template,
            Color color) {
        TextureFactory.makeTexture(item, name, base, template, color);
    }
}
