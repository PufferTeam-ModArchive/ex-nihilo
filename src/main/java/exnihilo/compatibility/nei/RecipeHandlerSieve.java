package exnihilo.compatibility.nei;

import java.awt.Point;
import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exnihilo.api.items.IMesh;
import exnihilo.registries.MeshRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import exnihilo.registries.SieveRegistry;
import exnihilo.registries.helpers.SiftingResult;
import exnihilo.utils.ItemInfo;

public class RecipeHandlerSieve extends TemplateRecipeHandler {

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public class CachedSieveRecipe extends TemplateRecipeHandler.CachedRecipe {

        private final List<PositionedStack> input = new ArrayList<>();

        private final List<PositionedStack> outputs = new ArrayList<>();

        public Point focus;

        @Override
        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(RecipeHandlerSieve.this.cycleticks / 20, this.input);
        }

        @Override
        public List<PositionedStack> getOtherStacks() {
            return this.outputs;
        }

        @Override
        public PositionedStack getResult() {
            return null;
        }

        public CachedSieveRecipe(List<ItemStack> variations, IMesh mesh, ItemStack base, ItemStack focus) {
            super();
            PositionedStack pstack_item = new PositionedStack((base != null) ? base : variations, 11, 3);
            PositionedStack pstack_mesh = new PositionedStack(
                    new ItemStack(mesh.getItem(), 1, 0),
                    11,
                    39);
            pstack_item.setMaxSize(1);
            pstack_mesh.setMaxSize(1);
            this.input.add(pstack_item);
            this.input.add(pstack_mesh);
            int row = 0;
            int col = 0;
            for (ItemStack v : variations) {
                this.outputs.add(new PositionedStack(v, 39 + 18 * col, 3 + 18 * row));
                if (focus != null && NEIServerUtils.areStacksSameTypeCrafting(focus, v))
                    this.focus = new Point(38 + 18 * col, 2 + 18 * row);
                col++;
                if (col > 6) {
                    col = 0;
                    row++;
                }
            }
        }
    }

    @Override
    public String getRecipeName() {
        return "Sieve";
    }

    @Override
    public String getGuiTexture() {
        return "exnihilo:textures/sieveNEI.png";
    }

    private void addCached(List<ItemStack> variations, IMesh mesh, ItemStack base, ItemStack focus) {
        if (variations.size() > 21) {
            List<List<ItemStack>> parts = new ArrayList<>();
            int size = variations.size();
            for (int i = 0; i < size; i += 21)
                parts.add(new ArrayList<>(variations.subList(i, Math.min(size, i + 21))));
            for (List<ItemStack> part : parts) this.arecipes.add(new CachedSieveRecipe(part, mesh, base, focus));
        } else {
            this.arecipes.add(new CachedSieveRecipe(variations, mesh, base, focus));
        }
    }

    @Override
    public void drawBackground(int recipeIndex) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 0, 0, 166, 56);
        Point focus = ((CachedSieveRecipe) this.arecipes.get(recipeIndex)).focus;
        if (focus != null) GuiDraw.drawTexturedModalRect(focus.x, focus.y, 166, 0, 18, 18);
    }

    @Override
    public int recipiesPerPage() {
        return 4;
    }

    @Override
    public void loadTransferRects() {
        this.transferRects
                .add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(18, 22, 18, 14), "exnihilo.sieve"));
    }

    @Override
    public void loadCraftingRecipes(String outputID, Object... results) {
        if (outputID.equals("exnihilo.sieve")) {
            for (IMesh mesh : MeshRegistry.INSTANCE.getRegistry().values()) {
                for (ItemInfo ii : SieveRegistry.getSiftables().get(mesh).keySet()) {
                    ItemStack inputStack = ii.getStack();
                    ArrayList<ItemStack> resultStacks = new ArrayList<>();
                    for (SiftingResult s : SieveRegistry.getSiftingOutput(ii, mesh))
                        resultStacks.add(new ItemStack(s.drop.getItem(), 1, s.drop.getMeta()));
                    addCached(resultStacks, mesh, inputStack, null);
                }
            }
        } else {
            super.loadCraftingRecipes(outputID, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        for (Map.Entry<IMesh, ArrayList<ItemInfo>> set : SieveRegistry.getSources(result).entrySet()) {
            for (ItemInfo ii : set.getValue()) {
                HashMap<ItemInfo, Integer> stored = new HashMap<>();
                for (SiftingResult results : SieveRegistry
                        .getSiftingOutput(Block.getBlockFromItem(ii.getItem()), ii.getMeta(), set.getKey())) {
                    ItemInfo current = new ItemInfo(results.drop.getItem(), results.drop.getMeta());
                    if (stored.containsKey(current)) {
                        stored.put(current, stored.get(current) + 1);
                        continue;
                    }
                    stored.put(current, 1);
                }
                ArrayList<ItemStack> resultStacks = new ArrayList<>();
                for (ItemInfo info : stored.keySet()) {
                    ItemStack stack = info.getStack();
                    stack.stackSize = stored.get(info);
                    resultStacks.add(stack);
                }
                addCached(resultStacks, set.getKey(), ii.getStack(), result);
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        if (Block.getBlockFromItem(ingredient.getItem()) == Blocks.air) return;
        for (IMesh mesh : MeshRegistry.INSTANCE.getRegistry().values()) {
            HashMap<ItemInfo, Integer> stored = new HashMap<>();
            if (!SieveRegistry
                    .registered(Block.getBlockFromItem(ingredient.getItem()), ingredient.getItemDamage(), mesh))
                continue;
            for (SiftingResult results : SieveRegistry.getSiftingOutput(
                    Block.getBlockFromItem(ingredient.getItem()),
                    ingredient.getItemDamage(),
                    mesh)) {
                ItemInfo current = results.drop;
                if (stored.containsKey(current)) {
                    stored.put(current, stored.get(current) + 1);
                    continue;
                }
                stored.put(current, 1);
            }
            ArrayList<ItemStack> resultStacks = new ArrayList<>();
            for (ItemInfo info : stored.keySet()) {
                ItemStack stack = info.getStack();
                stack.stackSize = stored.get(info);
                resultStacks.add(stack);
            }
            addCached(resultStacks, mesh, ingredient, ingredient);
        }
    }

    @Override
    public List<String> handleItemTooltip(GuiRecipe<?> guiRecipe, ItemStack itemStack, List<String> currenttip,
            int recipe) {
        CachedSieveRecipe crecipe = (CachedSieveRecipe) this.arecipes.get(recipe);
        Point mouse = GuiDraw.getMousePosition();
        Point offset = guiRecipe.getRecipePosition(recipe);
        Point relMouse = new Point(
                mouse.x - (guiRecipe.width - 166) / 2 - offset.x,
                mouse.y - (guiRecipe.height - (56 * this.recipiesPerPage())) / 2 - offset.y);
        if (itemStack != null && relMouse.x > 32
                && mouse.y > offset.y + 32
                && mouse.y < (((guiRecipe.height - (56 * this.recipiesPerPage())) / 2 + offset.y) + 56) + 32) {
            currenttip.add("Drop Chance:");
            ItemStack sourceStack = (crecipe.input.get(0)).item;
            Block inBlock = Block.getBlockFromItem(sourceStack.getItem());
            int meta = sourceStack.getItemDamage();
            for (SiftingResult smash : SieveRegistry
                    .getSiftingOutput(inBlock, meta, (IMesh) crecipe.input.get(1).item.getItem())) {
                if (NEIServerUtils.areStacksSameTypeCrafting(
                        itemStack,
                        new ItemStack(smash.drop.getItem(), 1, smash.drop.getMeta()))) {
                    double chance = 100.0D / smash.rarity;
                    currenttip.add("  * " + df.format(chance) + "%");
                }
            }
        }
        return super.handleItemTooltip(guiRecipe, itemStack, currenttip, recipe);
    }
}
