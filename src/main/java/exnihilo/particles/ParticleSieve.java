package exnihilo.particles;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleSieve extends EntityFX {

    public ParticleSieve(World par1World, double par2, double par4, double par6, double par8, double par10,
            double par12, IIcon icon) {
        super(par1World, par2, par4, par6, par8, par10, par12);
        this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
        setParticleIcon(icon);
        setSize(0.02F, 0.02F);
        this.particleScale = 0.07F + this.rand.nextFloat() * 0.1F;
        this.motionX *= 0.019999999552965164D;
        this.motionY *= -0.4D;
        this.motionZ *= 0.019999999552965164D;
        this.particleMaxAge = (int) (20.0D / (Math.random() * 0.2D + 0.2D));
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.99D;
        this.motionY *= 0.99D;
        this.motionZ *= 0.99D;
        nextTextureIndexX();
        if (this.particleMaxAge-- <= 0) setDead();
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

    @Override
    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6,
            float par7) {
        float f6 = (this.particleTextureIndexX + this.particleTextureJitterX / 4.0F) / 16.0F;
        float f7 = f6 + 0.015609375F;
        float f8 = (this.particleTextureIndexY + this.particleTextureJitterY / 4.0F) / 16.0F;
        float f9 = f8 + 0.015609375F;
        float f10 = 0.1F * this.particleScale;
        if (this.particleIcon != null) {
            f6 = this.particleIcon.getInterpolatedU((this.particleTextureJitterX / 4.0F * 16.0F));
            f7 = this.particleIcon.getInterpolatedU(((this.particleTextureJitterX + 1.0F) / 4.0F * 16.0F));
            f8 = this.particleIcon.getInterpolatedV((this.particleTextureJitterY / 4.0F * 16.0F));
            f9 = this.particleIcon.getInterpolatedV(((this.particleTextureJitterY + 1.0F) / 4.0F * 16.0F));
        }
        float f11 = (float) (this.prevPosX + (this.posX - this.prevPosX) * par2 - interpPosX);
        float f12 = (float) (this.prevPosY + (this.posY - this.prevPosY) * par2 - interpPosY);
        float f13 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * par2 - interpPosZ);
        float f14 = 1.0F;
        par1Tessellator.setColorOpaque_F(f14 * this.particleRed, f14 * this.particleGreen, f14 * this.particleBlue);
        par1Tessellator.addVertexWithUV(
                (f11 - par3 * f10 - par6 * f10),
                (f12 - par4 * f10),
                (f13 - par5 * f10 - par7 * f10),
                f6,
                f9);
        par1Tessellator.addVertexWithUV(
                (f11 - par3 * f10 + par6 * f10),
                (f12 + par4 * f10),
                (f13 - par5 * f10 + par7 * f10),
                f6,
                f8);
        par1Tessellator.addVertexWithUV(
                (f11 + par3 * f10 + par6 * f10),
                (f12 + par4 * f10),
                (f13 + par5 * f10 + par7 * f10),
                f7,
                f8);
        par1Tessellator.addVertexWithUV(
                (f11 + par3 * f10 - par6 * f10),
                (f12 - par4 * f10),
                (f13 + par5 * f10 - par7 * f10),
                f7,
                f9);
    }
}
