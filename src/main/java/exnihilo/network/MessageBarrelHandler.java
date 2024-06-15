package exnihilo.network;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import exnihilo.blocks.tileentities.TileEntityBarrel;

public class MessageBarrelHandler implements IMessageHandler<MessageBarrel, IMessage> {

    @Override
    public IMessage onMessage(MessageBarrel message, MessageContext ctx) {
        World world = (Minecraft.getMinecraft()).thePlayer.worldObj;
        if (world.getTileEntity(message.x, message.y, message.z) != null) {
            TileEntityBarrel te = (TileEntityBarrel) world.getTileEntity(message.x, message.y, message.z);
            te.setTimer(message.timer);
            world.markBlockForUpdate(message.x, message.y, message.z);
        }
        return null;
    }
}
