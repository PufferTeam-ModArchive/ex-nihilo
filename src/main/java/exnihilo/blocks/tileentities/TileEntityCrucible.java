package exnihilo.blocks.tileentities;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import exnihilo.network.ENPacketHandler;
import exnihilo.network.MessageCrucible;
import exnihilo.network.VanillaPacket;
import exnihilo.registries.CrucibleRegistry;
import exnihilo.registries.HeatRegistry;
import exnihilo.registries.helpers.Meltable;

public class TileEntityCrucible extends TileEntity implements IFluidHandler, ISidedInventory {

    private static final float MIN_RENDER_CAPACITY = 0.2F;

    private static final float MAX_RENDER_CAPACITY = 0.95F;

    private static final int MAX_FLUID = 10000;

    private static final int UPDATE_INTERVAL = 10;

    public enum CrucibleMode {

        EMPTY(0),
        USED(1);

        public final int value;

        CrucibleMode(int v) {
            this.value = v;
        }
    }

    private boolean needsUpdate = false;

    private int updateTimer = 0;

    public FluidStack fluid;

    private Block content;

    private int contentMeta = 0;

    private float solidVolume = 0.0F;

    public float getSolidVolume() {
        return this.solidVolume;
    }

    private float airVolume = 0.0F;

    private float fluidVolume = 0.0F;

    public CrucibleMode mode;

    public float getFluidVolume() {
        return this.fluidVolume;
    }

    public TileEntityCrucible() {
        this.mode = CrucibleMode.EMPTY;
        this.fluid = new FluidStack(FluidRegistry.WATER, 0);
    }

    public float getAdjustedVolume() {
        float volume = (this.solidVolume + this.fluidVolume + this.airVolume) / 10000.0F;
        float capacity = 0.75F;
        float adjusted = volume * capacity;
        adjusted += 0.2F;
        return adjusted;
    }

    public IIcon getContentIcon() {
        if (this.worldObj.isRemote) {
            Meltable meltable = CrucibleRegistry.getItem(this.content, this.contentMeta);
            if (meltable != null && meltable.getIcon() != null) return meltable.getIcon();
            return Blocks.stone.getIcon(0, 0);
        }
        return null;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        switch (compound.getInteger("mode")) {
            case 0 -> this.mode = CrucibleMode.EMPTY;
            case 1 -> this.mode = CrucibleMode.USED;
        }
        this.solidVolume = compound.getFloat("solidVolume");
        this.fluidVolume = compound.getFloat("fluidVolume");
        this.airVolume = compound.getFloat("airVolume");
        if (!compound.getString("content").equals("")) {
            this.content = (Block) Block.blockRegistry.getObject(compound.getString("content"));
        } else {
            this.content = null;
        }
        this.contentMeta = compound.getInteger("contentMeta");
        this.fluid = new FluidStack(FluidRegistry.getFluid(compound.getShort("fluid")), Math.round(this.fluidVolume));
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("mode", this.mode.value);
        compound.setFloat("solidVolume", this.solidVolume);
        compound.setFloat("fluidVolume", this.fluidVolume);
        compound.setFloat("airVolume", this.airVolume);
        if (this.content == null) {
            compound.setString("content", "");
        } else {
            compound.setString("content", Block.blockRegistry.getNameForObject(this.content));
        }
        compound.setInteger("contentMeta", this.contentMeta);
        compound.setShort("fluid", (short) this.fluid.getFluidID());
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

    public boolean addItem(ItemStack item) {
        if (!CrucibleRegistry.containsItem(Block.getBlockFromItem(item.getItem()), item.getItemDamage())) return false;
        Meltable meltable = CrucibleRegistry.getItem(Block.getBlockFromItem(item.getItem()), item.getItemDamage());
        if (!this.worldObj.isRemote && getCapacity() >= meltable.solidVolume && isFluidValid(meltable.fluid)) {
            this.content = Block.getBlockFromItem(item.getItem());
            this.contentMeta = item.getItemDamage();
            this.solidVolume += meltable.fluidVolume;
            this.airVolume += meltable.solidVolume - meltable.fluidVolume;
            this.mode = CrucibleMode.USED;
            this.fluid = new FluidStack(meltable.fluid, (int) this.fluidVolume);
            VanillaPacket.sendTileEntityUpdate(this);
        } else {
            return false;
        }
        return true;
    }

    @Override
    public void updateEntity() {
        if (!this.worldObj.isRemote) {
            float speed = getMeltSpeed();
            if (this.airVolume > 0.0F) {
                this.airVolume -= this.airVolume * speed / this.solidVolume;
                if (this.airVolume < 0.0F) this.airVolume = 0.0F;
            }
            if (this.solidVolume > 0.0F) {
                if (this.solidVolume - speed >= 0.0F) {
                    this.fluidVolume += speed;
                    this.solidVolume -= speed;
                } else {
                    this.fluidVolume += this.solidVolume;
                    this.solidVolume = 0.0F;
                }
                this.fluid.amount = Math.round(this.fluidVolume);
                this.needsUpdate = true;
            } else if (Math.round(this.solidVolume + this.fluidVolume + this.airVolume) == 0
                    && this.mode != CrucibleMode.EMPTY) {
                        this.mode = CrucibleMode.EMPTY;
                        this.needsUpdate = true;
                    }
            if (this.updateTimer >= 10) {
                this.updateTimer = 0;
                if (this.needsUpdate) {
                    this.needsUpdate = false;
                    ENPacketHandler.sendToAllAround(
                            new MessageCrucible(
                                    this.xCoord,
                                    this.yCoord,
                                    this.zCoord,
                                    this.fluidVolume,
                                    this.solidVolume),
                            this);
                    VanillaPacket.sendTileEntityUpdate(this);
                }
            } else {
                this.updateTimer++;
            }
        }
    }

    public float getCapacity() {
        return 10000.0F - this.solidVolume + this.fluidVolume + this.airVolume;
    }

    public float getMeltSpeed() {
        Block targetBlock = this.worldObj.getBlock(this.xCoord, this.yCoord - 1, this.zCoord);
        int targetMeta = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord - 1, this.zCoord);
        if (HeatRegistry.containsItem(targetBlock, targetMeta)) return HeatRegistry.getSpeed(targetBlock, targetMeta);
        return 0.0F;
    }

