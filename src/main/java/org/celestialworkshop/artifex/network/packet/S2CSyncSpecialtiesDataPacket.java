package org.celestialworkshop.artifex.network.packet;

import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;
import org.celestialworkshop.artifex.api.AFSpecialtyWrapper;
import org.celestialworkshop.artifex.data.reloadlistener.AFSpecialtiesReloadListener;

import java.util.Map;
import java.util.function.Supplier;

public record S2CSyncSpecialtiesDataPacket(Map<ResourceLocation, AFSpecialtyWrapper> data) {

    @SuppressWarnings("deprecation")
    public static void encode(S2CSyncSpecialtiesDataPacket packet, FriendlyByteBuf buffer) {
        buffer.writeMap(packet.data(),
                FriendlyByteBuf::writeResourceLocation,
                (buf, list) -> buf.writeWithCodec(NbtOps.INSTANCE, AFSpecialtyWrapper.CODEC, list)
        );
    }

    public static S2CSyncSpecialtiesDataPacket decode(FriendlyByteBuf buffer) {
        Map<ResourceLocation, AFSpecialtyWrapper> data = buffer.readMap(
                FriendlyByteBuf::readResourceLocation,
                buf -> buf.readWithCodec(NbtOps.INSTANCE, AFSpecialtyWrapper.CODEC)
        );
        return new S2CSyncSpecialtiesDataPacket(data);
    }

    public static void handle(S2CSyncSpecialtiesDataPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            AFSpecialtiesReloadListener.SPECIALTY_DATA.clear();
            AFSpecialtiesReloadListener.SPECIALTY_DATA.putAll(packet.data());
        });
        context.get().setPacketHandled(true);
    }
}
