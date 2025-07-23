package exnihilo.registries;

import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import exnihilo.api.items.IMesh;
import exnihilo.api.registries.IMeshRegistry;

public class MeshRegistry implements IMeshRegistry {

    public static final MeshRegistry INSTANCE = new MeshRegistry();

    private final Map<String, IMesh> registry;
    private final Map<IMesh, String> reverseRegistry;

    public MeshRegistry() {
        this.registry = new HashMap<>();
        this.reverseRegistry = new HashMap<>();
    }

    @Override
    public void clearRegistry() {
        registry.clear();
        reverseRegistry.clear();
    }

    @Override
    public Map<String, IMesh> getRegistry() {
        return registry;
    }

    /**
     * Registers a Mesh with the supplied mod-unique ID. If no mod id can be determined, exnihilo will be assumed. The
     * prefix is separated with a colon.
     *
     * @param id   id to prefix and register under
     * @param mesh the mesh to register
     */
    @Override
    public void register(String id, IMesh mesh) {
        String prefix;
        ModContainer mc = Loader.instance().activeModContainer();
        if (mc != null) {
            prefix = mc.getModId();
        } else {
            prefix = "exnihilo";
        }
        id = prefix + ":" + id;
        if (registry.containsKey(id)) {
            throw new IllegalArgumentException("Tried to register a mesh to an existing ID: " + id);
        }
        if (reverseRegistry.containsKey(mesh)) {
            throw new IllegalArgumentException("Tried to register the same mesh twice: " + id);
        }
        registry.put(id, mesh);
        reverseRegistry.put(mesh, id);
    }

    @Override
    public IMesh get(String key) {
        return registry.get(key);
    }

    public String get(IMesh mesh) {
        return reverseRegistry.get(mesh);
    }
}
