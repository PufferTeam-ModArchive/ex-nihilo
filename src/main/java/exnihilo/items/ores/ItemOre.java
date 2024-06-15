package exnihilo.items.ores;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemOre extends Item {

    private final String name;

    private TextureAtlasSprite texture;

    public ItemOre(String name) {
        this.name = name;
        setCreativeTab(CreativeTabs.tabMaterials);
    }

    @SideOnly(Side.CLIENT)
    public void setTexture(TextureAtlasSprite texture) {
        this.texture = texture;
    }

    @Override
    public String getUnlocalizedName() {
        return "exnihilo." + this.name;
    }

    @Override
    public String getUnlocalizedName(ItemStack item) {
        return "exnihilo." + this.name;
    }

    @Override
    public void registerIcons(IIconRegister register) {
        if (this.texture != null) {
            TextureMap map = (TextureMap) register;
            TextureAtlasSprite existing = map.getTextureExtry(this.texture.getIconName());
            if (existing == null) {
                boolean success = map.setTextureEntry(this.texture.getIconName(), this.texture);
                if (success) {
                    this.itemIcon = map.getTextureExtry(this.texture.getIconName());
                } else {
                    this.itemIcon = Items.coal.getIconFromDamage(0);
                }
            }
        }
    }
}
