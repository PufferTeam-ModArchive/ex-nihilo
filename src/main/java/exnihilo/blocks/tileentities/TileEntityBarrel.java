package exnihilo.blocks.tileentities;

import java.lang.reflect.Constructor;
import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import exnihilo.ENBlocks;
import exnihilo.ENItems;
import exnihilo.Fluids;
import exnihilo.config.BarrelConfig;
import exnihilo.network.ENPacketHandler;
import exnihilo.network.MessageBarrel;
import exnihilo.network.VanillaPacket;
import exnihilo.registries.BarrelRecipeRegistry;
import exnihilo.registries.ColorRegistry;
import exnihilo.registries.CompostRegistry;
import exnihilo.registries.helpers.Color;
import exnihilo.registries.helpers.Compostable;
import exnihilo.registries.helpers.EntityWithItem;
import exnihilo.utils.ItemInfo;

public class TileEntityBarrel extends TileEntity implements IFluidHandler, ISidedInventory {

    private static final float MIN_RENDER_CAPACITY = 0.1F;

    private static final float MAX_RENDER_CAPACITY = 0.9F;

    private static final int MAX_COMPOSTING_TIME = 1000;

    private static final int MAX_FLUID = 1000;

    private static final int UPDATE_INTERVAL = 10;

    private static final int MOSS_SPREAD_X_POS = 2;

    private static final int MOSS_SPREAD_X_NEG = -2;

    private static final int MOSS_SPREAD_Y_POS = 2;

    private static final int MOSS_SPREAD_Y_NEG = -1;

    private static final int MOSS_SPREAD_Z_POS = 2;

    private static final int MOSS_SPREAD_Z_NEG = -2;

    public FluidStack fluid;

    private float volume;

    private int timer;

    private BarrelMode mode;

    public Block block;

    public int blockMeta;

    public Color color;

    private Color colorBase;

    public IIcon icon;

    public ItemStack outputStack;

    public Fluid transformFluid;

    public Block transformBlock;

    public int transformMeta;

    public EntityLivingBase entity;

    public String entityParticleName;

    public ItemStack peacefulDrop;

    public enum BarrelMode {

        EMPTY(0, TileEntityBarrel.ExtractMode.None),
        FLUID(1, TileEntityBarrel.ExtractMode.None),
        COMPOST(2, TileEntityBarrel.ExtractMode.None),
        DIRT(3, TileEntityBarrel.ExtractMode.Always),
        CLAY(4, TileEntityBarrel.ExtractMode.Always),
        SPORED(5, TileEntityBarrel.ExtractMode.None),
        SLIME(6, TileEntityBarrel.ExtractMode.Always),
        NETHERRACK(7, TileEntityBarrel.ExtractMode.Always),
        ENDSTONE(8, TileEntityBarrel.ExtractMode.Always),
        MILKED(9, TileEntityBarrel.ExtractMode.None),
        SOULSAND(10, TileEntityBarrel.ExtractMode.Always),
        BEETRAP(11, TileEntityBarrel.ExtractMode.Always),
        OBSIDIAN(12, TileEntityBarrel.ExtractMode.Always),
        COBBLESTONE(13, TileEntityBarrel.ExtractMode.Always),
        BLAZE_COOKING(14, TileEntityBarrel.ExtractMode.None),
        BLAZE(15, TileEntityBarrel.ExtractMode.PeacefulOnly),
        ENDER_COOKING(16, TileEntityBarrel.ExtractMode.None),
        ENDER(17, TileEntityBarrel.ExtractMode.PeacefulOnly),
        DARKOAK(18, TileEntityBarrel.ExtractMode.Always),
        BLOCK(19, TileEntityBarrel.ExtractMode.Always),
        RECIPE(20, TileEntityBarrel.ExtractMode.Always),
        MOB(21, TileEntityBarrel.ExtractMode.None),
        FLUIDTRANSFORM(22, TileEntityBarrel.ExtractMode.None);

        public final int value;

        public final TileEntityBarrel.ExtractMode canExtract;

        BarrelMode(int v, TileEntityBarrel.ExtractMode extract) {
            this.value = v;
            this.canExtract = extract;
        }
    }

    public enum ExtractMode {
        None,
        Always,
        PeacefulOnly
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    private boolean needsUpdate = false;

    private int updateTimer = 0;

    public BarrelMode getMode() {
        return this.mode;
    }

    public void setMode(BarrelMode mode) {
        this.mode = mode;
        this.needsUpdate = true;
    }

    public TileEntityBarrel() {
        this.color = ColorRegistry.color("white");
        this.colorBase = this.color;
        setMode(BarrelMode.EMPTY);
        this.volume = 0.0F;
        this.timer = 0;
        this.fluid = new FluidStack(FluidRegistry.WATER, 0);
    }

