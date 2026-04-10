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
    public static final UUID ATTACK_SPEED_MODIFIER_UUID = UUID.fromString("ee78e25b-5c1a-4cd5-8df3-4ee0dec4d723");

    public FinesseSpecialty(Category category) {
        super(category);
    }

    @Override
    public void onPostMelee(LivingEntity attacker, LivingEntity target, ItemStack itemStack, boolean wasCritical, int specialityLevel) {
        super.onPostMelee(attacker, target, itemStack, wasCritical, specialityLevel);
        manageSpeed(attacker, specialityLevel);
    }

    @Override
    public void onPostRanged(LivingEntity attacker, LivingEntity target, ItemStack itemStack, Projectile ammo, boolean wasCrit, int specialityLevel) {
        super.onPostRanged(attacker, target, itemStack, ammo, wasCrit, specialityLevel);
        manageSpeed(attacker, specialityLevel);
    }

    private void manageSpeed(LivingEntity attacker, int specialityLevel) {
        if (attacker instanceof ServerPlayer) {
            AFEntityData entityData = AFEntityDataCapability.get(attacker).resolve().get();

            AttributeInstance atkSpd = attacker.getAttribute(Attributes.ATTACK_SPEED);
            if (atkSpd != null) {
                atkSpd.removeModifier(ATTACK_SPEED_MODIFIER_UUID);
                double amount = this.calculateSpeedIncrement(specialityLevel) * entityData.comboCount;
                AttributeModifier modifier = new AttributeModifier(ATTACK_SPEED_MODIFIER_UUID, "finesse_attack_speed_bonus", amount, AttributeModifier.Operation.ADDITION);
                atkSpd.addTransientModifier(modifier);
            }
        }
    }

    @Override
    public void onComboEnd(LivingEntity attacker, ItemStack itemStack, int specialityLevel) {

        AttributeInstance atkSpd = attacker.getAttribute(Attributes.ATTACK_SPEED);
        if (atkSpd != null) {
            atkSpd.removeModifier(ATTACK_SPEED_MODIFIER_UUID);
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
        return level * 0.1f;
    }

    private int calculateMaxCombo(int level) {
        return 5 + level;
    }
}
