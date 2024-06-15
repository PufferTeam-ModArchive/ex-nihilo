package exnihilo;

import exnihilo.api.items.IMesh;
import exnihilo.items.meshes.*;
import exnihilo.registries.MeshRegistry;
import net.minecraft.item.Item;

import cpw.mods.fml.common.registry.GameRegistry;
import exnihilo.items.ItemGrassSeeds;
import exnihilo.items.ItemPorcelainBall;
import exnihilo.items.ItemSilkworm;
import exnihilo.items.ItemSilkwormCooked;
import exnihilo.items.ItemSpores;
import exnihilo.items.ItemStone;
import exnihilo.items.crooks.CrookType;
import exnihilo.items.crooks.ItemCrook;
import exnihilo.items.dolls.ItemDoll;
import exnihilo.items.dolls.ItemDollAngry;
import exnihilo.items.dolls.ItemDollCreepy;
import exnihilo.items.hammers.HammerType;
import exnihilo.items.hammers.ItemHammer;
import exnihilo.items.seeds.ItemSeedAcacia;
import exnihilo.items.seeds.ItemSeedBirch;
import exnihilo.items.seeds.ItemSeedCactus;
import exnihilo.items.seeds.ItemSeedCarrot;
import exnihilo.items.seeds.ItemSeedJungle;
import exnihilo.items.seeds.ItemSeedOak;
import exnihilo.items.seeds.ItemSeedPotato;
import exnihilo.items.seeds.ItemSeedRubber;
import exnihilo.items.seeds.ItemSeedSpruce;
import exnihilo.items.seeds.ItemSeedSugarcane;

public class ENItems {

    public static Item HammerWood;

    public static Item HammerStone;

    public static Item HammerIron;

    public static Item HammerGold;

    public static Item HammerDiamond;

    public static Item CrookWood;

    public static Item CrookStone;

    public static Item CrookIron;

    public static Item CrookGold;

    public static Item CrookDiamond;

    public static Item CrookBone;

    public static Item Silkworm;

    public static Item SilkwormCooked;

    public static ItemMesh MeshSilk;

    public static ItemMesh MeshFlint;

    public static ItemMesh MeshIron;

    public static ItemMesh MeshDiamond;

    public static Item Stones;

    public static Item Porcelain;

    public static Item Doll;

    public static Item DollAngry;

    public static Item DollCreepy;

    public static Item Spores;

    public static Item GrassSeeds;

    public static Item SeedsOak;

    public static Item SeedsAcacia;

    public static Item SeedsSpruce;

    public static Item SeedsBirch;

    public static Item SeedsJungle;

    public static Item SeedsCactus;

    public static Item SeedsSugarcane;

    public static Item SeedsCarrot;

    public static Item SeedsPotato;

    public static Item SeedsRubber;

    public static Item IceShard;

