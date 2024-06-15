package exnihilo.fluids;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BucketHandler {

    public static final BucketHandler INSTANCE = new BucketHandler();

    public final Map<Block, Item> buckets = new HashMap<>();

    @SubscribeEvent
    public void onBucketFill(FillBucketEvent event) {
        if (event.current.getItem() == Items.bucket) {
            ItemStack result = fillCustomBucket(event.world, event.target);
            if (result == null) return;
            event.result = result;
            event.setResult(Event.Result.ALLOW);
        }
    }

    private ItemStack fillCustomBucket(World world, MovingObjectPosition pos) {
        Item bucket = this.buckets.get(world.getBlock(pos.blockX, pos.blockY, pos.blockZ));
        if (bucket != null && world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ) == 0) {
            world.setBlock(pos.blockX, pos.blockY, pos.blockZ, Blocks.air);
            return new ItemStack(bucket);
        }
        return null;
    }
}
