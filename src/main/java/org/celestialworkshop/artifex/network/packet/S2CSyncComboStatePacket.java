package org.celestialworkshop.artifex.network.packet;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.network.ClientPacketHandler;

public record S2CSyncComboStatePacket(ItemStack stack, int count, int timer) implements CustomPacketPayload {
    public static final Type<S2CSyncComboStatePacket> TYPE = new Type<>(Artifex.prefix("sync_combo_state"));

    public static final StreamCodec<RegistryFriendlyByteBuf, S2CSyncComboStatePacket> CODEC = StreamCodec.composite(
            ItemStack.OPTIONAL_STREAM_CODEC, S2CSyncComboStatePacket::stack,
            ByteBufCodecs.INT, S2CSyncComboStatePacket::count,
            ByteBufCodecs.INT, S2CSyncComboStatePacket::timer,
            S2CSyncComboStatePacket::new
    );

    public static void handle(S2CSyncComboStatePacket packet, IPayloadContext context) {
        context.enqueueWork(() -> ClientPacketHandler.handleSyncComboState(packet));
    }

    @Override
    public Type<S2CSyncComboStatePacket> type() {
        return TYPE;
    }
}
