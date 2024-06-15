package exnihilo.proxies;

import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Proxy {

    protected static Proxy proxyInstance = null;

    public static void setInstance(Proxy newProxy) {
        proxyInstance = newProxy;
    }

    public static Proxy getProxy() {
        if (proxyInstance == null) proxyInstance = new Proxy();
        return proxyInstance;
    }

    public void initializeSounds() {}

    public void initializeRenderers() {}

    public World getWorldObj() {
        return null;
    }

    public static boolean runningOnServer() {
        boolean server = false;
        try {
            server = serverCheck();
        } catch (NoSuchMethodError ignored) {}
        return server;
    }

    @SideOnly(Side.SERVER)
    public static boolean serverCheck() {
        return true;
    }
}