    @Override
    public void updateEntity() {
        Color colorSlime;
        int nearbyMycelium;
        Color colorWitchy;
        if (this.updateTimer >= 10) {
            this.updateTimer = 0;
            if (this.needsUpdate) {
                this.needsUpdate = false;
                VanillaPacket.sendTileEntityUpdate(this);
            }
        } else {
            this.updateTimer++;
        }
        switch (getMode()) {
            case EMPTY -> {
                if (!this.worldObj.isRemote && this.worldObj.isRaining()
                        && this.yCoord >= this.worldObj.getTopSolidOrLiquidBlock(this.xCoord, this.zCoord) - 1
                        && (this.worldObj.getBiomeGenForCoords(this.xCoord, this.zCoord)).rainfall > 0.0F
                        && BarrelConfig.enableBarrelRainFill) {
                    this.fluid = new FluidStack(FluidRegistry.WATER, 0);
                    setMode(BarrelMode.FLUID);
                }
            }
            case FLUID -> {
                if (!this.worldObj.isRemote && isFull()) {
                    HashSet<ItemInfo> nearby = getNearbyBlocks();
                    if (nearby != null) {
                        for (ItemInfo next : getNearbyBlocks()) {
                            if (Block.getBlockFromItem(next.getItem()) != Blocks.air) {
                                Fluid potentialFluid = BarrelRecipeRegistry.getFluidTransformRecipeOutput(
                                        this.fluid.getFluid(),
                                        Block.getBlockFromItem(next.getItem()),
                                        next.getMeta());
                                if (potentialFluid != null) {
                                    this.transformFluid = potentialFluid;
                                    this.transformBlock = Block.getBlockFromItem(next.getItem());
                                    this.transformMeta = next.getMeta();
                                    setMode(BarrelMode.FLUIDTRANSFORM);
                                    VanillaPacket.sendTileEntityUpdate(this);
                                }
                            }
                        }
                    }
                }
                if (this.fluid.getFluidID() == FluidRegistry.WATER.getID()) {
                    if (!this.worldObj.isRemote && !isFull()
                            && this.worldObj.isRaining()
                            && this.yCoord >= this.worldObj.getTopSolidOrLiquidBlock(this.xCoord, this.zCoord) - 1
                            && (this.worldObj.getBiomeGenForCoords(this.xCoord, this.zCoord)).rainfall > 0.0F
                            && BarrelConfig.enableBarrelRainFill) {
                        this.volume += (this.worldObj.getBiomeGenForCoords(this.xCoord, this.zCoord)).rainfall
                                / 1000.0F;
                        if (this.volume > 1.0F) this.volume = 1.0F;
                        this.fluid.amount = (int) (1000.0F * this.volume);
                        this.needsUpdate = true;
                    }
                    if (!this.worldObj.isRemote && isFull()
                            && BarrelConfig.enableBarrelRecipeSoulsand
                            && getNearbyBlocks(Blocks.mycelium, 0) > 0) {
                        this.colorBase = new Color(this.fluid.getFluid().getColor());
                        setMode(BarrelMode.SPORED);
                        this.needsUpdate = true;
                    }
                    if (isFull() && this.worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord)
                            == FluidRegistry.LAVA.getBlock())
                        setMode(BarrelMode.COBBLESTONE);
                    if (!this.worldObj.isRemote && this.fluid.amount > 0
                            && this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord).getMaterial().getCanBurn()
                            && this.worldObj.rand.nextInt(500) == 0) {
                        int x = this.xCoord + this.worldObj.rand.nextInt(5) - 2;
                        int y = this.yCoord + this.worldObj.rand.nextInt(4) - 1;
                        int z = this.zCoord + this.worldObj.rand.nextInt(5) - 2;
                        int lightLevel = this.worldObj.getBlockLightValue(x, y + 1, z);
                        if (!this.worldObj.isAirBlock(x, y, z) && this.worldObj.getTopSolidOrLiquidBlock(x, z) > y
                                && lightLevel >= 9
                                && lightLevel <= 11) {
                            Block selected = this.worldObj.getBlock(x, y, z);
                            int meta = this.worldObj.getBlockMetadata(x, y, z);
                            if (selected == Blocks.stonebrick && meta == 0) {
                                this.worldObj.setBlock(x, y, z, Blocks.stonebrick, 1, 3);
                                drain(ForgeDirection.DOWN, 100, true);
                            }
                            if (selected == Blocks.cobblestone) {
                                this.worldObj.setBlock(x, y, z, Blocks.mossy_cobblestone, 0, 3);
                                drain(ForgeDirection.DOWN, 100, true);
                            }
                        }
                    }
                }
                if (this.fluid.getFluidID() == FluidRegistry.LAVA.getID()) {
                    if (this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord).getMaterial().getCanBurn()) {
                        this.timer++;
                        if (this.timer % 30 == 0) this.worldObj.spawnParticle(
                                "largesmoke",
                                this.xCoord + Math.random(),
                                this.yCoord + 1.2D,
                                this.zCoord + Math.random(),
                                0.0D,
                                0.0D,
                                0.0D);
                        if (this.timer % 5 == 0) this.worldObj.spawnParticle(
                                "smoke",
                                this.xCoord + Math.random(),
                                this.yCoord + 1.2D,
                                this.zCoord + Math.random(),
                                0.0D,
                                0.0D,
                                0.0D);
                        if (this.timer >= 400) {
                            this.timer = 0;
                            if (this.fluid.amount < 1000) {
                                this.worldObj.setBlock(this.xCoord, this.yCoord + 2, this.zCoord, Blocks.fire);
                                return;
                            }
                            this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, Blocks.lava, 0, 3);
                            return;
                        }
                    }
                    if (isFull() && this.worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord)
                            == FluidRegistry.WATER.getBlock())
                        setMode(BarrelMode.OBSIDIAN);
                }
            }
            case COMPOST -> {
                if (this.volume >= 1.0F) {
                    this.timer++;
                    Color colorDirt = ColorRegistry.color("dirt");
                    this.color = Color.average(this.colorBase, colorDirt, this.timer / 1000.0F);
                    if (this.timer >= 1000) {
                        setMode(BarrelMode.DIRT);
                        this.timer = 0;
                        this.color = ColorRegistry.color("white");
                        VanillaPacket.sendTileEntityUpdate(this);
                    }
                }
            }
            case MILKED -> {
                this.timer++;
                colorSlime = ColorRegistry.color("water_slime_offset");
                this.color = Color.average(this.colorBase, colorSlime, this.timer / 1000.0F);
                if (isDone()) {
                    this.timer = 0;
                    setMode(BarrelMode.SLIME);
                    VanillaPacket.sendTileEntityUpdate(this);
                }
            }
            case SLIME -> {
                if (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL) {
                    this.timer++;
                    if (isDone()) {
                        this.timer = 0;
                        if (!this.worldObj.isRemote) {
                            EntitySlime slime = new EntitySlime(this.worldObj);
                            slime.setPosition(this.xCoord, (this.yCoord + 1), this.zCoord);
                            this.worldObj.spawnEntityInWorld(slime);
                        }
                        resetBarrel();
                    }
                }
            }
            case SPORED -> {
                nearbyMycelium = getNearbyBlocks(Blocks.mycelium, 0);
                this.timer += 1 + nearbyMycelium / 2;
                colorWitchy = ColorRegistry.color("water_witchy_offset");
                this.color = Color.average(this.colorBase, colorWitchy, this.timer / 1000.0F);
                if (!this.worldObj.isRemote && nearbyMycelium > 0) for (int x = -2; x <= 2; x++) {
                    for (int y = -1; y <= 1; y++) {
                        for (int z = -2; z <= 2; z++) {
                            if (this.worldObj.getBlock(this.xCoord + x, this.yCoord + y, this.zCoord + z)
                                    == Blocks.mycelium
                                    && this.worldObj.isAirBlock(this.xCoord + x, this.yCoord + y + 1, this.zCoord + z)
                                    && this.worldObj.rand.nextInt(1500) == 0) {
                                int choice = this.worldObj.rand.nextInt(2);
                                if (choice == 0) this.worldObj.setBlock(
                                        this.xCoord + x,
                                        this.yCoord + y + 1,
                                        this.zCoord + z,
                                        Blocks.brown_mushroom,
                                        0,
                                        3);
                                if (choice == 1) this.worldObj.setBlock(
                                        this.xCoord + x,
                                        this.yCoord + y + 1,
                                        this.zCoord + z,
                                        Blocks.red_mushroom,
                                        0,
                                        3);
                            }
                        }
                    }
                }
                if (isDone()) {
                    this.timer = 0;
                    this.fluid = FluidRegistry.getFluidStack("witchwater", this.fluid.amount);
                    setMode(BarrelMode.FLUID);
                    VanillaPacket.sendTileEntityUpdate(this);
                }
            }
            case BLAZE_COOKING -> {
                this.timer++;
                if (!this.worldObj.isRemote
                        && this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord).getMaterial().getCanBurn()) {
                    this.worldObj.func_147480_a(this.xCoord, this.yCoord, this.zCoord, false);
                    this.worldObj.createExplosion(null, this.xCoord, this.yCoord, this.zCoord, 4.0F, true);
                }
                if (this.worldObj.isRemote && this.worldObj.rand.nextInt(20) == 0) this.worldObj.spawnParticle(
                        "lava",
                        this.xCoord + this.worldObj.rand.nextFloat() * 0.6D + 0.2D,
                        (this.yCoord + 1),
                        this.zCoord + this.worldObj.rand.nextFloat() * 0.6D + 0.2D,
                        0.0D,
                        0.0D,
                        0.0D);
                if (this.timer >= 700 && this.worldObj.isAirBlock(this.xCoord, this.yCoord + 1, this.zCoord))
                    this.worldObj.setBlock(this.xCoord, this.yCoord + 1, this.zCoord, Blocks.fire);
                if (isDone()) {
                    setMode(BarrelMode.BLAZE);
                    this.timer = 0;
                }
            }
            case BLAZE -> {
                if (this.worldObj.isRemote) if (this.worldObj.rand.nextInt(5) == 0) this.worldObj.spawnParticle(
                        "lava",
                        this.xCoord + this.worldObj.rand.nextFloat() * 0.6D + 0.2D,
                        (this.yCoord + 1),
                        this.zCoord + this.worldObj.rand.nextFloat() * 0.6D + 0.2D,
                        0.0D,
                        0.0D,
                        0.0D);
                if (!this.worldObj.isRemote && this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL) {
                    if (isDone()) {
                        this.timer = 0;
                        resetBarrel();
                        break;
                    }
                    for (int x = -1; x <= 1; x++) {
                        for (int y = -1; y <= 1; y++) {
                            for (int z = -1; z <= 1; z++) {
                                if ((this.worldObj.isAirBlock(this.xCoord + x, this.yCoord + y, this.zCoord + z)
                                        || this.worldObj.getBlock(this.xCoord + x, this.yCoord + y, this.zCoord + z)
                                                == Blocks.fire)
                                        && (this.worldObj
                                                .isAirBlock(this.xCoord + x, this.yCoord + y + 1, this.zCoord + z)
                                                || this.worldObj
                                                        .getBlock(this.xCoord + x, this.yCoord + y, this.zCoord + z)
                                                        == Blocks.fire)
                                        && this.worldObj.rand.nextInt(10) == 0
                                        && !isDone()) {
                                    this.timer = 1000;
                                    EntityBlaze blaze = new EntityBlaze(this.worldObj);
                                    blaze.setPosition(
                                            (this.xCoord + x) + 0.5D,
                                            (this.yCoord + y),
                                            (this.zCoord + z) + 0.5D);
                                    this.worldObj.spawnEntityInWorld(blaze);
                                }
                            }
                        }
                    }
                }
            }
            case ENDER_COOKING -> {
                this.timer++;
                if (this.worldObj.isRemote && this.worldObj.rand.nextInt(20) == 0) {
                    float f = (this.worldObj.rand.nextFloat() - 0.5F) * 0.2F;
                    float f1 = (this.worldObj.rand.nextFloat() - 0.5F) * 0.2F;
                    float f2 = (this.worldObj.rand.nextFloat() - 0.5F) * 0.2F;
                    this.worldObj.spawnParticle(
                            "portal",
                            this.xCoord + this.worldObj.rand.nextFloat() * 0.6D + 0.2D,
                            (this.yCoord + 1),
                            this.zCoord + this.worldObj.rand.nextFloat() * 0.6D + 0.2D,
                            f,
                            f1,
                            f2);
                }
                if (isDone()) {
                    setMode(BarrelMode.ENDER);
                    this.timer = 0;
                }
            }
            case ENDER -> {
                if (this.worldObj.isRemote && this.worldObj.rand.nextInt(20) == 0) {
                    float f = (this.worldObj.rand.nextFloat() - 0.5F) * 0.2F;
                    float f1 = (this.worldObj.rand.nextFloat() - 0.5F) * 0.2F;
                    float f2 = (this.worldObj.rand.nextFloat() - 0.5F) * 0.2F;
                    this.worldObj.spawnParticle(
                            "portal",
                            this.xCoord + this.worldObj.rand.nextFloat() * 0.6D + 0.2D,
                            (this.yCoord + 1),
                            this.zCoord + this.worldObj.rand.nextFloat() * 0.6D + 0.2D,
                            f,
                            f1,
                            f2);
                }
                if (!this.worldObj.isRemote && this.worldObj.difficultySetting.getDifficultyId() > 0) {
                    if (isDone()) {
                        this.timer = 0;
                        resetBarrel();
                        break;
                    }
                    for (int x = -1; x <= 1; x++) {
                        for (int y = -1; y <= 1; y++) {
                            for (int z = -1; z <= 1; z++) {
                                if (this.worldObj.isAirBlock(this.xCoord + x, this.yCoord + y, this.zCoord + z)
                                        && this.worldObj
                                                .isAirBlock(this.xCoord + x, this.yCoord + y + 1, this.zCoord + z)
                                        && this.worldObj
                                                .isAirBlock(this.xCoord + x, this.yCoord + y + 2, this.zCoord + z)
                                        && this.worldObj.rand.nextInt(10) == 0
                                        && !isDone()) {
                                    this.timer = 1000;
                                    EntityEnderman enderman = new EntityEnderman(this.worldObj);
                                    enderman.setPosition(
                                            (this.xCoord + x) + 0.5D,
                                            (this.yCoord + y),
                                            (this.zCoord + z) + 0.5D);
                                    this.worldObj.spawnEntityInWorld(enderman);
                                }
                            }
                        }
                    }
                }
            }
            case MOB -> {
                if (this.worldObj.isRemote && this.worldObj.rand.nextInt(20) == 0 && this.entityParticleName != null) {
                    float f = (this.worldObj.rand.nextFloat() - 0.5F) * 0.2F;
                    float f1 = (this.worldObj.rand.nextFloat() - 0.5F) * 0.2F;
                    float f2 = (this.worldObj.rand.nextFloat() - 0.5F) * 0.2F;
                    this.worldObj.spawnParticle(
                            this.entityParticleName,
                            this.xCoord + this.worldObj.rand.nextFloat() * 0.6D + 0.2D,
                            (this.yCoord + 1),
                            this.zCoord + this.worldObj.rand.nextFloat() * 0.6D + 0.2D,
                            f,
                            f1,
                            f2);
                }
                if (!this.worldObj.isRemote) {
                    if (!isDone()) {
                        this.timer++;
                        ENPacketHandler.sendToAllAround(
                                new MessageBarrel(this.xCoord, this.yCoord, this.zCoord, this.timer),
                                this);
                        break;
                    }
                    if (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL) {
                        for (int x = -1; x <= 1; x++) {
                            for (int y = -1; y <= 1; y++) {
                                for (int z = -1; z <= 1; z++) {
                                    if (this.worldObj.isAirBlock(this.xCoord + x, this.yCoord + y, this.zCoord + z)
                                            && this.worldObj
                                                    .isAirBlock(this.xCoord + x, this.yCoord + y + 1, this.zCoord + z)
                                            && this.worldObj
                                                    .isAirBlock(this.xCoord + x, this.yCoord + y + 2, this.zCoord + z)
                                            && this.worldObj.rand.nextInt(10) == 0) {
                                        this.entity.setPosition(
                                                (this.xCoord + x) + 0.5D,
                                                (this.yCoord + y),
                                                (this.zCoord + z) + 0.5D);
                                        if (this.worldObj.spawnEntityInWorld(this.entity)) {
                                            this.timer = 0;
                                            ENPacketHandler.sendToAllAround(
                                                    new MessageBarrel(
                                                            this.xCoord,
                                                            this.yCoord,
                                                            this.zCoord,
                                                            this.timer),
                                                    this);
                                            resetBarrel();
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    }
                    this.outputStack = this.peacefulDrop;
                    setMode(BarrelMode.RECIPE);
                }
            }
            case FLUIDTRANSFORM -> {
                if (!this.worldObj.isRemote) {
                    if (isDone()) {
                        this.timer = 0;
                        ENPacketHandler.sendToAllAround(
                                new MessageBarrel(this.xCoord, this.yCoord, this.zCoord, this.timer),
                                this);
                        this.fluid = new FluidStack(this.transformFluid, this.fluid.amount);
                        setMode(BarrelMode.FLUID);
                        VanillaPacket.sendTileEntityUpdate(this);
                        break;
                    }
                    int blocks = getNearbyBlocks(this.transformBlock, this.transformMeta);
                    this.timer += 1 + blocks / 2;
                    ENPacketHandler.sendToAllAround(
                            new MessageBarrel(this.xCoord, this.yCoord, this.zCoord, this.timer),
                            this);
                    this.color = Color.average(
                            new Color(this.fluid.getFluid().getColor()),
                            new Color(this.transformFluid.getColor()),
                            this.timer / 1000.0F);
                    if (this.fluid.getFluid().equals(FluidRegistry.WATER)
                            && this.transformFluid.equals(Fluids.fluidWitchWater))
                        for (int x = -2; x <= 2; x++) {
                            for (int y = -1; y <= 1; y++) {
                                for (int z = -2; z <= 2; z++) {
                                    if (this.worldObj.getBlock(this.xCoord + x, this.yCoord + y, this.zCoord + z)
                                            == Blocks.mycelium
                                            && this.worldObj
                                                    .isAirBlock(this.xCoord + x, this.yCoord + y + 1, this.zCoord + z)
                                            && this.worldObj.rand.nextInt(1500) == 0) {
                                        int choice = this.worldObj.rand.nextInt(2);
                                        if (choice == 0) this.worldObj.setBlock(
                                                this.xCoord + x,
                                                this.yCoord + y + 1,
                                                this.zCoord + z,
                                                Blocks.brown_mushroom,
                                                0,
                                                3);
                                        if (choice == 1) this.worldObj.setBlock(
                                                this.xCoord + x,
                                                this.yCoord + y + 1,
                                                this.zCoord + z,
                                                Blocks.red_mushroom,
                                                0,
                                                3);
                                    }
                                }
                            }
                        }
                    break;
                }
                this.color = Color.average(
                        new Color(this.fluid.getFluid().getColor()),
                        new Color(this.transformFluid.getColor()),
                        this.timer / 1000.0F);
            }
        }
    }

    public boolean addCompostItem(Compostable item) {
        if (getMode() == BarrelMode.EMPTY) {
            setMode(BarrelMode.COMPOST);
            this.timer = 0;
        }
        if (getMode() == BarrelMode.COMPOST && this.volume < 1.0F) {
            this.volume += item.value;
            if (this.volume > 1.0F) this.volume = 1.0F;
            float weightA = item.value / this.volume;
            float weightB = 1.0F - weightA;
            float r = weightA * item.color.r + weightB * this.color.r;
            float g = weightA * item.color.g + weightB * this.color.g;
            float b = weightA * item.color.b + weightB * this.color.b;
            float a = weightA * item.color.a + weightB * this.color.a;
            this.color = new Color(r, g, b, a);
            if (this.volume == 1.0F) this.colorBase = this.color;
            VanillaPacket.sendTileEntityUpdate(this);
            return true;
        }
        return false;
    }

    public boolean isFull() {
        return this.volume >= 1.0F;
    }

    public boolean isDone() {
        return (this.timer >= 1000);
    }

    public void resetColor() {
        this.colorBase = ColorRegistry.color("white");
        this.color = ColorRegistry.color("white");
    }

    public void giveAppropriateItem() {
        giveItem(getExtractItem());
    }

    private void giveItem(ItemStack item) {
        if (!this.worldObj.isRemote && item != null) {
            EntityItem entityitem = new EntityItem(
                    this.worldObj,
                    this.xCoord + 0.5D,
                    this.yCoord + 1.5D,
                    this.zCoord + 0.5D,
                    item);
            double f3 = 0.05000000074505806D;
            entityitem.motionX = this.worldObj.rand.nextGaussian() * f3;
            entityitem.motionY = 0.2D;
            entityitem.motionZ = this.worldObj.rand.nextGaussian() * f3;
            this.worldObj.spawnEntityInWorld(entityitem);
            this.timer = 0;
        }
        resetBarrel();
    }

    private ItemStack getExtractItem() {
        switch (getMode()) {
            case CLAY -> {
                return new ItemStack(Blocks.clay, 1, 0);
            }
            case DIRT -> {
                return new ItemStack(Blocks.dirt, 1, 0);
            }
            case ENDSTONE -> {
                return new ItemStack(Blocks.end_stone, 1, 0);
            }
            case NETHERRACK -> {
                return new ItemStack(Blocks.netherrack, 1, 0);
            }
            case SLIME -> {
                return new ItemStack(Items.slime_ball, 1 + this.worldObj.rand.nextInt(4));
            }
            case SOULSAND -> {
                return new ItemStack(Blocks.soul_sand, 1, 0);
            }
            case OBSIDIAN -> {
                return new ItemStack(Blocks.obsidian, 1, 0);
            }
            case COBBLESTONE -> {
                return new ItemStack(Blocks.cobblestone, 1, 0);
            }
            case BLAZE -> {
                return new ItemStack(Items.blaze_rod, 1, 0);
            }
            case ENDER -> {
                return new ItemStack(Items.ender_pearl, 1, 0);
            }
            case BEETRAP -> {
                return new ItemStack(ENBlocks.BeeTrapTreated, 1, 0);
            }
            case DARKOAK -> {
                return new ItemStack(Blocks.sapling, 1, 5);
            }
            case BLOCK -> {
                return new ItemStack(this.block);
            }
            case RECIPE -> {
                return this.outputStack;
            }
            case MOB -> {
                if (this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL) return this.peacefulDrop;
                return null;
            }
        }
        return null;
    }

    public float getVolume() {
        return this.volume;
    }

    public int getTimer() {
        return this.timer;
    }

    public float getAdjustedVolume() {
        float capacity = 0.79999995F;
        float adjusted = this.volume * capacity;
        adjusted += 0.1F;
        return adjusted;
    }

    private void resetBarrel() {
        this.fluid = new FluidStack(FluidRegistry.WATER, 0);
        this.volume = 0.0F;
        this.color = ColorRegistry.color("white");
        this.colorBase = ColorRegistry.color("white");
        setMode(BarrelMode.EMPTY);
        this.needsUpdate = true;
        this.outputStack = null;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        switch (compound.getInteger("mode")) {
            case 0 -> setMode(BarrelMode.EMPTY);
            case 1 -> setMode(BarrelMode.FLUID);
            case 2 -> setMode(BarrelMode.COMPOST);
            case 3 -> setMode(BarrelMode.DIRT);
            case 4 -> setMode(BarrelMode.CLAY);
            case 5 -> setMode(BarrelMode.SPORED);
            case 6 -> setMode(BarrelMode.SLIME);
            case 7 -> setMode(BarrelMode.NETHERRACK);
            case 8 -> setMode(BarrelMode.ENDSTONE);
            case 9 -> setMode(BarrelMode.MILKED);
            case 10 -> setMode(BarrelMode.SOULSAND);
            case 11 -> setMode(BarrelMode.BEETRAP);
            case 12 -> setMode(BarrelMode.OBSIDIAN);
            case 13 -> setMode(BarrelMode.COBBLESTONE);
            case 14 -> setMode(BarrelMode.BLAZE_COOKING);
            case 15 -> setMode(BarrelMode.BLAZE);
            case 16 -> setMode(BarrelMode.ENDER_COOKING);
            case 17 -> setMode(BarrelMode.ENDER);
            case 18 -> setMode(BarrelMode.DARKOAK);
            case 19 -> setMode(BarrelMode.BLOCK);
            case 20 -> setMode(BarrelMode.RECIPE);
            case 21 -> setMode(BarrelMode.MOB);
            case 22 -> setMode(BarrelMode.FLUIDTRANSFORM);
        }
        this.volume = compound.getFloat("volume");
        this.timer = compound.getInteger("timer");
        this.color = new Color(compound.getInteger("color"));
        this.colorBase = new Color(compound.getInteger("colorBase"));
        this.fluid = new FluidStack(FluidRegistry.getFluid(compound.getShort("fluid")), (int) (this.volume * 1000.0F));
        this.needsUpdate = true;
        if (!compound.getString("block").equals("")) {
            this.block = (Block) Block.blockRegistry.getObject(compound.getString("block"));
        } else {
            this.block = null;
        }
        this.blockMeta = compound.getInteger("blockMeta");
        if (compound.hasKey("outputStack"))
            this.outputStack = ItemStack.loadItemStackFromNBT((NBTTagCompound) compound.getTag("outputStack"));
        if (compound.hasKey("entity")) {
            this.entity = (EntityLivingBase) EntityList
                    .createEntityFromNBT((NBTTagCompound) compound.getTag("entity"), this.worldObj);
            this.entityParticleName = compound.getString("entityParticleName");
            if (compound.hasKey("peacefulDrop"))
                this.peacefulDrop = ItemStack.loadItemStackFromNBT((NBTTagCompound) compound.getTag("peacefulDrop"));
        }
        if (compound.hasKey("transform")) {
            NBTTagCompound tag = compound.getCompoundTag("transform");
            this.transformFluid = FluidRegistry.getFluid(tag.getShort("transformFluid"));
            this.transformMeta = tag.getShort("transformFluidMeta");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("mode", (getMode()).value);
        compound.setFloat("volume", this.volume);
        compound.setInteger("timer", this.timer);
        compound.setInteger("color", this.color.toInt());
        compound.setInteger("colorBase", this.colorBase.toInt());
        compound.setShort("fluid", (short) this.fluid.getFluidID());
        if (this.block == null) {
            compound.setString("block", "");
        } else {
            compound.setString("block", Block.blockRegistry.getNameForObject(this.block));
        }
        compound.setInteger("blockMeta", this.blockMeta);
        if (this.outputStack != null) {
            NBTTagCompound tag = new NBTTagCompound();
            this.outputStack.writeToNBT(tag);
            compound.setTag("outputStack", tag);
        }
        if (this.entity != null) {
            NBTTagCompound tag = new NBTTagCompound();
            this.entity.writeToNBT(tag);
            compound.setTag("entity", tag);
            compound.setString("entityParticleName", this.entityParticleName);
            if (this.peacefulDrop != null) {
                tag = new NBTTagCompound();
                this.peacefulDrop.writeToNBT(tag);
                compound.setTag("peacefulDrop", tag);
            }
        }
        if (this.transformFluid != null) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setShort("transformFluid", (short) this.transformFluid.getID());
            tag.setShort("transformFluidMeta", (short) this.transformMeta);
            compound.setTag("transform", tag);
        }
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

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        int capacity = 1000 - this.fluid.amount;
        if (!doFill) {
            if (getMode() == BarrelMode.EMPTY) return Math.min(resource.amount, 1000);
            if (getMode() == BarrelMode.FLUID && resource.getFluidID() == this.fluid.getFluidID()) {
                return Math.min(capacity, resource.amount);
            }
        } else {
            if (getMode() == BarrelMode.EMPTY) {
                if (resource.getFluidID() != this.fluid.getFluidID()) {
                    int amount = resource.amount;
                    if (resource.amount > 1000) resource.amount = 1000;
                    this.fluid = new FluidStack(FluidRegistry.getFluid(resource.getFluidID()), amount);
                } else {
                    this.fluid.amount = Math.min(resource.amount, 1000);
                }
                setMode(BarrelMode.FLUID);
                this.volume = this.fluid.amount / 1000.0F;
                VanillaPacket.sendTileEntityUpdate(this);
                return resource.amount;
            }
            if (getMode() == BarrelMode.FLUID && resource.getFluidID() == this.fluid.getFluidID()) {
                if (capacity >= resource.amount) {
                    this.fluid.amount += resource.amount;
                    this.volume = this.fluid.amount / 1000.0F;
                    this.needsUpdate = true;
                    return resource.amount;
                }
                this.fluid.amount = 1000;
                this.volume = 1.0F;
                VanillaPacket.sendTileEntityUpdate(this);
                return capacity;
            }
        }
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (resource == null || getMode() != BarrelMode.FLUID || !resource.isFluidEqual(this.fluid)) return null;
        if (!doDrain) {
            if (this.fluid.amount >= resource.amount) {
                return new FluidStack(FluidRegistry.getFluid(resource.getFluidID()), resource.amount);
            }
            return new FluidStack(FluidRegistry.getFluid(resource.getFluidID()), this.fluid.amount);
        }
        if (this.fluid.amount > resource.amount) {
            FluidStack fluidStack = new FluidStack(FluidRegistry.getFluid(resource.getFluidID()), resource.amount);
            this.fluid.amount -= resource.amount;
            this.volume = this.fluid.amount / 1000.0F;
            this.needsUpdate = true;
            return fluidStack;
        }
        FluidStack drained = new FluidStack(FluidRegistry.getFluid(resource.getFluidID()), this.fluid.amount);
        this.fluid.amount = 0;
        this.volume = 0.0F;
        setMode(BarrelMode.EMPTY);
        this.timer = 0;
        this.needsUpdate = true;
        return drained;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (getMode() != BarrelMode.FLUID) return null;
        if (!doDrain) {
            if (this.fluid.amount >= maxDrain) {
                return new FluidStack(FluidRegistry.getFluid(this.fluid.getFluidID()), maxDrain);
            }
            return new FluidStack(FluidRegistry.getFluid(this.fluid.getFluidID()), this.fluid.amount);
        }
        if (this.fluid.amount > maxDrain) {
            FluidStack fluidStack = new FluidStack(FluidRegistry.getFluid(this.fluid.getFluidID()), maxDrain);
            this.fluid.amount -= maxDrain;
            this.volume = this.fluid.amount / 1000.0F;
            this.needsUpdate = true;
            return fluidStack;
        }
        FluidStack drained = new FluidStack(FluidRegistry.getFluid(this.fluid.getFluidID()), this.fluid.amount);
        this.fluid.amount = 0;
        this.volume = 0.0F;
        setMode(BarrelMode.EMPTY);
        this.timer = 0;
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
        FluidTankInfo info = new FluidTankInfo(this.fluid, 1000);
        FluidTankInfo[] array = new FluidTankInfo[1];
        array[0] = info;
        return array;
    }

    public int getNearbyBlocks(Block block, int blockMeta) {
        int count = 0;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (this.worldObj.getBlock(this.xCoord + x, this.yCoord + y, this.zCoord + z) == block
                            && this.worldObj.getBlockMetadata(this.xCoord + x, this.yCoord + y, this.zCoord + z)
                                    == blockMeta)
                        count++;
                }
            }
        }
        return count;
    }

    public HashSet<ItemInfo> getNearbyBlocks() {
        HashSet<ItemInfo> out = new HashSet<>();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) out.add(
                        new ItemInfo(
                                this.worldObj.getBlock(this.xCoord + x, this.yCoord + y, this.zCoord + z),
                                this.worldObj.getBlockMetadata(this.xCoord + x, this.yCoord + y, this.zCoord + z)));
            }
        }
        return out;
    }

    public int getLightLevel() {
        if (getMode() == BarrelMode.FLUID) return this.fluid.getFluid().getLuminosity();
        if (getMode() == BarrelMode.BLAZE || getMode() == BarrelMode.BLAZE_COOKING) return 15;
        return 0;
    }

    @Override
    public int getSizeInventory() {
        return 2;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot == 0) return getExtractItem();
        return null;
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        if (slot == 0) {
            ItemStack item = getExtractItem();
            resetBarrel();
            return item;
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        if (stack == null || stack.getItem() == null) {
            if (slot == 0) resetBarrel();
        } else {
            Item item = stack.getItem();
            int meta = stack.getItemDamage();
            if (slot == 1) {
                if (getMode() == BarrelMode.COMPOST || getMode() == BarrelMode.EMPTY)
                    if (CompostRegistry.containsItem(item, meta)) addCompostItem(CompostRegistry.getItem(item, meta));
                if (getMode() == BarrelMode.FLUID && isFull()) {
                    ItemInfo output = BarrelRecipeRegistry.getOutput(this.fluid, stack);
                    if (output != null) {
                        this.outputStack = new ItemStack(output.getItem(), 1, output.getMeta());
                        setMode(BarrelMode.RECIPE);
                        VanillaPacket.sendTileEntityUpdate(this);
                        return;
                    }
                    EntityWithItem mob = BarrelRecipeRegistry.getMobOutput(this.fluid, stack);
                    if (mob != null) {
                        try {
                            Constructor<EntityLivingBase> constructor = mob.getEntity().getConstructor(World.class);
                            this.entity = constructor.newInstance(this.worldObj);
                        } catch (Exception ignored) {}
                        this.entityParticleName = mob.getParticle();
                        this.peacefulDrop = mob.getDrops();
                        setMode(BarrelMode.MOB);
                        return;
                    }
                }
            }
        }
        VanillaPacket.sendTileEntityUpdate(this);
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
        if (slot == 1) return isItemValid(item);
        return false;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        if (side == 0) return new int[] { 0 };
        if (side == 1) return new int[] { 1 };
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack item, int side) {
        if (side == 1 && slot == 1) return isItemValid(item);
        return false;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack item, int side) {
        if (side == 0 && slot == 0) {
            if ((getMode()).canExtract == ExtractMode.Always) return true;
            return this.worldObj.difficultySetting.getDifficultyId() == 0
                    && (getMode()).canExtract == ExtractMode.PeacefulOnly;
        }
        return false;
    }

    public boolean isItemValid(ItemStack stack) {
        Item item = stack.getItem();
        int meta = stack.getItemDamage();
        if ((!isFull() && getMode() == BarrelMode.COMPOST) || getMode() == BarrelMode.EMPTY)
            if (BarrelConfig.enableBarrelRecipeDirt && CompostRegistry.containsItem(item, meta)) return true;
        ItemInfo output = BarrelRecipeRegistry.getOutput(this.fluid, stack);
        if (isFull() && this.outputStack == null
                && output != null
                && !stack.getItem().hasContainerItem(new ItemStack(output.getItem(), 1, output.getMeta())))
            return true;
        EntityWithItem entity = BarrelRecipeRegistry.getMobOutput(this.fluid, stack);
        if (isFull() && entity != null
                && !stack.getItem().hasContainerItem(stack)
                && this.outputStack == null
                && !isCookingMob())
            return true;
        if (getMode() == BarrelMode.FLUID && isFull()) {
            if (this.fluid.getFluidID() == FluidRegistry.LAVA.getID())
                if (BarrelConfig.enableBarrelRecipeBlazeRod && item == ENItems.DollAngry) return true;
            if (this.fluid.getFluidID() == Fluids.fluidWitchWater.getID())
                return BarrelConfig.enableBarrelRecipeEnderPearl && item == ENItems.DollCreepy;
        }
        return false;
    }

    public boolean isCookingMob() {
        return (this.mode == BarrelMode.MOB);
    }
}
