package org.celestialworkshop.artifex.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.celestialworkshop.artifex.capability.AFAmmoDataCapability;
import org.celestialworkshop.artifex.capability.AFEntityDataCapability;
import org.celestialworkshop.artifex.network.packet.S2CEntityActionPacket;
import org.celestialworkshop.artifex.network.packet.S2CSyncAmmoPacket;
import org.celestialworkshop.artifex.network.packet.S2CSyncComboStatePacket;

import java.util.Map;

public class ClientPacketHandler {

    public static void handleEntityAction(S2CEntityActionPacket packet) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) return;

        Map<String, Float> parameters = packet.parameters();
        switch (packet.action()) {
            case FORCE_SYNC_DELTA -> {
                Entity entity = level.getEntity(packet.id());
                if (entity != null) {
                    entity.setDeltaMovement(parameters.get("DeltaX"), parameters.get("DeltaY"), parameters.get("DeltaZ"));
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + packet.action());
        }
    }

    public static void handleSyncAmmo(S2CSyncAmmoPacket packet) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null) {
            ItemStack stack = minecraft.player.getInventory().getItem(packet.idx());
            AFAmmoDataCapability.get(stack).ifPresent(ammo -> ammo.setAmmo(packet.ammo()));
        }
    }

    public static void handleSyncComboState(S2CSyncComboStatePacket packet) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null) {
            AFEntityDataCapability.get(minecraft.player).ifPresent(cap -> {
                cap.comboItemStack = packet.stack();
                cap.comboCount = packet.count();
                cap.comboTimer = packet.timer();
            });
        }
    }
}
