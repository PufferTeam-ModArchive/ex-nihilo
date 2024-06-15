package exnihilo.images;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import exnihilo.ExNihilo;
import exnihilo.registries.helpers.Color;

public class TextureDynamic extends TextureAtlasSprite {

    private final ResourceLocation template;

    private final ResourceLocation base;

    private final Color color;

    public TextureDynamic(String name, ResourceLocation base, ResourceLocation template, Color color) {
        super(name);
        this.template = template;
        this.base = base;
        this.color = color;
    }

    public static String getTextureName(String name) {
        return "exnihilo:" + name;
    }

    @Override
    public boolean hasCustomLoader(IResourceManager manager, ResourceLocation location) {
        try {
            manager.getResource(location);
        } catch (Exception e) {
            return true;
        }
        ExNihilo.log.info("Icon: " + this.template + " was overwritten by a texturepack or embedded resource.");
        return false;
    }

    @Override
    public boolean load(IResourceManager manager, ResourceLocation location) {
        int mipmapLevels = (Minecraft.getMinecraft()).gameSettings.mipmapLevels;
        try {
            BufferedImage[] imgFinal = new BufferedImage[1 + mipmapLevels];
            imgFinal[0] = tryLoadImage(manager, this.template);
            if (this.color != null) imgFinal[0] = ImageManipulator.Recolor(imgFinal[0], this.color);
            if (this.base != null) {
                BufferedImage imgBase = tryLoadImage(manager, this.base);
                if (imgBase != null) imgFinal[0] = ImageManipulator.Composite(imgBase, imgFinal[0]);
            }
            loadSprite(imgFinal, null, ((Minecraft.getMinecraft()).gameSettings.anisotropicFiltering > 1.0F));
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }

    private BufferedImage tryLoadImage(IResourceManager manager, ResourceLocation location) {
        try {
            IResource res = manager.getResource(location);
            BufferedImage imgOutput = ImageIO.read(res.getInputStream());
            return imgOutput;
        } catch (Exception e) {
            ExNihilo.log.error("Failed to load image: " + location.toString());
            return null;
        }
    }
}
