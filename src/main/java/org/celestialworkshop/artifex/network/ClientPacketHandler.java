package org.celestialworkshop.artifex.network;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import org.celestialworkshop.artifex.capability.AFAmmoDataCapability;
import org.celestialworkshop.artifex.capability.AFEntityDataCapability;
import org.celestialworkshop.artifex.network.packet.S2CSyncAmmoPacket;
import org.celestialworkshop.artifex.network.packet.S2CSyncComboStatePacket;
import org.celestialworkshop.artifex.network.packet.S2CSyncIaijutsuPacket;

public class ClientPacketHandler {

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

    public static void handleSyncIaijutsu(S2CSyncIaijutsuPacket packet) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null) {
            AFEntityDataCapability.get(minecraft.player).ifPresent(cap -> {
                cap.iaijutsuTimer = packet.value();
            });
        }
    }
}
