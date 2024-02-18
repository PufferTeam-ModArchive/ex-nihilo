package exnihilo.utils;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Objects;

public class BlockInfo {

    public static final BlockInfo EMPTY = new BlockInfo(Blocks.air, 0);

    private Block block;

    private int meta;

    public void setBlock(Block block) {
        this.block = block;
    }

    public void setMeta(int meta) {
        this.meta = meta;
    }

    public Block getBlock() {
        return this.block;
    }

    public int getMeta() {
        return this.meta;
    }

    public BlockInfo(Block block, int meta) {
        this.block = block;
        this.meta = meta;
    }

    public int hashCode() {
        int result = 1;
        Object $block = getBlock();
        result = result * 59 + (($block == null) ? 0 : $block.hashCode());
        return result * 59 + getMeta();
    }

    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof BlockInfo other))
            return false;
        if (!other.canEqual(this))
            return false;
        Object this$block = getBlock(), other$block = other.getBlock();
        return (Objects.equals(this$block, other$block)) && (getMeta() == other.getMeta());
    }

    protected boolean canEqual(Object other) { return other instanceof BlockInfo; }

    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        String key = Block.blockRegistry.getNameForObject(block);
        tag.setString("block", key);
        tag.setInteger("meta", meta);
        return tag;
    }

    public static BlockInfo readFromNBT(NBTTagCompound tag) {
        Block block = (Block) Block.blockRegistry.getObject(tag.getString("block"));
        int meta = tag.getInteger("meta");
        return block == null ? EMPTY : new BlockInfo(block, meta);
    }
}

