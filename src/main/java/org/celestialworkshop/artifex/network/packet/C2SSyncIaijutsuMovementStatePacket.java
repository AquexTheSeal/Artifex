package org.celestialworkshop.artifex.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.celestialworkshop.artifex.capability.AFEntityDataCapability;

import java.util.function.Supplier;

public record C2SSyncIaijutsuMovementStatePacket(boolean value) {

    public static void encode(C2SSyncIaijutsuMovementStatePacket packet, FriendlyByteBuf buffer) {
        buffer.writeBoolean(packet.value);
    }

    public static C2SSyncIaijutsuMovementStatePacket decode(FriendlyByteBuf buffer) {
        return new C2SSyncIaijutsuMovementStatePacket(buffer.readBoolean());
    }

    public static void handle(C2SSyncIaijutsuMovementStatePacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayer sender = context.get().getSender();
            if (sender != null) {
                AFEntityDataCapability.get(sender).ifPresent(cap -> {
                    cap.iaijutsuSpeedUp = packet.value;
                });
            }
        });
        context.get().setPacketHandled(true);
    }
}
