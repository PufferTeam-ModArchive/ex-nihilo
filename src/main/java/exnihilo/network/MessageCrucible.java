package exnihilo.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class MessageCrucible implements IMessage {

    public int x;

    public int y;

    public int z;

    public float fluidVolume;

    public float solidVolume;

    public MessageCrucible() {}

    public MessageCrucible(int x, int y, int z, float fluidVolume, float solidVolume) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.fluidVolume = fluidVolume;
        this.solidVolume = solidVolume;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.fluidVolume = buf.readFloat();
        this.solidVolume = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
        buf.writeFloat(this.fluidVolume);
        buf.writeFloat(this.solidVolume);
    }
}
