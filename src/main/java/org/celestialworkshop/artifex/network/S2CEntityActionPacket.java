package org.celestialworkshop.artifex.network;

import it.unimi.dsi.fastutil.objects.Object2FloatArrayMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public record S2CEntityActionPacket(int id, Action action, Map<String, Float> parameters) {

    public static void encode(S2CEntityActionPacket packet, FriendlyByteBuf buffer) {
        buffer.writeInt(packet.id);
        buffer.writeEnum(packet.action);
        packet.action.getEncoder().accept(packet, buffer);
    }

    public static S2CEntityActionPacket decode(FriendlyByteBuf buffer) {
        int id = buffer.readInt();
        Action action = buffer.readEnum(Action.class);
        Map<String, Float> params = new Object2FloatArrayMap<>();
        action.getDecoder().accept(params, buffer);
        return new S2CEntityActionPacket(id, action, params);
    }

    public static void handle(S2CEntityActionPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ClientLevel level = Minecraft.getInstance().level;
            Map<String, Float> parameters = packet.parameters();
            switch (packet.action()) {
                case FORCE_SYNC_DELTA -> {
                    Entity entity = level.getEntity(packet.id());
                    if (entity != null) {
                        entity.setDeltaMovement(
                                parameters.get("DeltaX"),
                                parameters.get("DeltaY"),
                                parameters.get("DeltaZ")
                        );
                    }
                }
                default -> throw new IllegalStateException("Unexpected value: " + packet.action());
            }
        });
        context.get().setPacketHandled(true);
    }

    // Minor system to prevent overhead and big big packets (Writing and reading maps are heavy!)
    public enum Action {
        FORCE_SYNC_DELTA((packet, buffer) -> {
            buffer.writeFloat(packet.parameters.getOrDefault("DeltaX", 0f));
            buffer.writeFloat(packet.parameters.getOrDefault("DeltaY", 0f));
            buffer.writeFloat(packet.parameters.getOrDefault("DeltaZ", 0f));
        }, (params, buffer) -> {
            params.put("DeltaX", buffer.readFloat());
            params.put("DeltaY", buffer.readFloat());
            params.put("DeltaZ", buffer.readFloat());
        });

        public final BiConsumer<S2CEntityActionPacket, FriendlyByteBuf> encoder;
        public final BiConsumer<Map<String, Float>, FriendlyByteBuf> decoder;

        Action(BiConsumer<S2CEntityActionPacket, FriendlyByteBuf> encoder, BiConsumer<Map<String, Float>, FriendlyByteBuf> decoder) {
            this.encoder = encoder;
            this.decoder = decoder;
        }

        public BiConsumer<S2CEntityActionPacket, FriendlyByteBuf> getEncoder() {
            return encoder;
        }

        public BiConsumer<Map<String, Float>, FriendlyByteBuf> getDecoder() {
            return decoder;
        }
    }
}
