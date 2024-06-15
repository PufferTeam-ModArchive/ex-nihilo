package exnihilo.items;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import exnihilo.ENBlocks;
import exnihilo.blocks.tileentities.TileEntityLeavesInfested;
import exnihilo.compatibility.foresty.Forestry;

public class ItemSilkworm extends Item {

    public ItemSilkworm() {
        setCreativeTab(CreativeTabs.tabMisc);
    }

    @Override
    public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int par7,
            float par8, float par9, float par10) {
        if (!world.isAirBlock(x, y, z)) {
            Block block = world.getBlock(x, y, z);
            if (block.isLeaves(world, 0, 0, 0) && block != ENBlocks.LeavesInfested && !Forestry.addsThisLeaf(block)) {
                Block oldBlock = world.getBlock(x, y, z);
                int oldMeta = world.getBlockMetadata(x, y, z);
                world.setBlock(x, y, z, ENBlocks.LeavesInfested, 0, 2);
                TileEntityLeavesInfested te = (TileEntityLeavesInfested) world.getTileEntity(x, y, z);
                te.setMimicBlock(oldBlock, oldMeta);
                item.stackSize--;
                if (item.stackSize <= 0) player.destroyCurrentEquippedItem();
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public String getUnlocalizedName() {
        return "exnihilo.silkworm";
    }

    @Override
    public String getUnlocalizedName(ItemStack item) {
        return "exnihilo.silkworm";
    }

    @Override
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon("exnihilo:Silkworm");
    }
}
