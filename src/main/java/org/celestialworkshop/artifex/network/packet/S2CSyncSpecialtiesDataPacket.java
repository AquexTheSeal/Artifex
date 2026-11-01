package org.celestialworkshop.artifex.network.packet;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.api.AFSpecialtyWrapper;
import org.celestialworkshop.artifex.data.reloadlistener.AFSpecialtiesReloadListener;

import java.util.Map;

public record S2CSyncSpecialtiesDataPacket(Map<ResourceLocation, AFSpecialtyWrapper> data) implements CustomPacketPayload {
    public static final Type<S2CSyncSpecialtiesDataPacket> TYPE = new Type<>(Artifex.prefix("sync_specialties_data"));

    public static final StreamCodec<RegistryFriendlyByteBuf, S2CSyncSpecialtiesDataPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.map(Object2ObjectOpenHashMap::new, ResourceLocation.STREAM_CODEC, ByteBufCodecs.fromCodec(AFSpecialtyWrapper.CODEC)), S2CSyncSpecialtiesDataPacket::data,
            S2CSyncSpecialtiesDataPacket::new
    );

    public static void handle(S2CSyncSpecialtiesDataPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            AFSpecialtiesReloadListener.SPECIALTY_DATA.clear();
            AFSpecialtiesReloadListener.SPECIALTY_DATA.putAll(packet.data());
            AFSpecialtiesReloadListener.SPECIALTY_CACHE.clear();
        });
    }

    @Override
    public Type<S2CSyncSpecialtiesDataPacket> type() {
        return TYPE;
    }
}
