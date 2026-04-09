package org.celestialworkshop.artifex.item.specialty;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import org.celestialworkshop.artifex.capability.AFEntityData;
import org.celestialworkshop.artifex.capability.AFEntityDataCapability;

import java.util.UUID;

public class FinesseSpecialty extends ComboBasedSpecialty {

    public static final UUID MOVEMENT_SPEED_MODIFIER_UUID = UUID.fromString("66f7556c-b7be-40da-bae2-3df032e6b1a2");

    public FinesseSpecialty(Category category) {
        super(category);
    }

    @Override
    public void onPostMelee(LivingEntity attacker, LivingEntity target, ItemStack itemStack, boolean wasCritical, int specialityLevel) {
        super.onPostMelee(attacker, target, itemStack, wasCritical, specialityLevel);
        manageSpeed(attacker);
    }

    @Override
    public void onPostRanged(LivingEntity attacker, LivingEntity target, ItemStack itemStack, Projectile ammo, boolean wasCrit, int specialityLevel) {
        super.onPostRanged(attacker, target, itemStack, ammo, wasCrit, specialityLevel);
        manageSpeed(attacker);
    }

    private static void manageSpeed(LivingEntity attacker) {
        if (attacker instanceof ServerPlayer) {
            AFEntityData entityData = AFEntityDataCapability.get(attacker).resolve().get();
            AttributeInstance attribute = attacker.getAttribute(Attributes.MOVEMENT_SPEED);
            if (attribute != null) {
                attribute.removeModifier(MOVEMENT_SPEED_MODIFIER_UUID);
                double amount = 0.01 * entityData.comboCount;
                AttributeModifier modifier = new AttributeModifier(MOVEMENT_SPEED_MODIFIER_UUID, "finesse_speed_bonus", amount, AttributeModifier.Operation.ADDITION);
                attribute.addTransientModifier(modifier);
            }
        }
    }

    @Override
    public void onComboEnd(LivingEntity attacker, ItemStack itemStack, int specialityLevel) {
        AttributeInstance attribute = attacker.getAttribute(Attributes.MOVEMENT_SPEED);
        if (attribute != null) {
            attribute.removeModifier(MOVEMENT_SPEED_MODIFIER_UUID);
        }
    }

    @Override
    public Object[] getDisplayDescriptionArgs(int level) {
        return new Object[]{
                asPercentFormat(calculateSpeedIncrement(level)),
                asPercentFormat(calculateMaxCombo(level) * calculateSpeedIncrement(level))
        };
    }

    private float calculateSpeedIncrement(int level) {
        return 0.05f + (level * 0.01f);
    }

    private int calculateMaxCombo(int level) {
        return 5 + level;
    }
}
