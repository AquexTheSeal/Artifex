package org.celestialworkshop.artifex.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import org.celestialworkshop.artifex.capability.AFAmmoDataCapability;

import java.util.function.Supplier;

public record S2CSyncAmmoPacket(int idx, int ammo) {

    public static void encode(S2CSyncAmmoPacket packet, FriendlyByteBuf buffer) {
        buffer.writeInt(packet.idx);
        buffer.writeInt(packet.ammo);
    }

    public static S2CSyncAmmoPacket decode(FriendlyByteBuf buffer) {
        return new S2CSyncAmmoPacket(buffer.readInt(), buffer.readInt());
    }

    public static void handle(S2CSyncAmmoPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.player != null) {
                ItemStack stack = minecraft.player.getInventory().getItem(packet.idx);
                AFAmmoDataCapability.get(stack).ifPresent(ammo -> ammo.setAmmo(packet.ammo));
            }
        });
        context.get().setPacketHandled(true);
    }
}
