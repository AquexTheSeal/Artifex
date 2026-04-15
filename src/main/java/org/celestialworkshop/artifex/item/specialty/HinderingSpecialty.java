package org.celestialworkshop.artifex.item.specialty;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import org.celestialworkshop.artifex.capability.AFEntityData;
import org.celestialworkshop.artifex.capability.AFEntityDataCapability;

public class HinderingSpecialty extends ComboBasedSpecialty {
    public HinderingSpecialty(Category category) {
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
                double amount = this.calculateSpeedDecrement(specialityLevel) * entityData.comboCount;
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
                asPercentFormat(Mth.abs(calculateSpeedDecrement(level)))
        };
    }

    private float calculateSpeedDecrement(int level) {
        return level * -0.05f;
    }
}
