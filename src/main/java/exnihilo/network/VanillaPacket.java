package exnihilo.network;

import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;

public class VanillaPacket {

    public static void sendTileEntityUpdate(TileEntity tileEntity) {
        if ((tileEntity.getWorldObj()).isRemote) return;
        List playerList = (tileEntity.getWorldObj()).playerEntities;
        for (Object obj : playerList) {
            EntityPlayerMP entityPlayer = (EntityPlayerMP) obj;
            if (Math.hypot(entityPlayer.posX - tileEntity.xCoord + 0.5D, entityPlayer.posZ - tileEntity.zCoord + 0.5D)
                    < 64.0D)
                entityPlayer.playerNetServerHandler.sendPacket(tileEntity.getDescriptionPacket());
        }
    }
}
