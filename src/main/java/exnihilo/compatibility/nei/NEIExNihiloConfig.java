package exnihilo.compatibility.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

public class NEIExNihiloConfig implements IConfigureNEI {

    @Override
    public String getName() {
        return "Ex Nihilo";
    }

    @Override
    public String getVersion() {
        return "1.38-53";
    }

    @Override
    public void loadConfig() {
        RecipeHandlerHammer handlerHammer = new RecipeHandlerHammer();
        RecipeHandlerSieve handlerSieve = new RecipeHandlerSieve();
        API.registerRecipeHandler(handlerHammer);
        API.registerUsageHandler(handlerHammer);
        API.registerRecipeHandler(handlerSieve);
        API.registerUsageHandler(handlerSieve);
    }
}
