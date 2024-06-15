package exnihilo.network;

import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class ENPacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("exnihilo");

    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void init() {
        INSTANCE.registerMessage(MessageCrucibleHandler.class, MessageCrucible.class, nextID(), Side.CLIENT);
        INSTANCE.registerMessage(MessageBarrelHandler.class, MessageBarrel.class, nextID(), Side.CLIENT);
        INSTANCE.registerMessage(MessageSieveHandler.class, MessageSieve.class, nextID(), Side.CLIENT);
    }

    public static void sendToAllAround(IMessage message, TileEntity te, int range) {
        INSTANCE.sendToAllAround(
                message,
                new NetworkRegistry.TargetPoint(
                        (te.getWorldObj()).provider.dimensionId,
                        te.xCoord,
                        te.yCoord,
                        te.zCoord,
                        range));
    }

    public static void sendToAllAround(IMessage message, TileEntity te) {
        sendToAllAround(message, te, 64);
    }

}
