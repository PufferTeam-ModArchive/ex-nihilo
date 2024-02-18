package exnihilo.events;

import codechicken.nei.event.NEIRegisterHandlerInfosEvent;
import codechicken.nei.recipe.HandlerInfo;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import exnihilo.ENBlocks;
import exnihilo.ExNihilo;
import exnihilo.compatibility.nei.RecipeHandlerSieve;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.function.Consumer;

public class HandlerNEIRecipeHandlerInfo {

    @SubscribeEvent(priority= EventPriority.HIGHEST, receiveCanceled=true)
    public void onEvent(NEIRegisterHandlerInfosEvent event)
    {
        event.registerHandlerInfo(
            RecipeHandlerSieve.class,
            "Ex Nihilo",
            "exnihilo",
            handlerInfoSieveBuilder);
    }

    Consumer<HandlerInfo.Builder> handlerInfoSieveBuilder = b ->
    {
        ExNihilo.log.info("Fucking why the hell doesn't this work please god make it stop");
        b.setMaxRecipesPerPage(4).setDisplayStack(new ItemStack(Item.getItemFromBlock(ENBlocks.Sieve), 1, 0));
    };
}
