package exnihilo.blocks.models;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import exnihilo.ENBlocks;

public class ModelBarrel extends ModelBase {

    private static final ResourceLocation[] textures = new ResourceLocation[] {
            new ResourceLocation("exnihilo", "textures/blocks/ModelBarrelOak.png"),
            new ResourceLocation("exnihilo", "textures/blocks/ModelBarrelSpruce.png"),
            new ResourceLocation("exnihilo", "textures/blocks/ModelBarrelBirch.png"),
            new ResourceLocation("exnihilo", "textures/blocks/ModelBarrelJungle.png"),
            new ResourceLocation("exnihilo", "textures/blocks/ModelBarrelAcacia.png"),
            new ResourceLocation("exnihilo", "textures/blocks/ModelBarrelDarkOak.png"),
            new ResourceLocation("exnihilo", "textures/blocks/ModelBarrelStone.png") };

    public final ModelRenderer bottom;

    public final ModelRenderer side1;

    public final ModelRenderer side2;

    public final ModelRenderer side3;

    public final ModelRenderer side4;

    public ResourceLocation getBarrelTexture(Block block, int meta) {
        if (block == ENBlocks.Barrel) return textures[meta];
        if (block == ENBlocks.BarrelStone) return textures[6];
        return null;
    }

    public ModelBarrel() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.bottom = new ModelRenderer(this, 0, 0);
        this.bottom.addBox(-7.0F, 0.0F, -7.0F, 14, 1, 14);
        this.bottom.setRotationPoint(0.0F, 23.0F, 0.0F);
        this.bottom.setTextureSize(128, 128);
        this.bottom.mirror = true;
        setRotation(this.bottom, 0.0F, 0.0F, 0.0F);
        this.side1 = new ModelRenderer(this, 0, 16);
        this.side1.addBox(-8.0F, -8.0F, 0.0F, 16, 16, 1);
        this.side1.setRotationPoint(0.0F, 16.0F, 7.0F);
        this.side1.setTextureSize(128, 128);
        this.side1.mirror = true;
        setRotation(this.side1, 0.0F, 0.0F, 0.0F);
        this.side2 = new ModelRenderer(this, 0, 34);
        this.side2.addBox(-8.0F, -8.0F, 0.0F, 16, 16, 1);
        this.side2.setRotationPoint(0.0F, 16.0F, -8.0F);
        this.side2.setTextureSize(128, 128);
        this.side2.mirror = true;
        setRotation(this.side2, 0.0F, 0.0F, 0.0F);
        this.side3 = new ModelRenderer(this, 35, 16);
        this.side3.addBox(0.0F, -8.0F, -7.0F, 1, 16, 14);
        this.side3.setRotationPoint(-8.0F, 16.0F, 0.0F);
        this.side3.setTextureSize(128, 128);
        this.side3.mirror = true;
        setRotation(this.side3, 0.0F, 0.0F, 0.0F);
        this.side4 = new ModelRenderer(this, 66, 16);
        this.side4.addBox(0.0F, -8.0F, -7.0F, 1, 16, 14);
        this.side4.setRotationPoint(7.0F, 16.0F, 0.0F);
        this.side4.setTextureSize(128, 128);
        this.side4.mirror = true;
        setRotation(this.side4, 0.0F, 0.0F, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.bottom.render(f5);
        this.side1.render(f5);
        this.side2.render(f5);
        this.side3.render(f5);
        this.side4.render(f5);
    }

    public void simpleRender(float scale) {
        this.bottom.render(scale);
        this.side1.render(scale);
        this.side2.render(scale);
        this.side3.render(scale);
        this.side4.render(scale);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }
}
