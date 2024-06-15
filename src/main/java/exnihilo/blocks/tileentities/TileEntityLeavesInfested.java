package exnihilo.blocks.tileentities;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import exnihilo.ENBlocks;
import exnihilo.compatibility.foresty.Forestry;
import exnihilo.network.VanillaPacket;
import exnihilo.registries.ColorRegistry;
import exnihilo.registries.helpers.Color;

public class TileEntityLeavesInfested extends TileEntity {

    public Block block = Blocks.leaves;

    public int meta = 0;

    public final Color color = ColorRegistry.color("white");

    public boolean dying = false;

    public boolean permanent = false;

    private static final int SPREAD_INTERVAL = 100;

    private int spreadTimer = 0;

    private static final float PROGRESS_INTERVAL = 5.0E-4F;

    private float progress = 0.0F;

    @Override
    public void updateEntity() {
        if (this.progress < 1.0F) {
            this.progress += 5.0E-4F;
            if (this.progress > 1.0F) this.progress = 1.0F;
        }
        if (!this.worldObj.isRemote && this.progress > 0.6F) {
            this.spreadTimer++;
            if (this.spreadTimer >= 100) {
                spread();
                this.spreadTimer = this.worldObj.rand.nextInt(10);
                if (this.progress < 1.0F) VanillaPacket.sendTileEntityUpdate(this);
            }
        }
    }

    public boolean isComplete() {
        return (this.progress >= 5.0E-4F);
    }

    public Color getRenderColor() {
        Color base = new Color(this.block.colorMultiplier(this.worldObj, this.xCoord, this.yCoord, this.zCoord));
        Color white = ColorRegistry.color("white");
        return Color.average(base, white, this.progress);
    }

    public int getBrightness() {
        return this.block.getMixedBrightnessForBlock(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
    }

    public float getProgress() {
        return this.progress;
    }

    private void spread() {
        int x = this.worldObj.rand.nextInt(3) - 1;
        int y = this.worldObj.rand.nextInt(3) - 1;
        int z = this.worldObj.rand.nextInt(3) - 1;
        int placeX = this.xCoord + x;
        int placeY = this.yCoord + y;
        int placeZ = this.zCoord + z;
        Block target = this.worldObj.getBlock(placeX, placeY, placeZ);
        int meta = this.worldObj.getBlockMetadata(placeX, placeY, placeZ);
        if (target != null && target.isLeaves(this.worldObj, x, y, z)
                && target != ENBlocks.LeavesInfested
                && !Forestry.addsThisLeaf(target)) {
            this.worldObj.setBlock(placeX, placeY, placeZ, ENBlocks.LeavesInfested, meta, 2);
            TileEntityLeavesInfested te = (TileEntityLeavesInfested) this.worldObj
                    .getTileEntity(placeX, placeY, placeZ);
            if (te != null) te.setMimicBlock(this.block, meta);
        }
    }

    public void setMimicBlock(Block block, int meta) {
        this.block = block;
        this.meta = meta;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.progress = compound.getFloat("progress");
        if (!compound.getString("block").equals("")) {
            this.block = (Block) Block.blockRegistry.getObject(compound.getString("block"));
        } else {
            this.block = null;
        }
        int tempMeta = compound.getInteger("meta");
        if (tempMeta != 0) this.meta = tempMeta;
        this.permanent = compound.getBoolean("permanent");
        this.dying = compound.getBoolean("dying");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setFloat("progress", this.progress);
        if (this.block == null) {
            compound.setString("block", "");
        } else {
            compound.setString("block", Block.blockRegistry.getNameForObject(this.block));
        }
        compound.setInteger("meta", this.meta);
        compound.setBoolean("permanent", this.permanent);
        compound.setBoolean("dying", this.dying);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        writeToNBT(tag);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, this.blockMetadata, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        NBTTagCompound tag = pkt.func_148857_g();
        readFromNBT(tag);
    }
}
