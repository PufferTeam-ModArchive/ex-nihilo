package exnihilo.network;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import exnihilo.blocks.tileentities.TileEntityCrucible;

public class MessageCrucibleHandler implements IMessageHandler<MessageCrucible, IMessage> {

    @Override
    public IMessage onMessage(MessageCrucible message, MessageContext ctx) {
        World world = (Minecraft.getMinecraft()).thePlayer.worldObj;
        if (world.getTileEntity(message.x, message.y, message.z) != null) {
            TileEntityCrucible te = (TileEntityCrucible) world.getTileEntity(message.x, message.y, message.z);
            te.setVolumes(message.fluidVolume, message.solidVolume);
            world.markBlockForUpdate(message.x, message.y, message.z);
        }
        return null;
    }
}
