package com.unseen.helios.message;

import com.unseen.helios.capability.CapabilityPlayerGreed;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketGreedCapability implements IMessage {
    private int entityId;
    private boolean enableGreed;

    public PacketGreedCapability() {}

    public PacketGreedCapability(int entityId, boolean enableGreedIn)
    {
        this.entityId = entityId;
        this.enableGreed = enableGreedIn;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        entityId = ByteBufUtils.readVarInt(buf, 5);
        enableGreed = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeVarInt(buf, entityId, 5);
        buf.writeBoolean(enableGreed);
    }

    public static class Handler implements IMessageHandler<PacketGreedCapability, IMessage>
    {
        @Override
        public IMessage onMessage(PacketGreedCapability message, MessageContext ctx)
        {
            Minecraft.getMinecraft().addScheduledTask(() ->
                    {
                        Entity player = Minecraft.getMinecraft().world.getEntityByID(message.entityId);

                        if(player.hasCapability(CapabilityPlayerGreed.PLAYER_GREED_CAP, null))
                        {
                            player.getCapability(CapabilityPlayerGreed.PLAYER_GREED_CAP, null).setGreedEnabled(message.enableGreed);
                        }
                    }
            );
            return null;
        }
    }
}
