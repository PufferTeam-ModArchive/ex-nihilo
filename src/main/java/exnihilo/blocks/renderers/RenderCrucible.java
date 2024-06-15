package exnihilo.blocks.renderers;

import java.util.Objects;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

import org.lwjgl.opengl.GL11;

import exnihilo.blocks.models.ModelCrucible;
import exnihilo.blocks.models.ModelCrucibleInternal;
import exnihilo.blocks.tileentities.TileEntityCrucible;
import exnihilo.registries.ColorRegistry;
import exnihilo.registries.helpers.Color;

public class RenderCrucible extends TileEntitySpecialRenderer {

    private final ModelCrucible model;

    private final ModelCrucibleInternal internal;

    public RenderCrucible(ModelCrucible model) {
        this.model = model;
        this.internal = new ModelCrucibleInternal();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
        drawCrucible(tileentity, x, y, z, f);
        drawContents(tileentity, x, y, z, f);
    }

    private void drawCrucible(TileEntity tileentity, double x, double y, double z, float f) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        bindCrucibleTexture();
        this.model.simpleRender(0.0625F);
        GL11.glPopMatrix();
    }

    private void drawContents(TileEntity tileentity, double x, double y, double z, float f) {
        TileEntityCrucible crucible = (TileEntityCrucible) tileentity;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + crucible.getAdjustedVolume(), (float) z + 0.5F);
        GL11.glScalef(0.92F, 1.0F, 0.92F);
        bindInternalTexture();
        if (crucible.hasSolids() && !crucible.renderFluid()) {
            renderSolid(tileentity);
        } else {
            if (Objects.requireNonNull(crucible.mode) == TileEntityCrucible.CrucibleMode.USED) {
                renderFluid(tileentity);
            }
        }
        GL11.glPopMatrix();
    }

    private void renderSolid(TileEntity tileentity) {
        TileEntityCrucible crucible = (TileEntityCrucible) tileentity;
        IIcon icon = crucible.getContentIcon();
        Color color = ColorRegistry.color("white");
        this.internal.render(color, icon, false);
    }

    private void renderFluid(TileEntity tileentity) {
        TileEntityCrucible crucible = (TileEntityCrucible) tileentity;
        Fluid content = crucible.fluid.getFluid();
        IIcon icon = content.getIcon();
        Color color = new Color(content.getColor());
        this.internal.render(color, icon, true);
    }

    public void bindCrucibleTexture() {
        bindTexture(ModelCrucible.textures[0]);
    }

    public void bindInternalTexture() {
        ResourceLocation fluidTexture = TextureMap.locationBlocksTexture;
        bindTexture(fluidTexture);
    }
}
