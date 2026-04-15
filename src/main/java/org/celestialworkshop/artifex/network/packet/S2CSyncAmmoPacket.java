package org.celestialworkshop.artifex.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.celestialworkshop.artifex.network.ClientPacketHandler;

import java.util.function.Supplier;

public record S2CSyncAmmoPacket(int idx, int ammo) {

    public static void encode(S2CSyncAmmoPacket packet, FriendlyByteBuf buffer) {
        buffer.writeInt(packet.idx);
        buffer.writeInt(packet.ammo);
    }

    public static S2CSyncAmmoPacket decode(FriendlyByteBuf buffer) {
        return new S2CSyncAmmoPacket(buffer.readInt(), buffer.readInt());
    }

    public static void handle(S2CSyncAmmoPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handleSyncAmmo(packet));
        });
        context.get().setPacketHandled(true);
    }
}
