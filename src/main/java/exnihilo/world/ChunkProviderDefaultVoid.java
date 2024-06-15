package exnihilo.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;

public class ChunkProviderDefaultVoid extends ChunkProviderGenerate {

    private final World world;

    private final Random rand;

    public ChunkProviderDefaultVoid(World par1World, long par2, boolean par4) {
        super(par1World, par2, par4);
        this.world = par1World;
        this.rand = new Random(this.world.getSeed());
    }

    @Override
    public void func_147424_a(int par1, int par2, Block[] par3ArrayOfBlock) {}

    @Override
    public void populate(IChunkProvider provider, int x, int z) {
        BlockFalling.fallInstantly = true;
        this.rand.setSeed(this.world.getSeed());
        long i1 = this.rand.nextLong() / 2L * 2L + 1L;
        long j1 = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed(x * i1 + z * j1 ^ this.world.getSeed());
        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(provider, this.world, this.rand, x, z, false));
        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(provider, this.world, this.rand, x, z, false));
        BlockFalling.fallInstantly = false;
    }

    @Override
    public void replaceBlocksForBiome(int par1, int par2, Block[] par3ArrayOfBlock, byte[] par4ArrayofBytes,
            BiomeGenBase[] par4ArrayOfBiomeGenBase) {}
}
