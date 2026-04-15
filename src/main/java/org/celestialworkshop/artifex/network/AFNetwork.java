package org.celestialworkshop.artifex.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.celestialworkshop.artifex.Artifex;
import org.celestialworkshop.artifex.network.packet.S2CEntityActionPacket;
import org.celestialworkshop.artifex.network.packet.S2CSyncAmmoPacket;
import org.celestialworkshop.artifex.network.packet.S2CSyncComboStatePacket;

public class AFNetwork {
    private static final String PROTOCOL_VERSION = "1.0";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            Artifex.prefix("main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        int id = 0;

        // S2C
        INSTANCE.registerMessage(id++, S2CEntityActionPacket.class, S2CEntityActionPacket::encode, S2CEntityActionPacket::decode, S2CEntityActionPacket::handle);
        INSTANCE.registerMessage(id++, S2CSyncAmmoPacket.class, S2CSyncAmmoPacket::encode, S2CSyncAmmoPacket::decode, S2CSyncAmmoPacket::handle);
        INSTANCE.registerMessage(id++, S2CSyncComboStatePacket.class, S2CSyncComboStatePacket::encode, S2CSyncComboStatePacket::decode, S2CSyncComboStatePacket::handle);
    }

    public static void sendToAll(Object packet) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), packet);
    }

    public static void sendToTrackingEntity(Entity entity, Object packet) {
        INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), packet);
    }

    public static void sendToTrackingEntityAndSelf(Entity entity, Object packet) {
        INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), packet);
    }

    public static void sendToPlayer(ServerPlayer player, Object packet) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    public static void sendToServer(Object packet) {
        INSTANCE.sendToServer(packet);
    }
}