    public static void registerItems() {
        HammerWood = new ItemHammer(HammerType.WOOD);
        GameRegistry.registerItem(HammerWood, HammerType.WOOD.getName());
        HammerStone = new ItemHammer(HammerType.STONE);
        GameRegistry.registerItem(HammerStone, HammerType.STONE.getName());
        HammerIron = new ItemHammer(HammerType.IRON);
        GameRegistry.registerItem(HammerIron, HammerType.IRON.getName());
        HammerGold = new ItemHammer(HammerType.GOLD);
        GameRegistry.registerItem(HammerGold, HammerType.GOLD.getName());
        HammerDiamond = new ItemHammer(HammerType.DIAMOND);
        GameRegistry.registerItem(HammerDiamond, HammerType.DIAMOND.getName());
        CrookWood = new ItemCrook(CrookType.WOOD);
        GameRegistry.registerItem(CrookWood, "crook");
        CrookStone = new ItemCrook(CrookType.STONE);
        GameRegistry.registerItem(CrookStone, "crook_stone");
        CrookIron = new ItemCrook(CrookType.IRON);
        GameRegistry.registerItem(CrookIron, "crook_iron");
        CrookGold = new ItemCrook(CrookType.GOLD);
        GameRegistry.registerItem(CrookGold, "crook_gold");
        CrookDiamond = new ItemCrook(CrookType.DIAMOND);
        GameRegistry.registerItem(CrookDiamond, "crook_diamond");
        CrookBone = new ItemCrook(CrookType.BONE);
        GameRegistry.registerItem(CrookBone, "crook_bone");
        Silkworm = new ItemSilkworm();
        GameRegistry.registerItem(Silkworm, "silkworm");
        MeshSilk = new SilkMesh();
        GameRegistry.registerItem(MeshSilk, MeshSilk.getName());
        MeshRegistry.INSTANCE.register("silk", (IMesh) MeshSilk);
        MeshFlint = new FlintMesh();
        GameRegistry.registerItem(MeshFlint, MeshFlint.getName());
        MeshRegistry.INSTANCE.register("flint", (IMesh) MeshFlint);
        MeshIron = new IronMesh();
        GameRegistry.registerItem(MeshIron, MeshIron.getName());
        MeshRegistry.INSTANCE.register("iron", (IMesh) MeshIron);
        MeshDiamond = new DiamondMesh();
        GameRegistry.registerItem(MeshDiamond, MeshDiamond.getName());
        MeshRegistry.INSTANCE.register("diamond", (IMesh) MeshDiamond);
        Spores = new ItemSpores();
        GameRegistry.registerItem(Spores, "spores");
        GrassSeeds = new ItemGrassSeeds();
        GameRegistry.registerItem(GrassSeeds, "seed_grass");
        Stones = new ItemStone();
        GameRegistry.registerItem(Stones, "stone");
        Porcelain = new ItemPorcelainBall();
        GameRegistry.registerItem(Porcelain, "porcelain");
        Doll = new ItemDoll();
        GameRegistry.registerItem(Doll, "doll");
        DollAngry = new ItemDollAngry();
        GameRegistry.registerItem(DollAngry, "doll_angry");
        DollCreepy = new ItemDollCreepy();
        GameRegistry.registerItem(DollCreepy, "doll_creepy");
        SilkwormCooked = new ItemSilkwormCooked();
        GameRegistry.registerItem(SilkwormCooked, "silkworm_cooked");
        SeedsOak = new ItemSeedOak();
        GameRegistry.registerItem(SeedsOak, "seed_oak");
        SeedsAcacia = new ItemSeedAcacia();
        GameRegistry.registerItem(SeedsAcacia, "seed_acacia");
        SeedsSpruce = new ItemSeedSpruce();
        GameRegistry.registerItem(SeedsSpruce, "seed_spruce");
        SeedsBirch = new ItemSeedBirch();
        GameRegistry.registerItem(SeedsBirch, "seed_birch");
        SeedsJungle = new ItemSeedJungle();
        GameRegistry.registerItem(SeedsJungle, "seed_jungle");
        SeedsCactus = new ItemSeedCactus();
        GameRegistry.registerItem(SeedsCactus, "seed_cactus");
        SeedsSugarcane = new ItemSeedSugarcane();
        GameRegistry.registerItem(SeedsSugarcane, "seed_sugar_cane");
        SeedsCarrot = new ItemSeedCarrot();
        GameRegistry.registerItem(SeedsCarrot, "seed_carrot");
        SeedsPotato = new ItemSeedPotato();
        GameRegistry.registerItem(SeedsPotato, "seed_potato");
        SeedsRubber = new ItemSeedRubber();
        GameRegistry.registerItem(SeedsRubber, "seed_rubber");
        IceShard = (new Item()).setUnlocalizedName("ice_shard").setTextureName("exnihilo:IceShard");
        GameRegistry.registerItem(IceShard, "ice_shard");
    }
}