    public boolean hasSolids() {
        return (this.solidVolume > 0.0F);
    }

    public boolean renderFluid() {
        return this.solidVolume < this.fluidVolume && this.fluid.getFluid().getID() != FluidRegistry.WATER.getID();
    }

    private boolean isFluidValid(Fluid fluid) {
        if (this.mode == CrucibleMode.EMPTY) return true;
        return this.mode == CrucibleMode.USED && fluid.getID() == this.fluid.getFluidID();
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        int capacity = (int) getCapacity();
        if (!doFill) {
            if (this.mode == CrucibleMode.EMPTY) return resource.amount;
            if (this.mode == CrucibleMode.USED && resource.getFluidID() == this.fluid.getFluidID()) {
                return Math.min(capacity, resource.amount);
            }
        } else {
            if (this.mode == CrucibleMode.EMPTY) {
                if (resource.getFluidID() != this.fluid.getFluidID()) {
                    this.fluid = new FluidStack(FluidRegistry.getFluid(resource.getFluidID()), resource.amount);
                } else {
                    this.fluid.amount = resource.amount;
                }
                this.mode = CrucibleMode.USED;
                this.fluidVolume += this.fluid.amount;
                this.needsUpdate = true;
                return resource.amount;
            }
            if (this.mode == CrucibleMode.USED && resource.getFluidID() == this.fluid.getFluidID()) {
                if (capacity >= resource.amount) {
                    this.fluidVolume += resource.amount;
                    this.fluid.amount = (int) this.fluidVolume;
                    this.needsUpdate = true;
                    return resource.amount;
                }
                this.fluidVolume += capacity;
                this.fluid.amount = (int) this.fluidVolume;
                this.needsUpdate = true;
                return capacity;
            }
        }
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (resource == null || this.mode != CrucibleMode.USED || !resource.isFluidEqual(this.fluid)) return null;
        if (!doDrain) {
            if (this.fluid.amount >= resource.amount) {
                FluidStack fluidStack = new FluidStack(FluidRegistry.getFluid(resource.getFluidID()), resource.amount);
                return fluidStack;
            }
            FluidStack simulated = new FluidStack(FluidRegistry.getFluid(resource.getFluidID()), this.fluid.amount);
            return simulated;
        }
        if (this.fluid.amount > resource.amount) {
            FluidStack fluidStack = new FluidStack(FluidRegistry.getFluid(resource.getFluidID()), resource.amount);
            this.fluidVolume -= resource.amount;
            this.fluid.amount = (int) this.fluidVolume;
            this.needsUpdate = true;
            return fluidStack;
        }
        FluidStack drained = new FluidStack(FluidRegistry.getFluid(resource.getFluidID()), this.fluid.amount);
        this.fluidVolume -= this.fluid.amount;
        this.fluid.amount = 0;
        this.needsUpdate = true;
        return drained;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (this.mode != CrucibleMode.USED) return null;
        if (!doDrain) {
            if (this.fluid.amount >= maxDrain) {
                FluidStack fluidStack = new FluidStack(FluidRegistry.getFluid(this.fluid.getFluidID()), maxDrain);
                return fluidStack;
            }
            FluidStack simulated = new FluidStack(FluidRegistry.getFluid(this.fluid.getFluidID()), this.fluid.amount);
            return simulated;
        }
        if (this.fluid.amount > maxDrain) {
            FluidStack fluidStack = new FluidStack(FluidRegistry.getFluid(this.fluid.getFluidID()), maxDrain);
            this.fluidVolume -= maxDrain;
            this.fluid.amount = (int) this.fluidVolume;
            this.needsUpdate = true;
            return fluidStack;
        }
        FluidStack drained = new FluidStack(FluidRegistry.getFluid(this.fluid.getFluidID()), this.fluid.amount);
        this.fluidVolume -= this.fluid.amount;
        this.fluid.amount = 0;
        this.needsUpdate = true;
        return drained;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        FluidTankInfo info = new FluidTankInfo(this.fluid, 10000);
        FluidTankInfo[] array = new FluidTankInfo[1];
        array[0] = info;
        return array;
    }

