package exnihilo.images;

import java.awt.image.BufferedImage;

import exnihilo.ExNihilo;
import exnihilo.registries.helpers.Color;

public class ImageManipulator {

    public static BufferedImage Recolor(BufferedImage template, Color colorNew) {
        int w = template.getWidth();
        int h = template.getHeight();
        BufferedImage imgOutput = new BufferedImage(w, h, template.getType());
        int[] templateData = new int[w * h];
        int[] outputData = new int[w * h];
        template.getRGB(0, 0, w, h, templateData, 0, w);
        for (int i = 0; i < templateData.length; i++) {
            Color colorRaw = new Color(templateData[i], false);
            if (colorRaw.a > 0.0F) {
                float a = colorRaw.a;
                float r = colorNew.r;
                float g = colorNew.g;
                float b = colorNew.b;
                outputData[i] = (new Color(r, g, b, a)).toInt();
            } else {
                outputData[i] = templateData[i];
            }
        }
        imgOutput.setRGB(0, 0, w, h, outputData, 0, w);
        return imgOutput;
    }

    public static BufferedImage Composite(BufferedImage imgBackground, BufferedImage imgForeground) {
        if (!normalCompositePossible(imgBackground, imgForeground)) {
            ExNihilo.log.error("Images with different sizes can't be composited.");
            return null;
        }
        int w = imgBackground.getWidth();
        int h = imgBackground.getHeight();
        BufferedImage imgOutput = new BufferedImage(w, h, imgBackground.getType());
        int[] backgroundData = new int[w * h];
        int[] foregroundData = new int[w * h];
        int[] outputData = new int[w * h];
        imgBackground.getRGB(0, 0, w, h, backgroundData, 0, w);
        imgForeground.getRGB(0, 0, w, h, foregroundData, 0, w);
        for (int i = 0; i < backgroundData.length; i++) {
            Color colorBackground = new Color(backgroundData[i]);
            Color colorForeground = new Color(foregroundData[i], false);
            outputData[i] = backgroundData[i];
            if (colorForeground.a > 0.0F) {
                float alpha = colorForeground.a;
                float a = colorBackground.a;
                float r = colorForeground.r * alpha + colorBackground.r * (1.0F - alpha);
                float g = colorForeground.g * alpha + colorBackground.g * (1.0F - alpha);
                float b = colorForeground.b * alpha + colorBackground.b * (1.0F - alpha);
                outputData[i] = (new Color(r, g, b, a)).toInt();
            } else {
                outputData[i] = backgroundData[i];
            }
        }
        imgOutput.setRGB(0, 0, w, h, outputData, 0, w);
        return imgOutput;
    }

    private static boolean normalCompositePossible(BufferedImage imgBackground, BufferedImage imgForeground) {
        return (imgBackground.getWidth() == imgForeground.getWidth()
                && imgBackground.getHeight() == imgForeground.getHeight());
    }
}
