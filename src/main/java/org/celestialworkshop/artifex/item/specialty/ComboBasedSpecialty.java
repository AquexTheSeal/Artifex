package org.celestialworkshop.artifex.item.specialty;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.capability.AFEntityData;
import org.celestialworkshop.artifex.capability.AFEntityDataCapability;
import org.celestialworkshop.artifex.network.AFNetwork;
import org.celestialworkshop.artifex.network.packet.S2CSyncComboStatePacket;

import java.util.UUID;

public class ComboBasedSpecialty extends AFSpecialty {

    public static final UUID MOVEMENT_SPEED_MODIFIER_UUID = UUID.fromString("66f7556c-b7be-40da-bae2-3df032e6b1a2");
    public static final UUID ATTACK_SPEED_MODIFIER_UUID = UUID.fromString("ee78e25b-5c1a-4cd5-8df3-4ee0dec4d723");

    public ComboBasedSpecialty(Category category) {
        super(category);
    }

    public static void manageComboStack(LivingEntity attacker, ItemStack itemStack) {
        if (attacker instanceof ServerPlayer serverPlayer && serverPlayer.getAttackStrengthScale(0) > 0.85F) {
            AFEntityData entityData = AFEntityDataCapability.get(attacker).resolve().get();
            entityData.comboItemStack = itemStack;
            entityData.comboCount = Math.min(entityData.getMaxComboCount(), entityData.comboCount + 1);

            double windowMultiplier = 1.5;
            double attackSpeed = attacker.getAttributeValue(Attributes.ATTACK_SPEED);
            entityData.maxComboTimer = (int) ((20.0 / attackSpeed) * windowMultiplier);

            entityData.comboTimer = entityData.maxComboTimer;
            AFNetwork.sendToPlayer(serverPlayer, new S2CSyncComboStatePacket(itemStack, entityData.comboCount, entityData.comboTimer));
        }
    }

    public void onComboEnd(LivingEntity attacker, ItemStack itemStack, int specialityLevel) {

    }
}
