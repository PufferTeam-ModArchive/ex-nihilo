package exnihilo.proxies;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import exnihilo.ENBlocks;
import exnihilo.ENItems;
import exnihilo.blocks.models.ModelBarrel;
import exnihilo.blocks.models.ModelBlock;
import exnihilo.blocks.models.ModelCrucible;
import exnihilo.blocks.models.ModelCrucibleRaw;
import exnihilo.blocks.models.ModelSieve;
import exnihilo.blocks.models.ModelSieveMesh;
import exnihilo.blocks.renderers.RenderBarrel;
import exnihilo.blocks.renderers.RenderCrucible;
import exnihilo.blocks.renderers.RenderCrucibleUnfired;
import exnihilo.blocks.renderers.RenderLeaves;
import exnihilo.blocks.renderers.RenderSieve;
import exnihilo.blocks.renderers.blockItems.ItemRenderBarrel;
import exnihilo.blocks.renderers.blockItems.ItemRenderCrucible;
import exnihilo.blocks.renderers.blockItems.ItemRenderCrucibleUnfired;
import exnihilo.blocks.renderers.blockItems.ItemRenderLeaves;
import exnihilo.blocks.renderers.blockItems.ItemRenderSieve;
import exnihilo.blocks.tileentities.TileEntityBarrel;
import exnihilo.blocks.tileentities.TileEntityCrucible;
import exnihilo.blocks.tileentities.TileEntityCrucibleUnfired;
import exnihilo.blocks.tileentities.TileEntityLeavesInfested;
import exnihilo.blocks.tileentities.TileEntitySieve;
import exnihilo.entities.EntityStone;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends Proxy {

    public ClientProxy() {
        Proxy.setInstance(this);
    }

    @Override
    public void initializeSounds() {}

    @Override
    public void initializeRenderers() {
        ModelBarrel barrel = new ModelBarrel();
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBarrel.class, new RenderBarrel(barrel));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ENBlocks.Barrel), new ItemRenderBarrel(barrel));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ENBlocks.BarrelStone), new ItemRenderBarrel(barrel));

        ModelBlock block = new ModelBlock();
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLeavesInfested.class, new RenderLeaves(block));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ENBlocks.LeavesInfested), new ItemRenderLeaves(block));

        ModelSieve sieve = new ModelSieve();
        ModelSieveMesh mesh = new ModelSieveMesh();
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySieve.class, new RenderSieve(sieve, mesh));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ENBlocks.Sieve), new ItemRenderSieve(sieve));

        ModelCrucible crucible = new ModelCrucible();
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrucible.class, new RenderCrucible(crucible));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ENBlocks.Crucible), new ItemRenderCrucible(crucible));

        ModelCrucibleRaw crucibleRaw = new ModelCrucibleRaw();
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrucibleUnfired.class, new RenderCrucibleUnfired(crucibleRaw));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ENBlocks.CrucibleUnfired), new ItemRenderCrucibleUnfired(crucibleRaw));

        RenderingRegistry.registerEntityRenderingHandler(EntityStone.class, new RenderSnowball(ENItems.Stones));
    }

    @Override
    public World getWorldObj() {
        World world = null;
        try {
            world = (Minecraft.getMinecraft()).theWorld;
        } catch (Exception ignored) {}
        return world;
    }
}
