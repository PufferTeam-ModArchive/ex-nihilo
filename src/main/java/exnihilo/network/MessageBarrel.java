package exnihilo.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class MessageBarrel implements IMessage {

    public int x;

    public int y;

    public int z;

    public int timer;

    public MessageBarrel() {}

    public MessageBarrel(int x, int y, int z, int timer) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.timer = timer;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.timer = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
        buf.writeInt(this.timer);
    }
}
