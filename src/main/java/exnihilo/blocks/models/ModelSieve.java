package exnihilo.blocks.models;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class ModelSieve extends ModelBase {

    final ModelRenderer Leg1;

    final ModelRenderer Leg2;

    final ModelRenderer Leg3;

    final ModelRenderer Leg4;

    final ModelRenderer BoxSide1;

    final ModelRenderer BoxSide2;

    final ModelRenderer BoxSide3;

    final ModelRenderer BoxSide4;

    public static final ResourceLocation[] textures = new ResourceLocation[] {
            new ResourceLocation("exnihilo", "textures/blocks/ModelSieveOak.png"),
            new ResourceLocation("exnihilo", "textures/blocks/ModelSieveSpruce.png"),
            new ResourceLocation("exnihilo", "textures/blocks/ModelSieveBirch.png"),
            new ResourceLocation("exnihilo", "textures/blocks/ModelSieveJungle.png"),
            new ResourceLocation("exnihilo", "textures/blocks/ModelSieveAcacia.png"),
            new ResourceLocation("exnihilo", "textures/blocks/ModelSieveDarkOak.png") };

    public ResourceLocation getSieveTexture(Block block, int meta) {
        return textures[meta];
    }

    public ModelSieve() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.Leg1 = new ModelRenderer(this, 0, 0);
        this.Leg1.addBox(0.0F, 0.0F, 0.0F, 1, 11, 1);
        this.Leg1.setRotationPoint(-7.0F, 13.0F, -7.0F);
        this.Leg1.setTextureSize(128, 128);
        this.Leg1.mirror = true;
        setRotation(this.Leg1, 0.0F, 0.0F, 0.0F);
        this.Leg2 = new ModelRenderer(this, 0, 0);
        this.Leg2.addBox(0.0F, 0.0F, 0.0F, 1, 11, 1);
        this.Leg2.setRotationPoint(-7.0F, 13.0F, 6.0F);
        this.Leg2.setTextureSize(128, 128);
        this.Leg2.mirror = true;
        setRotation(this.Leg2, 0.0F, 0.0F, 0.0F);
        this.Leg3 = new ModelRenderer(this, 0, 0);
        this.Leg3.addBox(0.0F, 0.0F, 0.0F, 1, 11, 1);
        this.Leg3.setRotationPoint(6.0F, 13.0F, 6.0F);
        this.Leg3.setTextureSize(128, 128);
        this.Leg3.mirror = true;
        setRotation(this.Leg3, 0.0F, 0.0F, 0.0F);
        this.Leg4 = new ModelRenderer(this, 0, 0);
        this.Leg4.addBox(0.0F, 0.0F, 0.0F, 1, 11, 1);
        this.Leg4.setRotationPoint(6.0F, 13.0F, -7.0F);
        this.Leg4.setTextureSize(128, 128);
        this.Leg4.mirror = true;
        setRotation(this.Leg4, 0.0F, 0.0F, 0.0F);
        this.BoxSide1 = new ModelRenderer(this, 6, 0);
        this.BoxSide1.addBox(0.0F, 0.0F, 0.0F, 16, 6, 1);
        this.BoxSide1.setRotationPoint(-8.0F, 8.0F, -8.0F);
        this.BoxSide1.setTextureSize(128, 128);
        this.BoxSide1.mirror = true;
        setRotation(this.BoxSide1, 0.0F, 0.0F, 0.0F);
        this.BoxSide2 = new ModelRenderer(this, 6, 8);
        this.BoxSide2.addBox(0.0F, 0.0F, 0.0F, 16, 6, 1);
        this.BoxSide2.setRotationPoint(-8.0F, 8.0F, 7.0F);
        this.BoxSide2.setTextureSize(128, 128);
        this.BoxSide2.mirror = true;
        setRotation(this.BoxSide2, 0.0F, 0.0F, 0.0F);
        this.BoxSide3 = new ModelRenderer(this, 6, 16);
        this.BoxSide3.addBox(0.0F, 0.0F, 0.0F, 1, 6, 14);
        this.BoxSide3.setRotationPoint(7.0F, 8.0F, -7.0F);
        this.BoxSide3.setTextureSize(128, 128);
        this.BoxSide3.mirror = true;
        setRotation(this.BoxSide3, 0.0F, 0.0F, 0.0F);
        this.BoxSide4 = new ModelRenderer(this, 6, 37);
        this.BoxSide4.addBox(0.0F, 0.0F, 0.0F, 1, 6, 14);
        this.BoxSide4.setRotationPoint(-8.0F, 8.0F, -7.0F);
        this.BoxSide4.setTextureSize(128, 128);
        this.BoxSide4.mirror = true;
        setRotation(this.BoxSide4, 0.0F, 0.0F, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.Leg1.render(f5);
        this.Leg2.render(f5);
        this.Leg3.render(f5);
        this.Leg4.render(f5);
        this.BoxSide1.render(f5);
        this.BoxSide2.render(f5);
        this.BoxSide3.render(f5);
        this.BoxSide4.render(f5);
    }

    public void simpleRender(float scale) {
        this.Leg1.render(scale);
        this.Leg2.render(scale);
        this.Leg3.render(scale);
        this.Leg4.render(scale);
        this.BoxSide1.render(scale);
        this.BoxSide2.render(scale);
        this.BoxSide3.render(scale);
        this.BoxSide4.render(scale);
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
