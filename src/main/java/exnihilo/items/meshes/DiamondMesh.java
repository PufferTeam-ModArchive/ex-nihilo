package exnihilo.items.meshes;

import exnihilo.api.items.IMesh;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

public class DiamondMesh extends ItemMesh implements IMesh {

    @Override
    public String getName() {
        return "mesh_diamond";
    }

    @Override
    public IIcon getSieveRenderIcon() {
        return this.sieveRenderIcon;
    }

    @Override
    public Item getItem() {
        return this;
    }
}
