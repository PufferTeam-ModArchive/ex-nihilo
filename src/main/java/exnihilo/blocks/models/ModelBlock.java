package exnihilo.blocks.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;

import org.lwjgl.opengl.GL11;

import exnihilo.registries.helpers.Color;

public class ModelBlock extends ModelBase {

    public void render(Color color, IIcon icon, int brightness, boolean blend) {
        Tessellator tessellator = Tessellator.instance;
        GL11.glDisable(2896);
        if (blend) {
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
        }
        double minU = icon.getMinU();
        double maxU = icon.getMaxU();
        double minV = icon.getMinV();
        double maxV = icon.getMaxV();
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(color.r, color.g, color.b, color.a);
        DrawTop(tessellator, minU, maxU, minV, maxV, brightness);
        DrawSide1(tessellator, minU, maxU, minV, maxV, brightness);
        DrawSide2(tessellator, minU, maxU, minV, maxV, brightness);
        DrawSide3(tessellator, minU, maxU, minV, maxV, brightness);
        DrawSide4(tessellator, minU, maxU, minV, maxV, brightness);
        DrawBottom(tessellator, minU, maxU, minV, maxV, brightness);
        tessellator.draw();
        if (blend) GL11.glDisable(3042);
        GL11.glEnable(2896);
    }

    public void DrawTop(Tessellator tessellator, double minU, double maxU, double minV, double maxV, int brightness) {
        tessellator.setBrightness(brightness);
        tessellator.addVertexWithUV(0.0D, 1.0D, 0.0D, minU, minV);
        tessellator.addVertexWithUV(0.0D, 1.0D, 1.0D, minU, maxV);
        tessellator.addVertexWithUV(1.0D, 1.0D, 1.0D, maxU, maxV);
        tessellator.addVertexWithUV(1.0D, 1.0D, 0.0D, maxU, minV);
    }

    public void DrawSide1(Tessellator tessellator, double minU, double maxU, double minV, double maxV, int brightness) {
        tessellator.setBrightness(brightness);
        tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, maxU, maxV);
        tessellator.addVertexWithUV(0.0D, 1.0D, 0.0D, maxU, minV);
        tessellator.addVertexWithUV(1.0D, 1.0D, 0.0D, minU, minV);
        tessellator.addVertexWithUV(1.0D, 0.0D, 0.0D, minU, maxV);
    }

    public void DrawSide2(Tessellator tessellator, double minU, double maxU, double minV, double maxV, int brightness) {
        tessellator.setBrightness(brightness);
        tessellator.addVertexWithUV(1.0D, 0.0D, 0.0D, maxU, maxV);
        tessellator.addVertexWithUV(1.0D, 1.0D, 0.0D, maxU, minV);
        tessellator.addVertexWithUV(1.0D, 1.0D, 1.0D, minU, minV);
        tessellator.addVertexWithUV(1.0D, 0.0D, 1.0D, minU, maxV);
    }

    public void DrawSide3(Tessellator tessellator, double minU, double maxU, double minV, double maxV, int brightness) {
        tessellator.setBrightness(brightness);
        tessellator.addVertexWithUV(1.0D, 0.0D, 1.0D, maxU, maxV);
        tessellator.addVertexWithUV(1.0D, 1.0D, 1.0D, maxU, minV);
        tessellator.addVertexWithUV(0.0D, 1.0D, 1.0D, minU, minV);
        tessellator.addVertexWithUV(0.0D, 0.0D, 1.0D, minU, maxV);
    }

    public void DrawSide4(Tessellator tessellator, double minU, double maxU, double minV, double maxV, int brightness) {
        tessellator.setBrightness(brightness);
        tessellator.addVertexWithUV(0.0D, 0.0D, 1.0D, maxU, maxV);
        tessellator.addVertexWithUV(0.0D, 1.0D, 1.0D, maxU, minV);
        tessellator.addVertexWithUV(0.0D, 1.0D, 0.0D, minU, minV);
        tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, minU, maxV);
    }

    public void DrawBottom(Tessellator tessellator, double minU, double maxU, double minV, double maxV,
            int brightness) {
        tessellator.setBrightness(brightness);
        tessellator.addVertexWithUV(0.0D, 0.0D, 1.0D, minU, maxV);
        tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, minU, minV);
        tessellator.addVertexWithUV(1.0D, 0.0D, 0.0D, maxU, minV);
        tessellator.addVertexWithUV(1.0D, 0.0D, 1.0D, maxU, maxV);
    }
}
