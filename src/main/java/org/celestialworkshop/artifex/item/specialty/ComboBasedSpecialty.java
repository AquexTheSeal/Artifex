package org.celestialworkshop.artifex.item.specialty;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.capability.AFEntityData;
import org.celestialworkshop.artifex.capability.AFEntityDataCapability;
import org.celestialworkshop.artifex.network.AFNetwork;
import org.celestialworkshop.artifex.network.S2CSyncComboStatePacket;

public class ComboBasedSpecialty extends AFSpecialty {

    public ComboBasedSpecialty(Category category) {
        super(category);
    }

    public static void manageComboStack(LivingEntity attacker, ItemStack itemStack) {
        if (attacker instanceof ServerPlayer serverPlayer) {
            AFEntityData entityData = AFEntityDataCapability.get(attacker).resolve().get();
            entityData.incrementComboCount(itemStack);
            AFNetwork.sendToPlayer(serverPlayer, new S2CSyncComboStatePacket(itemStack, entityData.comboCount, entityData.comboTimer));
        }
    }

    public void onComboEnd(LivingEntity attacker, ItemStack itemStack, int specialityLevel) {

    }
}
