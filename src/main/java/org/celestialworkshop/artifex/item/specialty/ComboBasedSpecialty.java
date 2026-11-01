package org.celestialworkshop.artifex.item.specialty;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import org.celestialworkshop.artifex.api.AFSpecialty;
import org.celestialworkshop.artifex.capability.AFEntityData;
import org.celestialworkshop.artifex.network.AFNetwork;
import org.celestialworkshop.artifex.network.packet.S2CSyncComboStatePacket;
import org.celestialworkshop.artifex.registry.AFAttachmentTypes;

public class ComboBasedSpecialty extends AFSpecialty {

    public ComboBasedSpecialty(Category category) {
        super(category);
    }

    public static void manageComboStack(LivingEntity attacker, ItemStack itemStack) {
        if (attacker instanceof ServerPlayer serverPlayer && serverPlayer.getAttackStrengthScale(0) > 0.85F) {
            AFEntityData entityData = attacker.getData(AFAttachmentTypes.ENTITY_DATA);
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
