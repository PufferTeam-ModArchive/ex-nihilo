package exnihilo.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityStone extends EntityThrowable {

    public EntityStone(World par1World) {
        super(par1World);
    }

    public EntityStone(World par1World, EntityLivingBase par2EntityLivingBase) {
        super(par1World, par2EntityLivingBase);
    }

    public EntityStone(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6);
    }

    @Override
    protected void onImpact(MovingObjectPosition position) {
        if (position.entityHit != null)
            position.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 0.5F);
        for (int i = 0; i < 10; i++) this.worldObj.spawnParticle(
                "townaura",
                position.hitVec.xCoord,
                position.hitVec.yCoord,
                position.hitVec.zCoord,
                0.0D,
                0.0D,
                0.0D);
        this.worldObj.playSoundAtEntity(this, "dig.stone", 0.5F, 1.0F);
        if (!this.worldObj.isRemote && this.worldObj.difficultySetting.getDifficultyId() > 0) {
            if (this.worldObj.rand.nextInt(64) == 0) {
                EntitySilverfish sf = new EntitySilverfish(this.worldObj);
                sf.setLocationAndAngles(position.blockX, position.blockY, position.blockZ, 0.0F, 0.0F);
                this.worldObj.spawnEntityInWorld(sf);
            }
            setDead();
        }
    }
}
