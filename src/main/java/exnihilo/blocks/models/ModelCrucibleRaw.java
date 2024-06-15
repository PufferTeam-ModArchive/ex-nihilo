package exnihilo.blocks.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class ModelCrucibleRaw extends ModelBase {

    public static final ResourceLocation[] textures = new ResourceLocation[] {
            new ResourceLocation("exnihilo", "textures/blocks/ModelCrucibleUnfired.png") };

    final ModelRenderer Bottom3;

    final ModelRenderer Side1;

    final ModelRenderer Side2;

    final ModelRenderer Side3;

    final ModelRenderer Side4;

    final ModelRenderer Leg1;

    final ModelRenderer Leg2;

    final ModelRenderer Leg3;

    final ModelRenderer Leg4;

    public ModelCrucibleRaw() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.Bottom3 = new ModelRenderer(this, 0, 70);
        this.Bottom3.addBox(0.0F, 0.0F, 0.0F, 14, 1, 14);
        this.Bottom3.setRotationPoint(-7.0F, 21.0F, -7.0F);
        this.Bottom3.setTextureSize(64, 32);
        this.Bottom3.mirror = true;
        setRotation(this.Bottom3, 0.0F, 0.0F, 0.0F);
        this.Side1 = new ModelRenderer(this, 0, 0);
        this.Side1.addBox(0.0F, 0.0F, 0.0F, 16, 13, 1);
        this.Side1.setRotationPoint(-8.0F, 8.0F, -8.0F);
        this.Side1.setTextureSize(64, 32);
        this.Side1.mirror = true;
        setRotation(this.Side1, 0.0F, 0.0F, 0.0F);
        this.Side2 = new ModelRenderer(this, 0, 0);
        this.Side2.addBox(0.0F, 0.0F, 0.0F, 16, 13, 1);
        this.Side2.setRotationPoint(-8.0F, 8.0F, 7.0F);
        this.Side2.setTextureSize(64, 32);
        this.Side2.mirror = true;
        setRotation(this.Side2, 0.0F, 0.0F, 0.0F);
        this.Side3 = new ModelRenderer(this, 0, 35);
        this.Side3.addBox(0.0F, 0.0F, 0.0F, 1, 13, 14);
        this.Side3.setRotationPoint(7.0F, 8.0F, -7.0F);
        this.Side3.setTextureSize(64, 32);
        this.Side3.mirror = true;
        setRotation(this.Side3, 0.0F, 0.0F, 0.0F);
        this.Side4 = new ModelRenderer(this, 0, 35);
        this.Side4.addBox(0.0F, 0.0F, 0.0F, 1, 13, 14);
        this.Side4.setRotationPoint(-8.0F, 8.0F, -7.0F);
        this.Side4.setTextureSize(64, 32);
        this.Side4.mirror = true;
        setRotation(this.Side4, 0.0F, 0.0F, 0.0F);
        this.Leg1 = new ModelRenderer(this, 70, 0);
        this.Leg1.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
        this.Leg1.setRotationPoint(-8.0F, 21.0F, -8.0F);
        this.Leg1.setTextureSize(64, 32);
        this.Leg1.mirror = true;
        setRotation(this.Leg1, 0.0F, 0.0F, 0.0F);
        this.Leg2 = new ModelRenderer(this, 70, 0);
        this.Leg2.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
        this.Leg2.setRotationPoint(7.0F, 21.0F, 7.0F);
        this.Leg2.setTextureSize(64, 32);
        this.Leg2.mirror = true;
        setRotation(this.Leg2, 0.0F, 0.0F, 0.0F);
        this.Leg3 = new ModelRenderer(this, 70, 0);
        this.Leg3.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
        this.Leg3.setRotationPoint(-8.0F, 21.0F, 7.0F);
        this.Leg3.setTextureSize(64, 32);
        this.Leg3.mirror = true;
        setRotation(this.Leg3, 0.0F, 0.0F, 0.0F);
        this.Leg4 = new ModelRenderer(this, 70, 0);
        this.Leg4.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
        this.Leg4.setRotationPoint(7.0F, 21.0F, -8.0F);
        this.Leg4.setTextureSize(64, 32);
        this.Leg4.mirror = true;
        setRotation(this.Leg4, 0.0F, 0.0F, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);
        this.Bottom3.render(f5);
        this.Side1.render(f5);
        this.Side2.render(f5);
        this.Side3.render(f5);
        this.Side4.render(f5);
        this.Leg1.render(f5);
        this.Leg2.render(f5);
        this.Leg3.render(f5);
        this.Leg4.render(f5);
    }

    public void simpleRender(float scale) {
        this.Bottom3.render(scale);
        this.Side1.render(scale);
        this.Side2.render(scale);
        this.Side3.render(scale);
        this.Side4.render(scale);
        this.Leg1.render(scale);
        this.Leg2.render(scale);
        this.Leg3.render(scale);
        this.Leg4.render(scale);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        setRotationAngles(f, f1, f2, f3, f4, f5, null);
    }
}
