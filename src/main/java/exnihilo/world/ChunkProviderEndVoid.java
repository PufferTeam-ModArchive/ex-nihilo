package exnihilo.world;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.ChunkProviderEnd;

public class ChunkProviderEndVoid extends ChunkProviderEnd {

    public ChunkProviderEndVoid(World par1World, long par2) {
        super(par1World, par2);
    }

    @Override
    public void func_147420_a(int par1, int par2, Block[] par3ArrayOfBlock, BiomeGenBase[] par4ArrayOfBiomeGenBase) {}

    @Override
    public void func_147421_b(int par1, int par2, Block[] par3ArrayOfBlock, BiomeGenBase[] par4ArrayOfBiomeGenBase) {}
}
