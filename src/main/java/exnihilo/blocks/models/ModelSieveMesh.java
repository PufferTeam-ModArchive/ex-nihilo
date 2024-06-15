package exnihilo.blocks.models;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;

import org.lwjgl.opengl.GL11;

public class ModelSieveMesh {

    public void render(IIcon icon) {
        Tessellator tessellator = Tessellator.instance;
        GL11.glDisable(2896);
        GL11.glDisable(2884);
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
        tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
        tessellator.addVertexWithUV(x + width, y, z + length, minU, minV);
        tessellator.addVertexWithUV(x + width, y, z, minU, maxV);
        tessellator.addVertexWithUV(x, y, z, maxU, maxV);
        tessellator.addVertexWithUV(x, y, z + length, maxU, minV);
        tessellator.draw();
        GL11.glEnable(2896);
        GL11.glEnable(2884);
    }
}
