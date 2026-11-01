package org.celestialworkshop.artifex.network.packet;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.capability.AFEntityData;
import org.celestialworkshop.artifex.registry.AFAttachmentTypes;

public record C2SSyncIaijutsuMovementStatePacket(boolean value) implements CustomPacketPayload {
    public static final Type<C2SSyncIaijutsuMovementStatePacket> TYPE = new Type<>(Artifex.prefix("sync_iaijutsu_movement_state"));

    public static final StreamCodec<RegistryFriendlyByteBuf, C2SSyncIaijutsuMovementStatePacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, C2SSyncIaijutsuMovementStatePacket::value,
            C2SSyncIaijutsuMovementStatePacket::new
    );

    public static void handle(C2SSyncIaijutsuMovementStatePacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            AFEntityData cap = context.player().getData(AFAttachmentTypes.ENTITY_DATA);
            cap.iaijutsuSpeedUp = packet.value;
        });
    }

    @Override
    public Type<C2SSyncIaijutsuMovementStatePacket> type() {
        return TYPE;
    }
}