    public int getLightLevel() {
        if (this.mode == CrucibleMode.USED) {
            float lumens = this.fluid.getFluid().getLuminosity() * this.fluidVolume / 10000.0F;
            return Math.round(lumens);
        }
        return 0;
    }

    public void setVolumes(float newFluidVolume, float newSolidVolume) {
        this.fluidVolume = newFluidVolume;
        this.fluid.amount = Math.round(newFluidVolume);
        this.solidVolume = newSolidVolume;
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return null;
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack item) {
        if (slot == 0)
            if (CrucibleRegistry.containsItem(Block.getBlockFromItem(item.getItem()), item.getItemDamage())) {
                Meltable meltable = CrucibleRegistry
                        .getItem(Block.getBlockFromItem(item.getItem()), item.getItemDamage());
                if (getCapacity() >= meltable.solidVolume && isFluidValid(meltable.fluid)) addItem(item);
            }
    }

    @Override
    public String getInventoryName() {
        return null;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return false;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        if (slot == 0)
            if (CrucibleRegistry.containsItem(Block.getBlockFromItem(item.getItem()), item.getItemDamage())) {
                Meltable meltable = CrucibleRegistry
                        .getItem(Block.getBlockFromItem(item.getItem()), item.getItemDamage());
                return getCapacity() >= meltable.solidVolume && isFluidValid(meltable.fluid);
            }
        return false;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        if (side == 1) return new int[] { 0 };
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack item, int side) {
        if (side == 1 && slot == 0)
            if (CrucibleRegistry.containsItem(Block.getBlockFromItem(item.getItem()), item.getItemDamage())) {
                Meltable meltable = CrucibleRegistry
                        .getItem(Block.getBlockFromItem(item.getItem()), item.getItemDamage());
                return getCapacity() >= meltable.solidVolume && isFluidValid(meltable.fluid);
            }
        return false;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack item, int side) {
        return false;
    }
}
