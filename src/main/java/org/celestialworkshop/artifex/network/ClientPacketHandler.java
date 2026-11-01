package org.celestialworkshop.artifex.network;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import org.celestialworkshop.artifex.capability.AFEntityData;
import org.celestialworkshop.artifex.network.packet.S2CSyncAmmoPacket;
import org.celestialworkshop.artifex.network.packet.S2CSyncComboStatePacket;
import org.celestialworkshop.artifex.network.packet.S2CSyncIaijutsuPacket;
import org.celestialworkshop.artifex.registry.AFAttachmentTypes;
import org.celestialworkshop.artifex.util.ItemStackUtil;

public class ClientPacketHandler {

    public static void handleSyncAmmo(S2CSyncAmmoPacket packet) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null) {
            ItemStack stack = minecraft.player.getInventory().getItem(packet.idx());
            ItemStackUtil.setAmmo(stack, packet.ammo());
        }
    }

    public static void handleSyncComboState(S2CSyncComboStatePacket packet) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null) {
            AFEntityData cap = minecraft.player.getData(AFAttachmentTypes.ENTITY_DATA);
            cap.comboItemStack = packet.stack();
            cap.comboCount = packet.count();
            cap.maxComboTimer = packet.timer();
            cap.comboTimer = cap.maxComboTimer;
        }
    }

    public static void handleSyncIaijutsu(S2CSyncIaijutsuPacket packet) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null) {
            AFEntityData cap = minecraft.player.getData(AFAttachmentTypes.ENTITY_DATA);
            cap.iaijutsuItemStack = packet.stack();
            cap.iaijutsuTimer = packet.timer();
            cap.iaijutsuSpeedUp = packet.speedState();
        }
    }
}
