package org.celestialworkshop.artifex.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.celestialworkshop.artifex.network.packet.*;

public class AFNetwork {

    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1.0");

        registrar.playToClient(S2CSyncAmmoPacket.TYPE, S2CSyncAmmoPacket.CODEC, S2CSyncAmmoPacket::handle);
        registrar.playToClient(S2CSyncComboStatePacket.TYPE, S2CSyncComboStatePacket.CODEC, S2CSyncComboStatePacket::handle);
        registrar.playToClient(S2CSyncIaijutsuPacket.TYPE, S2CSyncIaijutsuPacket.CODEC, S2CSyncIaijutsuPacket::handle);
        registrar.playToClient(S2CSyncSpecialtiesDataPacket.TYPE, S2CSyncSpecialtiesDataPacket.CODEC, S2CSyncSpecialtiesDataPacket::handle);

        registrar.playToServer(C2SSyncIaijutsuMovementStatePacket.TYPE, C2SSyncIaijutsuMovementStatePacket.CODEC, C2SSyncIaijutsuMovementStatePacket::handle);
    }

    public static void sendToAll(CustomPacketPayload packet) {
        PacketDistributor.sendToAllPlayers(packet);
    }

    public static void sendToTrackingEntity(Entity entity, CustomPacketPayload packet) {
        PacketDistributor.sendToPlayersTrackingEntity(entity, packet);
    }

    public static void sendToTrackingEntityAndSelf(Entity entity, CustomPacketPayload packet) {
        PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, packet);
    }

    public static void sendToPlayer(ServerPlayer player, CustomPacketPayload packet) {
        PacketDistributor.sendToPlayer(player, packet);
    }

    public static void sendToServer(CustomPacketPayload packet) {
        PacketDistributor.sendToServer(packet);
    }
}
