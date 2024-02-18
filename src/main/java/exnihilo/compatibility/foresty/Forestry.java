package exnihilo.compatibility.foresty;

import cpw.mods.fml.common.registry.GameRegistry;
import exnihilo.ENBlocks;
import exnihilo.ENItems;
import exnihilo.ExNihilo;
import exnihilo.registries.SieveRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class Forestry {
  public static final String modId = "Forestry";

  private static Block forestryLeafBlock = null;

  public static void loadCompatibility() {
    forestryLeafBlock = GameRegistry.findBlock("Forestry", "leaves");
    ItemStack apatite = GameRegistry.findItemStack("Forestry", "apatite", 1);
    if (apatite != null) {
      SieveRegistry.register(Blocks.gravel, 0, apatite.getItem(), apatite.getItemDamage(), 16);
      ExNihilo.log.info("Apatite was successfully integrated");
    } else {
      ExNihilo.log.error("APATITE WAS NOT INTEGRATED");
    }
    HiveRegistry.registerHives();
    registerRecipes();
    ExNihilo.log.info("--- Forestry Integration Complete!");
  }

  public static boolean addsThisLeaf(Block block) {
    return Block.isEqualTo(block, forestryLeafBlock);
  }

  private static void registerRecipes() {
    GameRegistry.addShapelessRecipe(new ItemStack(ENBlocks.BeeTrap, 1, 0),
        new ItemStack(Blocks.hay_block, 1, 0),
        new ItemStack(ENItems.MeshSilk, 1, 0));
    ExNihilo.log.info("Recipes sucessfully added");
  }
}
