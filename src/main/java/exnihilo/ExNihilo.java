package exnihilo;

import net.minecraft.init.Blocks;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gtnewhorizon.gtnhlib.config.ConfigException;
import com.gtnewhorizon.gtnhlib.config.ConfigurationManager;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import exnihilo.compatibility.AE2;
import exnihilo.compatibility.IC2;
import exnihilo.compatibility.MineFactoryReloaded;
import exnihilo.compatibility.OreList;
import exnihilo.compatibility.ThermalExpansion;
import exnihilo.compatibility.TinkersConstruct;
import exnihilo.compatibility.foresty.Forestry;
import exnihilo.config.*;
import exnihilo.events.HandlerHammer;
import exnihilo.events.HandlerNEIRecipeHandlerInfo;
import exnihilo.network.ENPacketHandler;
import exnihilo.proxies.Proxy;
import exnihilo.registries.BarrelRecipeRegistry;
import exnihilo.registries.ColorRegistry;
import exnihilo.registries.CompostRegistry;
import exnihilo.registries.CrucibleRegistry;
import exnihilo.registries.HammerRegistry;
import exnihilo.registries.HeatRegistry;
import exnihilo.registries.SieveRegistry;
import exnihilo.utils.CrookUtils;

@Mod(modid = ExNihilo.MODID, name = ExNihilo.MODNAME, version = Tags.VERSION)
public class ExNihilo {

    public static final String MODID = "exnihilo";
    public static final String MODNAME = "Ex Nihilo";

    @Instance(ExNihilo.MODID)
    public static ExNihilo instance;

    @SidedProxy(clientSide = "exnihilo.proxies.ClientProxy", serverSide = "exnihilo.proxies.ServerProxy")
    public static Proxy proxy = Proxy.getProxy();

    public static Logger log;

    @EventHandler
    public void PreInitialize(FMLPreInitializationEvent event) {
        try {
            ConfigurationManager.registerConfig(GeneralConfig.class);
            ConfigurationManager.registerConfig(SieveConfig.class);
            ConfigurationManager.registerConfig(HammerConfig.class);
            ConfigurationManager.registerConfig(CrucibleConfig.class);
            ConfigurationManager.registerConfig(BarrelConfig.class);
            ConfigurationManager.registerConfig(CrookConfig.class);
            ConfigurationManager.registerConfig(WorldConfig.class);
            ConfigurationManager.registerConfig(OreConfig.class);
            ConfigurationManager.registerConfig(ColorConfig.class);
        } catch (ConfigException e) {
            throw new RuntimeException(e);
        }

        log = LogManager.getLogger("Ex Nihilo");
        ENPacketHandler.init();
        ENBlocks.registerBlocks();
        Fluids.registerFluids();
        Fluids.registerBuckets();
        ENItems.registerItems();
        Entities.registerEntities();
        ColorRegistry.load();
        CompostRegistry.load();
        HammerRegistry.load();
        proxy.initializeSounds();
        proxy.initializeRenderers();
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new HandlerHammer());
        if (Loader.isModLoaded("notenoughitems"))
            MinecraftForge.EVENT_BUS.register(new HandlerNEIRecipeHandlerInfo());
    }

    @EventHandler
    public void Initialize(FMLInitializationEvent event) {
        Blocks.fire.setFireInfo(ENBlocks.Barrel, 5, 150);
        Blocks.fire.setFireInfo(ENBlocks.LeavesInfested, 5, 150);
        Blocks.fire.setFireInfo(ENBlocks.Sieve, 5, 150);
        if (SieveConfig.enableDefaultSieveRewards) SieveRegistry.registerRewards();
        if (HammerConfig.enableDefaultHammerRewards) HammerRegistry.registerSmashables();
        if (CrucibleConfig.enableDefaultCrucibleRewards) CrucibleRegistry.registerMeltables();
        if (CrucibleConfig.enableDefaultHeatSources) HeatRegistry.registerVanillaHeatSources();
        Recipes.registerCraftingRecipes();
        Recipes.registerFurnaceRecipes();
        World.registerWorldProviders();
        FMLInterModComms.sendMessage("Waila", "register", "exnihilo.compatibility.Waila.callbackRegister");
    }

    @EventHandler
    public void PostInitialize(FMLPostInitializationEvent event) {
        OreList.registerOres();
        CrookUtils.load();
        if (Loader.isModLoaded("IC2")) {
            log.info("+++ - Found IC2!");
            IC2.loadCompatibility();
        }
        if (Loader.isModLoaded("Forestry")) {
            log.info("+++ - Found Forestry!");
            Forestry.loadCompatibility();
        }
        if (Loader.isModLoaded("ThermalExpansion")) {
            log.info("+++ - Found ThermalExpansion!");
            ThermalExpansion.loadCompatibility();
        }
        if (Loader.isModLoaded("appliedenergistics2")) {
            log.info("+++ - Found AE2!");
            AE2.loadCompatibility();
        }
        if (Loader.isModLoaded("MineFactoryReloaded")) {
            log.info("+++ - Found MineFactory Reloaded!");
            MineFactoryReloaded.loadCompatibility();
        }
        if (Loader.isModLoaded("TConstruct")) {
            log.info("+++ - Found Tinkers Construct!");
            TinkersConstruct.loadCompatibility();
        }
        OreList.processOreDict();
        CompostRegistry.registerOreDictAdditions(BarrelConfig.compostAdditionsOredict);
        CompostRegistry.registerNonDictAdditions(BarrelConfig.compostAdditions);
        SieveRegistry.registerOreDictAdditions(SieveConfig.sieveAdditionsOredict);
        SieveRegistry.registerNonDictAdditions(SieveConfig.sieveAdditions);
        BarrelRecipeRegistry.registerBaseRecipes();
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void textureHook(TextureStitchEvent.Post event) {
        Fluids.registerIcons(event);
    }
}
