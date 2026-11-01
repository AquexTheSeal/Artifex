package org.celestialworkshop.artifex.network.packet;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.network.ClientPacketHandler;

public record S2CSyncIaijutsuPacket(ItemStack stack, int timer, boolean speedState) implements CustomPacketPayload {
    public static final Type<S2CSyncIaijutsuPacket> TYPE = new Type<>(Artifex.prefix("sync_iaijutsu"));

    public static final StreamCodec<RegistryFriendlyByteBuf, S2CSyncIaijutsuPacket> CODEC = StreamCodec.composite(
            ItemStack.OPTIONAL_STREAM_CODEC, S2CSyncIaijutsuPacket::stack,
            ByteBufCodecs.INT, S2CSyncIaijutsuPacket::timer,
            ByteBufCodecs.BOOL, S2CSyncIaijutsuPacket::speedState,
            S2CSyncIaijutsuPacket::new
    );

    public static void handle(S2CSyncIaijutsuPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> ClientPacketHandler.handleSyncIaijutsu(packet));
    }

    @Override
    public Type<S2CSyncIaijutsuPacket> type() {
        return TYPE;
    }
}
