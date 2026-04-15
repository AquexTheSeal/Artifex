package org.celestialworkshop.artifex.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record S2CSyncComboStatePacket(ItemStack stack, int count, int timer) {

    public static void encode(S2CSyncComboStatePacket packet, FriendlyByteBuf buffer) {
        buffer.writeItem(packet.stack);
        buffer.writeInt(packet.count);
        buffer.writeInt(packet.timer);
    }

    public static S2CSyncComboStatePacket decode(FriendlyByteBuf buffer) {
        return new S2CSyncComboStatePacket(buffer.readItem(), buffer.readInt(), buffer.readInt());
    }

    public static void handle(S2CSyncComboStatePacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handleSyncComboState(packet));
        });
        context.get().setPacketHandled(true);
    }
}
