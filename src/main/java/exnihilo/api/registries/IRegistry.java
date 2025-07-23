package exnihilo.api.registries;

public interface IRegistry<V> {

    void clearRegistry();

    V getRegistry();
}
