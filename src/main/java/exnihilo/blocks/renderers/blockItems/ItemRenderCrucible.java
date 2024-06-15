package exnihilo.blocks.renderers.blockItems;

import java.util.Objects;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import exnihilo.blocks.models.ModelCrucible;

public class ItemRenderCrucible implements IItemRenderer {

    private final ModelCrucible model;

    public ItemRenderCrucible(ModelCrucible model) {
        this.model = model;
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return Objects.requireNonNull(type) != ItemRenderType.FIRST_PERSON_MAP;
    }

    @Override
    public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return Objects.requireNonNull(type) != ItemRenderType.FIRST_PERSON_MAP;
    }

    @Override
    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
        renderCrucible(type, item);
    }

    private void renderCrucible(IItemRenderer.ItemRenderType type, ItemStack item) {
        GL11.glPushMatrix();
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        switch (type) {
            case EQUIPPED -> GL11.glTranslatef(-0.5F, -1.5F, 0.5F);
            case EQUIPPED_FIRST_PERSON -> GL11.glTranslatef(0F, -1.6F, 0.6F);
            case ENTITY, INVENTORY -> GL11.glTranslatef(0F, -1.0F, 0F);
            default -> GL11.glTranslatef(0F, 0F, 0F);
        }
        bindTexture();
        this.model.simpleRender(0.0625F);
        GL11.glPopMatrix();
    }

    protected void bindTexture() {
        TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
        if (texturemanager != null) texturemanager.bindTexture(ModelCrucible.textures[0]);
    }
}
