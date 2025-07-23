package exnihilo.items.meshes;

import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

import exnihilo.api.items.IMesh;

public class IronMesh extends ItemMesh implements IMesh {

    @Override
    public String getName() {
        return "mesh_iron";
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
