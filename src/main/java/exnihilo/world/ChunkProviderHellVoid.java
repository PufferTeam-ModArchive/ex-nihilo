package exnihilo.world;

import java.util.Random;

import exnihilo.config.WorldConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderHell;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;

public class ChunkProviderHellVoid extends ChunkProviderHell {

    private final World world;

    private final Random rand;

    public ChunkProviderHellVoid(World par1World, long par2) {
        super(par1World, par2);
        this.world = par1World;
        this.rand = new Random(this.world.getSeed());
    }

    @Override
    public void func_147419_a(int par1, int par2, Block[] par3ArrayOfBlock) {}

    @Override
    public void func_147418_b(int par1, int par2, Block[] par3ArrayOfBlock) {}

    @Override
    public void populate(IChunkProvider provider, int x, int z) {
        if (WorldConfig.allowNetherFortresses) {
            super.populate(provider, x, z);
        } else {
            BlockFalling.fallInstantly = true;
            this.rand.setSeed(this.world.getSeed());
            long i1 = this.rand.nextLong() / 2L * 2L + 1L;
            long j1 = this.rand.nextLong() / 2L * 2L + 1L;
            this.rand.setSeed(x * i1 + z * j1 ^ this.world.getSeed());
            MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(provider, this.world, this.rand, x, z, false));
            MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(provider, this.world, this.rand, x, z, false));
            BlockFalling.fallInstantly = false;
        }
    }
}
