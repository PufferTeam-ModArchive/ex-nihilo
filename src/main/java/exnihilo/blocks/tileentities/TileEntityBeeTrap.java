package exnihilo.blocks.tileentities;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.biome.BiomeGenBase;

import cpw.mods.fml.common.Loader;
import exnihilo.ENBlocks;
import exnihilo.compatibility.foresty.Hive;
import exnihilo.compatibility.foresty.HiveRegistry;
import exnihilo.compatibility.foresty.Surrounding;

public class TileEntityBeeTrap extends TileEntity {

    private Surrounding blocks = new Surrounding();

    private static final int TIMER_MAX = 6000;

    private int timer = 0;

    private static final int MAX_X = 2;

    private static final int MIN_X = -2;

    private int x = -2;

    private static final int MAX_Y = 2;

    private static final int MIN_Y = -2;

    private int y = -2;

    private static final int MAX_Z = 2;

    private static final int MIN_Z = -2;

    private int z = -2;

    private static final int HIVE_SPAWN_CHANCE = 50;

    private boolean complete = false;

    @Override
    public void updateEntity() {
        if (!this.worldObj.isRemote && Loader.isModLoaded("Forestry")) {
            this.timer++;
            if (this.timer / TIMER_MAX > 0.6F) {
                if (this.x > 2) {
                    this.x = -2;
                    this.y++;
                }
                if (this.y > 2) {
                    this.y = -2;
                    this.z++;
                }
                if (this.z > 2) {
                    this.z = -2;
                    this.complete = true;
                }
            }
            if (this.complete) {
                boolean canSeeSky = (this.yCoord
                        >= this.worldObj.getTopSolidOrLiquidBlock(this.xCoord, this.zCoord) - 1);
                BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(this.xCoord, this.zCoord);
                Hive hive = HiveRegistry.getHive(biome, this.blocks, canSeeSky, this.yCoord);
                if (hive != null
                        && this.worldObj.rand.nextInt(50 - Math.min(30, hive.getSpawnChanceModifier(this.blocks)))
                                == 0) {
                    this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, hive.block, hive.meta, 3);
                } else {
                    this.blocks = new Surrounding();
                    this.complete = false;
                }
            } else {
                Block block = this.worldObj.getBlock(this.xCoord + this.x, this.yCoord + this.y, this.zCoord + this.z);
                int meta = this.worldObj
                        .getBlockMetadata(this.xCoord + this.x, this.yCoord + this.y, this.zCoord + this.z);
                this.blocks.addBlock(this.worldObj, this.xCoord + this.x, this.yCoord + this.y, this.zCoord + this.z);
                if (this.x == 0 && this.y == 1 && this.z == 0) this.blocks.setBlockAbove(block, meta);
                this.x++;
            }
            if (this.timer > TIMER_MAX)
                this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, ENBlocks.BeeTrap, 0, 3);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.timer = compound.getInteger("timer");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("timer", this.timer);
    }
}
