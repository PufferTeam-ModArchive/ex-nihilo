package exnihilo.blocks.ores;

import java.util.List;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import exnihilo.ExNihilo;

public class BlockOre extends BlockFalling {

    private final String name;

    private IIcon icon;

    private TextureAtlasSprite texture;

    public BlockOre(String name) {
        super(Material.sand);
        this.name = name;
    }

    @SideOnly(Side.CLIENT)
    public void setTexture(TextureAtlasSprite texture) {
        this.texture = texture;
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void getSubBlocks(Item item, CreativeTabs tabs, List items) {
        items.add(new ItemStack(item, 1, 0));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister register) {
        if (this.texture != null) {
            TextureMap map = (TextureMap) register;
            TextureAtlasSprite existing = map.getTextureExtry(this.texture.getIconName());
            if (existing == null) {
                boolean success = map.setTextureEntry(this.texture.getIconName(), this.texture);
                if (success) this.icon = map.getTextureExtry(this.texture.getIconName());
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int id, int meta) {
        if (this.icon != null) return this.icon;
        return Blocks.stone.getIcon(id, meta);
    }

    @Override
    public String getUnlocalizedName() {
        return ExNihilo.MODID + "." + name;
    }

    public String getName() {
        return this.name;
    }
}
