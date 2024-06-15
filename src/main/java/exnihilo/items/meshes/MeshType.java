package exnihilo.items.meshes;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

import exnihilo.ENItems;

public enum MeshType {

    NONE("none"),
    SILK("silk"),
    FLINT("flint"),
    IRON("iron"),
    DIAMOND("diamond");

    private final String name;
    private IIcon meshRenderIcon;

    MeshType(String name) {
        this.name = "mesh_" + name;
    }

    public String getName() {
        return this.name;
    }

    public IIcon getMeshRenderIcon() {
        return this.meshRenderIcon;
    }

    public void registerMeshRenderIcon(IIconRegister register) {
        this.meshRenderIcon = register.registerIcon("exnihilo:" + this.name);
    }

    public static Item getItemForType(MeshType type) {
        switch (type) {
            case SILK:
                return ENItems.MeshSilk;
            case FLINT:
                return ENItems.MeshFlint;
            case IRON:
                return ENItems.MeshIron;
            case DIAMOND:
                return ENItems.MeshDiamond;
            default:
                return null;
        }
    }

    public static MeshType[] getValues() {
        MeshType[] res = new MeshType[5];
        res[0] = NONE;
        res[1] = SILK;
        res[2] = FLINT;
        res[3] = IRON;
        res[4] = DIAMOND;
        return res;
    }
}
