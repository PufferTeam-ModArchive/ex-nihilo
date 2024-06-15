package exnihilo.world;

import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderDefaultVoid extends WorldProvider {

    @Override
    public String getDimensionName() {
        return "Overworld";
    }

    @Override
    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderDefaultVoid(this.worldObj, this.worldObj.getSeed(), false);
    }
}
