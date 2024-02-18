package exnihilo.compatibility;

import exnihilo.ExNihilo;
import exnihilo.blocks.BlockBarrel;
import exnihilo.blocks.BlockCrucible;
import exnihilo.blocks.BlockLeavesInfested;
import exnihilo.blocks.BlockSieve;
import exnihilo.blocks.tileentities.TileEntityBarrel;
import exnihilo.blocks.tileentities.TileEntityCrucible;
import exnihilo.blocks.tileentities.TileEntityLeavesInfested;
import exnihilo.blocks.tileentities.TileEntitySieve;
import java.text.DecimalFormat;
import java.util.List;

import exnihilo.utils.BlockInfo;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class Waila implements IWailaDataProvider {
  @Override
  public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
    return null;
  }

  @Override
  public List<String> getWailaHead(ItemStack stack, List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
    return currentTip;
  }

  @Override
  public List<String> getWailaBody(ItemStack stack, List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
    if (accessor.getBlock() instanceof BlockBarrel) {
      TileEntityBarrel teBarrel = (TileEntityBarrel)accessor.getTileEntity();
      currentTip.add(getBarrelDisplay(teBarrel.getMode(), teBarrel));
    } else if (accessor.getBlock() instanceof BlockLeavesInfested) {
      TileEntityLeavesInfested teLeaves = (TileEntityLeavesInfested)accessor.getTileEntity();
      currentTip.add(getLeavesDisplay(teLeaves));
    } else if (accessor.getBlock() instanceof BlockSieve) {
      TileEntitySieve teSieve = (TileEntitySieve)accessor.getTileEntity();
      currentTip.add(getSieveDisplay(teSieve));
    } else if (accessor.getBlock() instanceof BlockCrucible) {
      TileEntityCrucible teCrucible = (TileEntityCrucible)accessor.getTileEntity();
      currentTip.add("Solid Volume: " + (int)teCrucible.getSolidVolume());
      currentTip.add("Fluid Volume: " + (int)teCrucible.getFluidVolume() + "mb");
      currentTip.add(getCrucibleDisplay(teCrucible));
    }
    return currentTip;
  }

  @Override
  public List<String> getWailaTail(ItemStack stack, List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
    return currentTip;
  }

  public String getBarrelDisplay(TileEntityBarrel.BarrelMode mode, TileEntityBarrel barrel) {
    DecimalFormat format = new DecimalFormat("##.#");
      switch (mode) {
          case EMPTY -> {
              return "Empty";
          }
          case FLUID -> {
              if (barrel.isFull()) return barrel.fluid.getFluid().getName();
              return barrel.fluid.getFluid().getName() + " " + format.format((barrel.getVolume() * 100.0F)) + "%";
          }
          case COMPOST -> {
              if (barrel.isFull()) return "Composting: " + Math.round(getBarrelTimeRemaining(barrel)) + "%";
              return "Collecting Material: " + format.format((barrel.getVolume() * 100.0F)) + "%";
          }
          case DIRT -> {
              return "Dirt";
          }
          case CLAY -> {
              return "Clay";
          }
          case MILKED -> {
              return "Sliming: " + Math.round(getBarrelTimeRemaining(barrel)) + "%";
          }
          case SLIME -> {
              return "Slime";
          }
          case SPORED, FLUIDTRANSFORM -> {
              return "Transforming: " + Math.round(getBarrelTimeRemaining(barrel)) + "%";
          }
          case ENDER_COOKING, BLAZE_COOKING, MOB -> {
              return "Summoning: " + Math.round(getBarrelTimeRemaining(barrel)) + "%";
          }
          case ENDER, BLAZE -> {
              return "Incoming!";
          }
          case DARKOAK -> {
              return "Dark Oak Sapling";
          }
          case BEETRAP -> {
              return "Scented Artifical Hive";
          }
          case COBBLESTONE -> {
              return "Cobblestone";
          }
          case ENDSTONE -> {
              return "End Stone";
          }
          case NETHERRACK -> {
              return "Netherrack";
          }
          case OBSIDIAN -> {
              return "Obsidian";
          }
          case SOULSAND -> {
              return "Soul Sand";
          }
          case BLOCK -> {
              return barrel.block.getLocalizedName();
          }
          case RECIPE -> {
              return barrel.outputStack.getDisplayName();
          }
      }
    return "";
  }

  public String getLeavesDisplay(TileEntityLeavesInfested leaves) {
    if (leaves.getProgress() >= 1.0F)
      return "Infested";
    return "Infesting: " + Math.round(getLeavesTimeRemaining(leaves)) + "%";
  }

  public String getSieveDisplay(TileEntitySieve sieve) {
    if (sieve.getCurrentStack() == BlockInfo.EMPTY)
      return "Empty";
    return Math.round(getSieveClicksRemaining(sieve)) + "% left";
  }

  public String getCrucibleDisplay(TileEntityCrucible crucible) {
    return "Melting Speed: " + (crucible.getMeltSpeed() * 10.0F) + "x";
  }

  public float getBarrelTimeRemaining(TileEntityBarrel barrel) {
    return barrel.getTimer() / 1000.0F * 100.0F;
  }

  public float getLeavesTimeRemaining(TileEntityLeavesInfested leaves) {
    return leaves.getProgress() / 1.0F * 100.0F;
  }

  public float getSieveClicksRemaining(TileEntitySieve sieve) {
    return (1.0F - sieve.getProgress()) * 100.0F;
  }

  public static void callbackRegister(IWailaRegistrar registrar) {
    Waila instance = new Waila();
    registrar.registerBodyProvider(instance, BlockBarrel.class);
    registrar.registerBodyProvider(instance, BlockLeavesInfested.class);
    registrar.registerBodyProvider(instance, BlockSieve.class);
    registrar.registerBodyProvider(instance, BlockCrucible.class);
  }

  @Override
  public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) {
    ExNihilo.log.info("Waila getNBTData called");
    return tag;
  }
}
