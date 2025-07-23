package exnihilo.blocks.tileentities;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import exnihilo.api.items.IMesh;
import exnihilo.network.ENPacketHandler;
import exnihilo.network.MessageSieve;
import exnihilo.network.VanillaPacket;
import exnihilo.registries.MeshRegistry;
import exnihilo.registries.SieveRegistry;
import exnihilo.registries.helpers.SiftingResult;
import exnihilo.utils.BlockInfo;
import exnihilo.utils.ItemInfo;

public class TileEntitySieve extends TileEntity {

    private static final float MIN_RENDER_CAPACITY = 0.7F;
    private static final float MAX_RENDER_CAPACITY = 0.9F;
    private static final float PROCESSING_INTERVAL = 0.075F;

    private IMesh currentMesh;

    private BlockInfo currentStack = BlockInfo.EMPTY;
    private float progress = 0;
    private long lastSieveAction = 0;

    public TileEntitySieve() {}

    public void addSievable(Block block, int blockMeta) {
        if (currentMesh == null || currentStack != BlockInfo.EMPTY) return;
        ItemInfo itemInfo = new ItemInfo(Item.getItemFromBlock(block), blockMeta);
        if (SieveRegistry.getSiftingOutput(itemInfo, this.currentMesh) != null) {
            this.currentStack = new BlockInfo(block, blockMeta);
            sendPacketUpdate();
        }
    }

    private void sendPacketUpdate() {
        if (this.worldObj.isRemote) return;
        String meshId = "null";
        if (this.currentMesh != null) {
            meshId = MeshRegistry.INSTANCE.get(this.currentMesh);
        }
        ENPacketHandler.sendToAllAround(
                new MessageSieve(
                        this.xCoord,
                        this.yCoord,
                        this.zCoord,
                        this.progress,
                        meshId,
                        this.getCurrentStack().getMeta(),
                        Block.blockRegistry.getNameForObject(this.getCurrentStack().getBlock())),
                this);
        VanillaPacket.sendTileEntityUpdate(this);
    }

    public void ProcessContents() {
        if (this.worldObj.isRemote) return;

        // 4 ticks is the same period of time as holding down right click
        if (this.worldObj.getTotalWorldTime() - lastSieveAction < 4) {
            return;
        }

        lastSieveAction = this.worldObj.getTotalWorldTime();

        progress += PROCESSING_INTERVAL;

        if (progress >= 1) {
            ItemInfo itemInfo = new ItemInfo(
                    Item.getItemFromBlock(this.currentStack.getBlock()),
                    this.currentStack.getMeta());
            final ArrayList<SiftingResult> rewards = SieveRegistry.getSiftingOutput(itemInfo, this.currentMesh);
            if (rewards != null && rewards.size() > 0) {
                for (final SiftingResult reward : rewards) {
                    if (this.worldObj.rand.nextInt(reward.rarity) == 0) {
                        final EntityItem entityitem = new EntityItem(
                                this.worldObj,
                                this.xCoord + 0.5D,
                                this.yCoord + 1.5D,
                                this.zCoord + 0.5D,
                                new ItemStack(reward.drop.getItem(), 1, reward.drop.getMeta()));
                        final double f3 = 0.05F;
                        entityitem.motionX = this.worldObj.rand.nextGaussian() * f3;
                        entityitem.motionY = 0.2D;
                        entityitem.motionZ = this.worldObj.rand.nextGaussian() * f3;
                        this.worldObj.spawnEntityInWorld(entityitem);
                    }
                }
            }
            resetSieve();
        }

        sendPacketUpdate();

    }

    private void resetSieve() {
        progress = 0;
        lastSieveAction = 0;
        currentStack = BlockInfo.EMPTY;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("stack")) this.currentStack = BlockInfo.readFromNBT(compound.getCompoundTag("stack"));
        String currentMesh = compound.getString("mesh");
        if (currentMesh.equals("null")) {
            this.currentMesh = null;
        } else {
            this.currentMesh = MeshRegistry.INSTANCE.get(compound.getString("mesh"));
        }
        this.progress = compound.getFloat("progress");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if (!(currentStack == BlockInfo.EMPTY)) {
            NBTTagCompound stackTag = currentStack.writeToNBT(new NBTTagCompound());
            compound.setTag("stack", stackTag);
        }
        if (this.getCurrentMesh() != null) {
            compound.setString("mesh", MeshRegistry.INSTANCE.get(this.currentMesh));
        } else {
            compound.setString("mesh", "null");
        }
        compound.setFloat("progress", this.progress);
    }

    public void setCurrentMesh(IMesh mesh) {
        this.currentMesh = mesh;
        sendPacketUpdate();
    }

    public IMesh getCurrentMesh() {
        return this.currentMesh;
    }

    public void setCurrentStack(BlockInfo info) {
        this.currentStack = info;
    }

    public BlockInfo getCurrentStack() {
        return currentStack;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public float getProgress() {
        return progress;
    }

    public float getAdjustedProgress() {
        final float capacity = MAX_RENDER_CAPACITY - MIN_RENDER_CAPACITY;
        float adjusted = (1 - this.progress) * capacity;
        adjusted += MIN_RENDER_CAPACITY;
        return adjusted;
    }

    @Override
    public Packet getDescriptionPacket() {
        final NBTTagCompound tag = new NBTTagCompound();
        writeToNBT(tag);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, this.blockMetadata, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        final NBTTagCompound tag = pkt.func_148857_g();
        readFromNBT(tag);
    }
}
