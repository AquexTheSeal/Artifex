package org.celestialworkshop.artifex.network.packet;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.network.ClientPacketHandler;

public record S2CSyncAmmoPacket(int idx, int ammo) implements CustomPacketPayload {
    public static final Type<S2CSyncAmmoPacket> TYPE = new Type<>(Artifex.prefix("sync_ammo"));

    public static final StreamCodec<RegistryFriendlyByteBuf, S2CSyncAmmoPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, S2CSyncAmmoPacket::idx,
            ByteBufCodecs.INT, S2CSyncAmmoPacket::ammo,
            S2CSyncAmmoPacket::new
    );

    public static void handle(S2CSyncAmmoPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> ClientPacketHandler.handleSyncAmmo(packet));
    }

    @Override
    public Type<S2CSyncAmmoPacket> type() {
        return TYPE;
    }
}
