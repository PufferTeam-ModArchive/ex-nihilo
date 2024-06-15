package exnihilo.blocks.renderers;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;

import org.lwjgl.opengl.GL11;

import exnihilo.blocks.models.ModelSieve;
import exnihilo.blocks.models.ModelSieveContents;
import exnihilo.blocks.models.ModelSieveMesh;
import exnihilo.blocks.tileentities.TileEntitySieve;

public class RenderSieve extends TileEntitySpecialRenderer {

    private final ModelSieve model;

    private final ModelSieveMesh mesh;

    private final ModelSieveContents contents;

    public RenderSieve(ModelSieve model, ModelSieveMesh mesh) {
        this.model = model;
        this.mesh = mesh;
        this.contents = new ModelSieveContents();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
        renderTable(tileentity, x, y, z, f);
        renderMesh(tileentity, x, y, z, f);
        renderContents(tileentity, x, y, z, f);
    }

    private void renderTable(TileEntity tileentity, double x, double y, double z, float f) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        bindSieveTexture(tileentity.getBlockType(), tileentity.getBlockMetadata());
        this.model.simpleRender(0.0625F);
        GL11.glPopMatrix();
    }

    private void renderMesh(TileEntity tileentity, double x, double y, double z, float f) {
        TileEntitySieve sieve = (TileEntitySieve) tileentity;
        if (sieve.getCurrentMesh() != null) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float) x + 0.5F, (float) y + 0.69F, (float) z + 0.5F);
            bindTexture(TextureMap.locationBlocksTexture);
            this.mesh.render(sieve.getCurrentMesh().getSieveRenderIcon());
            GL11.glPopMatrix();
        }
    }

    private void renderContents(TileEntity tileentity, double x, double y, double z, float f) {
        TileEntitySieve sieve = (TileEntitySieve) tileentity;
        if (sieve.getCurrentStack().getBlock() == Blocks.air) {
            return;
        }
        IIcon icon = sieve.getCurrentStack().getBlock().getIcon(0, sieve.getCurrentStack().getMeta());
        bindTexture(TextureMap.locationBlocksTexture);
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + sieve.getAdjustedProgress(), (float) z + 0.5F);
        this.contents.renderTop(icon);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.7F, (float) z + 0.5F);
        this.contents.renderBottom(icon);
        GL11.glPopMatrix();
    }

    public void bindSieveTexture(Block block, int meta) {
        if (meta >= 0) bindTexture(this.model.getSieveTexture(block, meta));
    }
}
