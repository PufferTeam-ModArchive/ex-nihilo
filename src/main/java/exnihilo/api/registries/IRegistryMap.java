package exnihilo.api.registries;

import java.util.Map;

public interface IRegistryMap<K, V> extends IRegistry<Map<K, V>> {

    void register(K key, V value);

    V get(K key);
}
