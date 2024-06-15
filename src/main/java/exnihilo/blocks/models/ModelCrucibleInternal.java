package exnihilo.blocks.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;

import org.lwjgl.opengl.GL11;

import exnihilo.registries.helpers.Color;

public class ModelCrucibleInternal extends ModelBase {

    public void render(Color color, IIcon icon, boolean blend) {
        Tessellator tessellator = Tessellator.instance;
        if (blend) {
            GL11.glDisable(2896);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
        }
        double length = 1.0D;
        double width = 1.0D;
        double x = 0.0D - width / 2.0D;
        double y = 0.0D;
        double z = 0.0D - length / 2.0D;
        double minU = icon.getMinU();
        double maxU = icon.getMaxU();
        double minV = icon.getMinV();
        double maxV = icon.getMaxV();
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(color.r, color.g, color.b, color.a);
        tessellator.addVertexWithUV(x + width, y, z + length, minU, minV);
        tessellator.addVertexWithUV(x + width, y, z, minU, maxV);
        tessellator.addVertexWithUV(x, y, z, maxU, maxV);
        tessellator.addVertexWithUV(x, y, z + length, maxU, minV);
        tessellator.draw();
        if (blend) {
            GL11.glEnable(2896);
            GL11.glDisable(3042);
        }
    }
}
