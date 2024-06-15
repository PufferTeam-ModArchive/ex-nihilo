package exnihilo.registries.helpers;

public class Color {

    public final float r;

    public final float g;

    public final float b;

    public final float a;

    public Color(float red, float green, float blue, float alpha) {
        this.r = red;
        this.g = green;
        this.b = blue;
        this.a = alpha;
    }

    public Color(int color) {
        this(color, true);
    }

    public Color(int color, boolean ignoreAlpha) {
        if (ignoreAlpha) {
            this.a = 1.0F;
        } else {
            this.a = (color >> 24 & 0xFF) / 255.0F;
        }
        this.r = (color >> 16 & 0xFF) / 255.0F;
        this.g = (color >> 8 & 0xFF) / 255.0F;
        this.b = (color & 0xFF) / 255.0F;
    }

    public Color(String hex) {
        this(Integer.parseInt(hex, 16));
    }

    public int toInt() {
        int color = 0;
        color |= (int) (this.a * 255.0F) << 24;
        color |= (int) (this.r * 255.0F) << 16;
        color |= (int) (this.g * 255.0F) << 8;
        color |= (int) (this.b * 255.0F);
        return color;
    }

    public static Color average(Color colorA, Color colorB, float percentage) {
        float avgR = colorA.r + (colorB.r - colorA.r) * percentage;
        float avgG = colorA.g + (colorB.g - colorA.g) * percentage;
        float avgB = colorA.b + (colorB.b - colorA.b) * percentage;
        float avgA = colorA.a + (colorB.a - colorA.a) * percentage;
        return new Color(avgR, avgG, avgB, avgA);
    }
}
