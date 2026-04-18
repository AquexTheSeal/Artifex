package org.celestialworkshop.artifex.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.celestialworkshop.artifex.network.ClientPacketHandler;

import java.util.function.Supplier;

public record S2CSyncIaijutsuPacket(int value) {

    public static void encode(S2CSyncIaijutsuPacket packet, FriendlyByteBuf buffer) {
        buffer.writeInt(packet.value);
    }

    public static S2CSyncIaijutsuPacket decode(FriendlyByteBuf buffer) {
        return new S2CSyncIaijutsuPacket(buffer.readInt());
    }

    public static void handle(S2CSyncIaijutsuPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handleSyncIaijutsu(packet));
        });
        context.get().setPacketHandled(true);
    }
}
