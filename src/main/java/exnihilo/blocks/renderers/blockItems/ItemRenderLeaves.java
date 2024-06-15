package exnihilo.blocks.renderers.blockItems;

import java.util.Objects;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import exnihilo.blocks.models.ModelBlock;
import exnihilo.registries.helpers.Color;

public class ItemRenderLeaves implements IItemRenderer {

    private final ModelBlock model;

    public ItemRenderLeaves(ModelBlock model) {
        this.model = model;
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return Objects.requireNonNull(type) != ItemRenderType.FIRST_PERSON_MAP;
    }

    @Override
    public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item,
            IItemRenderer.ItemRendererHelper helper) {
        return Objects.requireNonNull(type) != ItemRenderType.FIRST_PERSON_MAP;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        boolean blend = false;
        GL11.glPushMatrix();
        switch (type) {
            case EQUIPPED:
            case EQUIPPED_FIRST_PERSON:
            case ENTITY:
                break;
            case INVENTORY:
                GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                blend = true;
                break;
            default:
                GL11.glTranslatef(0.0F, 0.0F, 0.0F);
                break;
        }
        bindTexture();
        this.model.render(
                new Color(Blocks.leaves.getRenderColor(item.getItemDamage())),
                Blocks.leaves.getIcon(0, item.getItemDamage()),
                2147483647,
                blend);
        GL11.glPopMatrix();
    }

    protected void bindTexture() {
        TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
        if (texturemanager != null) texturemanager.bindTexture(TextureMap.locationBlocksTexture);
    }
}
