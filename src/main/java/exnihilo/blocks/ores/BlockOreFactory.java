package exnihilo.blocks.ores;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import exnihilo.ExNihilo;
import exnihilo.images.Resource;
import exnihilo.images.TextureFactory;
import exnihilo.proxies.Proxy;
import exnihilo.registries.helpers.Color;

public class BlockOreFactory {

    public static BlockOre MakeOverworldGravel(String name, Color color) {
        String texture_name = "IconGravel" + formatName(name);
        String block_name = name.toLowerCase() + "_gravel";
        BlockOre gravel = new BlockOre(block_name);
        ResourceLocation baseTexture = Resource.getBlockTextureLocation("exnihilo", "IconGravelBase");
        ResourceLocation templateTexture = Resource.getBlockTextureLocation("exnihilo", "IconGravelTemplate");
        if (!Proxy.runningOnServer()) attachTexture(gravel, texture_name, baseTexture, templateTexture, color);
        gravel.setHardness(0.8F).setStepSound(Block.soundTypeGravel).setBlockName(ExNihilo.MODID + "." + block_name);
        return gravel;
    }

    public static BlockOre MakeNetherGravel(String name, Color color) {
        String texture_name = "IconGravelNether" + formatName(name);
        String block_name = "nether_" + name.toLowerCase() + "_gravel";
        BlockOre gravel = new BlockOre(block_name);
        ResourceLocation baseTexture = Resource.getBlockTextureLocation("exnihilo", "IconGravelBaseNether");
        ResourceLocation templateTexture = Resource.getBlockTextureLocation("exnihilo", "IconGravelTemplate");
        if (!Proxy.runningOnServer()) attachTexture(gravel, texture_name, baseTexture, templateTexture, color);
        gravel.setHardness(0.8F).setStepSound(Block.soundTypeGravel).setBlockName(ExNihilo.MODID + "." + block_name);
        return gravel;
    }

    public static BlockOre MakeEnderGravel(String name, Color color) {
        String texture_name = "IconGravelEnder" + formatName(name);
        String block_name = "ender_" + name.toLowerCase() + "_gravel";
        BlockOre gravel = new BlockOre(block_name);
        ResourceLocation baseTexture = Resource.getBlockTextureLocation("exnihilo", "IconGravelBaseEnder");
        ResourceLocation templateTexture = Resource.getBlockTextureLocation("exnihilo", "IconGravelTemplate");
        if (!Proxy.runningOnServer()) attachTexture(gravel, texture_name, baseTexture, templateTexture, color);
        gravel.setHardness(0.8F).setStepSound(Block.soundTypeGravel).setBlockName(ExNihilo.MODID + "." + block_name);
        return gravel;
    }

    public static BlockOre MakeSand(String name, Color color) {
        String texture_name = "IconSand" + formatName(name);
        String block_name = name.toLowerCase() + "_sand";
        BlockOre sand = new BlockOre(block_name);
        ResourceLocation baseTexture = Resource.getBlockTextureLocation("exnihilo", "IconSandBase");
        ResourceLocation templateTexture = Resource.getBlockTextureLocation("exnihilo", "IconSandTemplate");
        if (!Proxy.runningOnServer()) attachTexture(sand, texture_name, baseTexture, templateTexture, color);
        sand.setHardness(0.6F).setStepSound(Block.soundTypeSand).setBlockName(ExNihilo.MODID + "." + block_name);
        return sand;
    }

    public static BlockOre MakeDust(String name, Color color) {
        String texture_name = "IconDust" + formatName(name);
        String block_name = name.toLowerCase() + "_dust";
        BlockOre dust = new BlockOre(block_name);
        ResourceLocation baseTexture = Resource.getBlockTextureLocation("exnihilo", "IconDustBase");
        ResourceLocation templateTexture = Resource.getBlockTextureLocation("exnihilo", "IconDustTemplate");
        if (!Proxy.runningOnServer()) attachTexture(dust, texture_name, baseTexture, templateTexture, color);
        dust.setHardness(0.4F).setStepSound(Block.soundTypeSnow).setBlockName(ExNihilo.MODID + "." + block_name);
        return dust;
    }

    @SideOnly(Side.CLIENT)
    private static void attachTexture(BlockOre block, String name, ResourceLocation base, ResourceLocation template,
            Color color) {
        TextureFactory.makeTexture(block, name, base, template, color);
    }

    private static String formatName(String input) {
        String lcase = input.toLowerCase();
        return lcase.substring(0, 1).toUpperCase() + lcase.substring(1);
    }
}
