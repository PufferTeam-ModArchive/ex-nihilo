package exnihilo.blocks.renderers.blockItems;

import java.util.Objects;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import exnihilo.blocks.models.ModelBarrel;

public class ItemRenderBarrel implements IItemRenderer {

    private final ModelBarrel model;

    public ItemRenderBarrel(ModelBarrel model) {
        this.model = model;
    }

    @Override
    public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
        return Objects.requireNonNull(type) != ItemRenderType.FIRST_PERSON_MAP;
    }

    @Override
    public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item,
            IItemRenderer.ItemRendererHelper helper) {
        return Objects.requireNonNull(type) != ItemRenderType.FIRST_PERSON_MAP;
    }

    @Override
    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();
        GL11.glScalef(-0.8F, -1.0F, 0.8F);
        switch (type) {
            case EQUIPPED -> GL11.glTranslatef(-0.5F, -1.5F, 0.5F);
            case EQUIPPED_FIRST_PERSON -> GL11.glTranslatef(0F, -1.6F, 0.6F);
            case ENTITY, INVENTORY -> GL11.glTranslatef(0F, -1.0F, 0F);
            default -> GL11.glTranslatef(0F, 0F, 0F);
        }
        bindTexture(Block.getBlockFromItem(item.getItem()), item.getItemDamage());
        this.model.simpleRender(0.0625F);
        GL11.glPopMatrix();
    }

    protected void bindTexture(Block block, int meta) {
        TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
        if (texturemanager != null) if (meta >= 0) texturemanager.bindTexture(this.model.getBarrelTexture(block, meta));
    }
}
