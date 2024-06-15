package exnihilo.blocks.renderers;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import exnihilo.blocks.models.ModelCrucibleRaw;

public class RenderCrucibleUnfired extends TileEntitySpecialRenderer {

    private final ModelCrucibleRaw model;

    public RenderCrucibleUnfired(ModelCrucibleRaw model) {
        this.model = model;
    }

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
        drawCrucible(tileentity, x, y, z, f);
    }

    private void drawCrucible(TileEntity tileentity, double x, double y, double z, float f) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        bindCrucibleTexture();
        this.model.simpleRender(0.0625F);
        GL11.glPopMatrix();
    }

    public void bindCrucibleTexture() {
        bindTexture(ModelCrucibleRaw.textures[0]);
    }
}
