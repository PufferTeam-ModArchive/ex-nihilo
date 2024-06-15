package exnihilo.world;

import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderEndVoid extends WorldProviderEnd {

    @Override
    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderEndVoid(this.worldObj, this.worldObj.getSeed());
    }
}
